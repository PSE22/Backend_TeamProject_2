package org.example.backend.service.admin;

import lombok.RequiredArgsConstructor;
import org.example.backend.model.dto.IReportDto;
import org.example.backend.model.entity.board.Report;
import org.example.backend.repository.board.ReportRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * packageName : org.example.backend.service.admin
 * fileName : ReportService
 * author : gumiji
 * date : 6/9/24
 * description :
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 6/9/24         gumiji          최초 생성
 */
@Service
@RequiredArgsConstructor
public class ReportService {
    private final ReportRepository reportRepository;

    public Page<IReportDto> findByBoard(Pageable pageable) {
        Page<IReportDto> list = reportRepository.findByBoard(pageable);
        return list;
    }
}
