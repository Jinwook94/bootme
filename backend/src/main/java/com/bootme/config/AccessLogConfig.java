package com.bootme.config;

import ch.qos.logback.access.servlet.TeeFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 엑세스 로그에서 요청 바디를 출력하기 위해 TeeFilter 등록 필요하여 작성 <br>
 * 엑세스 로그 설정 파일(file-access-logger.xml)의 로그 출력 패턴 참고 <br><br>
 *
 * 참고: <a href="https://logback.qos.ch/manual/layouts.html#requestContent:~:text=of%20the%20response.-,requestContent,-This%20conversion%20word">Logback documentation</a>, <a href="https://ozymaxx.github.io/blog/2020/06/19/logback-access-en/#:~:text=We%20went%20really,ourselves%20(here)%3A">TeeFilter 관련 아티클</a>
 */
@Configuration
public class AccessLogConfig {
    @Bean
    public FilterRegistrationBean<TeeFilter> requestLogFilter() {
        FilterRegistrationBean<TeeFilter> registrationBean = new FilterRegistrationBean<>();

        TeeFilter teeFilter = new TeeFilter();
        registrationBean.setFilter(teeFilter);

        return registrationBean;
    }
}