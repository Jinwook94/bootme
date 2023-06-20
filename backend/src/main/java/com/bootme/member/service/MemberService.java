package com.bootme.member.service;

import com.bootme.auth.dto.AuthInfo;
import com.bootme.auth.service.AuthService;
import com.bootme.common.exception.ConflictException;
import com.bootme.common.exception.ResourceNotFoundException;
import com.bootme.course.domain.Course;
import com.bootme.course.dto.CourseResponse;
import com.bootme.course.repository.CourseRepository;
import com.bootme.course.repository.CourseStackRepository;
import com.bootme.course.service.CourseService;
import com.bootme.member.domain.BookmarkCourse;
import com.bootme.member.domain.Member;
import com.bootme.member.domain.MemberStack;
import com.bootme.member.dto.MyProfileResponse;
import com.bootme.member.dto.ProfileResponse;
import com.bootme.member.dto.UpdateImageRequest;
import com.bootme.member.dto.UpdateProfileRequest;
import com.bootme.member.repository.BookmarkCourseRepository;
import com.bootme.member.repository.MemberRepository;
import com.bootme.member.repository.MemberStackRepository;
import com.bootme.stack.domain.Stack;
import com.bootme.stack.dto.StackResponse;
import com.bootme.stack.service.StackService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.bootme.common.exception.ErrorType.*;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final AuthService authService;
    private final MemberRepository memberRepository;
    private final MemberStackRepository memberStackRepository;
    private final StackService stackService;
    private final CourseService courseService;
    private final CourseRepository courseRepository;
    private final CourseStackRepository courseStackRepository;
    private final BookmarkCourseRepository bookmarkCourseRepository;

    @Transactional(readOnly = true)
    public ProfileResponse findMemberProfile(Long id) {
        Member member = getMemberById(id);

        List<MemberStack> memberStacks = memberStackRepository.findByMember_Id(id);
        List<StackResponse> stacks = memberStacks.stream()
                .map(memberStack -> StackResponse.of(memberStack.getStack()))
                .collect(Collectors.toList());

        return ProfileResponse.of(member, stacks);
    }

    @Transactional(readOnly = true)
    public MyProfileResponse findMyProfile(AuthInfo authInfo) {
        authService.validateLogin(authInfo);
        Long id = authInfo.getMemberId();
        Member member = getMemberById(id);

        List<MemberStack> memberStacks = memberStackRepository.findByMember_Id(id);
        String[] stacks = memberStacks.stream()
                .map(memberStack -> memberStack.getStack().getName())
                .toArray(String[]::new);

        return MyProfileResponse.of(member, stacks);
    }

    // 이메일, 닉네임, 직업, 기술 스택 수정 (프로필 사진 수정은 별도 메서드: modifyProfileImage)
    public void modifyProfile(AuthInfo authInfo, Long memberId, UpdateProfileRequest request) {
        authService.validateLogin(authInfo);
        Member member = getMemberById(memberId);
        member.validateIdMatchesToken(authInfo.getMemberId(), memberId);

        member.modifyEmail(request.getEmail());
        member.modifyNickname(request.getNickname());
        member.modifyJob(request.getJob());

        Set<Stack> requestedStacks = request.getStackNames().stream()
                                        .map(stackService::getStackByName)
                                        .collect(Collectors.toSet());
        member.modifyStacks(requestedStacks);
    }

    public void modifyProfileImage(AuthInfo authinfo, Long memberId, UpdateImageRequest request) {
        authService.validateLogin(authinfo);
        Member member = getMemberById(memberId);
        member.validateIdMatchesToken(authinfo.getMemberId(), memberId);

        member.modifyProfileImage(request);
    }

    @Transactional(readOnly = true)
    public Member findById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND_MEMBER, id.toString()));
    }

    @Transactional(readOnly = true)
    public boolean isNicknameDuplicate(String nickname) {
        return memberRepository.existsByNickname(nickname);
    }

    public Long addBookmarkCourse(Long memberId, Long courseId) {
        boolean isExist = bookmarkCourseRepository.existsByMemberIdAndCourseId(memberId, courseId);

        if (isExist){
            throw new ConflictException(ALREADY_BOOKMARKED, "memberId=" + memberId + ", courseId="+courseId);
        }

        Member foundMember = getMemberById(memberId);
        Course foundCourse = courseService.getCourseById(courseId);

        BookmarkCourse bookmarkCourses = new BookmarkCourse(foundMember, foundCourse);
        BookmarkCourse savedBookmarkCourse = bookmarkCourseRepository.save(bookmarkCourses);
        return savedBookmarkCourse.getId();

    }

    @Transactional(readOnly = true)
    public Page<CourseResponse> findBookmarkCourses(Long memberId, int page, int size){
        List<Long> bookmarkCourseIds = findBookmarkCourseIds(memberId);
        Pageable pageable = PageRequest.of(page-1, size);

        Page<Course> courses = courseRepository.findByIdIn(bookmarkCourseIds, pageable);
        return courses.map(course -> CourseResponse.of(course, courseStackRepository.findStacksByCourseId(course.getId())));
    }

    @Transactional(readOnly = true)
    public List<Long> findBookmarkCourseIds(Long memberId) {
        List<BookmarkCourse> bookmarkCourseList = bookmarkCourseRepository.findByMember(getMemberById(memberId));
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

    public Member getMemberById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND_MEMBER, String.valueOf(id)));
    }

}
