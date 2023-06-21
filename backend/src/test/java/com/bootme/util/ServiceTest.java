package com.bootme.util;

import com.amazonaws.secretsmanager.caching.SecretCache;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.bootme.auth.dto.AwsSecrets;
import com.bootme.auth.service.AuthService;
import com.bootme.auth.util.TokenProvider;
import com.bootme.bookmark.service.CourseBookmarkService;
import com.bootme.common.interceptor.TokenValidationInterceptor;
import com.bootme.course.service.CompanyService;
import com.bootme.course.service.CourseService;
import com.bootme.image.service.ImageService;
import com.bootme.member.service.MemberService;
import com.bootme.notification.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public abstract class ServiceTest {

    @MockBean
    protected AWSSecretsManager awsSecretsManager;

    @MockBean
    protected SecretCache secretCache;

    @MockBean
    protected AwsSecrets awsSecrets;

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
    protected CourseBookmarkService courseBookmarkService;

    @Autowired
    protected NotificationService notificationService;

    @MockBean
    protected ImageService imageService;

}
