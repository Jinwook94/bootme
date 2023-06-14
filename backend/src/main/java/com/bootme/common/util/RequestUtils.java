package com.bootme.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class RequestUtils {

    private RequestUtils() {
    }

    public static String getRequestInfo(HttpServletRequest request) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
        Map<String, Object> requestInfo = new HashMap<>();

        putIfNotNull(requestInfo, "method", request.getMethod());
        putIfNotNull(requestInfo, "request_url", request.getRequestURL() != null ? request.getRequestURL().toString() : null);
        putIfNotNull(requestInfo, "query_parameters", request.getQueryString());
        putIfNotNull(requestInfo, "header_path", request.getHeader("Path"));
        putIfNotNull(requestInfo, "header_origin", request.getHeader("Origin"));
        putIfNotNull(requestInfo, "header_referer", request.getHeader("Referer"));
        putIfNotNull(requestInfo, "header_user_agent", request.getHeader("User-Agent"));

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            Map<String, String> cookiesMap = Arrays.stream(cookies)
                    .collect(Collectors.toMap(Cookie::getName, Cookie::getValue));
            requestInfo.put("cookies", cookiesMap);
        }
        return mapper.writeValueAsString(requestInfo);
    }

    private static void putIfNotNull(Map<String, Object> map, String key, Object value) {
        if (value != null) {
            map.put(key, value);
        }
    }

    public static String getCookieValue(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

}
