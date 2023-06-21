package com.bootme.member.controller;

import com.bootme.auth.dto.AuthInfo;
import com.bootme.auth.util.Login;
import com.bootme.member.dto.MyProfileResponse;
import com.bootme.member.dto.ProfileResponse;
import com.bootme.member.dto.UpdateImageRequest;
import com.bootme.member.dto.UpdateProfileRequest;
import com.bootme.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/member")
@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/{memberId}/profile")
    public ResponseEntity<ProfileResponse> findMemberProfile(@PathVariable Long memberId) {
        ProfileResponse response = memberService.findMemberProfile(memberId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<MyProfileResponse> findMyProfile(@Login AuthInfo authInfo) {
        MyProfileResponse response = memberService.findMyProfile(authInfo);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{memberId}")
    public ResponseEntity<Void> modifyProfile(@Login AuthInfo authInfo,
                                              @PathVariable Long memberId,
                                              @RequestBody UpdateProfileRequest request) {
        memberService.modifyProfile(authInfo, memberId, request);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{memberId}/profile_image")
    public ResponseEntity<Void> modifyProfileImage(@Login AuthInfo authInfo,
                                                   @PathVariable Long memberId,
                                                   @RequestBody UpdateImageRequest request) {
        memberService.modifyProfileImage(authInfo, memberId, request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/nickname/{nickname}/exists")
    public ResponseEntity<Boolean> checkNicknameDuplicate(@PathVariable String nickname){
        boolean isDuplicate = memberService.isNicknameDuplicate(nickname);
        return ResponseEntity.ok().body(isDuplicate);
    }

}
