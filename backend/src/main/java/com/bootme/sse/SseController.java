package com.bootme.sse;

import com.bootme.common.exception.SseException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

import static com.bootme.common.exception.ErrorType.SSE_CONNECT_FAIL;

@RequiredArgsConstructor
@RestController
public class SseController {

    private final SseEmitterManager sseEmitterManager;

    @GetMapping(value = "/connect", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<SseEmitter> connect() {
        SseEmitter emitter = new SseEmitter(60 * 1000L);
        sseEmitterManager.add(emitter);
        try {
            emitter.send(SseEmitter.event()
                    .name(SseEvent.CONNECT.toString())
                    .data(SseEvent.CONNECT.toString()));
        } catch (IOException e) {
            throw new SseException(SSE_CONNECT_FAIL, SseEvent.CONNECT.toString(), e);
        }
        return ResponseEntity.ok(emitter);
    }

}
