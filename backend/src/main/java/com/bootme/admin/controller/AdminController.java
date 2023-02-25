package com.bootme.admin.controller;

import com.bootme.admin.service.AdminService;
import com.bootme.course.dto.CompanyRequest;
import com.bootme.course.dto.CompanyResponse;
import com.bootme.course.dto.CourseRequest;
import com.bootme.course.dto.CourseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/courses")
    public ResponseEntity<CourseResponse> addCourse(@Valid @RequestBody CourseRequest courseRequest){
        Long courseId = adminService.addCourse(courseRequest);
        CourseResponse courseResponse = adminService.findCourseById(courseId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/courses/" + courseId);
        return new ResponseEntity<>(courseResponse, headers, HttpStatus.CREATED);
    }

    @GetMapping("/courses/{id}")
    public ResponseEntity<CourseResponse> findCourse(@PathVariable Long id){
        CourseResponse courseResponse = adminService.findCourseById(id);
        return ResponseEntity.ok(courseResponse);
    }

    @GetMapping("/courses")
    public ResponseEntity<List<CourseResponse>> findAllCourses(){
        List<CourseResponse> courseResponses = adminService.findAllCourses();
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

    @PostMapping("/companies")
    public ResponseEntity<CompanyResponse> addCompany(@Valid @RequestBody CompanyRequest companyRequest){
        Long companyId = adminService.addCompany(companyRequest);
        CompanyResponse companyResponse = adminService.findCompanyById(companyId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/companies/" + companyId);
        return new ResponseEntity<>(companyResponse, headers, HttpStatus.CREATED);
    }

    @GetMapping("/companies/{id}")
    public ResponseEntity<CompanyResponse> findCompanyById(@PathVariable Long id) {
        CompanyResponse companyResponse = adminService.findCompanyById(id);
        return ResponseEntity.ok(companyResponse);
    }

    @GetMapping("/companies")
    public ResponseEntity<List<CompanyResponse>> findAllCompanies(){
        List<CompanyResponse> companyResponses = adminService.findAllCompanies();
        int size = companyResponses.size();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Expose-Headers", "X-Total-Count");
        headers.add("X-Total-Count", String.valueOf(size));
        headers.add("Content-Range", "companies 0-"+size+"/"+size);

        return ResponseEntity.ok().headers(headers).body(companyResponses);
    }

    @PutMapping("/companies/{id}")
        public ResponseEntity<Void> modifyCompany(@PathVariable Long id, @Valid @RequestBody CompanyRequest companyRequest){
            adminService.modifyCompany(id, companyRequest);
            return ResponseEntity.noContent().build();
        }

    @DeleteMapping("/companies/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable Long id){
        adminService.deleteCompany(id);
        return ResponseEntity.noContent().build();
    }
}
