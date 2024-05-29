package org.example.backend.controller.board;

import lombok.extern.slf4j.Slf4j;
import org.example.backend.model.dto.board.IClubDto;
import org.example.backend.model.entity.CmCode;
import org.example.backend.model.entity.board.Board;
import org.example.backend.service.board.ClubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * packageName : org.example.backend.controller.board
 * fileName : ClubController
 * author : gumiji
 * date : 5/23/24
 * description :
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 5/23/24         gumiji          최초 생성
 */
@Slf4j
@RestController
@RequestMapping("api/board")
public class ClubController {
    @Autowired
    ClubService clubService;

    //    동호회 게시판 전체조회
    @GetMapping("/club")
    public ResponseEntity<Object> findAll(
            @RequestParam(defaultValue = "") String boardTitle,
            @RequestParam(defaultValue = "") String bocode,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
//            페이징 객체 생성
            Pageable pageable = PageRequest.of(page, size);

//            전체 조회 서비스 실행
            Page<IClubDto> clubDtoPage
                    = clubService.findByCode(boardTitle, bocode, pageable);

//            공통 페이징 객체 생성 : 자료구조 맵 사용
            Map<String, Object> response = new HashMap<>();
            response.put("club", clubDtoPage.getContent()); // club 배열
            response.put("currentPage", clubDtoPage.getNumber()); // 현재페이지번호
            response.put("totalItems", clubDtoPage.getTotalElements()); // 총건수(개수)
            response.put("totalPages", clubDtoPage.getTotalPages()); // 총페이지수

            if (clubDtoPage.isEmpty() == false) {
                log.debug("뭐가문제고 : " + response);
//                조회 성공
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                log.debug("뭐가문제고2222 : " + response);
//                데이터 없음
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //    동호회 공지 전체조회
    @GetMapping("/club-notice")
    public ResponseEntity<Object> findNotice(
            @RequestParam(defaultValue = "") String bocode
    ) {
        try {
//            전체 조회 서비스 실행
            List<IClubDto> club
                    = clubService.findByCodeAndNotice(bocode);

            if (club.isEmpty() == false) {
//                조회 성공
                return new ResponseEntity<>(club, HttpStatus.OK);
            } else {
//                데이터 없음
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            log.debug("에러 : " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //    동호회 소메뉴
    @GetMapping("/club-smcode")
    public ResponseEntity<Object> findSmcode() {
        try {
//            전체 조회 서비스 실행
            List<IClubDto> smcode
                    = clubService.findBySmcode();
            if (smcode.isEmpty() == false) {
//                조회 성공
                return new ResponseEntity<>(smcode, HttpStatus.OK);
            } else {
//                데이터 없음
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            log.debug("에러 : " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //    동호회 게시물 상세조회
    @GetMapping("/club/{boardId}")
    public ResponseEntity<Object> findById(
            @PathVariable Long boardId
    ) {
        try {
//            상세조회 서비스 실행
            Optional<Board> optionalBoard
                    = clubService.findById(boardId);
            if (optionalBoard.isEmpty() == true) {
//                데이터 없음
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
//                조회 성공
                return new ResponseEntity<>(optionalBoard.get(), HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
