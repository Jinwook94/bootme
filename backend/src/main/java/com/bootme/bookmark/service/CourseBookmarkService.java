package com.bootme.bookmark.service;

import com.bootme.bookmark.domain.Bookmark;
import com.bootme.bookmark.domain.BookmarkType;
import com.bootme.bookmark.domain.CourseBookmark;
import com.bootme.bookmark.repository.BookmarkRepository;
import com.bootme.bookmark.repository.CourseBookmarkRepository;
import com.bootme.common.exception.ConflictException;
import com.bootme.common.exception.ResourceNotFoundException;
import com.bootme.course.domain.Course;
import com.bootme.course.dto.CourseResponse;
import com.bootme.course.repository.CourseStackRepository;
import com.bootme.course.service.CourseService;
import com.bootme.member.domain.Member;
import com.bootme.member.service.MemberService;
import com.bootme.stack.domain.Stack;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.bootme.bookmark.domain.BookmarkType.COURSE;
import static com.bootme.common.exception.ErrorType.ALREADY_BOOKMARKED;
import static com.bootme.common.exception.ErrorType.NOT_FOUND_BOOKMARK;

@Service
@RequiredArgsConstructor
public class CourseBookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final CourseStackRepository courseStackRepository;
    private final CourseBookmarkRepository courseBookmarkRepository;
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

    @Transactional
    public void deleteCourseBookmark(Long memberId, Long courseId) {
        CourseBookmark courseBookmark = courseBookmarkRepository.findByBookmark_Member_IdAndCourse_Id(memberId, courseId)
                .orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND_BOOKMARK,
                        "memberId=" + memberId + ", courseId="+courseId));

        courseBookmarkRepository.delete(courseBookmark);
    }

    @Transactional(readOnly = true)
    public Page<CourseResponse> getBookmarkedCourses(Long memberId, Pageable pageable) {
        List<CourseBookmark> courseBookmarks = findCourseBookmarksByMemberId(memberId);
        List<CourseResponse> courseResponses = mapCourseBookmarksToCourseResponse(courseBookmarks);

        return getPageableCourseResponse(pageable, courseResponses);
    }

    private List<CourseBookmark> findCourseBookmarksByMemberId(Long memberId) {
        return bookmarkRepository.findByMember_Id(memberId).stream()
                .filter(bookmark -> bookmark.getType() == BookmarkType.COURSE)
                .flatMap(bookmark -> courseBookmarkRepository.findByBookmark_Id(bookmark.getId()).stream())
                .toList();
    }

    private List<CourseResponse> mapCourseBookmarksToCourseResponse(List<CourseBookmark> courseBookmarks) {
        return courseBookmarks.stream()
                .map(this::mapCourseBookmarkToCourseResponse)
                .toList();
    }

    private CourseResponse mapCourseBookmarkToCourseResponse(CourseBookmark courseBookmark) {
        Course course = courseBookmark.getCourse();
        List<Stack> stacks = courseStackRepository.findStacksByCourseId(course.getId());
        return CourseResponse.of(course, stacks, true);
    }

    private Page<CourseResponse> getPageableCourseResponse(Pageable pageable, List<CourseResponse> courseResponses) {
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), courseResponses.size());
        return new PageImpl<>(courseResponses.subList(start, end), pageable, courseResponses.size());
    }

}
