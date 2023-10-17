package com.bootme.common.filter;

import jakarta.servlet.*;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 참고 <br>
 * - <a href="https://mangkyu.tistory.com/266">[Spring] 멀티쓰레드 환경에서 MDC를 사용해 요청 별로 식별가능한 로그 남기기</a> <br>
 * - <a href="https://logback.qos.ch/manual/mdc.html">logback documentation_MDC</a>
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
class MDCLoggingFilter implements Filter {

    private static final AtomicLong idCounter = new AtomicLong();

    private static String createID() {
        return String.valueOf(idCounter.getAndIncrement());
    }

    @Override
    public void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse, final FilterChain filterChain) throws IOException, ServletException {
        MDC.put("requestId", createID());
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            MDC.remove("requestId");
        }
    }

}
