package com.bootme.common.exception;

public class ResourceNotFoundException extends BadRequestException {

    public ResourceNotFoundException(ErrorType errorType) {
        super(errorType);
    }

    public ResourceNotFoundException(ErrorType errorType, String invalidInput) {
        super(errorType, invalidInput);
    }

    public ResourceNotFoundException(ErrorType errorType, String invalidInput, Throwable cause) {
        super(errorType, invalidInput, cause);
    }
}
