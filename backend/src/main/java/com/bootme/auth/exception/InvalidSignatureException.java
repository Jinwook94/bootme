package com.bootme.auth.exception;

import com.bootme.common.exception.ErrorType;
import com.bootme.common.exception.UnauthorizedException;

public class InvalidSignatureException extends UnauthorizedException {

    public InvalidSignatureException(final ErrorType errorType) {
        super(errorType);
    }

}
