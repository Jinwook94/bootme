package com.bootme.course.controller;

import com.bootme.course.dto.CourseRequest;
import com.bootme.course.dto.CourseResponse;
import com.bootme.course.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public ResponseEntity<CourseResponse> addCourse(@Valid @RequestBody CourseRequest courseRequest){
        Long courseId = courseService.addCourse(courseRequest);
        CourseResponse courseResponse = courseService.findById(courseId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/courses/" + courseId);
        return new ResponseEntity<>(courseResponse, headers, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseResponse> findCourse(@PathVariable Long id){
        CourseResponse courseResponse = courseService.findById(id);
        return ResponseEntity.ok(courseResponse);
    }

    @GetMapping
    public ResponseEntity<Page<CourseResponse>> findAllCourses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size,
            @RequestParam MultiValueMap<String, String> parameters
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CourseResponse> coursePage = courseService.findAll(parameters, pageable);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Expose-Headers", "X-Total-Count");
        headers.add("X-Total-Count", String.valueOf(coursePage.getTotalElements()));
        headers.add("Content-Range", "courses " + coursePage.getNumber() * coursePage.getSize() + "-" + (coursePage.getNumber() * coursePage.getSize() + coursePage.getNumberOfElements()) + "/" + coursePage.getTotalElements());

        return ResponseEntity.ok().headers(headers).body(coursePage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> modifyCourse(@PathVariable Long id,
                                             @Valid @RequestBody CourseRequest courseRequest){
        courseService.modifyCourse(id, courseRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id){
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }
}

