package com.bootme.common.exception;

import lombok.Getter;

@Getter
public abstract class BadRequestException extends BusinessException {

    protected BadRequestException(ErrorType errorType){
        super(errorType);
    }

    protected BadRequestException(ErrorType errorType, String invalidInput){
        super(errorType, invalidInput);
    }

    protected BadRequestException(ErrorType errorType, String invalidInput, Throwable cause){
        super(errorType, invalidInput, cause);
    }

}
