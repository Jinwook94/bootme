package com.bootme.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

import java.lang.reflect.Method;

@Slf4j
public class AsyncExceptionHandler implements AsyncUncaughtExceptionHandler {

    @Override
    public void handleUncaughtException(Throwable throwable, Method method, Object... obj) {
        log.error("Exception message - " + throwable.getMessage());
        log.error("Method name - " + method.getName());
        for (Object param : obj) {
            log.error("Parameter value - " + param);
        }

        if (throwable instanceof BadRequestException) {
            BadRequestException exception = (BadRequestException) throwable;
            log.error("BadRequestException occurred, error type: " + exception.getErrorType());
        } else if (throwable instanceof UnauthorizedException) {
            UnauthorizedException exception = (UnauthorizedException) throwable;
            log.error("UnauthorizedException occurred, error type: " + exception.getErrorType());
        } else if (throwable instanceof ForbiddenException) {
            ForbiddenException exception = (ForbiddenException) throwable;
            log.error("ForbiddenException occurred, error type: " + exception.getErrorType());
        } else if (throwable instanceof RuntimeException) {
            log.error("A RuntimeException occurred", throwable);
        }
    }

}

