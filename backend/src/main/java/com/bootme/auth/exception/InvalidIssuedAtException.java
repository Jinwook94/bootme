package com.bootme.auth.exception;

import com.bootme.common.exception.ErrorType;
import com.bootme.common.exception.UnauthorizedException;

public class InvalidIssuedAtException extends UnauthorizedException {

    public InvalidIssuedAtException(final ErrorType errorType, final String issuedAt) {
        super(errorType, issuedAt);
    }

}
