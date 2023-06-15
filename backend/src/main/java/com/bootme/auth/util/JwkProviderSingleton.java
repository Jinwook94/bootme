package com.bootme.auth.util;

import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.JwkProviderBuilder;

import java.util.concurrent.TimeUnit;

// 카카오 로그인시 매번 OAuth provider 에 접속해야 하는 성능 이슈 방지 위해 싱글톤으로 구현
public class JwkProviderSingleton {

    private static JwkProvider instance;

    private JwkProviderSingleton() {}

    public static synchronized JwkProvider getInstance() {
        if (instance == null) {
            instance = new JwkProviderBuilder("https://kauth.kakao.com")
                    .cached(10, 7, TimeUnit.DAYS)
                    .build();
        }
        return instance;
    }

}
