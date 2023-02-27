package com.bootme.common.exception;

import lombok.Getter;

@Getter
public abstract class ForbiddenException extends BusinessException {

    public ForbiddenException(ErrorType errorType){
        super(errorType);
    }

    public ForbiddenException(ErrorType errorType, String invalidInput){
        super(errorType, invalidInput);
    }

}
