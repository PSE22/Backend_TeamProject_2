package org.example.backend.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.example.backend.model.dto.BoardManageDto;
import org.example.backend.service.admin.BoardManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * packageName : org.example.backend.controller.board
 * fileName : BoardManageController
 * author : GGG
 * date : 2024-05-29
 * description :
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-05-29         GGG          최초 생성
 */
@RestController
@Slf4j
@RequestMapping("/api/board")
public class BoardManageController {

    @Autowired
    BoardManageService boardManageService;

    @GetMapping("/management")
    public ResponseEntity<List<BoardManageDto>> getCmCodeList() {
        List<BoardManageDto> cmCodeList = boardManageService.getCmCodeList();
        return ResponseEntity.ok(cmCodeList);
    }
}
