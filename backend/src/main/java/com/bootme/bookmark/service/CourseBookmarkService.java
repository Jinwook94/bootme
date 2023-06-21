package com.bootme.bookmark.service;

import com.bootme.bookmark.domain.Bookmark;
import com.bootme.bookmark.domain.CourseBookmark;
import com.bootme.bookmark.repository.CourseBookmarkRepository;
import com.bootme.common.exception.ConflictException;
import com.bootme.common.exception.ResourceNotFoundException;
import com.bootme.course.domain.Course;
import com.bootme.course.dto.CourseResponse;
import com.bootme.course.repository.CourseRepository;
import com.bootme.course.repository.CourseStackRepository;
import com.bootme.course.service.CourseService;
import com.bootme.member.domain.Member;
import com.bootme.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.bootme.bookmark.domain.BookmarkType.COURSE;
import static com.bootme.common.exception.ErrorType.ALREADY_BOOKMARKED;
import static com.bootme.common.exception.ErrorType.NOT_FOUND_BOOKMARK;

@Service
@RequiredArgsConstructor
public class CourseBookmarkService {

    private final CourseBookmarkRepository courseBookmarkRepository;
    private final CourseRepository courseRepository;
    private final CourseStackRepository courseStackRepository;
    private final CourseService courseService;
    private final MemberService memberService;

    @Transactional
    public Long addCourseBookmark(Long memberId, Long courseId) {
        boolean isExist = isCourseBookmarkExist(memberId, courseId);

        if (isExist){
            throw new ConflictException(ALREADY_BOOKMARKED, "memberId=" + memberId + ", courseId="+courseId);
        }

        Member foundMember = memberService.getMemberById(memberId);
        Course foundCourse = courseService.getCourseById(courseId);

        Bookmark bookmark = new Bookmark(COURSE, foundMember);
        CourseBookmark courseBookmark = new CourseBookmark(bookmark, foundCourse);

        return courseBookmarkRepository.save(courseBookmark).getId();
    }

    private boolean isCourseBookmarkExist(Long memberId, Long courseId) {
        return courseBookmarkRepository.existsByBookmark_Member_IdAndCourse_Id(memberId, courseId);
    }

    @Transactional(readOnly = true)
    public Page<CourseResponse> findCourseBookmarks(Long memberId, int page, int size){
        List<Long> courseBookmarkIds = findCourseBookmarkIds(memberId);
        Pageable pageable = PageRequest.of(page-1, size);

        Page<Course> courses = courseRepository.findByIdIn(courseBookmarkIds, pageable);
        return courses.map(course -> CourseResponse.of(course, courseStackRepository.findStacksByCourseId(course.getId())));
    }

    @Transactional(readOnly = true)
    public List<Long> findCourseBookmarkIds(Long memberId) {
        return courseBookmarkRepository.findAll().stream()
                .filter(cb -> Objects.equals(cb.getBookmark().getMember().getId(), memberId))
                .map(cb -> cb.getCourse().getId())
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteCourseBookmark(Long memberId, Long courseId) {
        CourseBookmark courseBookmark = courseBookmarkRepository.findAll().stream()
                .filter(cb -> cb.getBookmark().getType() == COURSE)
                .filter(cb -> Objects.equals(cb.getBookmark().getMember().getId(), memberId))
                .filter(cb -> cb.getCourse().getId().equals(courseId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND_BOOKMARK,
                        "memberId=" + memberId + ", courseId="+courseId));

        courseBookmarkRepository.delete(courseBookmark);
    }

}
