package com.bootme.common.exception;

public class ExternalServiceException extends BusinessException {

    public ExternalServiceException(ErrorType errorType) {
        super(errorType);
    }

    public ExternalServiceException(ErrorType errorType, String invalidInput) {
        super(errorType, invalidInput);
    }

    public ExternalServiceException(ErrorType errorType, Throwable cause) {
        super(errorType, cause);
    }

    public ExternalServiceException(ErrorType errorType, String invalidInput, Throwable cause) {
        super(errorType, invalidInput, cause);
    }

}
