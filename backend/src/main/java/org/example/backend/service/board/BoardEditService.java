package org.example.backend.service.board;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.model.dto.board.*;
import org.example.backend.model.entity.board.Board;
import org.example.backend.model.entity.board.File;
import org.example.backend.model.entity.board.Place;
import org.example.backend.repository.board.BoardRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
@Slf4j
@Service
@RequiredArgsConstructor
public class BoardEditService {
    private final BoardRepository boardRepository;
    private final VoteService voteService;
    private final FileService fileService;
    private final PlaceService placeService;

    @Transactional
    public void update(BoardWriteDto boardWriteDto) {
        // BoardWriteDto에서 boardId를 가져옵니다.
        Long boardId = boardWriteDto.getBoardDto().getBoardId();

        // 수정할 기존 게시글 객체를 찾습니다.
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        // 기존 게시글 객체의 필드를 업데이트합니다.
        BoardDto boardDto = boardWriteDto.getBoardDto();
        board.setBoardContent(boardDto.getBoardContent());
        board.setBoardTitle(boardDto.getBoardTitle());
        board.setBocode(boardDto.getBocode());
        board.setSmcode(boardDto.getSmcode());
        board.setNoticeYn(boardDto.getNoticeYn());
        board.setMemberId(boardDto.getMemberId());

        // 저장
        boardRepository.save(board);

        // 파일, 투표, 장소 등의 저장 로직 추가
        if (boardWriteDto.getFileDtos() != null) {
            fileService.saveFiles(boardWriteDto.getFileDtos(), board.getBoardId());
        }
        if (boardWriteDto.getVoteDtos() != null) {
            voteService.saveVote(board.getBoardId(), boardWriteDto.getVoteDtos());
        }
        if (boardWriteDto.getPlaceDto() != null) {
            placeService.savePlace(board.getBoardId(), boardWriteDto.getPlaceDto());
        }
    }
}