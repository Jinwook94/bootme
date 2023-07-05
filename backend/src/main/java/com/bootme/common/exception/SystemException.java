package com.bootme.common.exception;

import lombok.Getter;

@Getter
public abstract class SystemException extends RuntimeException {

    private final ErrorType errorType;

    protected SystemException(ErrorType errorType) {
        super(errorType.getMessage());
        this.errorType = errorType;
    }

    protected SystemException(ErrorType errorType, String invalidInput) {
        super(errorType.getMessage(invalidInput));
        this.errorType = errorType;
    }

    protected SystemException(ErrorType errorType, Throwable cause) {
        super(cause);
        this.errorType = errorType;
    }

    protected SystemException(ErrorType errorType, String invalidInput, Throwable cause) {
        super(errorType.getMessage(invalidInput), cause);
        this.errorType = errorType;
    }

}
