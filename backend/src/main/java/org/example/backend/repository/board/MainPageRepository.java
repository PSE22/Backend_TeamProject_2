package org.example.backend.repository.board;

import org.example.backend.model.entity.board.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * packageName : org.example.backend.repository.board
 * fileName : MainPage
 * author : GGG
 * date : 2024-05-22
 * description :
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-05-22         GGG          최초 생성
 */
@Repository
public interface MainPageRepository extends JpaRepository<Board, Integer> {
//    메인페이지 조회 쿼리 (자유,건의,칭찬 게시판)
    @Query(value =
            "WITH all_replies AS (" +
                    "    SELECT BOARD_ID, COUNT(*) AS total_reply_count" +
                    "    FROM TB_REPLY" +
                    "    WHERE STATUS = 'Y'" +
                    "    GROUP BY BOARD_ID" +
                    ")" +
                    " SELECT '자유게시판' AS board," +
                    "        B.BOARD_TITLE AS title," +
                    "        B.NICKNAME AS nickname," +
                    "        B.GOOD AS good," +
                    "        NVL(R.total_reply_count, 0) AS reply_count," +
                    "        B.ADD_DATE AS add_date" +
                    " FROM TB_BOARD B" +
                    " LEFT JOIN all_replies R ON B.BOARD_ID = R.BOARD_ID" +
                    " WHERE B.STATUS = 'Y' AND B.CODE = 'BO03'" +
                    " UNION ALL" +
                    " SELECT '건의게시판' AS board," +
                    "        B.BOARD_TITLE AS title," +
                    "        B.NICKNAME AS nickname," +
                    "        B.GOOD AS good," +
                    "        NVL(R.total_reply_count, 0) AS reply_count," +
                    "        B.ADD_DATE AS add_date" +
                    " FROM TB_BOARD B" +
                    " LEFT JOIN all_replies R ON B.BOARD_ID = R.BOARD_ID" +
                    " WHERE B.STATUS = 'Y' AND B.CODE = 'BO04'" +
                    " UNION ALL" +
                    " SELECT '칭찬게시판' AS board," +
                    "        B.BOARD_TITLE AS title," +
                    "        B.NICKNAME AS nickname," +
                    "        B.GOOD AS good," +
                    "        NVL(R.total_reply_count, 0) AS reply_count," +
                    "        B.ADD_DATE AS add_date" +
                    " FROM TB_BOARD B" +
                    " LEFT JOIN all_replies R ON B.BOARD_ID = R.BOARD_ID" +
                    " WHERE B.STATUS = 'Y' AND B.CODE = 'BO05'" +
                    " ORDER BY board, add_date DESC", nativeQuery = true)
    List<Object[]> findBoardData();


    @Query(value =
            "WITH all_replies AS (" +
                    "    SELECT BOARD_ID, COUNT(*) AS total_reply_count" +
                    "    FROM TB_REPLY" +
                    "    WHERE STATUS = 'Y' AND TO_DATE(ADD_DATE, 'YYYY-MM-DD HH24:MI:SS') > SYSDATE - 30" +
                    "    GROUP BY BOARD_ID" +
                    ")" +
                    " SELECT '핫토픽게시판' AS board," +
                    "        B.BOARD_TITLE AS title," +
                    "        B.NICKNAME AS nickname," +
                    "        B.GOOD AS good," +
                    "        NVL(R.total_reply_count, 0) AS reply_count," +
                    "        B.ADD_DATE AS add_date" +
                    " FROM TB_BOARD B" +
                    " LEFT JOIN all_replies R ON B.BOARD_ID = R.BOARD_ID" +
                    " WHERE B.STATUS = 'Y' AND B.CODE = 'BO03' AND NVL(R.total_reply_count, 0) >= 1" +
                    " ORDER BY TO_DATE(B.ADD_DATE, 'YYYY-MM-DD HH24:MI:SS') DESC", nativeQuery = true)
    List<Object[]> findHotTopics();

//    @Query(value =
//            "WITH all_replies AS (" +
//                    "    SELECT BOARD_ID, COUNT(*) AS total_reply_count" +
//                    "    FROM TB_REPLY" +
//                    "    WHERE STATUS = 'Y' AND TO_DATE(ADD_DATE, 'YYYY-MM-DD HH24:MI:SS') > SYSDATE - 30" +
//                    "    GROUP BY BOARD_ID" +
//                    ")" +
//                    " SELECT '핫토픽게시판' AS board," +
//                    "        B.BOARD_TITLE AS title," +
//                    "        B.NICKNAME AS nickname," +
//                    "        B.GOOD AS good," +
//                    "        NVL(R.total_reply_count, 0) AS reply_count," +
//                    "        B.ADD_DATE AS add_date" +
//                    " FROM TB_BOARD B" +
//                    " LEFT JOIN all_replies R ON B.BOARD_ID = R.BOARD_ID" +
//                    " WHERE B.STATUS = 'Y' AND B.CODE = 'BO03' AND NVL(R.total_reply_count, 0) >= 1" +
//                    " ORDER BY TO_DATE(B.ADD_DATE, 'YYYY-MM-DD HH24:MI:SS') DESC", nativeQuery = true)
//    List<MainPageDto> findHotTopics();

}