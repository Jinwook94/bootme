package com.bootme.common.exception;

public class FileHandlingException extends BadRequestException {

    public FileHandlingException(ErrorType errorType) {
        super(errorType);
    }

    public FileHandlingException(ErrorType errorType, String invalidInput) {
        super(errorType, invalidInput);
    }

    public FileHandlingException(ErrorType errorType, String invalidInput, Throwable cause) {
        super(errorType, invalidInput, cause);
    }

}
