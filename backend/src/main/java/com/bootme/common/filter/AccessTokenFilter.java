package com.bootme.common.filter;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AccessTokenFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String cookie = request.getHeader("Cookie");
        String login = "false";
        if (cookie != null) {
            boolean isAccessToken = cookie.contains("accessToken");
            boolean inValidToken = cookie.contains("accessToken=;");

            if (isAccessToken && !inValidToken) {
                login = "true";
            }
        }

        response.addHeader("Login", login);
        response.addHeader("Access-Control-Expose-Headers", "Login");

        filterChain.doFilter(request, response);
    }

}
