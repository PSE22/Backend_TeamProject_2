package org.example.backend.repository.board;

import org.example.backend.model.entity.board.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * packageName : org.example.backend.repository.board
 * fileName : ReportRepository
 * author : SAMSUNG
 * date : 2024-06-06
 * description :
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-06-06         SAMSUNG          최초 생성
 */
@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
}
