package com.bootme.bookmark.service;

import com.bootme.bookmark.domain.Bookmark;
import com.bootme.bookmark.domain.CourseBookmark;
import com.bootme.bookmark.repository.BookmarkRepository;
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
import java.util.stream.Collectors;

import static com.bootme.bookmark.domain.BookmarkType.COURSE;
import static com.bootme.common.exception.ErrorType.ALREADY_BOOKMARKED;
import static com.bootme.common.exception.ErrorType.NOT_FOUND_BOOKMARK;

@Service
@RequiredArgsConstructor
public class CourseBookmarkService {

    private final BookmarkRepository bookmarkRepository;
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

        CourseBookmark courseBookmark = new CourseBookmark(foundCourse);
        Bookmark bookmark = Bookmark.builder()
                                    .type(COURSE)
                                    .member(foundMember)
                                    .courseBookmark(courseBookmark)
                                    .build();

        return bookmarkRepository.save(bookmark).getId();
    }

    private boolean isCourseBookmarkExist(Long memberId, Long courseId) {
        return bookmarkRepository.findByMemberId(memberId).stream()
                .filter(bookmark -> bookmark.getType() == COURSE)
                .anyMatch(bookmark -> bookmark.getCourseBookmark().getCourse().getId().equals(courseId));
    }


    @Transactional(readOnly = true)
    public Page<CourseResponse> findCourseBookmarks(Long memberId, int page, int size){
        List<Long> bookmarkCourseIds = findCourseBookmarkIds(memberId);
        Pageable pageable = PageRequest.of(page-1, size);

        Page<Course> courses = courseRepository.findByIdIn(bookmarkCourseIds, pageable);
        return courses.map(course -> CourseResponse.of(course, courseStackRepository.findStacksByCourseId(course.getId())));
    }

    @Transactional(readOnly = true)
    public List<Long> findCourseBookmarkIds(Long memberId) {
        return bookmarkRepository.findByMemberId(memberId).stream()
                .filter(b -> b.getType() == COURSE)
                .map(b -> b.getCourseBookmark().getCourse().getId())
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteCourseBookmark(Long memberId, Long courseId) {
        Bookmark bookmark = bookmarkRepository.findByMemberId(memberId).stream()
                .filter(b -> b.getType() == COURSE)
                .filter(b -> b.getCourseBookmark().getCourse().getId().equals(courseId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND_BOOKMARK,
                        "memberId=" + memberId + ", courseId="+courseId));

        bookmarkRepository.delete(bookmark);
    }

}
