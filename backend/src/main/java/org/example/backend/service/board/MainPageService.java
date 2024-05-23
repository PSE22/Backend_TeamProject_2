package org.example.backend.service.board;

import org.example.backend.repository.board.MainPageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * packageName : org.example.backend.service.board
 * fileName : MainPageService
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

@Service
public class MainPageService {

    @Autowired
    private MainPageRepository mainPageRepository;

//    메인페이지 조회함수
    public List<Object[]> getBoardData() {
        return mainPageRepository.findBoardData();
    }

//    핫토픽 조회함수
    public List<Object[]> getHotTopics() {
        return mainPageRepository.findHotTopics();
    }

    //    핫토픽 조회함수
//    public List<MainPageDto> getHotTopics() {
//        return mainPageRepository.findHotTopics();
//    }
}
