package com.bootme.common.exception;

public class SerializationException extends SystemException {

    public SerializationException(ErrorType errorType) {
        super(errorType);
    }

    public SerializationException(ErrorType errorType, String invalidInput) {
        super(errorType, invalidInput);
    }

    public SerializationException(ErrorType errorType, String invalidInput, Throwable cause) {
        super(errorType, invalidInput, cause);
    }

}
