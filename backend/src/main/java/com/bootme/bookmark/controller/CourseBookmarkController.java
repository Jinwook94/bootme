package com.bootme.bookmark.controller;

import com.bootme.bookmark.service.CourseBookmarkService;
import com.bootme.course.dto.CourseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequestMapping("/bookmarks")
@RequiredArgsConstructor
@RestController
public class CourseBookmarkController {

    private final CourseBookmarkService courseBookmarkService;

    @PostMapping("/{memberId}/courses/{courseId}")
    public ResponseEntity<Void> addCourseBookmark(@PathVariable Long memberId, @PathVariable Long courseId){
        Long bookmarkCourseId = courseBookmarkService.addCourseBookmark(memberId, courseId);
        return ResponseEntity.created(URI.create("/member/" + memberId + "/bookmarks" + bookmarkCourseId)).build();
    }

    @GetMapping("/{memberId}/courses")
    public ResponseEntity<Page<CourseResponse>> findCourseBookmarks(
            @PathVariable Long memberId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<CourseResponse> bookmarkCoursePage = courseBookmarkService.findCourseBookmarks(memberId, page, size);

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

    @GetMapping("/{memberId}/courses/ids")
    public ResponseEntity<List<Long>> findCourseBookmarkIds(@PathVariable Long memberId){
        List<Long> bookmarkCourses = courseBookmarkService.findCourseBookmarkIds(memberId);
        return ResponseEntity.ok().body(bookmarkCourses);
    }

    @DeleteMapping("/{memberId}/courses/{courseId}")
    public ResponseEntity<Void> deleteCourseBookmark(@PathVariable Long memberId, @PathVariable Long courseId) {
        courseBookmarkService.deleteCourseBookmark(memberId, courseId);
        return ResponseEntity.noContent().build();
    }

}
