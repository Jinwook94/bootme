package com.bootme.common.interceptor;

//import com.bootme.auth.util.IPFilter;
//import com.bootme.common.exception.AccessDeniedException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//import org.springframework.web.method.HandlerMethod;
//import org.springframework.web.servlet.HandlerInterceptor;
//
//import java.util.Arrays;
//import java.util.List;
//
//import static com.bootme.common.exception.ErrorType.FORBIDDEN_REQUEST;
//
//@Component
//public class IPInterceptor implements HandlerInterceptor {
//
//
//    private final List<String> allowedIps;
//
//    public IPInterceptor(@Value("${allowed-ips}") String allowedOriginsString) {
//        this.allowedIps = Arrays.asList(allowedOriginsString.split(","));
//    }
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
//        if (handler instanceof HandlerMethod) {
//            HandlerMethod method = (HandlerMethod) handler;
//
//            IPFilter ipFilter = method.getMethodAnnotation(IPFilter.class);
//
//            if (ipFilter != null) {
//                String requestIP = request.getRemoteAddr();
//                if (allowedIps.contains(requestIP)) {
//                    return true;
//                } else {
//                    throw new AccessDeniedException(FORBIDDEN_REQUEST, requestIP);
//                }
//            }
//        }
//
//        return true;
//    }
//}
