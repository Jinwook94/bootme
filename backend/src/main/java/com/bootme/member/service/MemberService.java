package com.bootme.member.service;

import com.bootme.auth.dto.AuthInfo;
import com.bootme.common.exception.ResourceNotFoundException;
import com.bootme.member.domain.Member;
import com.bootme.member.domain.MemberStack;
import com.bootme.member.domain.OauthInfo;
import com.bootme.member.dto.MyProfileResponse;
import com.bootme.member.dto.ProfileResponse;
import com.bootme.member.dto.UpdateImageRequest;
import com.bootme.member.dto.UpdateProfileRequest;
import com.bootme.member.repository.MemberRepository;
import com.bootme.member.repository.MemberStackRepository;
import com.bootme.notification.service.NotificationService;
import com.bootme.stack.domain.Stack;
import com.bootme.stack.dto.StackResponse;
import com.bootme.stack.service.StackService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.bootme.common.exception.ErrorType.*;
import static com.bootme.notification.domain.NotificationEventType.SIGN_UP;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberStackRepository memberStackRepository;
    private final StackService stackService;
    private final NotificationService notificationService;

    public Member getMemberById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND_MEMBER, String.valueOf(id)));
    }

    public Member getMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND_MEMBER, email));
    }

    @Transactional
    public void registerMember(OauthInfo oauthInfo) {
        Member member = Member.of(oauthInfo);
        Member savedMember = memberRepository.save(member);

        notificationService.sendNotification(savedMember, SIGN_UP);
    }

    @Transactional(readOnly = true)
    public ProfileResponse findMemberProfile(Long id) {
        Member member = getMemberById(id);

        List<MemberStack> memberStacks = memberStackRepository.findByMember_Id(id);
        List<StackResponse> stacks = memberStacks.stream()
                .map(memberStack -> StackResponse.of(memberStack.getStack()))
                .toList();

        return ProfileResponse.of(member, stacks);
    }

    @Transactional(readOnly = true)
    public MyProfileResponse findMyProfile(AuthInfo authInfo) {
        Long id = authInfo.getMemberId();
        Member member = getMemberById(id);

        List<MemberStack> memberStacks = memberStackRepository.findByMember_Id(id);
        String[] stacks = memberStacks.stream()
                .map(memberStack -> memberStack.getStack().getName())
                .toArray(String[]::new);

        return MyProfileResponse.of(member, stacks);
    }

    // 이메일, 닉네임, 직업, 기술 스택 수정 (프로필 사진 수정은 modifyProfileImage)
    @Transactional
    public void modifyProfile(AuthInfo authInfo, Long memberId, UpdateProfileRequest request) {
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

    @Transactional
    public void modifyProfileImage(AuthInfo authinfo, Long memberId, UpdateImageRequest request) {
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

    @Transactional(readOnly = true)
    public boolean isRegistered(String email) {
        return memberRepository.existsMemberByEmail(email);
    }

    @Transactional
    public void incrementVisits(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND_MEMBER, String.valueOf(email)));
        member.incrementVisits();
    }

}
