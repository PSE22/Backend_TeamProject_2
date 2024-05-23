package org.example.backend.controller.board;

import lombok.extern.slf4j.Slf4j;
import org.example.backend.service.board.MainPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * packageName : org.example.backend.controller.board
 * fileName : MainPageController
 * author : GGG
 * date : 2024-05-22
 * description :
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-05-22         GGG          최초 생성
 */
@Slf4j
@RestController
@RequestMapping("/api/board")
public class MainPageController {

    @Autowired
    private MainPageService mainPageService;

//    메인페이지 조회함수
    @GetMapping("/board-data")
    public List<Object[]> getBoardData() {
        return mainPageService.getBoardData();
    }

//    핫토픽 조회함수
    @GetMapping("/hot-topics")
    public List<Object[]> getHotTopics() {
        return mainPageService.getHotTopics();
    }

//    @GetMapping("/hot-topics")
//    public ResponseEntity<List<MainPageDto>> getHotTopics() {
//        try {
//            List<MainPageDto> hotTopics = mainPageService.getHotTopics();
//            return ResponseEntity.ok(hotTopics);
//        } catch (Exception e) {
//            return ResponseEntity.internalServerError().body(null);
//        }
//    }
}