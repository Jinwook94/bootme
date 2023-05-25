package com.bootme.course.service;

import com.bootme.common.exception.ResourceNotFoundException;
import com.bootme.course.domain.*;
import com.bootme.course.dto.CourseRequest;
import com.bootme.course.dto.CourseResponse;
import com.bootme.course.repository.CompanyRepository;
import com.bootme.course.repository.CourseRepository;
import com.bootme.course.repository.CourseStackRepository;
import com.bootme.stack.domain.Stack;
import com.bootme.stack.repository.StackRepository;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;

import static com.bootme.common.exception.ErrorType.*;

@Service
@Transactional
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final CompanyRepository companyRepository;
    private final CourseStackRepository courseStackRepository;
    private final StackRepository stackRepository;
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
        addCourseStack(courseRequest, course);

        return savedCourse.getId();
    }

    private void addCourseStack(CourseRequest courseRequest, Course course) {
        List<String> languages = courseRequest.getLanguages();
        List<String> frameworks = courseRequest.getFrameworks();
        List<String> stacks = new ArrayList<>();
        stacks.addAll(languages);
        stacks.addAll(frameworks);
        for (String stack : stacks) {
            Stack stackEntity = stackRepository.findByName(stack)
                    .orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND_STACK, stack));
            CourseStack courseStack = new CourseStack(course, stackEntity);
            courseStackRepository.save(courseStack);
        }
    }

    @Transactional(readOnly = true)
    public CourseResponse findById(Long id) {
        Course foundCourse = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND_COURSE, String.valueOf(id)));
        List<Stack> stacks = courseStackRepository.findStacksByCourseId(id);
        return CourseResponse.of(foundCourse, stacks);
    }

    @Transactional(readOnly = true)
    public Page<CourseResponse> findAll(int page, int size, String sort, MultiValueMap<String, String> filters) {
        Predicate predicate = courseFilterPredicate.build(filters);
        return getCoursePage(page, size, sort, predicate)
                .map(course -> CourseResponse.of(course, courseStackRepository.findStacksByCourseId(course.getId())));
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
