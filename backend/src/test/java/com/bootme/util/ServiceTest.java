package com.bootme.util;

import com.bootme.auth.service.AuthService;
import com.bootme.auth.token.TokenProvider;
import com.bootme.common.interceptor.TokenValidationInterceptor;
import com.bootme.course.service.CompanyService;
import com.bootme.course.service.CourseService;
import com.bootme.member.service.MemberService;
import com.bootme.notification.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public abstract class ServiceTest {

    @Autowired
    protected TokenProvider tokenProvider;

    @Autowired
    protected TokenValidationInterceptor tokenValidationInterceptor;

    @Autowired
    protected AuthService authService;

    @Autowired
    protected CourseService courseService;

    @Autowired
    protected CompanyService companyService;

    @Autowired
    protected MemberService memberService;

    @Autowired
    protected NotificationService notificationService;

}
