package org.example.backend.service;

import lombok.extern.slf4j.Slf4j;
import org.example.backend.model.dto.ChatMessageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

/**
 * packageName : org.example.backend.service.profile
 * fileName : ChatSubService
 * author : kimtaewan
 * date : 2024-05-30
 * description :
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-05-30         kimtaewan          최초 생성
 */
@Service
@Slf4j
public class ChatSubService implements MessageListener {

    @Autowired
    private RedisTemplate<String, Object> chatRedisTemplate;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        ChatMessageDto chatMessage = (ChatMessageDto) chatRedisTemplate.getValueSerializer().deserialize(message.getBody());
        log.debug("Publishing to /topic/message: {}", chatMessage); // 로그 추가
        messagingTemplate.convertAndSend("/topic/message", chatMessage);
    }
}
