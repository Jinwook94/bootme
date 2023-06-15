package com.bootme.common.exception;

import lombok.Getter;

@Getter
public abstract class ForbiddenException extends BusinessException {

    protected ForbiddenException(ErrorType errorType){
        super(errorType);
    }

    protected ForbiddenException(ErrorType errorType, String invalidInput){
        super(errorType, invalidInput);
    }

}
