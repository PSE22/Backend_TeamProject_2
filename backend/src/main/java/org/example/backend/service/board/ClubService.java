package org.example.backend.service.board;

import org.example.backend.model.entity.board.Board;
import org.example.backend.repository.board.ClubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

/**
 * packageName : org.example.backend.service.board
 * fileName : ClubService
 * author : gumiji
 * date : 5/22/24
 * description :
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 5/22/24         gumiji          최초 생성
 */
@Service
public class ClubService {
    @Autowired
    ClubRepository clubRepository;

//    public Page<Board>
}
