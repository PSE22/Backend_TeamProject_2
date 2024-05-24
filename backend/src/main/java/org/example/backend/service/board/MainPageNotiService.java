package org.example.backend.service.board;

import org.example.backend.model.dto.MainPageArmDto;
import org.example.backend.repository.board.MainPageArmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * packageName : org.example.backend.service.board
 * fileName : MainPageNotiService
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
@Service
public class MainPageNotiService {


    @Autowired
    private MainPageArmRepository mainPageArmRepository;

    // 알람 상세 정보 조회
    public List<MainPageArmDto> getNotificationsByMemberId(String memberId) {
        return mainPageArmRepository.NotiByMemberId(memberId);
    }


    //    알람 카운트 함수
    public int countNotification(String memberId) {
        return mainPageArmRepository.CountNotiByMemberId(memberId);
    }

}
