package org.example.backend.repository.board;

import org.example.backend.model.entity.board.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FreeBoardRepository extends JpaRepository<Board, Long> {
    @Query(value = "SELECT B.* FROM TB_BOARD B " +
            "WHERE B.CODE = 'BO03' " +
            "AND B.STATUS = 'Y' " +
            "AND B.BOARD_TITLE LIKE '%' || :boardTitle || '%' " +
            "ORDER BY B.ADD_DATE DESC",
            countQuery = "SELECT count(*) FROM TB_BOARD B " +
                    "WHERE B.CODE = 'BO03' " +
                    "AND B.STATUS = 'Y' " +
                    "AND B.BOARD_TITLE LIKE '%' || :boardTitle || '%'",
            nativeQuery = true)
    Page<Board> findAllByFreeBoardTitleContaining(@Param("boardTitle") String boardTitle,
                                                 Pageable pageable
    );

    @Query("SELECT b FROM Board b WHERE b.code = :code AND b.boardId = :boardId")
    Optional<Board> findByCodeAndId(@Param("code") String code, @Param("boardId") Long boardId);

}