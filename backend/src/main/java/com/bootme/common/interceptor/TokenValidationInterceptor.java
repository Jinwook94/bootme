package com.bootme.common.interceptor;

import com.bootme.auth.dto.JwtVo;
import com.bootme.auth.service.AuthService;
import com.bootme.auth.util.TokenProvider;
import com.bootme.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import static com.bootme.common.util.RequestUtils.getCookieValue;

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

    private final String domain;
    private final long accessTokenExpireTimeInMilliseconds;
    private final TokenProvider tokenProvider;
    private final MemberService memberService;
    private final AuthService authService;

    public TokenValidationInterceptor(@Value("${domain}") String domain,
                                      @Value("${security.jwt.bootme.exp.millisecond.access}") long accessTokenExpireTimeInMilliseconds,
                                      TokenProvider tokenProvider,
                                      MemberService memberService,
                                      AuthService authService) {
        this.domain = domain;
        this.accessTokenExpireTimeInMilliseconds = accessTokenExpireTimeInMilliseconds;
        this.tokenProvider = tokenProvider;
        this.memberService = memberService;
        this.authService = authService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String accessToken = getCookieValue(request, "accessToken");
        String refreshToken = getCookieValue(request, "refreshToken");

        boolean login = isAccessTokenValid(accessToken) || isRefreshTokenValidAndReissueAccessToken(refreshToken, response);
        response.addHeader("Login", Boolean.toString(login));
        response.addHeader("Access-Control-Expose-Headers", "Login");

        return true;
    }

    private boolean isAccessTokenValid(String accessToken) {
        if (accessToken == null) {
            return false;
        }
        JwtVo jwtVo = authService.parseToken(accessToken);
        String email = jwtVo.getBody().getEmail();
        return tokenProvider.isValid(accessToken) && memberService.isRegistered(email);
    }

    private boolean isRefreshTokenValidAndReissueAccessToken(String refreshToken, HttpServletResponse response) {
        if (refreshToken == null) {
            return false;
        }
        JwtVo jwtVo = authService.parseToken(refreshToken);
        String email = jwtVo.getBody().getEmail();
        if (tokenProvider.isValid(refreshToken) && memberService.isRegistered(email)) {
            String newAccessToken = tokenProvider.reissueAccessToken(refreshToken);
            setAccessTokenCookie(newAccessToken, response);
            return true;
        }
        return false;
    }

    private void setAccessTokenCookie(String accessToken, HttpServletResponse response) {
        String cookie = tokenProvider.getCookie("accessToken", accessToken, domain, accessTokenExpireTimeInMilliseconds);
        response.addHeader("Set-Cookie", cookie);
    }

}
