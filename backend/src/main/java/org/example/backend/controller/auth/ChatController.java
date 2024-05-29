package org.example.backend.controller.auth;

import lombok.extern.slf4j.Slf4j;
import org.example.backend.model.dto.ChatMessageDto;
import org.example.backend.model.entity.auth.Member;
import org.example.backend.service.RedisPubService;
import org.example.backend.service.auth.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * packageName : org.example.backend.controller.auth
 * fileName : ChatController
 * author : kimtaewan
 * date : 2024-05-29
 * description :
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-05-29         kimtaewan          최초 생성
 */
@RestController
@Slf4j
public class ChatController {
    @Autowired
    RedisPubService redisPubService;
    @Autowired
    MemberService memberService;

    @MessageMapping("/message")
    public void sendMessage(ChatMessageDto message) {
        Optional<Member> member = memberService.findById(message.getMemberId());
        if (member.isEmpty() == false) {
            message.setNickname(member.get().getNickname());
            redisPubService.chatPublish("chat", message);
        } else {
            log.debug("ID가 존재하지 않습니다.");
        }
    }
}
