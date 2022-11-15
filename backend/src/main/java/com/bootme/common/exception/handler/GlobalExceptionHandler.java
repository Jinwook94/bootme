package com.bootme.common.exception.handler;

import com.bootme.common.exception.BadRequestException;
import com.bootme.common.exception.UnauthorizedException;
import com.bootme.common.exception.ForbiddenException;
import com.bootme.common.exception.dto.ErrorResponse;
import com.bootme.common.exception.ErrorType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> badRequestExceptionHandler(final BadRequestException e) {
        log.warn("Bad Request Exception", e);
        ErrorType errorType = e.getErrorType();
        return handleExceptionInternal(errorType);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> unauthorizedExceptionHandler(final UnauthorizedException e) {
        log.warn("Unauthorized Exception", e);
        ErrorType errorType = e.getErrorType();
        return handleExceptionInternal(errorType);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorResponse> forbiddenExceptionHandler(final ForbiddenException e) {
        log.warn("Forbidden Exception", e);
        ErrorType errorType = e.getErrorType();
        return handleExceptionInternal(errorType);
    }

    private ResponseEntity<ErrorResponse> handleExceptionInternal(ErrorType errorType) {
        return ResponseEntity.status(errorType.getHttpStatus()).body(makeErrorResponse(errorType));
    }

    private ErrorResponse makeErrorResponse(ErrorType errorType) {
        return ErrorResponse.builder().
                errorCode(errorType.getErrorCode()).
                message(errorType.getMessage()).
                build();
    }

}
