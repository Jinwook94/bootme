package com.bootme.webhook.controller;

import com.bootme.auth.service.AuthService;
import com.bootme.common.exception.ValidationException;
import com.bootme.course.service.CourseService;
import com.bootme.post.service.PostService;
import com.bootme.webhook.dto.WebhookRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.bootme.common.exception.ErrorType.INVALID_EVENT;

@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping("/webhook")
public class WebhookController {

    private static final String COURSE_CLICKED = "courseClicked";
    private static final String COURSE_BOOKMARKED = "courseBookmarked";
    private static final String POST_CLICKED = "postClicked";
    private static final String POST_BOOKMARKED = "postBookmarked";

    private final AuthService authService;
    private final CourseService courseService;
    private final PostService postService;

    @PostMapping
    public ResponseEntity<String> handleWebhook(@RequestHeader(name = "Bootme-Secret") String secret,
                                                @RequestBody WebhookRequest webhookRequest) {
        String jwt = authService.getToken(secret);
        authService.verifyToken(jwt);
        processWebhookEvent(webhookRequest.getEvent(), webhookRequest.getData());
        return ResponseEntity.ok().build();
    }

    private void processWebhookEvent(String event, Map<String, String> data) {
        switch (event) {
            case COURSE_CLICKED:
                long courseId = Long.parseLong(data.get("courseId"));
                courseService.incrementClicks(courseId);
                break;
            case COURSE_BOOKMARKED:
                courseId = Long.parseLong(data.get("courseId"));
                courseService.incrementBookmarks(courseId);
                break;
            case POST_CLICKED:
                long postId = Long.parseLong(data.get("postId"));
                postService.incrementClicks(postId);
                break;
            case POST_BOOKMARKED:
                postId = Long.parseLong(data.get("postId"));
                postService.incrementBookmarks(postId);
                break;
            default:
                throw new ValidationException(INVALID_EVENT, event);
        }
    }

}
