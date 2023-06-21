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

    @GetMapping
    public ResponseEntity<Page<CourseResponse>> findCourseBookmarks(
            @PathVariable Long memberId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<CourseResponse> courseBookmarkPage = courseBookmarkService.findCourseBookmarks(memberId, page, size);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Expose-Headers", "X-Total-Count");
        headers.add("X-Total-Count", String.valueOf(courseBookmarkPage.getTotalElements()));
        headers.add("Content-Range", getContentRange(courseBookmarkPage));

        return ResponseEntity.ok().headers(headers).body(courseBookmarkPage);
    }

    private String getContentRange(Page<CourseResponse> coursePage) {
        int startRange = coursePage.getNumber() * coursePage.getSize();
        int endRange = startRange + coursePage.getNumberOfElements();
        long totalElements = coursePage.getTotalElements();

        return String.format("courses %d-%d/%d", startRange, endRange, totalElements);
    }

    @GetMapping("/ids")
    public ResponseEntity<List<Long>> findCourseBookmarkIds(@PathVariable Long memberId){
        List<Long> courseBookmarks = courseBookmarkService.findCourseBookmarkIds(memberId);
        return ResponseEntity.ok().body(courseBookmarks);
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<Void> deleteCourseBookmark(@PathVariable Long memberId, @PathVariable Long courseId) {
        courseBookmarkService.deleteCourseBookmark(memberId, courseId);
        return ResponseEntity.noContent().build();
    }

}
