package com.bootme.member.controller;

import com.bootme.auth.dto.AuthInfo;
import com.bootme.auth.util.Login;
import com.bootme.course.dto.CourseResponse;
import com.bootme.member.domain.Member;
import com.bootme.member.dto.MemberResponse;
import com.bootme.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequestMapping("/member")
@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/me")
    public ResponseEntity<MemberResponse> getMember(@Login AuthInfo authInfo) {
        Member member = memberService.getMemberById(authInfo.getMemberId());
        MemberResponse of = MemberResponse.of(member);
        return ResponseEntity.ok(of);
    }

    @GetMapping("/nickname/{nickname}/exists")
    public ResponseEntity<Boolean> checkNicknameDuplicate(@PathVariable String nickname){
        boolean isDuplicate = memberService.isNicknameDuplicate(nickname);
        return ResponseEntity.ok().body(isDuplicate);
    }

    @PostMapping("/{memberId}/bookmarks/{courseId}")
    public ResponseEntity<Void> addBookmarkCourse(@PathVariable Long memberId, @PathVariable Long courseId){
        Long bookmarkCourseId = memberService.addBookmarkCourse(memberId, courseId);
        return ResponseEntity.created(URI.create("/member/" + memberId + "/bookmarks" + bookmarkCourseId)).build();
    }

    @GetMapping("/{memberId}/bookmarks")
    public ResponseEntity<Page<CourseResponse>> findBookmarkCourses(
            @PathVariable Long memberId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<CourseResponse> bookmarkCoursePage = memberService.findBookmarkCourses(memberId, page, size);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Expose-Headers", "X-Total-Count");
        headers.add("X-Total-Count", String.valueOf(bookmarkCoursePage.getTotalElements()));
        headers.add("Content-Range", getContentRange(bookmarkCoursePage));

        return ResponseEntity.ok().headers(headers).body(bookmarkCoursePage);
    }

    private String getContentRange(Page<CourseResponse> coursePage) {
        int startRange = coursePage.getNumber() * coursePage.getSize();
        int endRange = startRange + coursePage.getNumberOfElements();
        long totalElements = coursePage.getTotalElements();

        return String.format("courses %d-%d/%d", startRange, endRange, totalElements);
    }

    @GetMapping("/{memberId}/bookmarks/courseIds")
    public ResponseEntity<List<Long>> findBookmarkCourseIds(@PathVariable Long memberId){
        List<Long> bookmarkCourses = memberService.findBookmarkCourseIds(memberId);
        return ResponseEntity.ok().body(bookmarkCourses);
    }

    @DeleteMapping("/{memberId}/bookmarks/{courseId}")
    public ResponseEntity<Void> deleteBookmarkCourse(@PathVariable Long memberId, @PathVariable Long courseId) {
        memberService.deleteBookmarkCourse(memberId, courseId);
        return ResponseEntity.noContent().build();
    }

}
