package org.example.backend.service.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.model.dto.ChatMessageDto;
import org.example.backend.service.ChatSubService;
import org.example.backend.service.RedisPubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

/**
 * packageName : org.example.backend.service.auth
 * fileName : ChatService
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
@Service
@Slf4j
@RequiredArgsConstructor
public class ChatService {
    @Autowired
    RedisPubService redisPubService;
    @Autowired
    ChatSubService chatSubService;
    private final ConcurrentHashMap<String, ChannelTopic> topics = new ConcurrentHashMap<>();
    private final RedisMessageListenerContainer redisMessageListener;

    public void message(ChatMessageDto message) {
        String channel = "chat";

        topics.computeIfAbsent(channel, ch -> {
            ChannelTopic topic = new ChannelTopic(ch);
            redisMessageListener.addMessageListener(chatSubService, topic);
            return topic;
        });

        redisPubService.chatPublish(channel, message);
    }
}
