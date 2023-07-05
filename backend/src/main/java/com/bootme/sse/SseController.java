package com.bootme.sse;

import com.bootme.auth.dto.AuthInfo;
import com.bootme.auth.util.Login;
import com.bootme.common.exception.SseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static com.bootme.common.exception.ErrorType.SSE_CONNECT_FAIL;

@Slf4j
@RequiredArgsConstructor
@RestController
public class SseController {

    private final SseEmitterManager sseEmitterManager;

    @GetMapping(value = "/connect", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public DeferredResult<ResponseEntity<SseEmitter>> connect(@Login AuthInfo authinfo) {
        Long memberId = authinfo.getMemberId();
        SseEmitter emitter = new SseEmitter(TimeUnit.MINUTES.toMillis(5));
        DeferredResult<ResponseEntity<SseEmitter>> deferredResult = new DeferredResult<>();

        deferredResult.onError((Throwable t) -> log.error("Error in DeferredResult", t));

        CompletableFuture.runAsync(() -> {
            try {
                sseEmitterManager.add(memberId, emitter);
                emitter.send(SseEmitter.event()
                        .name(SseEvent.CONNECT.toString())
                        .data(SseEvent.CONNECT.toString()));
                deferredResult.setResult(ResponseEntity.ok(emitter));
                log.info("SSE connection successfully established for member id: {}", memberId);
            } catch (IOException | IllegalStateException e) {
                log.error("Failed to establish SSE connection for member id: {}", memberId, e);
                deferredResult.setErrorResult(ResponseEntity.status(500).build());
                throw new SseException(SSE_CONNECT_FAIL, SseEvent.CONNECT.toString(), e);
            }
        }).exceptionally(ex -> {
            deferredResult.setErrorResult(ResponseEntity.status(500).build());
            return null;
        });

        return deferredResult;
    }

}
