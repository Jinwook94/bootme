package com.bootme.bookmark.controller;

import com.bootme.bookmark.service.PostBookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequestMapping("/bookmarks/{memberId}/posts")
@RequiredArgsConstructor
@RestController
public class PostBookmarkController {

    private final PostBookmarkService postBookmarkService;

    @PostMapping("/{postId}")
    public ResponseEntity<Void> addPostBookmark(@PathVariable Long memberId, @PathVariable Long postId){
        Long bookmarkPostId = postBookmarkService.addPostBookmark(memberId, postId);
        return ResponseEntity.created(URI.create("/member/" + memberId + "/bookmarks" + bookmarkPostId)).build();
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePostBookmark(@PathVariable Long memberId, @PathVariable Long postId) {
        postBookmarkService.deletePostBookmark(memberId, postId);
        return ResponseEntity.noContent().build();
    }

}
