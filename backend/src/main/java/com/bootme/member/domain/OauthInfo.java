package com.bootme.member.domain;

import com.bootme.auth.dto.UserInfo;
import com.bootme.common.domain.BaseEntity;
import com.bootme.common.enums.JwtIssuer;
import com.bootme.common.exception.ArgumentNotValidException;
import com.bootme.common.exception.ErrorType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
        JwtIssuer issuer = JwtIssuer.fromString(userInfo.getIss());
        String email = userInfo.getEmail();
        if (email == null || email.isEmpty()) {
            throw new ArgumentNotValidException(ErrorType.INVALID_EMAIL_NULL, email);
        }
        String nickname = userInfo.getNickname();
        if (nickname == null || nickname.isEmpty()) {
            nickname = userInfo.getName();
        }
        String profileImage = userInfo.getPicture();
        if (profileImage == null || profileImage.isEmpty()) {
            profileImage = "https://bootme-images.s3.ap-northeast-2.amazonaws.com/profile/default_profile.png";
        }
        return OauthInfo.builder()
                .oAuthProvider(issuer.toString())
                .email(userInfo.getEmail())
                .nickname(nickname)
                .name(userInfo.getName())
                .profileImage(profileImage)
                .build();
    }

}
