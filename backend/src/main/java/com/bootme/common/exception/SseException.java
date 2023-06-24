package com.bootme.common.exception;

public class SseException extends SystemException {

    public SseException(ErrorType errorType) {
        super(errorType);
    }

    public SseException(ErrorType errorType, String invalidInput) {
        super(errorType, invalidInput);
    }

    public SseException(ErrorType errorType, String invalidInput, Throwable cause) {
        super(errorType, invalidInput, cause);
    }

}
