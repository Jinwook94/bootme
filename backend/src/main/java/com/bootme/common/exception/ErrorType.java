package com.bootme.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorType {

    // Authentication errors
    NOT_AUTHENTICATED       (1001, "인증에 실패했습니다."),
    ALREADY_AUTHENTICATED   (1002, "이미 인증정보가 존재합니다."),
    INVALID_TOKEN           (1003, "유효하지 않은 토큰입니다."),
    INVALID_ISSUER          (1004, "유효하지 않은 토큰 발급자입니다."),
    INVALID_AUDIENCE        (1005, "토큰의 Audience 값이 유효하지 않습니다."),
    INVALID_ISSUED_AT       (1006, "토큰의 발행시간이 올바르지 않습니다."),
    TOKEN_EXPIRED           (1007, "토큰이 만료되었습니다."),
    INVALID_SIGNATURE       (1008, "토큰이 서명이 올바르지 않습니다."),

    // Authorization
    FORBIDDEN_REQUEST       (2001, "권한이 없습니다."),

    // Resource not found errors
    NOT_FOUND_COURSE        (3001, "존재하지 않는 코스입니다."),
    NOT_FOUND_COMPANY       (3002, "존재하지 않는 회사입니다."),
    NOT_FOUND_MEMBER        (3003, "존재하지 않는 회원입니다."),
    NOT_FOUND_BOOKMARK      (3004, "해당 북마크 코스가 존재하지 않습니다."),
    NOT_FOUND_EVENT         (3005, "존재하지 않는 알림 이벤트입니다."),

    // Resource already exists errors
    ALREADY_BOOKMARKED      (4001, "이미 북마크 저장된 코스입니다."),

    // Invalid input errors
    INVALID_EVENT           (5001, "유효하지 않은 webhook 이벤트입니다."),

    // Internal server errors
    RUNTIME_EXCEPTION       (9001, "서버에 알 수 없는 문제가 발생했습니다.");

    final int errorCode;
    final String message;

    public String getMessage(String invalidInput) {
        return String.format(getMessage() +" : " + invalidInput);
    }

}
