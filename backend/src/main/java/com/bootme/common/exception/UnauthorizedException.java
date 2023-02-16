package com.bootme.common.exception;

import lombok.Getter;

@Getter
public class UnauthorizedException extends RuntimeException{

    private final ErrorType errorType;
    private String invalidInput;

    public UnauthorizedException(ErrorType errorType) {
        this.errorType = errorType;
    }

    public UnauthorizedException(ErrorType errorType, String invalidInput) {
        this.errorType = errorType;
        this.invalidInput = errorType.getMessage(invalidInput);
    }

}
