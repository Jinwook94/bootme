package com.bootme.bookmark.controller;

import com.bootme.bookmark.service.CourseBookmarkService;
import com.bootme.course.dto.CourseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequestMapping("/bookmarks/{memberId}/courses")
@RequiredArgsConstructor
@RestController
public class CourseBookmarkController {

    private final CourseBookmarkService courseBookmarkService;

    @PostMapping("/{courseId}")
    public ResponseEntity<Void> addCourseBookmark(@PathVariable Long memberId, @PathVariable Long courseId){
        Long courseBookmarkId = courseBookmarkService.addCourseBookmark(memberId, courseId);
        return ResponseEntity.created(URI.create("/bookmarks/" + memberId + "/courses" + courseBookmarkId)).build();
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<Void> deleteCourseBookmark(@PathVariable Long memberId, @PathVariable Long courseId) {
        courseBookmarkService.deleteCourseBookmark(memberId, courseId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<CourseResponse>> getBookmarkedCourses(
            @PathVariable Long memberId,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page-1, size);
        Page<CourseResponse> coursePage = courseBookmarkService.getBookmarkedCourses(memberId, pageable);

        return ResponseEntity.ok().body(coursePage);
    }

}
