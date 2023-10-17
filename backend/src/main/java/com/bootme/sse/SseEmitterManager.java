package com.bootme.sse;

import com.bootme.common.exception.SseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.bootme.common.exception.ErrorType.SSE_SEND_FAIL;

@Slf4j
@Component
public class SseEmitterManager {

    private final Map<Long, SseEmitter> emitterMap = new ConcurrentHashMap<>();

    public void add(Long memberId, SseEmitter emitter) {
        this.emitterMap.put(memberId, emitter);
        emitter.onTimeout(() -> {
            emitter.complete();
            log.info("SSE connection timed out for member id: {}", memberId);
        });
        emitter.onCompletion(() -> {
            this.emitterMap.remove(memberId, emitter);
            log.info("SSE connection completed for member id: {}", memberId);
        });
        emitter.onError((Throwable t) -> {
            this.emitterMap.remove(memberId, emitter);
            log.error("Error in SseEmitter for member id: {}", memberId, t);
            throw new SseException(SSE_SEND_FAIL, t);
        });
    }

    public SseEmitter getEmitter(Long memberId) {
        return this.emitterMap.get(memberId);
    }

    public void remove(SseEmitter emitter) {
        this.emitterMap.values().remove(emitter);
    }

}