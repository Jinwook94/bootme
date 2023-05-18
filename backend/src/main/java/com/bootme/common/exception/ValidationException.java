package com.bootme.common.exception;

public class ValidationException extends BadRequestException {
    public ValidationException(ErrorType errorType) {
        super(errorType);
    }

    public ValidationException(ErrorType errorType, String invalidInput) {
        super(errorType, invalidInput);
    }

    public ValidationException(ErrorType errorType, String invalidInput, Throwable cause) {
        super(errorType, invalidInput, cause);
    }
}
