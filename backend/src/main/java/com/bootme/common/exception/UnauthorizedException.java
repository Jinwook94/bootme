package com.bootme.common.exception;

import lombok.Getter;

@Getter
public abstract class UnauthorizedException extends BusinessException {

    public UnauthorizedException(ErrorType errorType) {
        super(errorType);
    }

    public UnauthorizedException(ErrorType errorType, String invalidInput) {
        super(errorType, invalidInput);
    }

}
