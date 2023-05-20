package com.bootme.member.domain;

import com.bootme.auth.dto.JwtVo;
import com.bootme.common.domain.BaseEntity;
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

    @Column(nullable = false)
    private String oAuthProvider;

    private String name;

    private String profileImage;

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
                  String birthday, String birthYear, String ageRange, String gender, String nickname,
                  String phoneNumber, RoleType roleType) {
        this.email = email;
        this.password = password;
        this.oAuthProvider = oAuthProvider;
        this.name = name;
        this.profileImage = profileImage;
        this.birthday = birthday;
        this.birthYear = birthYear;
        this.ageRange = ageRange;
        this.gender = gender;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.roleType = roleType;
    }

    public static Member of(JwtVo.Body body) {
        return Member.builder()
                .email(body.getEmail())
//                .password()
                .oAuthProvider(body.getOAuthProvider())
                .name(body.getName())
                .profileImage(body.getPicture())
                .birthday(body.getBirthDay())
                .birthYear(body.getBirthYear())
                .ageRange(body.getAgeRange())
                .gender(body.getGender())
                .nickname(body.getNickname())
                .phoneNumber(body.getPhoneNumber())
                .roleType(RoleType.USER)
                .build();
    }
}
