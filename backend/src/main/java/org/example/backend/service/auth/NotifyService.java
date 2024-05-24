package org.example.backend.service.auth;

import lombok.extern.slf4j.Slf4j;
import org.example.backend.model.dto.NotifyDto;
import org.example.backend.model.entity.Notify;
import org.example.backend.model.entity.board.Board;
import org.example.backend.repository.board.BoardRepository;
import org.example.backend.repository.board.ReplyRepository;
import org.example.backend.repository.profile.NotifyRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * packageName : org.example.backend.service.profile
 * fileName : NotifyService
 * author : kimtaewan
 * date : 2024-05-23
 * description :
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-05-23         kimtaewan          최초 생성
 */
@Service
@Slf4j
public class NotifyService {
    @Autowired
    NotifyRepository notifyRepository;
    @Autowired
    BoardRepository boardRepository;
    @Autowired
    ReplyRepository replyRepository;
    @Autowired
    SseService sseService;
    @Autowired
    ModelMapper modelMapper;

    public void createBestNotify(Long boardId, NotifyDto notifyDto) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new RuntimeException("Board not found"));
        String content = board.getBoardTitle() + "이 베스트에 선정되었습니다.";
        Notify notify = new Notify();
        notify.setMemberId(board.getMemberId());
        notify.setNotiContent(content);
        notify.setNotiUrl(notifyDto.getNotiUrl());
        notifyRepository.save(notify);
        sseService.sendSseEvent(notify);
    }

    public void createHotTopicNotify(Long boardId, NotifyDto notifyDto) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new RuntimeException("Board not found"));
        String content = board.getBoardTitle() + "이 핫토픽에 선정되었습니다.";
        Notify notify = new Notify();
        notify.setMemberId(board.getMemberId());
        notify.setNotiContent(content);
        notify.setNotiUrl(notifyDto.getNotiUrl());
        notifyRepository.save(notify);
        sseService.sendSseEvent(notify);
    }

    public void createReplyNotify(Long boardId, NotifyDto notifyDto) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new RuntimeException("Board not found"));
        String content = board.getBoardTitle() + "에 새로운 댓글이 있습니다.";
        Notify notify = new Notify();
        notify.setMemberId(board.getMemberId());
        notify.setNotiContent(content);
        notify.setNotiUrl(notifyDto.getNotiUrl());
        notifyRepository.save(notify);
        sseService.sendSseEvent(notify);
    }

    public void readCheckAll(NotifyDto notifyDto) {
        notifyRepository.updateNotiCheck(notifyDto.getMemberId());
    }

    public void readCheck(Long notifyId) {
        Optional<Notify> notifyOptional = notifyRepository.findById(notifyId);
        notifyOptional.ifPresent(notify -> {
            notify.setNotiCheck("Y");
            notifyRepository.save(notify);
        });
    }
}