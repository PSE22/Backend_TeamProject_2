package org.example.backend.service.board;

import lombok.RequiredArgsConstructor;
import org.example.backend.model.dto.board.IReplyDto;
import org.example.backend.model.entity.board.Reply;
import org.example.backend.repository.board.ReplyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * packageName : org.example.backend.service.board
 * fileName : ReplyService
 * author : SAMSUNG
 * date : 2024-06-04
 * description :
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-06-04         SAMSUNG          최초 생성
 */
@Service
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository replyRepository;

    // 글번호로 댓글 조회
    public List<IReplyDto> findReply(Long boardId) {
        List<IReplyDto> list = replyRepository.findReply(boardId);
        return list;
    }

    // 대댓글 조회
    public List<IReplyDto> findReReply(Long boardId, Long replyId) {
        List<IReplyDto> list = replyRepository.findReReply(boardId, replyId);
        return list;
    }

    // 댓글 수 조회
    public Integer countReply(Long boardId) {
        Integer count = replyRepository.countReply(boardId);
        return count;
    }

    // 작성한 댓글 저장
    public Reply saveReply(Reply reply) {
        Reply reply2 = replyRepository.save(reply);
        return reply2;
    }
}
