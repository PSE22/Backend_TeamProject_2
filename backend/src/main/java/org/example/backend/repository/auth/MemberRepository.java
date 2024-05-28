package org.example.backend.repository.auth;

import org.example.backend.model.entity.auth.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * packageName : org.example.backend.repository.auth
 * fileName : MemberRepository
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
@Repository
public interface MemberRepository extends JpaRepository<Member, String> {
    @Query(value = "SELECT *\n" +
            "FROM TB_MEMBER\n" +
            "WHERE MEMBER_ID = :memberId\n" +
            "AND MEMBER_EMAIL = :memberEmail"
    , nativeQuery = true)
    Optional<Member> findByMemberIdAndMemberEmail(@Param("memberId") String memberId, @Param("memberEmail") String memberEmail);
}
