package com.bootme.common.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.bootme.common.exception.ErrorType.NOT_FOUND_MEMBER;
import static com.bootme.common.exception.ErrorType.RUNTIME_EXCEPTION;
import static com.bootme.common.util.RequestUtils.getRequestInfo;
import static org.springframework.http.HttpHeaders.SET_COOKIE;
import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @Value("${domain}")
    private String domain;

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
        return ResponseEntity.status(BAD_REQUEST).body(ErrorResponse.of(errorMessage));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> badRequestExceptionHandler(final BadRequestException e,
                                                                    HttpServletRequest request,
                                                                    HttpServletResponse response) throws JsonProcessingException {
        MDC.put(REQUEST_INFO, getRequestInfo(request));
        log.warn("Bad Request Exception", e);
        ErrorType errorType = e.getErrorType();
        MDC.remove(REQUEST_INFO);

        invalidateTokenCookiesIfMemberNotFound(e, response);
        return ResponseEntity.status(BAD_REQUEST).body(ErrorResponse.of(errorType));
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> unauthorizedExceptionHandler(final UnauthorizedException e, HttpServletRequest request) throws JsonProcessingException {
        MDC.put(REQUEST_INFO, getRequestInfo(request));
        log.warn("Unauthorized Exception", e);
        ErrorType errorType = e.getErrorType();
        MDC.remove(REQUEST_INFO);
        return ResponseEntity.status(UNAUTHORIZED).body(ErrorResponse.of(errorType));
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorResponse> forbiddenExceptionHandler(final ForbiddenException e, HttpServletRequest request) throws JsonProcessingException {
        MDC.put(REQUEST_INFO, getRequestInfo(request));
        log.warn("Forbidden Exception", e);
        ErrorType errorType = e.getErrorType();
        MDC.remove(REQUEST_INFO);
        return ResponseEntity.status(FORBIDDEN).body(ErrorResponse.of(errorType));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException e, HttpServletRequest request) throws JsonProcessingException {
        MDC.put(REQUEST_INFO, getRequestInfo(request));
        log.warn("Runtime Exception", e);
        MDC.remove(REQUEST_INFO);
        return ResponseEntity.internalServerError().body(ErrorResponse.of(RUNTIME_EXCEPTION));
    }

    // 엑세스 토큰이 있는데, 엑세스 토큰에 포함된 Member ID에 해당하는 멤버가 없는 경우 토큰을 무효화
    private void invalidateTokenCookiesIfMemberNotFound(final BadRequestException e, HttpServletResponse response) {
        if (e instanceof ResourceNotFoundException && e.getErrorType() == NOT_FOUND_MEMBER) {
            invalidateTokenCookies(response);
        }
    }

    private void invalidateTokenCookies(HttpServletResponse response) {
        response.addHeader(SET_COOKIE, "accessToken=; Max-Age=0; Domain=" + domain +";");
        response.addHeader(SET_COOKIE, "refreshToken=; Max-Age=0; Domain=" + domain + ";");
    }

}
