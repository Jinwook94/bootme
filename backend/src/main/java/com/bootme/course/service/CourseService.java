package com.bootme.course.service;

import com.bootme.common.exception.ResourceNotFoundException;
import com.bootme.course.domain.*;
import com.bootme.course.dto.CourseRequest;
import com.bootme.course.dto.CourseResponse;
import com.bootme.course.repository.CompanyRepository;
import com.bootme.course.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.bootme.common.exception.ErrorType.NOT_FOUND_COMPANY;
import static com.bootme.common.exception.ErrorType.NOT_FOUND_COURSE;

@Service
@Transactional
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final CompanyRepository companyRepository;

    public Long addCourse(CourseRequest courseRequest){
        String companyName = courseRequest.getCompanyName();
        Company company = companyRepository.findByName(companyName)
                .orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND_COMPANY, companyName));
        Category category = courseRequest.getCategories();
        Stack stack = courseRequest.getStacks();
        Course course = Course.of(courseRequest, company, category, stack);
        Course savedCourse = courseRepository.save(course);
        return savedCourse.getId();
    }

    @Transactional(readOnly = true)
    public CourseResponse findById(Long id) {
        Course foundCourse = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND_COURSE, String.valueOf(id)));
        return CourseResponse.of(foundCourse);
    }

    @Transactional(readOnly = true)
    public Page<CourseResponse> findAll(Pageable pageable) {
        Page<Course> coursePage = courseRepository.findAll(pageable);
        return coursePage.map(CourseResponse::of);
    }

    public void modifyCourse(Long id, CourseRequest courseRequest){
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND_COURSE, String.valueOf(id)));

        course.modifyCourse(courseRequest);
    }

    public void deleteCourse(Long id){
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND_COURSE, String.valueOf(id)));
        Long companyId = course.getCompany().getId();
        Company company = this.companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND_COMPANY, String.valueOf(id)));

        company.deleteCourse(course);
        courseRepository.delete(course);
    }

    public void incrementClicks(Long id){
        courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND_COURSE, String.valueOf(id)));
        courseRepository.incrementClicks(id);
    }

    public void incrementBookmarks(Long id){
        courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND_COURSE, String.valueOf(id)));
        courseRepository.incrementBookmarks(id);
    }

}
