package com.bootme.common.exception;

import lombok.Getter;

@Getter
public abstract class BusinessException extends RuntimeException {

    private final ErrorType errorType;

    protected BusinessException(ErrorType errorType) {
        super(errorType.getMessage());
        this.errorType = errorType;
    }

    protected BusinessException(ErrorType errorType, String invalidInput) {
        super(errorType.getMessage(invalidInput));
        this.errorType = errorType;
    }

    protected BusinessException(ErrorType errorType, Throwable cause) {
        super(cause);
        this.errorType = errorType;
    }

    protected BusinessException(ErrorType errorType, String invalidInput, Throwable cause) {
        super(errorType.getMessage(invalidInput), cause);
        this.errorType = errorType;
    }

}
