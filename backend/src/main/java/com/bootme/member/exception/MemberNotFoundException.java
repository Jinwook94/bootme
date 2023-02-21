package com.bootme.member.exception;

import com.bootme.common.exception.BadRequestException;
import com.bootme.common.exception.ErrorType;

public class MemberNotFoundException extends BadRequestException {

    public MemberNotFoundException(final ErrorType errorType) {
        super(errorType);
    }

}
