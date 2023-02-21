package com.bootme.common.exception;

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
    FORBIDDEN_REQUEST       (FORBIDDEN,    1004, "권한이 없습니다."),
    INVALID_ISSUER          (UNAUTHORIZED, 1005, "유효하지 않은 토큰 발급자입니다."),
    INVALID_AUDIENCE        (UNAUTHORIZED, 1006, "토큰의 Audience 값이 유효하지 않습니다."),
    INVALID_ISSUED_AT       (UNAUTHORIZED, 1007, "토큰의 발행시간이 올바르지 않습니다."),
    TOKEN_EXPIRED           (UNAUTHORIZED, 1008, "토큰이 만료되었습니다."),
    INVALID_SIGNATURE       (UNAUTHORIZED, 1009, "토큰이 서명이 올바르지 않습니다."),
    INVALID_EVENT           (UNAUTHORIZED, 1010, "유효하지 않은 webhook 이벤트입니다."),

    NOT_FOUND_COURSE        (BAD_REQUEST,  3001, "존재하지 않는 코스입니다."),
    NOT_FOUND_MEMBER        (BAD_REQUEST,  3002, "존재하지 않는 회원입니다."),
    NOT_FOUND_BOOKMARK      (BAD_REQUEST,  3003, "해당 북마크 코스가 존재하지 않습니다."),
    ALREADY_BOOKMARKED      (BAD_REQUEST,  3004, "이미 북마크 저장된 코스입니다.."),

    NOT_FOUND_COMPANY       (BAD_REQUEST,  4001, "존재하지 않는 회사입니다.");

    final HttpStatus httpStatus;
    final int errorCode;
    final String message;

    public String getMessage(String invalidInput) {
        return String.format(getMessage() +" : " + invalidInput);
    }

}
