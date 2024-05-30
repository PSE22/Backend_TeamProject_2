package org.example.backend.service.board;

import lombok.RequiredArgsConstructor;
import org.example.backend.model.dto.board.VoteDto;
import org.example.backend.model.entity.board.Board;
import org.example.backend.repository.board.BoardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * packageName : org.example.backend.service.board
 * fileName : BoardWriteService
 * author : gumiji
 * date : 5/29/24
 * description :
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 5/29/24         gumiji          최초 생성
 */
@Service
@RequiredArgsConstructor
public class BoardWriteService {
    private final BoardRepository boardRepository;
    private final VoteService voteService;

    //    투표 기능
    @Transactional
    public void save(Board board, List<VoteDto> voteDtos) {
        // JPA 저장 함수 실행 : return 값 : 저장된 객체
        Board board2 = boardRepository.save(board);
        // 저장된 board의 boardId를 객체로 변환
        Long boardId = board2.getBoardId();
        voteService.saveVote(boardId, voteDtos);
    }
}
