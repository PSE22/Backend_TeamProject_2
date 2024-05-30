package org.example.backend.repository.board;

import org.example.backend.model.dto.board.IReplyDto;
import org.example.backend.model.entity.board.Board;
import org.example.backend.model.entity.board.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * packageName : org.example.backend.repository.board
 * fileName : ReplyRepository
 * author : kimtaewan
 * date : 2024-05-23
 * description :
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-05-23         kimtaewan          최초 생성
 */
@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long> {
    Long countByBoardId(Long boardId);

    @Query(value = "SELECT R.BOARD_ID AS boardId, \n" +
            "        R.REPLY_ID AS replyId,\n" +
            "        R.MEMBER_ID AS memberId,\n" +
            "        R.RE_REPLY AS reReplyId,\n" +
            "        R.REPLY AS reply,\n" +
            "        R.ADD_DATE AS addDate,\n" +
            "        F.FILE_URL AS fileUrl\n" +
            "FROM TB_REPLY R, TB_FILE F, TB_REPLY_FILE RF\n" +
            "WHERE R.REPLY_ID = RF.REPLY_ID(+)\n" +
            "AND F.UUID(+) = RF.UUID\n" +
            "AND R.BOARD_ID = :boardId\n" +
            "AND R.STATUS = 'Y'"
            , nativeQuery = true)
    List<IReplyDto> findByBoardId(@Param("boardId") Long boardId);
}
