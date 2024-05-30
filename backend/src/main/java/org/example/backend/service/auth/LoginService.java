package org.example.backend.service.auth;

import org.example.backend.security.jwt.JwtUtils;
import org.example.backend.service.dto.LoginRequest;
import org.example.backend.service.dto.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * packageName : org.example.backend.service.auth
 * fileName : LoginService
 * author : kimtaewan
 * date : 2024-05-29
 * description :
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-05-29         kimtaewan          최초 생성
 */
@Service
public class LoginService {
    @Autowired
    AuthenticationManagerBuilder authenticationManagerBuilder;
    @Autowired
    JwtUtils jwtUtils;

    public LoginResponse authenticate(LoginRequest loginRequest) throws BadCredentialsException {
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getMemberId(), loginRequest.getMemberPw())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        String memberCode = new ArrayList<>(authentication.getAuthorities()).get(0).toString();
        String deptCode = new ArrayList<>(authentication.getAuthorities()).get(1).toString();

        return new LoginResponse(jwt, loginRequest.getMemberId(), memberCode, deptCode);
    }
}