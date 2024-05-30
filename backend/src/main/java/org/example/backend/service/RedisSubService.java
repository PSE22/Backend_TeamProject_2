package org.example.backend.service;

import lombok.extern.slf4j.Slf4j;
import org.example.backend.model.dto.ChatMessageDto;
import org.example.backend.model.dto.NotifyDto;
import org.example.backend.service.auth.SseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

/**
 * packageName : org.example.backend.service
 * fileName : RedisSubService
 * author : kimtaewan
 * date : 2024-05-26
 * description :
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-05-26         kimtaewan          최초 생성
 */
@Service
@Slf4j
public class RedisSubService implements MessageListener {

    @Autowired
    private RedisTemplate<String, Object> notifyRedisTemplate;

    @Autowired
    private RedisTemplate<String, Object> chatRedisTemplate;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private SseService sseService;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        NotifyDto notifyDto = (NotifyDto) notifyRedisTemplate.getValueSerializer().deserialize(message.getBody());
        sseService.sendSseEvent(notifyDto);

        ChatMessageDto chatMessage = (ChatMessageDto) chatRedisTemplate.getValueSerializer().deserialize(message.getBody());
        messagingTemplate.convertAndSend("/topic/messages", chatMessage);
    }
}