package com.bootme.member.service;

import com.bootme.auth.dto.AuthInfo;
import com.bootme.auth.service.AuthService;
import com.bootme.common.exception.ResourceNotFoundException;
import com.bootme.member.domain.Member;
import com.bootme.member.domain.MemberStack;
import com.bootme.member.dto.MyProfileResponse;
import com.bootme.member.dto.ProfileResponse;
import com.bootme.member.dto.UpdateImageRequest;
import com.bootme.member.dto.UpdateProfileRequest;
import com.bootme.member.repository.MemberRepository;
import com.bootme.member.repository.MemberStackRepository;
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

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final AuthService authService;
    private final MemberRepository memberRepository;
    private final MemberStackRepository memberStackRepository;
    private final StackService stackService;

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

    public Member getMemberById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND_MEMBER, String.valueOf(id)));
    }

}
