package com.bootme.course.controller;

import com.bootme.auth.dto.AuthInfo;
import com.bootme.auth.util.Login;
import com.bootme.course.dto.CourseDetailRequest;
import com.bootme.course.dto.CourseDetailResponse;
import com.bootme.course.dto.CourseRequest;
import com.bootme.course.dto.CourseResponse;
import com.bootme.course.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @PostMapping
    public ResponseEntity<CourseDetailResponse> addCourse(@Login AuthInfo authInfo,
                                                          @Valid @RequestBody CourseRequest courseRequest){
        Long courseId = courseService.addCourse(courseRequest);
        CourseDetailResponse courseResponse = courseService.findById(courseId, authInfo.getMemberId());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/courses/" + courseId);
        return new ResponseEntity<>(courseResponse, headers, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDetailResponse> findCourse(@Login AuthInfo authInfo,
                                                           @PathVariable Long id){
        CourseDetailResponse courseResponse = courseService.findById(id, authInfo.getMemberId());
        return ResponseEntity.ok(courseResponse);
    }

    @GetMapping
    public ResponseEntity<Page<CourseResponse>> findAllCourses(
            @Login AuthInfo authInfo,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "12") int size,
            @RequestParam(defaultValue = "popular") String sort,
            @RequestParam MultiValueMap<String, String> params
    ) {
        Page<CourseResponse> coursePage = courseService.findAll(authInfo.getMemberId(), page, size, sort, params);
        return ResponseEntity.ok().body(coursePage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> modifyCourse(@PathVariable Long id,
                                             @Valid @RequestBody CourseRequest courseRequest){
        courseService.modifyCourse(id, courseRequest);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/detail")
    public ResponseEntity<Void> modifyCourseDetail(@PathVariable Long id,
                                                   @Valid @RequestBody CourseDetailRequest courseDetailRequest) {
        courseService.modifyCourseDetail(id, courseDetailRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id){
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }

}

