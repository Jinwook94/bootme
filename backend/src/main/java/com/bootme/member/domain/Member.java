package com.bootme.member.domain;

import com.bootme.auth.dto.JwtVo;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "email", unique = true)
    private String email;

    private String password;

    private String OAuthProvider;

    private String name;

    private String profileImage;

    private String birthday;

    private String birthYear;

    private String ageRange;

    private String gender;

    private String nickname;

    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    private Long visitsCount;

    private LocalDateTime createdAt;

    private LocalDateTime lastLoginAt;

    @Builder
    public Member(String email, String password, String OAuthProvider, String name, String profileImage,
                  String birthday, String birthYear, String ageRange, String gender, String nickname,
                  String phoneNumber, RoleType roleType, Long visitsCount, LocalDateTime createdAt, LocalDateTime lastLoginAt) {
        this.email = email;
        this.password = password;
        this.OAuthProvider = OAuthProvider;
        this.name = name;
        this.profileImage = profileImage;
        this.birthday = birthday;
        this.birthYear = birthYear;
        this.ageRange = ageRange;
        this.gender = gender;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.roleType = roleType;
        this.visitsCount = visitsCount;
        this.createdAt = createdAt;
        this.lastLoginAt = lastLoginAt;
    }

    public static Member of(JwtVo.Body body) {
        LocalDateTime now = LocalDateTime.now();
        return Member.builder()
                .email(body.getEmail())
//                .password()
                .OAuthProvider(body.getOAuthProvider())
                .name(body.getName())
                .profileImage(body.getPicture())
                .birthday(body.getBirthDay())
                .birthYear(body.getBirthYear())
                .ageRange(body.getAgeRange())
                .gender(body.getGender())
                .nickname(body.getNickname())
                .phoneNumber(body.getPhoneNumber())
                .roleType(RoleType.USER)
                .visitsCount(1L)
                .createdAt(now)
                .lastLoginAt(now)
                .build();
    }
}
