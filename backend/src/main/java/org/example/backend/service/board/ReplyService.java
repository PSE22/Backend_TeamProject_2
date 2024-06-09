package org.example.backend.service.board;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.model.dto.board.IReplyDto;
import org.example.backend.model.entity.board.*;
import org.example.backend.repository.board.FileRepository;
import org.example.backend.repository.board.ReplyFileRepository;
import org.example.backend.repository.board.ReplyReportRepository;
import org.example.backend.repository.board.ReplyRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

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
@Slf4j
@Service
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final ReplyReportRepository replyReportRepository;
    private final FileRepository fileRepository;
    private final ReplyFileRepository replyFileRepository;

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

    // 댓글 저장/수정
    public Reply saveReply(Reply reply) {
        Reply reply2 = replyRepository.save(reply);
        return reply2;
    }

    // 댓글 파일 저장
    @Transactional
    public void saveFile(MultipartFile file, String fileUrl) throws IOException {
        File file1 = new File();
        file1.setFileUrl(fileUrl);
        file1.setData(file.getBytes());

        fileRepository.save(file1);

        ReplyFile replyFile = new ReplyFile();
        replyFile.setUuid(replyFile.getUuid());

        replyFileRepository.save(replyFile);
    }

    // 댓글 신고 데이터 저장
    public ReplyReport saveReplyReport(ReplyReport replyReport) {
        ReplyReport replyReport2 = replyReportRepository.save(replyReport);
        return replyReport2;
    }
}
