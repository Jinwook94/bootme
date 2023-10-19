package com.bootme.sse;

import com.bootme.auth.dto.AuthInfo;
import com.bootme.auth.util.Login;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Slf4j
@RequiredArgsConstructor
@RestController
public class SseController {

    private final SseService sseService;

    @GetMapping(value = "/connect", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<SseEmitter> connect(@Login AuthInfo authinfo) {
        SseEmitter emitter = sseService.connect(authinfo.getMemberId());
        return ResponseEntity.ok().body(emitter);
    }

}
