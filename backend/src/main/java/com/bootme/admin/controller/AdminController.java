package com.bootme.admin.controller;

import com.bootme.admin.dto.AdminLoginRequest;
import com.bootme.admin.service.AdminService;
import com.bootme.auth.dto.TokenResponse;
import com.bootme.course.dto.CourseRequest;
import com.bootme.course.dto.CourseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody AdminLoginRequest adminLoginRequest) {
        return ResponseEntity.ok(adminService.login(adminLoginRequest));
    }

    @PostMapping("/courses")
    public ResponseEntity<Void> addCourse(@Valid @RequestBody CourseRequest courseRequest){
        Long courseId = adminService.addCourse(courseRequest);
        return ResponseEntity.created(URI.create("/courses/" + courseId)).build();
    }

    @GetMapping("/courses/{id}")
    public ResponseEntity<CourseResponse> findCourse(@PathVariable Long id){
        CourseResponse courseResponse = adminService.findById(id);
        return ResponseEntity.ok(courseResponse);
    }

    @GetMapping("/courses")
    public ResponseEntity<List<CourseResponse>> findCourses(){
        return ResponseEntity.ok(adminService.findAll());
    }

    @PutMapping("/courses/{id}")
    public ResponseEntity<Void> modifyCourse(@PathVariable Long id,
                                             @Valid @RequestBody CourseRequest courseRequest){
        adminService.modifyCourse(id, courseRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/courses/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id){
        adminService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }

}
