package com.bootme.post.controller;

import com.bootme.auth.dto.AuthInfo;
import com.bootme.auth.util.Login;
import com.bootme.post.dto.CommentResponse;
import com.bootme.post.dto.PostDetailResponse;
import com.bootme.post.dto.PostRequest;
import com.bootme.post.dto.PostResponse;
import com.bootme.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<Void> addPost(@Login AuthInfo authInfo, @Valid PostRequest postRequest) {
        Long postId = postService.addPost(authInfo, postRequest);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Expose-Headers", "Location");
        return ResponseEntity.created(URI.create("/posts/" + postId)).headers(headers).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDetailResponse> findPost(@Login AuthInfo authInfo,
                                                       @PathVariable Long id) {
        PostDetailResponse postResponse = postService.findPost(authInfo.getMemberId(), id);
        return ResponseEntity.ok(postResponse);
    }

    @GetMapping
    public ResponseEntity<Page<PostResponse>> findAllPosts(
            @Login AuthInfo authInfo,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "popular") String sort,
            @RequestParam MultiValueMap<String, String> params
            ){
        Page<PostResponse> postPage = postService.findAllPosts(authInfo.getMemberId(), page, size, sort, params);
        return ResponseEntity.ok(postPage);
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<List<CommentResponse>> findCommentsByPostId(@Login AuthInfo authInfo,
                                                                      @PathVariable Long id) {
        List<CommentResponse> commentResponses = postService.findCommentsByPostId(authInfo.getMemberId(), id);
        return ResponseEntity.ok(commentResponses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> modifyPost(@Login AuthInfo authInfo,
                                           @PathVariable Long id,
                                           @Valid PostRequest postRequest){
        postService.modifyPost(authInfo, id, postRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@Login AuthInfo authInfo,
                                           @PathVariable Long id){
        postService.deletePost(authInfo, id);
        return ResponseEntity.noContent().build();
    }

}
