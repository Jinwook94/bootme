package com.bootme.course.service;

import com.bootme.common.exception.ResourceNotFoundException;
import com.bootme.course.domain.*;
import com.bootme.course.dto.CourseRequest;
import com.bootme.course.dto.CourseResponse;
import com.bootme.course.repository.CompanyRepository;
import com.bootme.course.repository.CourseRepository;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import static com.bootme.common.exception.ErrorType.NOT_FOUND_COMPANY;
import static com.bootme.common.exception.ErrorType.NOT_FOUND_COURSE;

@Service
@Transactional
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final CompanyRepository companyRepository;
    private final CourseFilterPredicate courseFilterPredicate;

    private static final String CREATED_AT = "createdAt";
    private static final String CLICKS = "clicks";
    private static final String BOOKMARKS = "bookmarks";

    public Long addCourse(CourseRequest courseRequest){
        String companyName = courseRequest.getCompanyName();
        Company company = companyRepository.findByName(companyName)
                .orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND_COMPANY, companyName));
        Course course = Course.of(courseRequest, company);
        Course savedCourse = courseRepository.save(course);
        return savedCourse.getId();
    }

    @Transactional(readOnly = true)
    public CourseResponse findById(Long id) {
        Course foundCourse = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND_COURSE, String.valueOf(id)));
        return CourseResponse.of(foundCourse);
    }

    public Page<CourseResponse> findAll(int page, int size, String sort, MultiValueMap<String, String> filters) {
        Predicate predicate = courseFilterPredicate.build(filters);
        return getCoursePage(page, size, sort, predicate).map(CourseResponse::of);
    }

        Page<Course> coursePage = courseRepository.findAll(predicate, pageable);
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

    public void incrementClicks(Long id) {
        boolean exists = courseRepository.existsById(id);
        if (exists) {
            courseRepository.incrementClicks(id);
        } else {
            throw new ResourceNotFoundException(NOT_FOUND_COURSE, String.valueOf(id));
        }
    }

    public void incrementBookmarks(Long id){
        boolean exists = courseRepository.existsById(id);
        if (exists) {
            courseRepository.incrementBookmarks(id);
        } else {
            throw new ResourceNotFoundException(NOT_FOUND_COURSE, String.valueOf(id));
        }
    }

    private Page<Course> getCoursePage(int page, int size, String sort, Predicate predicate) {
        Pageable pageable = getSortedPageable(page, size, sort);
        if (predicate == null) {
            return courseRepository.findAll(pageable);
        } else {
            return courseRepository.findAll(predicate, pageable);
        }
    }

    private Pageable getSortedPageable(int page, int size, String sort) {
        Sort sorting;
        switch(sort) {
            case "newest":
                sorting = Sort.by(
                        Sort.Order.asc(CREATED_AT),
                        Sort.Order.desc(CLICKS),
                        Sort.Order.desc(BOOKMARKS)
                );
                break;
            case "bookmark":
                sorting = Sort.by(
                        Sort.Order.desc(BOOKMARKS),
                        Sort.Order.desc(CLICKS),
                        Sort.Order.asc(CREATED_AT)
                );
                break;
            default:
                sorting = Sort.by(
                        Sort.Order.desc(CLICKS),
                        Sort.Order.desc(BOOKMARKS),
                        Sort.Order.asc(CREATED_AT)
                );
                break;
        }
        return PageRequest.of(page-1, size, sorting);
    }

}
