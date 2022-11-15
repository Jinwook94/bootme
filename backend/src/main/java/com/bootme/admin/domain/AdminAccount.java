package com.bootme.admin.domain;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class AdminAccount {

    @Value("${admin.id}")
    private String id;
    @Value("${admin.password}")
    private String password;

}
