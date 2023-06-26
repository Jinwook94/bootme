package com.bootme.member.domain;

import com.bootme.auth.dto.UserInfo;
import com.bootme.common.domain.BaseEntity;
import com.bootme.common.exception.ArgumentNotValidException;
import com.bootme.common.exception.ErrorType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "oauth_info")
public class OauthInfo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "oauth_info_id")
    private Long id;

    @Column(name="oauth_provider", nullable = false)
    private String oAuthProvider;

    @Column(unique = true, nullable = false)
    private String email;

    private String nickname;

    private String name;

    private String profileImage;

    @Builder
    public OauthInfo(String oAuthProvider, String email, String nickname, String name, String profileImage) {
        this.oAuthProvider = oAuthProvider;
        this.email = email;
        this.nickname = nickname;
        this.name = name;
        this.profileImage = profileImage;
    }

    public static OauthInfo of(UserInfo userInfo) {
        String email = userInfo.getEmail();
        if (email == null || email.isEmpty()) {
            throw new ArgumentNotValidException(ErrorType.INVALID_EMAIL_NULL, email);
        }
        String nickname = userInfo.getNickname();
        if (nickname == null || nickname.isEmpty()) {
            nickname = userInfo.getName();
        }
        return OauthInfo.builder()
                .oAuthProvider(userInfo.getOAuthProvider())
                .email(userInfo.getEmail())
                .nickname(nickname)
                .name(userInfo.getName())
                .profileImage(userInfo.getPicture())
                .build();
    }

}
