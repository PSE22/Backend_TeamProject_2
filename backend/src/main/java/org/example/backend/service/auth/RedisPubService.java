package org.example.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * packageName : org.example.backend.service
 * fileName : redisPub
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
public class RedisPubService {
    @Autowired
    private RedisTemplate<String, Object> notifyRedisTemplate;

    @Autowired
    private RedisTemplate<String, Object> chatRedisTemplate;

    public void notifyPublish(String channel, Object message) {
        // 메세지 직렬화 후 레디스로 퍼블리싱
        notifyRedisTemplate.convertAndSend(channel, message);
    }

    public void chatPublish(String channel, Object message) {
        // 메세지 직렬화 후 레디스로 퍼블리싱
        chatRedisTemplate.convertAndSend(channel, message);
    }
}
