package com.bootme.auth.exception;

import com.bootme.common.exception.ErrorType;
import com.bootme.common.exception.UnauthorizedException;

public class InvalidAudienceException extends UnauthorizedException {

    public InvalidAudienceException(final ErrorType errorType, final String issuer) {
        super(errorType, issuer);
    }
}
