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

        if (request.getHeader("Cookie") != null && request.getHeader("Cookie").contains("accessToken")) {
            response.addHeader("Login", "true");
        } else {
            response.addHeader("Login", "false");
        }

        response.addHeader("Access-Control-Expose-Headers", "Login");

        filterChain.doFilter(request, response);
    }

}
