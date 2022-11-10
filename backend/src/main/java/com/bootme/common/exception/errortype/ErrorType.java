package com.bootme.common.exception.errortype;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@RequiredArgsConstructor
public enum ErrorType {

/*  Error Name               HTTP Status    Error Code     Error Message                  */
    NOT_AUTHENTICATED       (UNAUTHORIZED, 1001, "인증에 실패했습니다."),
    ALREADY_AUTHENTICATED   (UNAUTHORIZED, 1002, "이미 인증정보가 존재합니다."),
    INVALID_TOKEN           (BAD_REQUEST,  1003, "유효하지 않은 토큰입니다."),
    FORBIDDEN_REQUEST       (FORBIDDEN,    1004, "권한이 없습니다.");

    final HttpStatus httpStatus;
    final int errorCode;
    final String message;

}
