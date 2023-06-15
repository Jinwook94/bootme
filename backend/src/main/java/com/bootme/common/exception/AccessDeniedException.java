package com.bootme.common.exception;

public class AccessDeniedException extends ForbiddenException {

    public AccessDeniedException(ErrorType errorType) {
        super(errorType);
    }

    public AccessDeniedException(ErrorType errorType, String invalidInput) {
        super(errorType, invalidInput);
    }
}
