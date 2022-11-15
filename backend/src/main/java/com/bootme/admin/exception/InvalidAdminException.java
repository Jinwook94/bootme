package com.bootme.admin.exception;

import com.bootme.common.exception.UnauthorizedException;
import com.bootme.common.exception.ErrorType;

public class InvalidAdminException extends UnauthorizedException {

    public InvalidAdminException(final ErrorType errorType) {
        super(errorType);
    }
}
