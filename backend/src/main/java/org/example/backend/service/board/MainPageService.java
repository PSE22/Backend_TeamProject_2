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

    public List<Object[]> getBoardData() {
        return mainPageRepository.findBoardData();
    }
}
