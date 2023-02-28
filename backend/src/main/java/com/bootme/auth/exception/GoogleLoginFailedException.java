package com.bootme.auth.exception;

import com.bootme.common.exception.ErrorType;
import com.bootme.common.exception.UnauthorizedException;

public class GoogleLoginFailedException extends UnauthorizedException {

    public GoogleLoginFailedException(final ErrorType errorType, final String invalidInput, Throwable cause) {
        super(errorType, invalidInput, cause);
    }

}
