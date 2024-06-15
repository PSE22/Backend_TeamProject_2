package org.example.backend.repository.board;

import org.example.backend.model.dto.board.IReplyDto;
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

    // 글번호로 댓글 조회
    @Query(value = "SELECT R.BOARD_ID AS boardId,\n" +
            "        R.REPLY_ID AS replyId,\n" +
            "        R.MEMBER_ID AS memberId,\n" +
            "        M.MEMBER_NAME AS memberName,\n" +
            "        M.NICKNAME AS nickname,\n" +
            "        R.RE_REPLY AS reReplyId,\n" +
            "        R.REPLY AS reply,\n" +
            "        R.ADD_DATE AS addDate,\n" +
            "        F.FILE_URL AS fileUrl,\n" +
            "        F.FILE_NAME AS fileName\n" +
            "FROM TB_REPLY R, TB_FILE F, TB_REPLY_FILE RF, TB_MEMBER M\n" +
            "WHERE R.REPLY_ID = RF.REPLY_ID(+)\n" +
            "AND F.UUID(+) = RF.UUID\n" +
            "AND R.MEMBER_ID = M.MEMBER_ID\n" +
            "AND R.BOARD_ID = :boardId\n" +
            "AND R.RE_REPLY IS NULL\n" +
            "AND R.STATUS = 'Y'\n" +
            "ORDER BY R.ADD_DATE ASC"
            , nativeQuery = true)
    List<IReplyDto> findReply(@Param("boardId") Long boardId);

    // 대댓글 조회
    @Query(value = "SELECT R.BOARD_ID AS boardId,\n" +
            "        R.REPLY_ID AS replyId,\n" +
            "        R.MEMBER_ID AS memberId,\n" +
            "        M.MEMBER_NAME AS memberName,\n" +
            "        M.NICKNAME AS nickname,\n" +
            "        R.RE_REPLY AS reReplyId,\n" +
            "        R.REPLY AS reply,\n" +
            "        R.ADD_DATE AS addDate,\n" +
            "        F.FILE_URL AS fileUrl,\n" +
            "        F.FILE_NAME AS fileName\n" +
            "FROM TB_REPLY R, TB_FILE F, TB_REPLY_FILE RF, TB_MEMBER M\n" +
            "WHERE R.REPLY_ID = RF.REPLY_ID(+)\n" +
            "AND F.UUID(+) = RF.UUID\n" +
            "AND R.MEMBER_ID = M.MEMBER_ID\n" +
            "AND R.BOARD_ID = :boardId\n" +
            "AND R.RE_REPLY = :replyId\n" +
            "AND R.STATUS = 'Y'\n" +
            "ORDER BY R.ADD_DATE ASC"
            , nativeQuery = true)
    List<IReplyDto> findReReply(@Param("boardId") Long boardId, @Param("replyId") Long replyId);

    // 댓글 수 조회
    @Query(value = "SELECT count(*)" +
            "FROM TB_REPLY\n" +
            "WHERE BOARD_ID = :boardId\n" +
            "AND STATUS = 'Y'"
            , nativeQuery = true)
    Integer countReply(@Param("boardId") Long boardId);
}
