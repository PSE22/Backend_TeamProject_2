package org.example.backend.controller.board;

import lombok.extern.slf4j.Slf4j;
import org.example.backend.service.board.MainPageService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/board-data")
    public List<Object[]> getBoardData() {
        return mainPageService.getBoardData();
    }
}