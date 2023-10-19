package com.bootme.sse;

import com.bootme.common.exception.SseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

import static com.bootme.common.exception.ErrorType.SSE_CONNECT_FAIL;
import static com.bootme.common.exception.ErrorType.SSE_SEND_FAIL;

@Slf4j
@RequiredArgsConstructor
@Service
public class SseService {

    private final SseEmitterManager sseEmitterManager;

    public SseEmitter connect(Long memberId) throws SseException {
        try {
            SseEmitter emitter = sseEmitterManager.createEmitter();
            sseEmitterManager.add(memberId, emitter);

            emitter.send(SseEmitter.event()
                    .name(SseEvent.CONNECT.toString())
                    .data(SseEvent.CONNECT.toString()));

            log.info("SSE connection successfully established for member id: {}", memberId);
            return emitter;
        } catch (IOException | IllegalStateException | NullPointerException e) {
            log.error("Failed to establish SSE connection for member id: {}", memberId, e);
            throw new SseException(SSE_CONNECT_FAIL, SseEvent.CONNECT.toString(), e);
        }
    }

    public void emitEventToMember(Long memberId, SseEvent event) throws SseException {
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
