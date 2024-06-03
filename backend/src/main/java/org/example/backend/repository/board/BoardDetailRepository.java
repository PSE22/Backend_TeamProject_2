package org.example.backend.repository.board;

import org.example.backend.model.dto.board.IBoardDetailDto;
import org.example.backend.model.dto.board.IBoardDto;
import org.example.backend.model.dto.board.IReplyDto;
import org.example.backend.model.dto.board.IUserDto;
import org.example.backend.model.entity.board.Board;
import org.example.backend.model.entity.board.Place;
import org.example.backend.model.entity.board.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * packageName : org.example.backend.repository.board
 * fileName : BoardDetailRepository
 * author : SAMSUNG
 * date : 2024-05-31
 * description :
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-05-31         SAMSUNG          최초 생성
 */
@Repository
public interface BoardDetailRepository extends JpaRepository<Board, Long> {
    // 로그인된 회원 정보 조회
    @Query(value = "SELECT MEMBER_ID AS memberId,\n" +
            "MEMBER_NAME AS memberName,\n" +
            "NICKNAME AS nickname,\n" +
            "MEMBER_CODE AS memberCode,\n" +
            "DEPT_CODE AS deptCode,\n" +
            "POS_CODE AS posCode\n" +
            "FROM TB_MEMBER\n" +
            "WHERE MEMBER_ID = :memberId"
            , nativeQuery = true)
    Optional<IUserDto> findMember(@Param("memberId") String memberId);

    // 게시글, 작성자 정보 조회
    @Query(value = "SELECT M.MEMBER_ID AS memberId," +
            "M.MEMBER_NAME AS memberName,\n" +
            "M.NICKNAME AS nickname,\n" +
            "B.BOARD_ID AS boardId,\n" +
            "B.BOARD_TITLE AS boardTitle,\n" +
            "B.BOARD_CONTENT AS boardContent,\n" +
            "B.GOOD AS good,\n" +
            "B.BOCODE AS bocode,\n" +
            "B.SMCODE AS smcode,\n" +
            "B.ADD_DATE AS addDate\n" +
            "FROM TB_MEMBER M, TB_BOARD B\n" +
            "WHERE M.MEMBER_ID = B.MEMBER_ID\n" +
            "AND B.BOARD_ID = :boardId\n" +
            "AND B.STATUS = 'Y'"
            , nativeQuery = true)
    Optional<IBoardDto> findBoardAndMember(@Param("boardId")Long boardId);

    // 코드번호로 코드명 조회
    @Query(value = "SELECT c.CM_CD AS cmCd, " +
            "     c.CM_CD_NAME AS cmCdName " +
            "FROM TB_CM_CODE c WHERE c.CM_CD = :cmCd",
            nativeQuery = true)
    Optional<IBoardDetailDto> findCmCdName(@Param("cmCd") String cmCd);

    // 글번호로 투표 조회
    @Query(value = "SELECT *\n" +
            "FROM TB_VOTE\n" +
            "WHERE BOARD_ID = :boardId AND STATUS = 'Y'"
            , nativeQuery = true)
    List<Vote> findVote(@Param("boardId") Long boardId);

    // 글번호로 장소 조회 (게시글 하나당 장소 하나)
    @Query(value = "SELECT *\n" +
            "FROM TB_PLACE\n" +
            "WHERE BOARD_ID = :boardId"
            , nativeQuery = true)
    Optional<Place> findPlace(@Param("boardId") Long boardId);

    // 글번호로 이미지 조회
    @Query(value = "SELECT BF.BOARD_ID AS boardId,\n" +
            "              F.UUID AS uuid,\n" +
            "              F.FILE_URL AS fileUrl\n" +
            "        FROM TB_BOARD_FILE BF, TB_FILE F\n" +
            "        WHERE BF.UUID = F.UUID\n" +
            "        AND BF.BOARD_ID = :boardId"
            , nativeQuery = true)
    List<IBoardDetailDto> findBoardImg(@Param("boardId") Long boardId);

    // 글번호로 댓글 조회
    @Query(value = "SELECT R.BOARD_ID AS boardId,\n" +
            "        R.REPLY_ID AS replyId,\n" +
            "        R.MEMBER_ID AS memberId,\n" +
            "        M.MEMBER_NAME AS memberName,\n" +
            "        M.NICKNAME AS nickname,\n" +
            "        R.RE_REPLY AS reReplyId,\n" +
            "        R.REPLY AS reply,\n" +
            "        R.ADD_DATE AS addDate,\n" +
            "        F.FILE_URL AS fileUrl\n" +
            "FROM TB_REPLY R, TB_FILE F, TB_REPLY_FILE RF, TB_MEMBER M\n" +
            "WHERE R.REPLY_ID = RF.REPLY_ID(+)\n" +
            "AND F.UUID(+) = RF.UUID\n" +
            "AND R.MEMBER_ID = M.MEMBER_ID\n" +
            "AND R.BOARD_ID = :boardId\n" +
            "AND R.RE_REPLY IS NULL\n" +
            "AND R.STATUS = 'Y'"
            , nativeQuery = true)
    List<IReplyDto> findReply(@Param("boardId") Long boardId);

    // 댓글 수 조회
    @Query(value = "SELECT R.BOARD_ID AS boardId,\n" +
            "        R.REPLY_ID AS replyId,\n" +
            "        R.MEMBER_ID AS memberId,\n" +
            "        M.MEMBER_NAME AS memberName,\n" +
            "        M.NICKNAME AS nickname,\n" +
            "        R.RE_REPLY AS reReplyId,\n" +
            "        R.REPLY AS reply,\n" +
            "        R.ADD_DATE AS addDate,\n" +
            "        F.FILE_URL AS fileUrl\n" +
            "FROM TB_REPLY R, TB_FILE F, TB_REPLY_FILE RF, TB_MEMBER M\n" +
            "WHERE R.REPLY_ID = RF.REPLY_ID(+)\n" +
            "AND F.UUID(+) = RF.UUID\n" +
            "AND R.MEMBER_ID = M.MEMBER_ID\n" +
            "AND R.BOARD_ID = :boardId\n" +
            "AND R.RE_REPLY IS NULL\n" +
            "AND R.STATUS = 'Y'"
            , nativeQuery = true)
    Integer countReply(@Param("boardId") Long boardId);
}