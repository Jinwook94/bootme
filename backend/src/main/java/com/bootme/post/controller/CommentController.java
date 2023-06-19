package com.bootme.post.controller;

import com.bootme.auth.dto.AuthInfo;
import com.bootme.auth.util.Login;
import com.bootme.post.dto.CommentRequest;
import com.bootme.post.dto.CommentResponse;
import com.bootme.post.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/posts/{id}/comments")
    public ResponseEntity<Void> addComment(@Login AuthInfo authInfo,
                                           @PathVariable(name = "id") Long postId,
                                           @Valid @RequestBody CommentRequest commentRequest){
        Long commentId = commentService.addComment(authInfo, postId, commentRequest);
        return ResponseEntity.created(URI.create("/comments/" + commentId)).build();
    }

    @GetMapping("/comments/{id}")
    public ResponseEntity<CommentResponse> findComment(@Login AuthInfo authInfo,
                                                       @PathVariable Long id) {
        CommentResponse commentResponse = commentService.findComment(authInfo, id);
        return ResponseEntity.ok(commentResponse);
    }

    @PutMapping("/comments/{id}")
    public ResponseEntity<Void> modifyComment(@Login AuthInfo authInfo,
                                              @PathVariable(name = "id") Long id,
                                              @Valid @RequestBody CommentRequest commentRequest){
        commentService.modifyComment(authInfo, id, commentRequest.getContent());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/comments/{id}")
    public ResponseEntity<Void> deleteComment(@Login AuthInfo authInfo,
                                              @PathVariable Long id) {
        commentService.deleteComment(authInfo, id);
        return ResponseEntity.noContent().build();
    }

}
