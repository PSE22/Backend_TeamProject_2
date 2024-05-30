package org.example.backend.repository.board;

import org.example.backend.model.dto.board.IBoardDetailDto;
import org.example.backend.model.dto.board.IBoardDto;
import org.example.backend.model.entity.CmCode;
import org.example.backend.model.entity.board.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * packageName : org.example.backend.repository.board
 * fileName : BoardRepository
 * author : SAMSUNG
 * date : 2024-05-23
 * description :
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-05-23         SAMSUNG          최초 생성
 */
@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
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

    // 게시판 ID로 이미지(들) 조회
    @Query(value = "SELECT BF.BOARD_ID AS boardId,\n" +
            "              F.UUID AS uuid,\n" +
            "              F.FILE_URL AS fileUrl\n" +
            "        FROM TB_BOARD_FILE BF, TB_FILE F\n" +
            "        WHERE BF.UUID = F.UUID\n" +
            "        AND BF.BOARD_ID = :boardId"
                    , nativeQuery = true)
    List<IBoardDetailDto> findBoardImg(@Param("boardId") Long boardId);
}
