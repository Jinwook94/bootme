package com.bootme.util;

import com.bootme.admin.service.AdminService;
import com.bootme.auth.token.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class ServiceTest {

    @Autowired
    protected TokenProvider tokenProvider;

    @Autowired
    protected AdminService adminService;
}
