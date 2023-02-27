package com.bootme.notification.exception;

import com.bootme.common.exception.BadRequestException;
import com.bootme.common.exception.ErrorType;

public class NotificationEventNotFoundException extends BadRequestException {

    public NotificationEventNotFoundException(final ErrorType errorType, final String invalidInput) {
        super(errorType, invalidInput);
    }

}