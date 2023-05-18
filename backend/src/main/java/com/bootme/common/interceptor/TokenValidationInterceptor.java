package com.bootme.common.interceptor;

import com.bootme.auth.token.TokenProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * HTTP 요청을 처리하기 전에 엑세스 토큰과 리프레시 토큰의 유효성을 검증하는 인터셉터
 * <ul>
 *     <li>엑세스 토큰이 있고, 토큰이 유효한 경우: Login 헤더 true로 설정 </li>
 *     <li>엑세스 토큰이 없고, 리프레시 토큰이 있고, 토큰이 유효한 경우: 엑세스 토큰을 재발급하고, Login 헤더를 true로 설정 </li>
 *     <li>그 외의 경우: Login 헤더를 false로 설정</li>
 * </ul>
 * {@link com.bootme.config.WebMvcConfig} 에서 인터셉터로 등록되어 사용됨
 */
@Component
public class TokenValidationInterceptor implements HandlerInterceptor {

    private final long accessTokenExpireTimeInMilliseconds;

    private final TokenProvider tokenProvider;

    public TokenValidationInterceptor(@Value("${security.jwt.bootme.exp.millisecond.access}") long accessTokenExpireTimeInMilliseconds,
                                      TokenProvider tokenProvider) {
        this.accessTokenExpireTimeInMilliseconds = accessTokenExpireTimeInMilliseconds;
        this.tokenProvider = tokenProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        // 1. 요청 헤더의 쿠키에서 토큰을 추출
        Cookie[] cookies = request.getCookies();
        String accessToken = null;
        String refreshToken = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("accessToken")) {
                    accessToken = cookie.getValue();
                }
                if (cookie.getName().equals("refreshToken")) {
                    refreshToken = cookie.getValue();
                }
            }
        }

        // 2. 토큰 검증
        String login = "false";
        if (accessToken != null && tokenProvider.isValid(accessToken)) {
            login = "true";
        } else if (refreshToken != null && tokenProvider.isValid(refreshToken)) {
            accessToken = tokenProvider.reissueAccessToken(refreshToken);
            response.addHeader("Set-Cookie", "accessToken=" + accessToken + "; Max-Age=" +
                    accessTokenExpireTimeInMilliseconds/1000 + "; HttpOnly; Secure; SameSite=Lax");
            login = "true";
        }

        response.addHeader("Login", login);
        response.addHeader("Access-Control-Expose-Headers", "Login");

        return true;
    }
}
