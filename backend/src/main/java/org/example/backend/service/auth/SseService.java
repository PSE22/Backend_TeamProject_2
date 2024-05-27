package org.example.backend.service.auth;

import lombok.extern.slf4j.Slf4j;
import org.example.backend.model.dto.NotifyDto;
import org.example.backend.service.RedisPubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.concurrent.*;

import java.io.IOException;
import java.util.Map;

/**
 * packageName : org.example.backend.service.auth
 * fileName : SseService
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
@Component
@Slf4j
public class SseService {

    @Autowired
    RedisPubService redisPubService;

    private final Map<String, SseEmitter> sseEmitters = new ConcurrentHashMap<>();
    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

    public void sendSseEvent(NotifyDto notifyDto) {
        SseEmitter emitter = sseEmitters.get(notifyDto.getMemberId());
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event().name("notification").data(notifyDto));
            } catch (IOException e) {
                emitter.completeWithError(e);
                sseEmitters.remove(notifyDto.getMemberId(), emitter);
            }
        }
    }

    public SseEmitter subscribe(SseEmitter emitter, String memberId) {
        sseEmitters.put(memberId, emitter);
        redisPubService.publish("notification", "새로운 구독자가 연결되었습니다.: " + memberId);
        log.info("새로운 에미터가 등록되었습니다.: {}", emitter);
        log.info("등록된 에미터 수: {}", sseEmitters.size());

        emitter.onError((error) -> {
            log.error("onError callback", error);
            emitter.completeWithError(error);
            sseEmitters.remove(memberId, emitter);
        });

        emitter.onCompletion(() -> {
            log.info("onCompletion callback");
            sseEmitters.remove(memberId, emitter);
        });

        emitter.onTimeout(() -> {
            log.info("onTimeout callback");
            emitter.complete();
            sseEmitters.remove(memberId, emitter);
        });

        // 비동기 처리
        CompletableFuture.runAsync(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    sendPing(emitter);
                    Thread.sleep(60000); // 1분에 한 번씩 보냄
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    log.error("SseTask interrupted", e);
                }
            }
        }, executorService);

        return emitter;
    }

    private void sendPing(SseEmitter emitter) {
        try {
            emitter.send("ping");
            log.info("핑 {}", emitter);
        } catch (Exception e) {
            log.error("Error sending ping", e);
        }
    }
}