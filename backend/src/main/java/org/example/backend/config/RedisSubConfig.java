package org.example.backend.config;

import org.example.backend.service.ChatSubService;
import org.example.backend.service.auth.NotifySubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

/**
 * packageName : org.example.backend.config
 * fileName : RedisSubConfig
 * author : kimtaewan
 * date : 2024-05-26
 * description : 레디스 채널 설정
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-05-26         kimtaewan          최초 생성
 */
@Configuration
public class RedisSubConfig {

    @Autowired
    private NotifySubService notifySubService;

    @Autowired
    private ChatSubService chatSubService;

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Bean
    public RedisMessageListenerContainer redisContainer() {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);
        // 채널 추가
        container.addMessageListener(notifySubService, new ChannelTopic("notification"));
        container.addMessageListener(chatSubService, new ChannelTopic("chat"));
        return container;
    }
}