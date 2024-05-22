package org.example.backend.repository.board;

import org.example.backend.model.entity.board.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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
}
