package org.example.backend.repository.board;

import org.example.backend.model.entity.board.ReplyReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * packageName : org.example.backend.repository.board
 * fileName : ReplyReportRepository
 * author : SAMSUNG
 * date : 2024-06-07
 * description :
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-06-07         SAMSUNG          최초 생성
 */
@Repository
public interface ReplyReportRepository extends JpaRepository<ReplyReport, Long> {
}
