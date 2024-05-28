package org.example.backend.repository.board;

import org.example.backend.model.dto.board.FreeNoticeDto;
import org.example.backend.model.entity.board.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FreeBoardRepository extends JpaRepository<Board, Long> {
    @Query(value = "SELECT B.* FROM TB_BOARD B " +
            "WHERE B.BOCODE = 'BO03' " +
            "AND B.STATUS = 'Y' " +
            "AND B.NOTICE_YN = 'N' " +
            "AND B.BOARD_TITLE LIKE '%' || :boardTitle || '%' " +
            "ORDER BY B.ADD_DATE DESC",
            countQuery = "SELECT count(*) FROM TB_BOARD B " +
                    "WHERE B.BOCODE = 'BO03' " +
                    "AND B.STATUS = 'Y' " +
                    "AND B.NOTICE_YN = 'N'\n" +
                    "AND B.BOARD_TITLE LIKE '%' || :boardTitle || '%'",
            nativeQuery = true)
    Page<Board> findAllByFreeBoardTitleContaining(@Param("boardTitle") String boardTitle,
                                                 Pageable pageable
    );

    @Query("SELECT b FROM Board b WHERE b.bocode = :bocode AND b.boardId = :boardId")
    Optional<Board> findByCodeAndId(@Param("bocode") String code, @Param("boardId") Long boardId);

    //    자유게시판 공지 전체조회
    @Query(value = "SELECT B.BOARD_ID AS boardId,\n" +
            "B.BOARD_TITLE AS boardTitle,\n" +
            "M.MEMBER_NAME AS memberName,\n" +
            "B.ADD_DATE AS addDate\n" +
            "FROM TB_BOARD B, TB_MEMBER M\n" +
            "WHERE B.BOCODE LIKE 'BO03'\n" +
            "AND B.MEMBER_ID = M.MEMBER_ID\n" +
            "AND B.STATUS = 'Y'\n" +
            "AND B.NOTICE_YN = 'Y'\n" +
            "ORDER BY B.ADD_DATE DESC",
            nativeQuery = true)
    List<FreeNoticeDto> findByFreeCodeAndNotice();

}
