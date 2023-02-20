package com.bootme.webhook.controller;

import com.bootme.auth.service.AuthService;
import com.bootme.course.service.CourseService;
import com.bootme.webhook.dto.WebhookRequest;
import com.bootme.webhook.exception.InvalidEventException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Map;

import static com.bootme.common.exception.ErrorType.INVALID_EVENT;

@CrossOrigin
@RestController
@RequestMapping("/webhook")
public class WebhookController {

    private final AuthService authService;
    private final CourseService courseService;

    public WebhookController(AuthService authService,
                             CourseService courseService) {
        this.authService = authService;
        this.courseService = courseService;
    }

    @PostMapping
    public ResponseEntity<?> handleWebhook(@RequestHeader(name = "Bootme_Secret") String secret,
                                           @RequestBody WebhookRequest webhookRequest) throws GeneralSecurityException, IOException {
        final String COURSE_CLICKED = "courseClicked";
        final String COURSE_BOOKMARKED = "courseBookmarked";
        final String EVENT = webhookRequest.getEvent();
        final Map<String, String> DATA = webhookRequest.getData();
        final Long COURSE_ID = Long.parseLong(DATA.get("courseId"));
        final String jwt = authService.getToken(secret);

        authService.verifyToken(jwt);

        try {
            switch (EVENT) {
                case COURSE_CLICKED:
                    courseService.incrementClicks(COURSE_ID);
                    break;
                case COURSE_BOOKMARKED:
                    courseService.incrementBookmarks(COURSE_ID);
                    break;
                default:
                    throw new InvalidEventException(INVALID_EVENT, webhookRequest.getEvent());
            }
        } catch (InvalidEventException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
