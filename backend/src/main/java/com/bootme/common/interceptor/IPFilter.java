package com.bootme.common.interceptor;

import com.bootme.auth.utils.IPFiltering;
import com.bootme.common.exception.AccessDeniedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

import static com.bootme.common.exception.ErrorType.FORBIDDEN_REQUEST;

@Component
public class IPFilter implements HandlerInterceptor {


    private final List<String> allowedIps;

    public IPFilter(@Value("${allowed-ips}") String allowedOriginsString) {
        this.allowedIps = Arrays.asList(allowedOriginsString.split(","));
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod method = (HandlerMethod) handler;

            IPFiltering ipFiltering = method.getMethodAnnotation(IPFiltering.class);

            if (ipFiltering != null) {
                String requestIP = request.getRemoteAddr();
                if (allowedIps.contains(requestIP)) {
                    return true;
                } else {
                    throw new AccessDeniedException(FORBIDDEN_REQUEST, requestIP);
                }
            }
        }

        return true;
    }
}
