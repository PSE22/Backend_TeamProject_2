package org.example.backend.service.board;

import org.example.backend.model.entity.board.Board;
import org.example.backend.repository.board.FreeBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FreeBoardService {

    @Autowired
    FreeBoardRepository freeBoardRepository;

    //    TODO: 전체조회(read)
    public List<Board> findAll() {
        List<Board> list = freeBoardRepository.findAll();
        return list;
    }

    //    페이징 처리
    public Page<Board> findAllByFreeBoardTitleContaining(String boardTitle,
                                                                Pageable pageable) {
        Page<Board> page
                = freeBoardRepository
                .findAllByFreeBoardTitleContaining(boardTitle, pageable);
        return page;
    }

    //    상세조회
    public Optional<Board> findById(Long boardId) {
        //    JPA 상세조회 함수 실행
        Optional<Board> optionalFreeBoard
                = freeBoardRepository.findById(boardId);
        return optionalFreeBoard;
    }

    //    TODO: 등록(insert),수정(update)
    public Board save(Board board) {
        // 분류코드를 설정
        board.setBocode("BO03");
        //    JPA 저장 함수 실행 : return 값 : 저장된 객체
        Board board2 = freeBoardRepository.save(board);

        return board2;
    }

    // TODO: 삭제(delete)
    public boolean removeById(Long boardId) {
        // 해당 boardId에 해당하는 게시글 조회
        Optional<Board> optionalBoard = freeBoardRepository.findById(boardId);

        // 게시글이 존재하는 경우
        if (optionalBoard.isPresent()) {
            // 조회된 게시글의 분류코드가 BO03인지 확인
            Board board = optionalBoard.get();
            if ("BO03".equals(board.getBocode())) {
                // BO03 분류코드에 해당하는 게시글이면 삭제
                freeBoardRepository.deleteById(boardId);
                return true;
            } else {
                // BO03 분류코드가 아닌 경우 삭제하지 않고 false 반환
                return false;
            }
        } else {
            // 해당 boardId에 해당하는 게시글이 없는 경우 false 반환
            return false;
        }
    }

}
