package org.example.backend.controller.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.model.entity.auth.Member;
import org.example.backend.security.jwt.JwtUtils;
import org.example.backend.service.auth.MemberService;
import org.example.backend.service.dto.LoginRequest;
import org.example.backend.service.dto.LoginResponse;
import org.example.backend.service.dto.SignUpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

/**
 * packageName : org.example.backend.controller.auth
 * fileName : AuthController
 * author : sjuk2
 * date : 2024-05-22
 * description :
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-05-22         sjuk2          최초 생성
 */
@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final MemberService memberService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    AuthenticationManagerBuilder authenticationManagerBuilder;


    @PostMapping("/login")
    public ResponseEntity<Object> login(
            @RequestBody LoginRequest loginRequest
    ) {
        try {
            Authentication authentication
                    = authenticationManagerBuilder.getObject().authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getMemberId(), loginRequest.getMemberPw())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);
            String memberCode = new ArrayList(authentication.getAuthorities()).get(0).toString();
            String deptCode = new ArrayList(authentication.getAuthorities()).get(1).toString();

            LoginResponse loginResponse = new LoginResponse(
                    jwt,
                    loginRequest.getMemberId(),
                    memberCode,
                    deptCode
            );
            return new ResponseEntity<>(loginResponse, HttpStatus.OK);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("ID 또는 비밀번호가 일치하지 않습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping("/register/{memberId}")
    public ResponseEntity<Object> reId(@RequestParam String memberId) {
        try {
            if(memberService.existById(memberId)) {
                return ResponseEntity.badRequest().body("이미 가입된 회원입니다.");
            } else {
                return ResponseEntity.ok("사용 가능한 ID 입니다.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PostMapping("/register")
    public ResponseEntity<Object> signUp(@RequestBody SignUpRequest signUpRequest) {
        try {
            if(memberService.existById(signUpRequest.getMemberId())) {
                return ResponseEntity.badRequest().body("이미 가입된 회원입니다.");
            }
            Member member = new Member(
                    signUpRequest.getMemberId(),
                    passwordEncoder.encode(signUpRequest.getMemberPw()),
                    signUpRequest.getMemberName(),
                    signUpRequest.getMemberEmail(),
                    signUpRequest.getMemberExt(),
                    signUpRequest.getNickname(),
                    signUpRequest.getMemberCode(),
                    signUpRequest.getDeptCode(),
                    signUpRequest.getPosCode()

            );
            memberService.save(member);
            return ResponseEntity.ok("회원가입이 완료되었습니다.");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
