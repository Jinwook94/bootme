package com.bootme.sse;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SseService {

    private final SseEmitterManager sseEmitterManager;

    public void emitEventToClients(SseEvent event) {
        List<SseEmitter> sseEmitterList = sseEmitterManager.getEmitters();
        for (SseEmitter emitter : sseEmitterList) {
            try {
                emitter.send(SseEmitter.event().name(event.toString()).data(event.toString()));
            } catch (IOException e) {
                emitter.complete();
                sseEmitterManager.remove(emitter);
            }
        }
    }

}
