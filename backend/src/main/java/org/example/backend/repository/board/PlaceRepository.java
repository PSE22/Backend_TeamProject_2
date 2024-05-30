package org.example.backend.repository.board;

import org.example.backend.model.entity.board.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PlaceRepository extends JpaRepository<Place, Long> {

    // 글번호로 장소 조회 (게시글 하나당 장소 하나)
    @Query(value = "SELECT *\n" +
            "FROM TB_PLACE\n" +
            "WHERE BOARD_ID = :boardId"
            , nativeQuery = true)
    Optional<Place> findByBoardId(@Param("boardId") Long boardId);
}
