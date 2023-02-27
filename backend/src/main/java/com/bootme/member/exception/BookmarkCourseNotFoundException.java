package com.bootme.member.exception;

import com.bootme.common.exception.BadRequestException;
import com.bootme.common.exception.ErrorType;

public class BookmarkCourseNotFoundException extends BadRequestException {

    public BookmarkCourseNotFoundException(final ErrorType errorType, final String invalidInput) {
        super(errorType, invalidInput);
    }

}
