package com.bootme.common.exception;

import lombok.Getter;

@Getter
public abstract class UnauthorizedException extends BusinessException {

    protected UnauthorizedException(ErrorType errorType) {
        super(errorType);
    }

    protected UnauthorizedException(ErrorType errorType, String invalidInput) {
        super(errorType, invalidInput);
    }

    protected UnauthorizedException(ErrorType errorType, String invalidInput, Throwable e) {
        super(errorType, invalidInput, e);
    }

}
