package com.bootme.sse;

import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
@Getter
public class SseEmitterManager {

    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    void add(SseEmitter emitter) {
        this.emitters.add(emitter);
        emitter.onCompletion(() -> this.emitters.remove(emitter));
        emitter.onTimeout(emitter::complete);
    }

    public void remove(SseEmitter emitter) {
        this.emitters.remove(emitter);
    }

}
