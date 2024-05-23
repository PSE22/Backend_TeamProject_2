package org.example.backend.repository.board;

import org.example.backend.model.dto.board.IClubDto;
import org.example.backend.model.entity.board.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * packageName : org.example.backend.repository.board
 * fileName : ClubRepository
 * author : gumiji
 * date : 5/22/24
 * description :
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 5/22/24         gumiji          최초 생성
 */
@Repository
public interface ClubRepository extends JpaRepository<Board, Long> {
    //    동호회 게시판 전체조회
    @Query(value = "SELECT B.BOARD_ID AS boardId,\n" +
            "M.MEMBER_NAME AS memberName,\n" +
            "B.BOARD_TITLE AS boardTitle,\n" +
            "B.ADD_DATE AS addDate\n" +
            "FROM TB_BOARD B, TB_MEMBER M\n" +
            "WHERE B.CODE LIKE 'SM02%'\n" +
            "AND B.MEMBER_ID = M.MEMBER_ID\n" +
            "AND B.STATUS = 'Y'\n" +
            "AND B.NOTICE_YN = 'N'\n" +
            "ORDER BY B.ADD_DATE DESC",
            countQuery = "SELECT count(*)\n" +
                    "FROM TB_BOARD B, TB_MEMBER M\n" +
                    "WHERE B.CODE LIKE 'SM02%'\n" +
                    "AND B.MEMBER_ID = M.MEMBER_ID\n" +
                    "AND B.STATUS = 'Y'\n" +
                    "ORDER BY B.ADD_DATE DESC",
            nativeQuery = true)
    Page<IClubDto> findByCode(@Param("code") String code, Pageable pageable);

    //    동호회 공지 전체조회
    @Query(value = "SELECT B.BOARD_ID AS boardId,\n" +
            "M.MEMBER_NAME AS memberName,\n" +
            "B.BOARD_TITLE AS boardTitle,\n" +
            "B.ADD_DATE AS addDate\n" +
            "FROM TB_BOARD B, TB_MEMBER M\n" +
            "WHERE B.CODE LIKE 'SM02%'\n" +
            "AND B.MEMBER_ID = M.MEMBER_ID\n" +
            "AND B.STATUS = 'Y'\n" +
            "AND B.NOTICE_YN = 'Y'\n" +
            "ORDER BY B.ADD_DATE DESC",
            nativeQuery = true)
    List<IClubDto> findByCodeAndNotice(@Param("code") String code);

//    동호회 전체조회 유니온 쿼리문
//    @Query(value = "WITH NoticeBoards AS (\n" +
//            "    SELECT B.BOARD_ID AS boardId,\n" +
//            "           M.MEMBER_NAME AS memberName,\n" +
//            "           B.BOARD_TITLE AS boardTitle,\n" +
//            "           B.ADD_DATE AS addDate\n" +
//            "    FROM TB_BOARD B\n" +
//            "    JOIN TB_MEMBER M ON B.MEMBER_ID = M.MEMBER_ID\n" +
//            "    WHERE B.CODE LIKE 'SM02%'\n" +
//            "      AND B.STATUS = 'Y'\n" +
//            "      AND B.NOTICE_YN = 'Y' -- NOTICE_YN = 'Y' 조건 추가\n" +
//            "    ORDER BY B.ADD_DATE DESC\n" +
//            "    FETCH FIRST 5 ROWS ONLY -- 상위 5개만 가져옴\n" +
//            "),\n" +
//            "OtherBoards AS (\n" +
//            "    SELECT B.BOARD_ID AS boardId,\n" +
//            "           M.MEMBER_NAME AS memberName,\n" +
//            "           B.BOARD_TITLE AS boardTitle,\n" +
//            "           B.ADD_DATE AS addDate\n" +
//            "    FROM TB_BOARD B\n" +
//            "    JOIN TB_MEMBER M ON B.MEMBER_ID = M.MEMBER_ID\n" +
//            "    WHERE B.CODE LIKE 'SM02%'\n" +
//            "      AND B.STATUS = 'Y'\n" +
//            "    ORDER BY B.ADD_DATE DESC\n" +
//            ")\n" +
//            "SELECT boardId, memberName, boardTitle, addDate\n" +
//            "FROM NoticeBoards\n" +
//            "UNION ALL -- 두 결과를 합침\n" +
//            "SELECT boardId, memberName, boardTitle, addDate\n" +
//            "FROM OtherBoards",
//    nativeQuery = true)
}
