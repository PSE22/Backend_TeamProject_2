package org.example.backend.repository.board;

import org.example.backend.model.entity.board.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * packageName : org.example.backend.repository.board
 * fileName : ReplyRepository
 * author : kimtaewan
 * date : 2024-05-23
 * description :
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-05-23         kimtaewan          최초 생성
 */
@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long> {
    Long countByBoardId(Long boardId);
}
