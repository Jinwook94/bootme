package com.bootme.common.exception;

public class TokenParseException extends BadRequestException {

    public TokenParseException(final ErrorType errorType) {
        super(errorType);
    }

    public TokenParseException(final ErrorType errorType, final String invalidInput) {
        super(errorType, invalidInput);
    }

    public TokenParseException(final ErrorType errorType, final String invalidInput, Throwable cause) {
        super(errorType, invalidInput, cause);
    }
}
