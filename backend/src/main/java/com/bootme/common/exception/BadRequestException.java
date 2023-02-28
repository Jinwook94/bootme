package com.bootme.common.exception;

import lombok.Getter;

@Getter
public abstract class BadRequestException extends BusinessException {

    public BadRequestException(ErrorType errorType){
        super(errorType);
    }

    public BadRequestException(ErrorType errorType, String invalidInput){
        super(errorType, invalidInput);
    }

    public BadRequestException(ErrorType errorType, String invalidInput, Throwable cause){
        super(errorType, invalidInput, cause);
    }

}
