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

    private static final ConcurrentHashMap<String, CopyOnWriteArrayList<SseEmitter>> sseEmitters = new ConcurrentHashMap<>();
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
        // 아이디가 이미 구독중인지 확인
        CopyOnWriteArrayList<SseEmitter> emitters = sseEmitters.get(memberId);
        if (emitters != null) {
            // 이미 구독중인 아이디일 경우 에미터만 추가 생성
            emitters.add(emitter);
            log.info("이미 구독중인 아이디를 사용합니다.: {}" + memberId, emitters);
            log.info("등록된 에미터 수: {}", emitters.size());
            return emitter;
        }

        // 새로운 아이디로 구독하기
        CopyOnWriteArrayList<SseEmitter> newEmitters = new CopyOnWriteArrayList<>();
        newEmitters.add(emitter);
        sseEmitters.put(memberId, newEmitters);

        // 연결된 구독정보를 기록
        redisPubService.notifyPublish("notification", "새로운 구독자가 연결되었습니다.: " + memberId);
        log.info("새로운 구독자가 등록되었습니다.: {}" + memberId, emitter);
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
                    sendPing(memberId);
                    Thread.sleep(60000); // 1분에 한 번씩 보냄
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    log.error("SseTask interrupted", e);
                }
            }
        }, executorService);

        return emitter;
    }

    private void sendPing(String memberId) {
        CopyOnWriteArrayList<SseEmitter> emitters = sseEmitters.get(memberId);
        if (emitters != null) {
            for (SseEmitter emitter : emitters) {
                try {
                    emitter.send("ping");
                    log.info("핑 발송: {}", emitter);
                } catch (IOException e) {
                    log.error("에러 발생: {}", emitter, e);
                    removeFromEmitterList(emitter);
                }
            }
        }
    }

    public static void removeFromEmitterList(SseEmitter emitter) {
        // 완료된 SseEmitter를 감지하여 리스트에서 삭제
        sseEmitters.forEach((memberId, emitters) -> {
            if (emitters.contains(emitter)) {
                emitters.remove(emitter);
                log.info("에미터 삭제: {}", emitter);
                log.info("현재 에미터 수: {}", emitters.size());
            }
        });
    }
}