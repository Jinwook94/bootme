package com.bootme.webhook.exception;

import com.bootme.common.exception.ErrorType;
import com.bootme.common.exception.UnauthorizedException;

public class InvalidEventException extends UnauthorizedException {

    public InvalidEventException(final ErrorType errorType, final String event) {
        super(errorType, event);
    }

}
