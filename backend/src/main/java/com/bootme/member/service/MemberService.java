package com.bootme.member.service;

import com.bootme.common.exception.ConflictException;
import com.bootme.common.exception.ResourceNotFoundException;
import com.bootme.course.domain.Course;
import com.bootme.course.dto.CourseResponse;
import com.bootme.course.repository.CourseRepository;
import com.bootme.member.domain.BookmarkCourse;
import com.bootme.member.domain.Member;
import com.bootme.member.repository.BookmarkCourseRepository;
import com.bootme.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.bootme.common.exception.ErrorType.*;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final CourseRepository courseRepository;
    private final BookmarkCourseRepository bookmarkCourseRepository;

    public boolean isMemberRegistered(String email){
        return memberRepository.existsMemberByEmail(email);
    }

    @Transactional(readOnly = true)
    public Member findById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND_MEMBER, id.toString()));
    }

    @Transactional(readOnly = true)
    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND_MEMBER, email));
    }

    public Long addBookmarkCourse(Long memberId, Long courseId) {
        boolean isExist = bookmarkCourseRepository.existsByMemberIdAndCourseId(memberId, courseId);

        if (isExist){
            throw new ConflictException(ALREADY_BOOKMARKED, "memberId=" + memberId + ", courseId="+courseId);
        }

        Member foundMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND_MEMBER, memberId.toString()));
        Course foundCourse = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND_COURSE, courseId.toString()));

        BookmarkCourse bookmarkCourses = new BookmarkCourse(foundMember, foundCourse);
        BookmarkCourse savedBookmarkCourse = bookmarkCourseRepository.save(bookmarkCourses);
        return savedBookmarkCourse.getId();

    }

    @Transactional(readOnly = true)
    public Page<CourseResponse> findBookmarkCourses(Long memberId, int page, int size){
        List<Long> bookmarkCourseIds = findBookmarkCourseIds(memberId);
        Pageable pageable = PageRequest.of(page-1, size);

        Page<Course> courses = courseRepository.findByIdIn(bookmarkCourseIds, pageable);
        return courses.map(CourseResponse::of);
    }

    @Transactional(readOnly = true)
    public List<Long> findBookmarkCourseIds(Long memberId) {
        List<BookmarkCourse> bookmarkCourseList = bookmarkCourseRepository.findByMemberId(memberId);
        return bookmarkCourseList.stream()
                .map(BookmarkCourse::getCourse)
                .map(Course::getId)
                .collect(Collectors.toList());
    }

    public void deleteBookmarkCourse(Long memberId, Long courseId) {
        BookmarkCourse bookmarkCourse = bookmarkCourseRepository.findByMemberIdAndCourseId(memberId, courseId)
                .orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND_BOOKMARK, "memberId=" + memberId + ", courseId="+courseId));
        bookmarkCourseRepository.delete(bookmarkCourse);
    }

}
