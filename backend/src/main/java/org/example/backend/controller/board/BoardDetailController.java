package org.example.backend.controller.board;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.model.dto.board.IBoardDetailDto;
import org.example.backend.model.dto.board.IBoardDto;
import org.example.backend.model.dto.board.IReplyDto;
import org.example.backend.model.dto.board.IUserDto;
import org.example.backend.model.entity.board.Place;
import org.example.backend.model.entity.board.Vote;
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

    // 로그인된 회원 정보 조회
    @GetMapping("/board-detail/member")
    public ResponseEntity<Object> findMember(@RequestParam String memberId) {
        try {
            Optional<IUserDto> optional = boardDetailService.findMember(memberId);
            if (optional.isEmpty() == true) {
                return new ResponseEntity<>("데이터 없음", HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(optional.get(), HttpStatus.OK);
            }
        } catch (Exception e) {
            return handleException(e);
        }
    }

    // 게시글, 작성자 정보 조회
    @GetMapping("/board-detail")
    public ResponseEntity<Object> findBoardAndMember(@RequestParam Long boardId) {
        try {
            Optional<IBoardDto> optional = boardDetailService.findBoardAndMember(boardId);
            if (optional.isEmpty() == true) {
                return new ResponseEntity<>("데이터 없음", HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(optional.get(), HttpStatus.OK);
            }
        } catch (Exception e) {
            return handleException(e);
        }
    }

    // 코드번호로 코드명 조회
    @GetMapping("/board-detail/cmCd")
    public ResponseEntity<Object> findCmCdName(@RequestParam String cmCd) {
        try {
            Optional<IBoardDetailDto> optional = boardDetailService.findCmCdName(cmCd);
            if (optional.isEmpty() == true) {
                return new ResponseEntity<>("데이터 없음", HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(optional, HttpStatus.OK);
            }
        } catch (Exception e) {
            return handleException(e);
        }
    }

    // 글번호로 투표 조회
    @GetMapping("/board-detail/vote")
    public ResponseEntity<Object> findVote(@RequestParam Long boardId) {
        try {
            List<Vote> list = boardDetailService.findVote(boardId);
            if (list.isEmpty() == true) {
                return new ResponseEntity<>("데이터 없음", HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(list, HttpStatus.OK);
            }
        } catch (Exception e) {
            return handleException(e);
        }
    }

    // 글번호로 장소 조회 (게시글 하나당 장소 하나)
    @GetMapping("/board-detail/place")
    public ResponseEntity<Object> findPlace(@RequestParam Long boardId){
        try {
            log.debug("Controller 문제?? boardId :: ", boardId);
            Optional<Place> optional = boardDetailService.findPlace(boardId);
            if (optional.isEmpty() == true) {
                return new ResponseEntity<>("데이터 없음", HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(optional.get(), HttpStatus.OK);
            }
        } catch (Exception e) {
            return handleException(e);
        }
    }

    // 글번호로 이미지 조회
    @GetMapping("/board-detail/board-img")
    public ResponseEntity<Object> findBoardImg(@RequestParam Long boardId) {
        try {
            List<IBoardDetailDto> list = boardDetailService.findBoardImg(boardId);
            if (list.isEmpty() == true) {
                return new ResponseEntity<>("데이터 없음", HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(list, HttpStatus.OK);
            }
        } catch (Exception e) {
            return handleException(e);
        }
    }

    // 글번호로 댓글 조회
    @GetMapping("/board-detail/reply")
    public ResponseEntity<Object> findReplyCount(@RequestParam Long boardId) {
        try {
            List<IReplyDto> list = boardDetailService.findReply(boardId);
            if (list.isEmpty() == true) {
                return new ResponseEntity<>("데이터 없음", HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(list, HttpStatus.OK);
            }
        } catch (Exception e) {
            return handleException(e);
        }
    }

    // 댓글 수 조회
    @GetMapping("/board-detail/reply/count")
    public ResponseEntity<Object> countReply(@RequestParam Long boardId){
        try {
            Integer count = boardDetailService.countReply(boardId);
            if (count == null) {
                return new ResponseEntity<>("데이터 없음", HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(count, HttpStatus.OK);
            }
        } catch (Exception e) {
            return handleException(e);
        }
    }

    // 예외 처리 메서드
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception e) {
        if (e instanceof IllegalArgumentException) {
            log.error("잘못된 인자가 전달되었습니다", e);
            return new ResponseEntity<>("잘못된 인자가 전달되었습니다", HttpStatus.BAD_REQUEST);
        } else if (e instanceof EntityNotFoundException) {
            log.error("해당 엔티티를 찾을 수 없습니다", e);
            return new ResponseEntity<>("해당 엔티티를 찾을 수 없습니다", HttpStatus.NOT_FOUND);
        } else {
            log.error("서버 오류가 발생했습니다", e);
            return new ResponseEntity<>("서버 오류가 발생했습니다", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
