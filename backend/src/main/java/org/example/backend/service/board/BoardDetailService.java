package org.example.backend.service.board;

import lombok.RequiredArgsConstructor;
import org.example.backend.model.dto.board.IBoardDetailDto;
import org.example.backend.model.entity.board.Board;
import org.example.backend.repository.board.BoardRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * packageName : org.example.backend.service.board
 * fileName : BoardDetailService
 * author : SAMSUNG
 * date : 2024-05-29
 * description :
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-05-29         SAMSUNG          최초 생성
 */
@Service
@RequiredArgsConstructor
public class BoardDetailService {
    private final BoardRepository boardRepository;

    // 게시판 상세 조회 - 부서 게시판
    public Optional<Board> findByBoardId(Long boardId) {
        Optional<Board> optional = boardRepository.findById(boardId);
        return optional;
    }

    // 코드번호로 코드명 조회
    public List<IBoardDetailDto> findCmCdName(String cmCd) {
        List<IBoardDetailDto> list = boardRepository.findCmCdName(cmCd);
        return list;
    }

    // 투표 조회


    // 장소 조회

}
