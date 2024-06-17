package org.example.backend.repository.board;

import org.example.backend.model.dto.MainPageSearchDto;
import org.example.backend.model.entity.board.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * packageName : org.example.backend.repository.board
 * fileName : MainSearchRepository
 * author : GGG
 * date : 2024-05-28
 * description :
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-05-28         GGG          최초 생성
 */
@Repository
public interface MainSearchRepository extends JpaRepository<Board, Long> {
    @Query(value = "SELECT \n" +
            "B.BOARD_ID AS boardId,\n" +
            "B.BOARD_TITLE AS boardTitle, \n" +
            "B.ADD_DATE AS addDate, \n" +
            "B.BOCODE AS boCode, \n" +
            "B.SMCODE AS smCode, \n" +
            "M.NICKNAME AS nickName, \n" +
            "C.CM_CD AS cmCode, \n" +
            "C.CM_CD_NAME AS cmCodeName,\n" +
            "B.GOOD AS good, \n" +
            "NVL(R.total_reply_count, 0) AS totalReplyCount\n" +
            "FROM TB_BOARD B\n" +
            "JOIN TB_MEMBER M ON B.MEMBER_ID = M.MEMBER_ID\n" +
            "JOIN TB_CM_CODE C ON B.BOCODE = C.CM_CD\n" +
            "LEFT JOIN (SELECT BOARD_ID, COUNT(*) AS total_reply_count\n" +
            "           FROM TB_REPLY\n" +
            "           WHERE STATUS = 'Y'\n" +
            "           GROUP BY BOARD_ID) R ON B.BOARD_ID = R.BOARD_ID\n" +
            "WHERE B.BOCODE <> 'BO01'\n" +
            "AND B.STATUS = 'Y'\n" +
            "AND B.NOTICE_YN = 'N'\n" +
            "AND B.BOARD_TITLE LIKE '%' || :boardtitle || '%'\n" +
            "AND B.BOARD_TITLE NOT IN (SELECT BOARD_TITLE FROM TB_BOARD WHERE BOCODE = 'BO01' AND STATUS = 'Y')\n" +
            "ORDER BY B.ADD_DATE DESC", nativeQuery = true)
    Page<MainPageSearchDto> searchAllByBoard(@Param("boardtitle") String boardtitle, Pageable pageable);
}
