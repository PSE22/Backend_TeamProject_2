package org.example.backend.service.board;

import lombok.RequiredArgsConstructor;
import org.example.backend.model.dto.board.IClubDto;
import org.example.backend.model.dto.board.VoteDto;
import org.example.backend.model.entity.CmCode;
import org.example.backend.model.entity.board.Board;
import org.example.backend.repository.auth.CmCodeRepository;
import org.example.backend.repository.board.BoardRepository;
import org.example.backend.repository.board.ClubRepository;
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
    private final CmCodeRepository cmCodeRepository;

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

    //    동호회 중분류 조회
    public List<IClubDto> findByBocode() {
        List<IClubDto> list = clubRepository.findByBocode();
        return list;
    }

    //    동호회 게시판 소분류 조회
    public List<CmCode> findByCmCdNameAndSmcode() {
        List<CmCode> list = cmCodeRepository.findByCmCdNameAndSmcode();
        return list;

    }

    @Transactional
    public void save(Board board, List<VoteDto> voteDtos) {
        // JPA 저장 함수 실행 : return 값 : 저장된 객체
        Board board2 = boardRepository.save(board);
        // 저장된 board의 boardId를 객체로 변환
        Long boardId = board2.getBoardId();
        voteService.saveVote(boardId, voteDtos);
    }
}
