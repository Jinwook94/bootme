package com.bootme.util.fixture;

import com.bootme.member.domain.Member;
import com.bootme.member.domain.RoleType;

public class MemberFixture {

    public static final String VALID_EMAIL_1 = "john@gmail.com";
    public static final String VALID_EMAIL_2 = "mary@naver.com";
    public static final String VALID_EMAIL_3 = "james@kakao.com";
    public static final String VALID_PASSWORD_1 = null;
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

    public static Member getMember(int index){
        index--;
        String[] emails = {VALID_EMAIL_1, VALID_EMAIL_2, VALID_EMAIL_3};
        String[] passwords = {VALID_PASSWORD_1, VALID_PASSWORD_2, VALID_PASSWORD_3};
        String[] oAuthProviders = {GOOGLE, NAVER, KAKAO};
        String[] names = {VALID_NAME_1, VALID_NAME_2, VALID_NAME_3};
        String[] profileImages = {VALID_PROFILE_IMAGE_1, VALID_PROFILE_IMAGE_2 ,VALID_PROFILE_IMAGE_3};
        String[] birthdays = {VALID_BIRTHDAY_1, VALID_BIRTHDAY_2, VALID_BIRTHDAY_3};
        String[] birthYears = {VALID_BIRTHYEAR_1, VALID_BIRTHYEAR_2, VALID_BIRTHYEAR_3};
        String[] ageRanges = {VALID_AGE_RANGE_1, VALID_AGE_RANGE_2, VALID_AGE_RANGE_3};
        String[] nicknames = {VALID_NICKNAME_1, VALID_NICKNAME_2, VALID_NICKNAME_3};
        String[] phoneNumbers = {VALID_PHONE_NUMBER_1, VALID_PHONE_NUMBER_2, VALID_PHONE_NUMBER_3};

        return Member.builder()
                .email(emails[index])
                .password(passwords[index])
                .oAuthProvider(oAuthProviders[index])
                .name(names[index])
                .profileImage(profileImages[index])
                .birthday(birthdays[index])
                .birthYear(birthYears[index])
                .ageRange(ageRanges[index])
                .gender(MALE)
                .nickname(nicknames[index])
                .phoneNumber(phoneNumbers[index])
                .roleType(RoleType.USER)
                .build();
    }

}
