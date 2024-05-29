package org.example.backend.controller.board;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.model.dto.board.IBoardDetailDto;
import org.example.backend.model.entity.CmCode;
import org.example.backend.model.entity.board.Board;
import org.example.backend.service.board.BoardDetailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * packageName : org.example.backend.controller.board
 * fileName : BoardDetailController
 * author : SAMSUNG
 * date : 2024-05-29
 * description :
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-05-29         SAMSUNG          최초 생성
 */
@Slf4j
@RestController
@RequestMapping("api/board")
@RequiredArgsConstructor
public class BoardDetailController {
    private final BoardDetailService boardDetailService;

    // 게시판 상세 조회
    @GetMapping("/board-detail")
    public ResponseEntity<Object> findByBoardId(@RequestParam Long boardId) {
        try {
            Optional<Board> optional = boardDetailService.findByBoardId(boardId);
            if (optional.isEmpty() == true) {
                return new ResponseEntity<>("데이터 없음", HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(optional.get(), HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("서버 오류가 발생했습니다", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 코드번호로 코드명 조회
    @GetMapping("/board-detail/cmCd")
    public ResponseEntity<Object> findCmCdName(@RequestParam String cmCd) {
        try {
            List<IBoardDetailDto> list = boardDetailService.findCmCdName(cmCd);
            if (list.isEmpty() == true) {
                return new ResponseEntity<>("데이터 없음", HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(list, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("서버 오류가 발생했습니다", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
