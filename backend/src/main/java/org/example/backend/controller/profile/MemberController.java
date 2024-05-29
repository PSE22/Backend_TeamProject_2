package org.example.backend.controller.profile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.model.entity.auth.Member;
import org.example.backend.service.auth.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * packageName : org.example.backend.controller.profile
 * fileName : MemberController
 * author : sjuk2
 * date : 2024-05-29
 * description :
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-05-29         sjuk2          최초 생성
 */
@Slf4j
@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    //  회원 상세 조회
    @GetMapping("/profile/{memberId}")
    public ResponseEntity<Object> findByMemberId(@PathVariable String memberId) {
        try {
            Optional<Member> optionalMember = memberService.findByMemberId(memberId);
            if (optionalMember.isEmpty() == true) {
                // 데이터 없음
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                // 조회 성공
                return new ResponseEntity<>(optionalMember.get(), HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //  회원 정보 수정
    @PutMapping("/profile-edit/{memberId}")
    public ResponseEntity<Object> update(
            @PathVariable String memberId,
            @RequestBody Member member
    ) {
        log.debug("11"+ member);
        try {
            Member member2 = memberService.save(member); // 수정
            return new ResponseEntity<>(member2, HttpStatus.OK);
        } catch (Exception e) {
//            DB 에러 (서버 에러) -> 500 신호(INTERNAL_SERVER_ERROR)
            log.debug("확인 ::" + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
