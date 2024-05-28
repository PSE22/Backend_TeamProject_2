package org.example.backend.repository.board;

import org.apache.kafka.common.protocol.types.Field;
import org.example.backend.model.entity.board.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * packageName : org.example.backend.repository.board
 * fileName : VoteRepository
 * author : kimtaewan
 * date : 2024-05-28
 * description :
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-05-28         kimtaewan          최초 생성
 */
public interface VoteRepository extends JpaRepository<Vote, Long> {
    @Query(value = "UPDATE TB_VOTE\n" +
            "UPDATE TB_VOTE\n" +
            "SET vote_cnt = vote_cnt + 1\n" +
            "WHERE voteId = :voteId\n" +
            "AND NOT EXISTS (\n" +
            "SELECT 1\n" +
            "FROM TB_VOTE_MEMBER\n" +
            "WHERE boardId = :boardId\n" +
            "AND memberId = :memberId\n" +
            ")", nativeQuery = true)
    void updateVoteCnt(@Param("voteId") Long voteId, @Param("memberId") String memberId, @Param("boardId") Long boardId);


}