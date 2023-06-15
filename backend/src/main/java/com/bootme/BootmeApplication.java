package com.bootme;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class BootmeApplication {
    // CI CD
    public static void main(String[] args) {
        SpringApplication.run(BootmeApplication.class, args);
    }

}

