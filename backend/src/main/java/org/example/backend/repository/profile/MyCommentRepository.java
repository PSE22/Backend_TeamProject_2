package org.example.backend.repository.profile;

import org.example.backend.model.entity.board.Reply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * packageName : org.example.backend.repository.profile
 * fileName : MyCommentRepository
 * author : sjuk2
 * date : 2024-06-10
 * description :
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-06-10         sjuk2          최초 생성
 */
@Repository
public interface MyCommentRepository extends JpaRepository<Reply, Long> {
    @Query(value = "SELECT *\n" +
            "FROM TB_REPLY\n" +
            "WHERE MEMBER_ID = :memberId\n" +
            "ORDER BY ADD_DATE DESC"
            , nativeQuery = true)
    Page<Reply> findByMemberIdOfComment(@Param("memberId") String memberId, Pageable pageable);
}
