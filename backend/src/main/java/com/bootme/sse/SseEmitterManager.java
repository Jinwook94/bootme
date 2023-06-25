package com.bootme.sse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SseEmitterManager {

    private final Map<Long, SseEmitter> emitterMap = new ConcurrentHashMap<>();

    void add(Long memberId, SseEmitter emitter) {
        this.emitterMap.put(memberId, emitter);
        emitter.onCompletion(() -> this.emitterMap.remove(memberId, emitter));
        emitter.onTimeout(emitter::complete);
    }

    public SseEmitter getEmitter(Long memberId) {
        return this.emitterMap.get(memberId);
    }

    public void remove(SseEmitter emitter) {
        this.emitterMap.values().remove(emitter);
    }
}
