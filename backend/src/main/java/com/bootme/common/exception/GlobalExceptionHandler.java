package com.bootme.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.bootme.common.exception.ErrorType.RUNTIME_EXCEPTION;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> methodArgumentExceptionHandler(final MethodArgumentNotValidException e) {
        log.warn("Method Argument Not Valid Exception", e);
        String errorMessage = e.getBindingResult()
                .getFieldErrors()
                .get(0)
                .getDefaultMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse.of(errorMessage));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> badRequestExceptionHandler(final BadRequestException e) {
        log.warn("Bad Request Exception", e);
        ErrorType errorType = e.getErrorType();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse.of(errorType));
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> unauthorizedExceptionHandler(final UnauthorizedException e) {
        log.warn("Unauthorized Exception", e);
        ErrorType errorType = e.getErrorType();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ErrorResponse.of(errorType));
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorResponse> forbiddenExceptionHandler(final ForbiddenException e) {
        log.warn("Forbidden Exception", e);
        ErrorType errorType = e.getErrorType();
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ErrorResponse.of(errorType));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException e) {
        log.warn("Runtime Exception", e);
        return ResponseEntity.internalServerError().body(ErrorResponse.of(RUNTIME_EXCEPTION));
    }

}
