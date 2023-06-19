package com.bootme.member.domain;

import com.bootme.auth.dto.UserInfo;
import com.bootme.common.domain.BaseEntity;
import com.bootme.common.exception.ArgumentNotValidException;
import com.bootme.common.exception.ErrorType;
import com.bootme.notification.domain.Notification;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    @Column(name="oauth_provider", nullable = false)
    private String oAuthProvider;

    private String name;

    private String profileImage;

    private String job;

    private String birthday;

    private String birthYear;

    private String ageRange;

    private String gender;

    private String nickname;

    private String phoneNumber;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Notification> notifications = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleType roleType;

    @Column(nullable = false)
    private Long visitsCount;

    @Builder
    public Member(String email, String password, String oAuthProvider, String name, String profileImage,
                  String job, String birthday, String birthYear, String ageRange, String gender,
                  String nickname, String phoneNumber, RoleType roleType, Long visitsCount) {
        this.email = email;
        this.password = password;
        this.oAuthProvider = oAuthProvider;
        this.name = name;
        this.profileImage = profileImage;
        this.job = job;
        this.birthday = birthday;
        this.birthYear = birthYear;
        this.ageRange = ageRange;
        this.gender = gender;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.roleType = roleType;
        this.visitsCount = visitsCount;
    }

    public static Member of(UserInfo userInfo) {
        String email = userInfo.getEmail();
        if (email == null || email.isEmpty()) {
            throw new ArgumentNotValidException(ErrorType.INVALID_EMAIL_NULL, email);
        }
        String nickname = userInfo.getNickname();
        if (nickname == null || nickname.isEmpty()) {
            nickname = userInfo.getName();
        }
        return Member.builder()
                .email(userInfo.getEmail())
//                .password()
                .oAuthProvider(userInfo.getOAuthProvider())
                .name(userInfo.getName())
                .profileImage(userInfo.getPicture())
                .birthday(userInfo.getBirthDay())
                .birthYear(userInfo.getBirthYear())
                .ageRange(userInfo.getAgeRange())
                .gender(userInfo.getGender())
                .nickname(nickname)
                .phoneNumber(userInfo.getPhoneNumber())
                .roleType(RoleType.USER)
                .visitsCount(1L)
                .build();
    }

}
