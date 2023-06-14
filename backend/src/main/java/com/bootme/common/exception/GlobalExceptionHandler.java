package com.bootme.common.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

import static com.bootme.common.exception.ErrorType.RUNTIME_EXCEPTION;
import static com.bootme.common.util.RequestUtils.getRequestInfo;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private static final String REQUEST_INFO = "request_information";
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> methodArgumentExceptionHandler(final MethodArgumentNotValidException e,
                                                                        HttpServletRequest request) throws JsonProcessingException {
        MDC.put(REQUEST_INFO, getRequestInfo(request));
        log.warn("Method Argument Not Valid Exception", e);
        String errorMessage = e.getBindingResult()
                .getFieldErrors()
                .get(0)
                .getDefaultMessage();
        MDC.remove(REQUEST_INFO);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse.of(errorMessage));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> badRequestExceptionHandler(final BadRequestException e, HttpServletRequest request) throws JsonProcessingException {
        MDC.put(REQUEST_INFO, getRequestInfo(request));
        log.warn("Bad Request Exception", e);
        ErrorType errorType = e.getErrorType();
        MDC.remove(REQUEST_INFO);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse.of(errorType));
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> unauthorizedExceptionHandler(final UnauthorizedException e, HttpServletRequest request) throws JsonProcessingException {
        MDC.put(REQUEST_INFO, getRequestInfo(request));
        log.warn("Unauthorized Exception", e);
        ErrorType errorType = e.getErrorType();
        MDC.remove(REQUEST_INFO);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ErrorResponse.of(errorType));
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorResponse> forbiddenExceptionHandler(final ForbiddenException e, HttpServletRequest request) throws JsonProcessingException {
        MDC.put(REQUEST_INFO, getRequestInfo(request));
        log.warn("Forbidden Exception", e);
        ErrorType errorType = e.getErrorType();
        MDC.remove(REQUEST_INFO);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ErrorResponse.of(errorType));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException e, HttpServletRequest request) throws JsonProcessingException {
        MDC.put(REQUEST_INFO, getRequestInfo(request));
        log.warn("Runtime Exception", e);
        MDC.remove(REQUEST_INFO);
        return ResponseEntity.internalServerError().body(ErrorResponse.of(RUNTIME_EXCEPTION));
    }

}
