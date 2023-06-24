package com.bootme.course.service;

import com.bootme.auth.service.AuthService;
import com.bootme.bookmark.repository.CourseBookmarkRepository;
import com.bootme.common.exception.ConflictException;
import com.bootme.common.exception.ResourceNotFoundException;
import com.bootme.course.domain.*;
import com.bootme.course.dto.CourseDetailRequest;
import com.bootme.course.dto.CourseDetailResponse;
import com.bootme.course.dto.CourseRequest;
import com.bootme.course.dto.CourseResponse;
import com.bootme.course.repository.CourseRepository;
import com.bootme.course.repository.CourseStackRepository;
import com.bootme.course.domain.CourseStatus;
import com.bootme.stack.domain.Stack;
import com.bootme.stack.repository.StackRepository;
import com.querydsl.core.BooleanBuilder;
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

import static com.bootme.common.enums.SortOption.*;
import static com.bootme.common.exception.ErrorType.*;

@Service
@Transactional
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final CourseStackRepository courseStackRepository;
    private final CourseBookmarkRepository courseBookmarkRepository;
    private final StackRepository stackRepository;
    private final AuthService authService;
    private final CompanyService companyService;
    private final CourseFilterPredicate courseFilterPredicate;
    private final CourseSearchPredicate courseSearchPredicate;

    public Course getCourseById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND_COURSE, String.valueOf(id)));
    }

    public Long addCourse(CourseRequest courseRequest){
        validateDuplicateByTitle(courseRequest.getTitle());
        String companyName = courseRequest.getCompanyName();
        Company company = companyService.getCompanyByName(companyName);
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
    public CourseDetailResponse findById(Long id, Long memberId) {
        boolean isLogin = authService.validateLogin(memberId);
        Course foundCourse = getCourseById(id);
        return createCourseDetailResponse(foundCourse, isLogin, memberId);
    }

    private CourseDetailResponse createCourseDetailResponse(Course course, boolean isLogin, Long memberId) {
        boolean isBookmarked = isLogin && courseBookmarkRepository.existsByBookmark_Member_IdAndCourse_Id(memberId, course.getId());
        List<Stack> stacks = courseStackRepository.findStacksByCourseId(course.getId());
        return CourseDetailResponse.of(course, stacks, isBookmarked);
    }

    @Transactional(readOnly = true)
    public Page<CourseResponse> findAll(Long memberId, int page, int size, String sort,
                                        MultiValueMap<String, String> params) {
        boolean isLogin = authService.validateLogin(memberId);

        Predicate combinedPredicate = getCombinedPredicate(params);

        return getCoursePage(page, size, sort, combinedPredicate)
                .map(course -> createCourseResponse(course, isLogin, memberId));
    }

    private CourseResponse createCourseResponse(Course course, boolean isLogin, Long memberId) {
        boolean isBookmarked = isLogin && courseBookmarkRepository.existsByBookmark_Member_IdAndCourse_Id(memberId, course.getId());
        List<Stack> stacks = courseStackRepository.findStacksByCourseId(course.getId());
        return CourseResponse.of(course, stacks, isBookmarked);
    }

    public void modifyCourse(Long id, CourseRequest courseRequest){
        Course course = getCourseById(id);
        course.modifyCourse(courseRequest);
    }

    public void modifyCourseDetail(Long id, CourseDetailRequest courseDetailRequest){
        Course course = getCourseById(id);
        course.modifyCourseDetail(courseDetailRequest.getDetail());
    }

    public void deleteCourse(Long id){
        Course course = getCourseById(id);
        deleteCourseInCompany(course);

        course.softDeleteCourse();
    }

    private void deleteCourseInCompany(Course course){
        Long companyId = course.getCompany().getId();
        Company company = companyService.getCompanyById(companyId);

        company.deleteCourse(course);
    }

    public void incrementClicks(Long id) {
        Course course = getCourseById(id);
        course.incrementClicks();
    }

    public void incrementBookmarks(Long id){
        Course course = getCourseById(id);
        course.incrementBookmarks();
    }

    private void validateDuplicateByTitle(String title){
        boolean isExist = courseRepository.existsByTitle(title);
        if (isExist){
            throw new ConflictException(ALREADY_SAVED_COURSE, title);
        }
    }

    private Predicate getCombinedPredicate(MultiValueMap<String, String> params) {
        Predicate filterPredicate = courseFilterPredicate.build(params);
        Predicate searchPredicate = courseSearchPredicate.build(params);
        return combinePredicates(filterPredicate, searchPredicate);
    }

    private Predicate combinePredicates(Predicate filterPredicate, Predicate searchPredicate) {
        BooleanBuilder builder = new BooleanBuilder();

        if (filterPredicate != null) {
            builder.and(filterPredicate);
        }

        if (searchPredicate != null) {
            builder.and(searchPredicate);
        }

        builder.and(QCourse.course.status.eq(CourseStatus.DISPLAY));
        return builder.getValue();
    }

    private Page<Course> getCoursePage(int page, int size, String sort, Predicate combinedPredicate) {
        Pageable pageable = getSortedPageable(page, size, sort);
        if (combinedPredicate == null) {
            return courseRepository.findAll(pageable);
        } else {
            return courseRepository.findAll(combinedPredicate, pageable);
        }
    }

    private Pageable getSortedPageable(int page, int size, String sort) {
        Sort sorting;
        if (sort.equals(CREATED_AT.toString())) {
            sorting = Sort.by(
                    Sort.Order.desc(CREATED_AT.toString()),
                    Sort.Order.desc(CLICKS.toString()),
                    Sort.Order.desc(BOOKMARKS.toString())
            );
        } else if (sort.equals(BOOKMARKS.toString())) {
            sorting = Sort.by(
                    Sort.Order.desc(BOOKMARKS.toString()),
                    Sort.Order.desc(CLICKS.toString()),
                    Sort.Order.asc(CREATED_AT.toString())
            );
        } else {
            sorting = Sort.by(
                    Sort.Order.desc(CLICKS.toString()),
                    Sort.Order.desc(BOOKMARKS.toString()),
                    Sort.Order.asc(CREATED_AT.toString())
            );
        }
        return PageRequest.of(page - 1, size, sorting);
    }

}
