package org.example.backend.service.auth;

import lombok.extern.slf4j.Slf4j;
import org.example.backend.model.dto.NotifyDto;
import org.example.backend.service.RedisPubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
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

    private final ConcurrentHashMap<String, CopyOnWriteArrayList<SseEmitter>> sseEmitters = new ConcurrentHashMap<>();
    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

    public void sendSseEvent(NotifyDto notifyDto) {
        List<SseEmitter> emitters = sseEmitters.get(notifyDto.getMemberId());
        if (emitters != null) {
            for (SseEmitter emitter : emitters) {
                try {
                    log.debug("에미터로 알림 발송 성공: {}", emitter);
                    emitter.send(SseEmitter.event().name("notification").data(notifyDto));
                } catch (IOException e) {
                    log.error("에미터로 알림 발송 실패: {}", emitter, e);
                    emitter.completeWithError(e);
                    emitters.remove(emitter);
                }
            }
        }
    }

    public SseEmitter subscribe(SseEmitter emitter, String memberId) {
        // 이미 존재하는 리스트가 있는지 확인
        CopyOnWriteArrayList<SseEmitter> emitters = sseEmitters.get(memberId);
        if (emitters != null) {
            // 이미 존재하는 리스트가 있다면 그냥 해당 리스트에서 가져와서 반환
            emitters.add(emitter);
            log.info("이미 등록된 에미터 리스트를 사용합니다.: {}", emitters);
            log.info("등록된 에미터 수: {}", emitters.size());
            return emitter;
        }

        // 새로운 리스트를 생성하고 에미터를 추가
        CopyOnWriteArrayList<SseEmitter> newEmitters = new CopyOnWriteArrayList<>();
        newEmitters.add(emitter);
        sseEmitters.put(memberId, newEmitters);

        // 연결된 에미터 정보를 기록
        redisPubService.publish("notification", "새로운 구독자가 연결되었습니다.: " + memberId);
        log.info("새로운 에미터가 등록되었습니다.: {}", emitter);
        log.info("등록된 에미터 수: {}", newEmitters.size());

        // 에러, 완료, 타임아웃 콜백 설정
        emitter.onError((error) -> {
            log.error("onError callback", error);
            emitter.completeWithError(error);
            sseEmitters.get(memberId).remove(emitter);
        });

        emitter.onCompletion(() -> {
            log.info("onCompletion callback");
            sseEmitters.get(memberId).remove(emitter);
        });

        emitter.onTimeout(() -> {
            log.info("onTimeout callback");
            emitter.complete();
            sseEmitters.get(memberId).remove(emitter);
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