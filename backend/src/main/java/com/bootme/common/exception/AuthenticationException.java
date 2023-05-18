package com.bootme.common.exception;

public class AuthenticationException extends UnauthorizedException {

    public AuthenticationException(ErrorType errorType) {
        super(errorType);
    }

    public AuthenticationException(ErrorType errorType, String invalidInput) {
        super(errorType, invalidInput);
    }

    public AuthenticationException(ErrorType errorType, String invalidInput, Throwable e) {
        super(errorType, invalidInput, e);
    }
}
