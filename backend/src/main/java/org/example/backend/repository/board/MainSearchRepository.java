package org.example.backend.repository.board;

import org.example.backend.model.entity.board.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
public interface MainSearchRepository extends JpaRepository<Board, Integer> {
}
