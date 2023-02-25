package com.bootme.member.service;

import com.bootme.course.domain.Course;
import com.bootme.course.exception.CourseNotFoundException;
import com.bootme.course.repository.CourseRepository;
import com.bootme.member.domain.BookmarkCourse;
import com.bootme.member.domain.Member;
import com.bootme.member.exception.AlreadyBookmarkedException;
import com.bootme.member.exception.BookmarkCourseNotFoundException;
import com.bootme.member.exception.MemberNotFoundException;
import com.bootme.member.repository.BookmarkCourseRepository;
import com.bootme.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
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
                .orElseThrow(() -> new MemberNotFoundException(NOT_FOUND_MEMBER));
    }

    @Transactional(readOnly = true)
    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException(NOT_FOUND_MEMBER));
    }

    public Long addBookmarkCourse(Long memberId, Long courseId) {
        boolean isExist = bookmarkCourseRepository.existsByMemberIdAndCourseId(memberId, courseId);

        if (isExist){
            throw new AlreadyBookmarkedException(ALREADY_BOOKMARKED);
        }

        Member foundMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(NOT_FOUND_MEMBER));
        Course foundCourse = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException(NOT_FOUND_COURSE));

        BookmarkCourse bookmarkCourses = new BookmarkCourse(foundMember, foundCourse);
        BookmarkCourse savedBookmarkCourse = bookmarkCourseRepository.save(bookmarkCourses);
        return savedBookmarkCourse.getId();

    }

    public void deleteBookmarkCourse(Long memberId, Long courseId) {
        BookmarkCourse bookmarkCourse = bookmarkCourseRepository.findByMemberIdAndCourseId(memberId, courseId)
                .orElseThrow(() -> new BookmarkCourseNotFoundException(NOT_FOUND_BOOKMARK));
        bookmarkCourseRepository.delete(bookmarkCourse);
    }

    @Transactional(readOnly = true)
    public List<Long> findBookmarkCourseByMemberId(Long memberId) {
        List<BookmarkCourse> bookmarkCourseList = bookmarkCourseRepository.findByMemberId(memberId);
        return bookmarkCourseList.stream()
                .map(BookmarkCourse::getCourse)
                .map(Course::getId)
                .collect(Collectors.toList());
    }

}
