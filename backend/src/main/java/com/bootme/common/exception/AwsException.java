package com.bootme.common.exception;

public class AwsException extends BusinessException {

    public AwsException(ErrorType errorType) {
        super(errorType);
    }

    public AwsException(ErrorType errorType, String invalidInput) {
        super(errorType, invalidInput);
    }

    public AwsException(ErrorType errorType, String invalidInput, Throwable cause) {
        super(errorType, invalidInput, cause);
    }

}
