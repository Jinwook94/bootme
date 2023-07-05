package com.bootme.sse;

import com.bootme.common.exception.SseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

import static com.bootme.common.exception.ErrorType.SSE_SEND_FAIL;

@Slf4j
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
                throw new SseException(SSE_SEND_FAIL, event.toString(), e);
            }
        }
    }

}
