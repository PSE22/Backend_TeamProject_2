package org.example.backend.controller.board;

import org.example.backend.model.dto.MainPageArmDto;
import org.example.backend.service.board.MainPageNotiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * packageName : org.example.backend.controller.board
 * fileName : MainPageNotiController
 * author : GGG
 * date : 2024-05-24
 * description :
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-05-24         GGG          최초 생성
 */
@RestController
@RequestMapping("/api/notifications")
public class MainPageNotiController {

    @Autowired
    private MainPageNotiService mainPageNotiService;

    // 멤버별 알람 상세 조회
    @GetMapping("/{memberId}")
    public ResponseEntity<List<MainPageArmDto>> getNotificationsByMemberId(@PathVariable String memberId) {
        List<MainPageArmDto> notifications = mainPageNotiService.getNotificationsByMemberId(memberId);
        return ResponseEntity.ok(notifications);
    }

    // 알람카운트 조회 함수
    @GetMapping("/count/{memberId}")
    public ResponseEntity<Integer> getCountNotification(@PathVariable String memberId) {
        int count = mainPageNotiService.countNotification(memberId);
        return ResponseEntity.ok(count);
    }
}
