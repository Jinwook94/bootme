package com.bootme.sse;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@RequiredArgsConstructor
@Service
public class SseService {

    private final SseEmitterManager sseEmitterManager;

    @Async
    public void emitEventToMember(Long memberId, SseEvent event) {
        SseEmitter emitter = sseEmitterManager.getEmitter(memberId);
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event().name(event.toString()).data(event.toString()));
            } catch (IOException | IllegalStateException e) {
                emitter.complete();
                sseEmitterManager.remove(emitter);
            }
        }
    }

}
