package com.bootme.common.exception;

public class ArgumentNotValidException extends BadRequestException {

    public ArgumentNotValidException(ErrorType errorType) {
        super(errorType);
    }

    public ArgumentNotValidException(ErrorType errorType, String invalidInput) {
        super(errorType, invalidInput);
    }

    public ArgumentNotValidException(ErrorType errorType, String invalidInput, Throwable cause) {
        super(errorType, invalidInput, cause);
    }
}
