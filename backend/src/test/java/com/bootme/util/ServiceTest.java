package com.bootme.util;

import com.bootme.auth.token.TokenProvider;
import com.bootme.course.service.CompanyService;
import com.bootme.course.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class ServiceTest {

    @Autowired
    protected TokenProvider tokenProvider;

    @Autowired
    protected CourseService courseService;

    @Autowired
    protected CompanyService companyService;

}
