package org.example.backend.repository.board;

import org.example.backend.model.entity.board.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    @Query(value = "SELECT * FROM TB_BOARD\n" +
            "WHERE BOARD_TITLE LIKE '%' || :boardTitle || '%'"
            ,countQuery = "SELECT count(*) FROM TB_BOARD\n" +
            "WHERE BOARD_TITLE LIKE '%' || :boardTitle || '%'"
            ,nativeQuery = true
    )
    Page<Board> findAllByBoardTitleContaining(@Param("boardTitle") String boardTitle,
                                                 Pageable pageable
    );
}
