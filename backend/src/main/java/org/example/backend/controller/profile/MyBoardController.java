package org.example.backend.controller.profile;

import lombok.extern.slf4j.Slf4j;
import org.example.backend.model.entity.board.Board;
import org.example.backend.service.profile.MyBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * packageName : org.example.backend.controller.profile
 * fileName : MyBoardController
 * author : sjuk2
 * date : 2024-05-30
 * description :
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-05-30         sjuk2          최초 생성
 */
@Slf4j
@RestController
@RequestMapping("/api/member")
public class MyBoardController {
    @Autowired
    MyBoardService myBoardService;

    @GetMapping("/activity/{memberId}")
    public ResponseEntity<Object> findByMemberId(
            @PathVariable String memberId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        try {
//            페이징 객체 생성
            Pageable pageable = PageRequest.of(page, size);

//            전체 조회 서비스 실행
            Page<Board> boardPage
                    = myBoardService.findByMemberId(memberId, pageable);

//            공통 페이징 객체 생성 : 자료 구조 맵 사용
            Map<String, Object> response = new HashMap<>();
            response.put("board", boardPage.getContent()); // board 배열
            response.put("currentPage", boardPage.getNumber()); // 현재페이지번호
            response.put("totalItems", boardPage.getTotalElements()); // 총건수(개수)
            response.put("totalPages", boardPage.getTotalPages()); // 총페이지수

            if (boardPage.isEmpty() == false) {
//                조회 성공
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
//                데이터 없음
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}