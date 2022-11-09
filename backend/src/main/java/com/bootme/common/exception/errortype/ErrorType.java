package com.bootme.common.exception.errortype;

import org.springframework.http.HttpStatus;

public interface ErrorType {

    String name();

    HttpStatus getHttpStatus();

    int getErrorCode();

    String getMessage();

}
