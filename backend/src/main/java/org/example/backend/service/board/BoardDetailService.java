package org.example.backend.service.board;

import lombok.RequiredArgsConstructor;
import org.example.backend.model.dto.board.IBoardDetailDto;
import org.example.backend.model.dto.board.IBoardDto;
import org.example.backend.model.dto.board.IReplyDto;
import org.example.backend.model.entity.CmCode;
import org.example.backend.model.entity.board.Board;
import org.example.backend.model.entity.board.Place;
import org.example.backend.model.entity.board.Reply;
import org.example.backend.model.entity.board.Vote;
import org.example.backend.repository.board.BoardRepository;
import org.example.backend.repository.board.PlaceRepository;
import org.example.backend.repository.board.ReplyRepository;
import org.example.backend.repository.board.VoteRepository;
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
    private final VoteRepository voteRepository;
    private final PlaceRepository placeRepository;
    private final ReplyRepository replyRepository;

    // 게시글, 작성자 정보 조회
    public Optional<IBoardDto> findBoardAndMember(Long boardId) {
        Optional<IBoardDto> optional = boardRepository.findBoardAndMember(boardId);
        return optional;
    }

    // 코드번호로 코드명 조회
    public Optional<IBoardDetailDto> findCmCdName(String cmCd) {
        Optional<IBoardDetailDto> optional = boardRepository.findCmCdName(cmCd);
        return optional;
    }

    // 글번호로 투표 조회
    public List<Vote> findByVote(Long boardId) {
        List<Vote> list = voteRepository.findByBoardId(boardId);
        return list;
    }

    // 글번호로 장소 조회 (게시글 하나당 장소 하나)
    public Optional<Place> findPlace(Long boardId) {
        Optional<Place> optional = placeRepository.findByBoardId(boardId);
        return optional;
    }

    // 글번호로 이미지 조회
    public List<IBoardDetailDto> findBoardImg(Long boardId) {
        List<IBoardDetailDto> list = boardRepository.findBoardImg(boardId);
        return list;
    }

    // 글번호로 댓글 조회
    public List<IReplyDto> findReply(Long boardId) {
        List<IReplyDto> list = replyRepository.findByBoardId(boardId);
        return list;
    }
}
