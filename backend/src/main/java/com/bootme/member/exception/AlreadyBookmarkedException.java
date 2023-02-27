package com.bootme.member.exception;

import com.bootme.common.exception.BadRequestException;
import com.bootme.common.exception.ErrorType;

public class AlreadyBookmarkedException extends BadRequestException {

    public AlreadyBookmarkedException(final ErrorType errorType, final String invalidInput) {
        super(errorType, invalidInput);
    }

}
