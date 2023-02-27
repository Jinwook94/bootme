package com.bootme.course.exception;

import com.bootme.common.exception.BadRequestException;
import com.bootme.common.exception.ErrorType;

public class CompanyNotFoundException extends BadRequestException {

    public CompanyNotFoundException(final ErrorType errorType) {
        super(errorType);
    }

    public CompanyNotFoundException(final ErrorType errorType, final String invalidInput) {
        super(errorType, invalidInput);
    }

}
