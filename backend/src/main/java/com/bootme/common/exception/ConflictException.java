package com.bootme.common.exception;

public class ConflictException extends BadRequestException {
    public ConflictException(ErrorType errorType) {
        super(errorType);
    }

    public ConflictException(ErrorType errorType, String invalidInput) {
        super(errorType, invalidInput);
    }

    public ConflictException(ErrorType errorType, String invalidInput, Throwable cause) {
        super(errorType, invalidInput, cause);
    }
}
