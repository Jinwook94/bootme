package com.bootme.auth.exception;

import com.bootme.common.exception.ErrorType;
import com.bootme.common.exception.UnauthorizedException;

public class TokenExpiredException extends UnauthorizedException {

    public TokenExpiredException(final ErrorType errorType, final String expireTime) {
        super(errorType, expireTime);
    }

}
