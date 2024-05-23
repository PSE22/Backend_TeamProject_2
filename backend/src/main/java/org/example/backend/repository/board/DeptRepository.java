package org.example.backend.repository.board;

import org.example.backend.model.entity.board.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * packageName : org.example.backend.repository.board
 * fileName : DeptRepository
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
public interface DeptRepository extends JpaRepository<Board, Long> {
}