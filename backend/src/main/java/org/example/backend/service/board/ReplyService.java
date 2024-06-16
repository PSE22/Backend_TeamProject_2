package org.example.backend.service.board;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.model.dto.board.BoardDto;
import org.example.backend.model.dto.NotifyDto;
import org.example.backend.model.dto.board.IReplyDto;
import org.example.backend.model.dto.board.Reply.IDelReplyDto;
import org.example.backend.model.dto.board.Reply.ReplyDto;
import org.example.backend.model.entity.board.*;
import org.example.backend.repository.board.FileRepository;
import org.example.backend.repository.board.ReplyFileRepository;
import org.example.backend.repository.board.ReplyReportRepository;
import org.example.backend.repository.board.ReplyRepository;
import org.example.backend.service.auth.NotifyService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
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
    private final NotifyService notifyService;
    private final ReplyRepository replyRepository;
    private final ReplyReportRepository replyReportRepository;
    private final FileRepository fileRepository;
    private final ReplyFileRepository replyFileRepository;
    ModelMapper modelMapper = new ModelMapper();

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

    // 댓글 저장
    @Transactional
    public Reply saveReply(ReplyDto replyDto, MultipartFile file) {
        // DTO -> Entity 매핑
        Reply reply = modelMapper.map(replyDto, Reply.class);

        // Reply 저장
        reply.setBoardId(replyDto.getBoardId());
        reply.setMemberId(replyDto.getMemberId());
        reply.setReply(replyDto.getReply());
        reply.setReReply(replyDto.getReReply());
        replyRepository.save(reply);

        // File, ReplyFile 저장
        if (file != null && !file.isEmpty()) {
            File file2 = saveReplyFile(null, file);

            ReplyFile replyFile = modelMapper.map(replyDto, ReplyFile.class);

            replyFile.setReplyId(reply.getReplyId());
            replyFile.setUuid(file2.getUuid());
            replyFileRepository.save(replyFile);
        }

        // 댓글 알림
//        Long boardId = replyDto.getBoardId();
//        NotifyDto notifyDto = new NotifyDto();
////        notifyDto.setNotiUrl();
//        notifyService.createReplyNotify(boardId, notifyDto);
//
//        // 핫토픽 알림
//        int count = countReply(boardId);
//        if (count >= 10) {
//            notifyService.createHotTopicNotify(boardId, notifyDto);
//        }

        return reply;
    }



    // 댓글 수정
    @Transactional
    public Reply updateReply(ReplyDto replyDto, MultipartFile file) {

        // replyId로 기존 댓글 찾기
        Reply reply = replyRepository.findById(replyDto.getReplyId())
                .orElseThrow(() -> new RuntimeException("댓글을 찾을 수 없습니다."));

        // Reply 저장
        reply.setBoardId(replyDto.getBoardId());
        reply.setMemberId(replyDto.getMemberId());
        reply.setReply(replyDto.getReply());
        reply.setReReply(replyDto.getReReply());

        // 기존 파일 삭제 로직
        List<ReplyFile> existingFiles = replyFileRepository.findByReplyId(reply.getReplyId());
        for (ReplyFile existingFile : existingFiles) {
            // ReplyFile 에서 파일 정보 삭제
            replyFileRepository.delete(existingFile);
            // File 에서 삭제
            deleteFile(existingFile.getUuid());
        }

        replyRepository.save(reply);

        // File, ReplyFile 저장
        if (file != null && !file.isEmpty()) {
            File file2 = saveReplyFile(null, file);

            ReplyFile replyFile = new ReplyFile();
            replyFile.setReplyId(reply.getReplyId());
            replyFile.setUuid(file2.getUuid());
            replyFileRepository.save(replyFile);
        }

        return reply;
    }

    // File 테이블에서 파일 삭제 (hard delete)
    public void deleteFile(String uuid) {
        if(fileRepository.existsById(uuid) == true) {
            // hard delete
            fileRepository.deleteById2(uuid);
        }
    }

    // 댓글 파일 첨부 저장
    public File saveReplyFile(String uuid, MultipartFile file) {
        File file2 = null;

        try {
            if(uuid == null) {
                String tmpUuid = UUID.randomUUID()
                        .toString()
                        .replace("-", "");

                String fileDownload = ServletUriComponentsBuilder
                        .fromCurrentContextPath()               // spring 기본주소 : http://localhost:9000
                        .path("/api/board/file/upload2/")        // 추가 경로 넣기
                        .path(tmpUuid)                          // uuid 넣기
                        .toUriString();                         // 합치기
                // File 객체 생성(생성자, setter) + save()
                File file1 = new File(
                        tmpUuid,                        // 기존 uuid
                        fileDownload,                   // 파일 다운로드 url
                        file.getOriginalFilename(),     // 업로드 할때 파일명
                        file.getBytes()                 // 업로드 이미지
                );
                file2 = fileRepository.save(file1);  // DB 수정

                ReplyFile replyFile = new ReplyFile();
                replyFile.setUuid(file2.getUuid());
            } else {
                String fileDownload = ServletUriComponentsBuilder
                        .fromCurrentContextPath()           // spring 기본주소
                        .path("/api/board/file/upload2/")    // 추가 경로 넣기
                        .path(uuid)                         // uuid 넣기
                        .toUriString();                     // 합치기
                // File 객체 생성(생성자, setter) + save()
                File file1 = new File(
                        uuid,                        // 기존 uuid
                        fileDownload,                // 파일 다운로드 url
                        file.getOriginalFilename(),  // 업로드 할때 파일명
                        file.getBytes()              // 업로드 이미지
                );
                file2 = fileRepository.save(file1);  // DB 수정
            }
        } catch (Exception e) {
            log.debug(e.getMessage());
        }
        return file2;
    }

    // 댓글 신고 데이터 저장
    public ReplyReport saveReplyReport(ReplyReport replyReport) {
        ReplyReport replyReport2 = replyReportRepository.save(replyReport);
        return replyReport2;
    }

//    댓글 삭제
    @Transactional
    public void removeReply(Long replyId) {
        List<IDelReplyDto> delReply = replyRepository.findByReplyId(replyId);
        log.debug("댓글 삭제 디버깅 111");
        for (IDelReplyDto replyDto : delReply) {
            if (replyDto.getUuid() != null) {
                replyFileRepository.deleteByUuid(replyDto.getUuid());
                log.debug("댓글 삭제 디버깅 222");
                fileRepository.deleteById(replyDto.getUuid());
                log.debug("댓글 삭제 디버깅 333");
            }
            replyRepository.deleteById(replyDto.getReplyId());
        }
    }
}
