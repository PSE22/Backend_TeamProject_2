package org.example.backend.service.board;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.protocol.types.Field;
import org.example.backend.model.dto.board.IClubDto;
import org.example.backend.model.entity.CmCode;
import org.example.backend.model.entity.board.Board;
import org.example.backend.repository.board.BoardRepository;
import org.example.backend.repository.board.ClubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
@RequiredArgsConstructor
public class ClubService {
    private final ClubRepository clubRepository;    // DI
    private final BoardRepository boardRepository;

    @Transactional
    //    동호회 게시판 중메뉴 목록조회
    public Page<IClubDto> findByCode(String boardTitle,
                                     String bocode,
                                     Pageable pageable) {
        Page<IClubDto> page = clubRepository.findByCode(boardTitle, bocode, pageable);
        return page;
    }

    //    동호회 공지 조회
    public List<IClubDto> findByCodeAndNotice(String bocode) {
        List<IClubDto> list = clubRepository.findByCodeAndNotice(bocode);
        return list;
    }

    //    동호회 소메뉴
    public List<IClubDto> findBySmcode() {
        List<IClubDto> list = clubRepository.findBySmcode();
        return list;
    }

    //    TODO: 상세조회
    @Transactional
    public Optional<Board> findById(Long boardId) {
//        DB 상세조회 실행
        Optional<Board> optionalBoard
                = boardRepository.findById(boardId);
        return optionalBoard;
    }
}
