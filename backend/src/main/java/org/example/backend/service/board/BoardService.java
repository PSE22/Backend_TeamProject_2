package org.example.backend.service.board;

import org.example.backend.model.entity.board.Board;
import org.example.backend.repository.board.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BoardService {

    @Autowired
    BoardRepository boardRepository;

    //    TODO: 전체조회(read)
    public List<Board> findAll() {
        List<Board> list = boardRepository.findAll();
        return list;
    }

    //    페이징 처리
    public Page<Board> findAllByBoardTitleContaining(String boardTitle,
                                                                Pageable pageable) {
        Page<Board> page
                = boardRepository
                .findAllByBoardTitleContaining(boardTitle, pageable);
        return page;
    }

    //    상세조회
    public Optional<Board> findById(Long boardId) {
        //    JPA 상세조회 함수 실행
        Optional<Board> optionalBoard
                = boardRepository.findById(boardId);
        return optionalBoard;
    }

    //    TODO: 등록(insert),수정(update)
    public Board save(Board board) {
        //    JPA 저장 함수 실행 : return 값 : 저장된 객체
        Board board2 = boardRepository.save(board);

        return board2;
    }

    //    TODO: 삭제(delete)
    public boolean removeById(Long boardId) {
        if (boardRepository.existsById(boardId) == true) {
            boardRepository.deleteById(boardId);
            return true;
        } else {
            return false;
        }
    }
}
