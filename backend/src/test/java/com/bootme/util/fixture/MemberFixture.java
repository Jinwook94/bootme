package com.bootme.util.fixture;

import com.bootme.member.domain.Member;
import com.bootme.member.domain.RoleType;
import com.bootme.member.dto.MyProfileResponse;
import com.bootme.member.dto.ProfileResponse;
import com.bootme.member.dto.UpdateImageRequest;
import com.bootme.member.dto.UpdateProfileRequest;
import com.bootme.stack.dto.StackRequest;
import com.bootme.stack.dto.StackResponse;

import java.util.ArrayList;
import java.util.List;

public class MemberFixture {

    public static final String VALID_EMAIL_1 = "john@gmail.com";
    public static final String VALID_EMAIL_2 = "mary@naver.com";
    public static final String VALID_EMAIL_3 = "james@kakao.com";
    public static final String VALID_JOB_1 = "웹 백엔드 개발자";
    public static final String VALID_JOB_2 = "웹 프론트엔드 개발자";
    public static final String VALID_JOB_3 = "데브옵스";
    public static final String VALID_PASSWORD_2 = null;
    public static final String VALID_PASSWORD_3 = null;
    public static final String GOOGLE = "google";
    public static final String NAVER = "naver";
    public static final String KAKAO = "kakao";
    public static final String VALID_NAME_1 = "Michael";
    public static final String VALID_NAME_2 = "Magic";
    public static final String VALID_NAME_3 = "Kobe";
    public static final String VALID_PROFILE_IMAGE_1 = "https://lh3.googleusercontent.com/a/AGNmyxZgJo-m0_NrDgTZi2vJFtc4hC7c6xhDcTPcMg6S=s96-c";
    public static final String VALID_PROFILE_IMAGE_2 = "https://ssl.pstatic.net/static/pwe/address/img_profile.png";
    public static final String VALID_PROFILE_IMAGE_3 = "http://k.kakaocdn.net/dn/dpk9l1/btqmGhA2lKL/Oz0wDuJn1YV2DIn92f6DVK/img_110x110.jpg";
    public static final String VALID_STACK_1 = "Java";
    public static final String VALID_STACK_2 = "JavaScript";
    public static final String VALID_STACK_3 = "Spring";
    public static final String VALID_STACK_4 = "React";
    public static final String VALID_STACK_TYPE_1 = "language";
    public static final String VALID_STACK_TYPE_2 = "language";
    public static final String VALID_STACK_TYPE_3 = "framework";
    public static final String VALID_STACK_TYPE_4 = "framework";
    public static final String VALID_STACK_ICON_1 = "https://bootme-images.s3.ap-northeast-2.amazonaws.com/logos/stack/java.png";
    public static final String VALID_STACK_ICON_2 = "https://bootme-images.s3.ap-northeast-2.amazonaws.com/logos/stack/javascript.png";
    public static final String VALID_STACK_ICON_3 = "https://bootme-images.s3.ap-northeast-2.amazonaws.com/logos/stack/spring.png";
    public static final String VALID_STACK_ICON_4 = "https://bootme-images.s3.ap-northeast-2.amazonaws.com/logos/stack/react.png";
    public static final String VALID_BIRTHDAY_1 = "01-01";
    public static final String VALID_BIRTHDAY_2 = "02-01";
    public static final String VALID_BIRTHDAY_3 = "03-01";
    public static final String VALID_BIRTHYEAR_1 = "1994";
    public static final String VALID_BIRTHYEAR_2 = "1995";
    public static final String VALID_BIRTHYEAR_3 = "1996";
    public static final String VALID_AGE_RANGE_1 = "10-19";
    public static final String VALID_AGE_RANGE_2 = "20-29";
    public static final String VALID_AGE_RANGE_3 = "30-39";
    public static final String MALE = "male";
    public static final String FEMALE = "female";
    public static final String VALID_NICKNAME_1 = "michael_jordan_63";
    public static final String VALID_NICKNAME_2 = "magic_johnson";
    public static final String VALID_NICKNAME_3 = "black mamba";
    public static final String VALID_PHONE_NUMBER_1 = "010-1111-1111";
    public static final String VALID_PHONE_NUMBER_2 = "010-2222-2222";
    public static final String VALID_PHONE_NUMBER_3 = "010-3333-3333";

    public static Member getMember(int index) {
        index--;
        String[] emails = {VALID_EMAIL_1, VALID_EMAIL_2, VALID_EMAIL_3};
        String[] names = {VALID_NAME_1, VALID_NAME_2, VALID_NAME_3};
        String[] profileImages = {VALID_PROFILE_IMAGE_1, VALID_PROFILE_IMAGE_2 ,VALID_PROFILE_IMAGE_3};
        String[] nicknames = {VALID_NICKNAME_1, VALID_NICKNAME_2, VALID_NICKNAME_3};
        String[] phoneNumbers = {VALID_PHONE_NUMBER_1, VALID_PHONE_NUMBER_2, VALID_PHONE_NUMBER_3};

        return Member.builder()
                .email(emails[index])
                .name(names[index])
                .profileImage(profileImages[index])
                .nickname(nicknames[index])
                .phoneNumber(phoneNumbers[index])
                .roleType(RoleType.USER)
                .build();
    }

    public static StackRequest getStackRequest(int index) {
        index--;
        String[] names = {VALID_STACK_1, VALID_STACK_2, VALID_STACK_3, VALID_STACK_4};
        String[] types = {VALID_STACK_TYPE_1, VALID_STACK_TYPE_2, VALID_STACK_TYPE_3, VALID_STACK_TYPE_4};
        String[] icons = {VALID_STACK_ICON_1, VALID_STACK_ICON_2, VALID_STACK_ICON_3, VALID_STACK_ICON_4};

        return StackRequest.builder()
                .name(names[index])
                .type(types[index])
                .icon(icons[index])
                .build();
    }

    public static StackResponse getStackResponse(int index) {
        index--;
        String[] names = {VALID_STACK_1, VALID_STACK_2, VALID_STACK_3, VALID_STACK_4};
        String[] types = {VALID_STACK_TYPE_1, VALID_STACK_TYPE_2, VALID_STACK_TYPE_3, VALID_STACK_TYPE_4};
        String[] icons = {VALID_STACK_ICON_1, VALID_STACK_ICON_2, VALID_STACK_ICON_3, VALID_STACK_ICON_4};

        return StackResponse.builder()
                .name(names[index])
                .type(types[index])
                .icon(icons[index])
                .build();
    }

    public static ProfileResponse getProfileResponse(int index) {
        List<StackResponse> stackResponses = new ArrayList<>();
        stackResponses.add(getStackResponse(index));
        stackResponses.add(getStackResponse(index));

        index--;
        String[] profileImages = {VALID_PROFILE_IMAGE_1, VALID_PROFILE_IMAGE_2 ,VALID_PROFILE_IMAGE_3};
        String[] emails = {VALID_EMAIL_1, VALID_EMAIL_2, VALID_EMAIL_3};
        String[] nicknames = {VALID_NICKNAME_1, VALID_NICKNAME_2, VALID_NICKNAME_3};
        String[] jobs = {VALID_JOB_1, VALID_JOB_2, VALID_JOB_3};

        return ProfileResponse.builder()
                .profileImage(profileImages[index])
                .email(emails[index])
                .nickname(nicknames[index])
                .job(jobs[index])
                .stacks(stackResponses)
                .build();
    }

    public static MyProfileResponse getMyProfileResponse(int index) {
        index--;
        String[] profileImages = {VALID_PROFILE_IMAGE_1, VALID_PROFILE_IMAGE_2 ,VALID_PROFILE_IMAGE_3};
        String[] emails = {VALID_EMAIL_1, VALID_EMAIL_2, VALID_EMAIL_3};
        String[] nicknames = {VALID_NICKNAME_1, VALID_NICKNAME_2, VALID_NICKNAME_3};
        String[] jobs = {VALID_JOB_1, VALID_JOB_2, VALID_JOB_3};
        String[] stacks = {VALID_STACK_1, VALID_STACK_2, VALID_STACK_3, VALID_STACK_4};
        String[] selectedStacks = new String[] {stacks[index], stacks[index + 1]};

        return MyProfileResponse.builder()
                .profileImage(profileImages[index])
                .email(emails[index])
                .nickname(nicknames[index])
                .job(jobs[index])
                .stacks(selectedStacks)
                .build();
    }

    public static UpdateProfileRequest getUpdateProfileRequest(int index) {
        index--;
        String[] emails = {VALID_EMAIL_1, VALID_EMAIL_2, VALID_EMAIL_3};
        String[] nicknames = {VALID_NICKNAME_1, VALID_NICKNAME_2, VALID_NICKNAME_3};
        String[] jobs = {VALID_JOB_1, VALID_JOB_2, VALID_JOB_3};
        List<String> stackNames = new ArrayList<>();
        stackNames.add(VALID_STACK_1);
        stackNames.add(VALID_STACK_2);
        stackNames.add(VALID_STACK_3);
        stackNames.add(VALID_STACK_4);

        return UpdateProfileRequest.builder()
                .email(emails[index])
                .nickname(nicknames[index])
                .job(jobs[index])
                .stackNames(stackNames)
                .build();
    }

    public static UpdateImageRequest getUpdateImageRequest(int index) {
        index--;
        String[] profileImages = {VALID_PROFILE_IMAGE_1, VALID_PROFILE_IMAGE_2 ,VALID_PROFILE_IMAGE_3};

        return new UpdateImageRequest(profileImages[index]);

    }

}
