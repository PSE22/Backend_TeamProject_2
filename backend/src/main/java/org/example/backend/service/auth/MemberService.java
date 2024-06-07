package org.example.backend.service.auth;

import org.example.backend.model.entity.auth.Member;
import org.example.backend.repository.auth.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    @Autowired
    PasswordEncoder passwordEncoder;

    //  1) 회원 유무 확인 함수 : 회원가입
    public boolean existById(String memberId) {
        boolean result = memberRepository.existsById(memberId);

        return result;
    }
    //  2) 저장/수정 : 회원가입/회원정보 변경
    public Member insert(Member member) {
        Member member2 = memberRepository.save(member);

        return member2;
    }
    //  3) 수정 : 비밀번호 변경
    public Member update(Member member) {
        member.setMemberPw(passwordEncoder.encode(member.getMemberPw()));
        Member member2 = memberRepository.save(member);

        return member2;
    }

    public Optional<Member> findById(String memberId) {
        return memberRepository.findById(memberId);
    }

    //  4) 회원 상세 조회
    public Optional<Member> findByMemberId(String memberId) {
        Optional<Member> optionalMember = memberRepository.findByMemberId(memberId);
        return optionalMember;
    }

    //  5) email 로 회원 상세 조회 후 id 찾기
    public Optional<Member> findByMemberEmail(String memberId) {
        Optional<Member> optionalMember = memberRepository.findByMemberEmail(memberId);
        return optionalMember;
    }

    //  6) 신규 회원 전체 조회
    public List<Member> findAllByMemberCode(String memberCode) {
        List<Member> memberList = memberRepository.findAllByMemberCode(memberCode);
        return memberList;
    }

    //  7) 기존 회원 전체 조회(검색)
    public Page<Member> findAllByMemberName(String memberName, Pageable pageable) {
        Page<Member> memberList = memberRepository.findAllByMemberName(memberName, pageable);
        return memberList;
    }

    //  8) 기존 회원 부서별 전체 조회
    public Page<Member> findAllByMemberCodeAndDeptCode(String memberCode, String deptCode, Pageable pageable) {
        Page<Member> memberList = memberRepository.findAllByMemberCodeAndDeptCode(memberCode, deptCode, pageable);
        return memberList;
    }

    //  9) 회원 삭제 (soft delete)
    public boolean removeById(String memberId) {
        if (memberRepository.existsById(memberId) == true) {
            memberRepository.deleteById(memberId);
            return true;
        } else {
            return false;
        }
    }

    //  10) 회원 삭제 (hard delete)
    public void delMember(String memberId) {
        memberRepository.deleteMember(memberId);
    }
}
