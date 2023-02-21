package com.bootme.member.controller;

import com.bootme.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequestMapping("/member")
@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/{memberId}/bookmarks/{courseId}")
    public ResponseEntity<Void> addBookmarkCourse(@PathVariable Long memberId, @PathVariable Long courseId){
        Long bookmarkCourseId = memberService.addBookmarkCourse(memberId, courseId);
        return ResponseEntity.created(URI.create("/member/" + memberId + "/bookmarks" + bookmarkCourseId)).build();
    }

    @DeleteMapping("/{memberId}/bookmarks/{courseId}")
    public ResponseEntity<Void> deleteBookmarkCourse(@PathVariable Long memberId, @PathVariable Long courseId) {
        memberService.deleteBookmarkCourse(memberId, courseId);
        return ResponseEntity.noContent().build();
    }

}
