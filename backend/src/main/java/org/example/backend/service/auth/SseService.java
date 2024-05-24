package org.example.backend.service.auth;

import lombok.extern.slf4j.Slf4j;
import org.example.backend.model.dto.NotifyDto;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

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

    private final Map<String, SseEmitter> sseEmitters = new ConcurrentHashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    @Transactional
    void sendSseEvent(NotifyDto notifyDto) {
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

    @Transactional
    public SseEmitter subscribe(SseEmitter emitter, String memberId) {
        sseEmitters.put(memberId, emitter);
        log.info("새로운 에미터 등록 {}", emitter);
        log.info("등록된 에미터 수 {}", sseEmitters.size());

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

        scheduler.scheduleAtFixedRate(() -> sendPing(emitter), 0, 1, TimeUnit.MINUTES);

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
