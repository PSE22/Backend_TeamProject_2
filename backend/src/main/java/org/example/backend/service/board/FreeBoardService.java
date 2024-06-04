package org.example.backend.service.board;

import lombok.RequiredArgsConstructor;
import org.example.backend.model.dto.board.BoardFileDto;
import org.example.backend.model.dto.board.FileDto;
import org.example.backend.model.dto.board.IFreeBoardDto;
import org.example.backend.model.dto.board.VoteDto;
import org.example.backend.model.entity.board.Board;
import org.example.backend.model.entity.board.File;
import org.example.backend.model.entity.board.Place;
import org.example.backend.repository.board.FreeBoardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FreeBoardService {

    private final FreeBoardRepository freeBoardRepository;
    private final VoteService voteService;
    private final PlaceService placeService;
    private final FileService fileService;
    private final BoardFileService boardFileService;

    //    TODO: 전체조회(read)
    public Page<IFreeBoardDto> findAllByBoardTitleContaining(String boardTitle, Pageable pageable) {
        return freeBoardRepository.findAllByFrBoardTitleContaining(boardTitle, pageable);
    }

    //    동호회 공지 조회
    public List<IFreeBoardDto> findByCodeAndNotice() {
        List<IFreeBoardDto> list = freeBoardRepository.findByFreeNotice();
        return list;
    }

    //    페이징 처리
    public Page<IFreeBoardDto> findAllByFreeBoardTitleContaining(String boardTitle,
                                                                 Pageable pageable) {
        Page<IFreeBoardDto> page
                = freeBoardRepository
                .findAllByFrBoardTitleContaining(boardTitle, pageable);
        return page;
    }

    //    상세조회
    public Optional<Board> findById(Long boardId) {
        //    JPA 상세조회 함수 실행
        Optional<Board> optionalFreeBoard
                = freeBoardRepository.findById(boardId);
        return optionalFreeBoard;
    }

    @Transactional(rollbackFor = Exception.class)
    //    TODO: 등록(insert),수정(update)
    public void save(Board board, List<VoteDto> voteDtos, Place place, List<FileDto> fileDtos, List<BoardFileDto> boardFileDtos) {
        // 분류코드를 설정
        board.setBocode("BO03");

        // JPA 저장 함수 실행 : return 값 : 저장된 객체
        Board board2 = freeBoardRepository.save(board);

        // 저장된 board의 boardId를 객체로 변환
        Long boardId = board2.getBoardId();
        voteService.saveVote(boardId, voteDtos);

        placeService.savePlace(boardId, place);

        fileService.saveFiles(fileDtos);

        boardFileService.saveBoardFile(boardId, boardFileDtos);
        }

    public void update(Board board) {
        freeBoardRepository.save(board);
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
