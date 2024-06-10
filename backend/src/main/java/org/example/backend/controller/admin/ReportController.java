package org.example.backend.controller.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.model.dto.IReportDto;
import org.example.backend.model.entity.board.Report;
import org.example.backend.repository.board.ReportRepository;
import org.example.backend.service.admin.ReportService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * packageName : org.example.backend.controller.admin
 * fileName : ReportController
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
@Slf4j
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@RestControllerAdvice   // 전역적으로 예외처리
public class ReportController {
    private final ReportService reportService;

    @GetMapping("/report")
//    sort = reportId : 정렬 기준 컬럼, direction 정렬 순서
    public ResponseEntity<Object> findAllReport(@PageableDefault(sort = "addDate", direction = Sort.Direction.DESC) Pageable pageable) {
        try {
            Page<IReportDto> list = reportService.findByBoard(pageable);

            if (list.isEmpty() == false) {
//                조회 성공
                return new ResponseEntity<>(list, HttpStatus.OK);
            } else {
//                데이터 없음
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
