package org.example.backend.service.auth;

import lombok.extern.slf4j.Slf4j;
import org.example.backend.model.entity.Notify;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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

    void sendSseEvent(Notify notify) {
        SseEmitter emitter = sseEmitters.get(notify.getMemberId());
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event().name("notification").data(notify));
            } catch (IOException e) {
                sseEmitters.remove(notify.getMemberId());
            }
        }
    }

    public SseEmitter subscribe(SseEmitter emitter, String memberId) {
        sseEmitters.put(memberId, emitter);
        System.out.println("new emitter added: " + emitter);
        System.out.println("emitter list size: " + sseEmitters.size());
        emitter.onCompletion(() -> {
            System.out.println("onCompletion callback");
            sseEmitters.remove(memberId, emitter);
        });
        emitter.onTimeout(() -> {
            System.out.println("onTimeout callback");
            emitter.complete();
        });

        return emitter;
    }
}
