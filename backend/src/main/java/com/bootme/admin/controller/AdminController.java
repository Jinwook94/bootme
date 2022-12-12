package com.bootme.admin.controller;

import com.bootme.admin.dto.AdminLoginRequest;
import com.bootme.admin.service.AdminService;
import com.bootme.auth.dto.TokenResponse;
import com.bootme.course.dto.CourseRequest;
import com.bootme.course.dto.CourseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000/")
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
    public ResponseEntity<CourseResponse> addCourse(@Valid @RequestBody CourseRequest courseRequest){
        Long courseId = adminService.addCourse(courseRequest);
        CourseResponse courseResponse = adminService.findById(courseId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/courses/" + courseId);
        return new ResponseEntity<>(courseResponse, headers, HttpStatus.CREATED);
    }

    @GetMapping("/courses/{id}")
    public ResponseEntity<CourseResponse> findCourse(@PathVariable Long id){
        CourseResponse courseResponse = adminService.findById(id);
        return ResponseEntity.ok(courseResponse);
    }

    @GetMapping("/courses")
    public ResponseEntity<List<CourseResponse>> findCourses(){
        List<CourseResponse> courseResponses = adminService.findAll();
        int size = courseResponses.size();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Expose-Headers", "X-Total-Count");
        headers.add("X-Total-Count", String.valueOf(size));
        headers.add("Content-Range", "courses 0-"+size+"/"+size);

        return ResponseEntity.ok().headers(headers).body(courseResponses);
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
