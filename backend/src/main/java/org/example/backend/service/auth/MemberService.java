package org.example.backend.service.auth;

import org.example.backend.model.entity.auth.Member;
import org.example.backend.repository.auth.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * packageName : org.example.backend.service.auth
 * fileName : MemberService
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
@Service
public class MemberService {
    @Autowired
    MemberRepository memberRepository; // DI

    //    1) 회원 유무 확인 함수 : 회원가입
    public boolean existById(String memberId) {
        boolean result = memberRepository.existsById(memberId);

        return result;
    }
    //    2) 저장 : 회원가입
    public Member save(Member member) {
        Member member2 = memberRepository.save(member);

        return member2;
    }
}