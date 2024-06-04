package org.example.backend.service.board;

import lombok.RequiredArgsConstructor;
import org.example.backend.model.dto.board.BoardFileDto;
import org.example.backend.model.dto.board.VoteDto;
import org.example.backend.model.entity.board.Board;
import org.example.backend.model.entity.board.File;
import org.example.backend.model.entity.board.Place;
import org.example.backend.repository.board.BoardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
    private final PlaceService placeService;
    private final FileService fileService;
    private final BoardFileService boardFileService;


    //    투표 기능
    @Transactional
    public void save(Board board, List<VoteDto> voteDtos, Place place, List<File> files, List<BoardFileDto> boardFileDtos) {
        // JPA 저장 함수 실행 : return 값 : 저장된 객체
        Board board2 = boardRepository.save(board);
        // 저장된 board의 boardId를 객체로 변환
        Long boardId = board2.getBoardId();

//        null 체크
        Optional.ofNullable(voteDtos).ifPresent(dtos -> voteService.saveVote(boardId, dtos));
        Optional.ofNullable(place).ifPresent(p -> placeService.savePlace(boardId, p));
        Optional.ofNullable(files).ifPresent(fileService::saveFiles);
        Optional.ofNullable(boardFileDtos).ifPresent(dtos -> boardFileService.saveBoardFile(boardId, dtos));
    }
}
