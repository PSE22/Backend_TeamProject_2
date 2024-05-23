package org.example.backend.service.board;

import org.example.backend.model.dto.board.IClubDto;
import org.example.backend.repository.board.ClubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

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

    //    동호회 게시판 전체조회
    public Page<IClubDto> findByCode(String code, Pageable pageable) {
        Page<IClubDto> page = clubRepository.findByCode(code, pageable);
        return page;
    }

    //    동호회 공지 조회
    public List<IClubDto> findByCodeAndNotice(String code) {
        List<IClubDto> list = clubRepository.findByCodeAndNotice(code);
        return list;
    }

}
