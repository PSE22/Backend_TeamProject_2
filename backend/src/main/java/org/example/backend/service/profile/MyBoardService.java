package org.example.backend.service.profile;

import lombok.extern.slf4j.Slf4j;
import org.example.backend.model.entity.board.Board;
import org.example.backend.repository.profile.MyBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * packageName : org.example.backend.service.profile
 * fileName : MyBoardService
 * author : sjuk2
 * date : 2024-05-30
 * description :
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-05-30         sjuk2          최초 생성
 */
@Slf4j
@Service
public class MyBoardService {
    @Autowired
    MyBoardRepository myBoardRepository;
    public Page<Board> findByMemberId(
            String memberId, Pageable pageable
    ) {
        Page<Board> page = myBoardRepository.findByMemberId(memberId, pageable);
        return page;
    }
}
