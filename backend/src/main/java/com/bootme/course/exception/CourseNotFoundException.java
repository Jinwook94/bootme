package com.bootme.course.exception;

import com.bootme.common.exception.BadRequestException;
import com.bootme.common.exception.ErrorType;

public class CourseNotFoundException extends BadRequestException {

    public CourseNotFoundException(final ErrorType errorType) {
        super(errorType);
    }
}
