package org.example.backend.repository.auth;

import org.example.backend.model.entity.auth.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
            "WHERE MEMBER_ID = :memberId\n"
    , nativeQuery = true)
    Optional<Member> findByMemberId(@Param("memberId") String memberId);

    @Query(value = "SELECT *\n" +
            "FROM TB_MEMBER\n" +
            "WHERE MEMBER_CODE = :memberCode\n" +
            "AND STATUS = 'Y'\n" +
            "ORDER BY ADD_DATE"
    , nativeQuery = true)
    List<Member> findAllByMemberCode(@Param("memberCode") String memberCode);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM TB_MEMBER\n" +
            "WHERE MEMBER_Id = :memberId"
    , nativeQuery = true)
    void deleteMember(@Param("memberId") String memberId);
}
