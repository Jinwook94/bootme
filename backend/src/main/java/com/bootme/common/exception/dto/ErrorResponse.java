package com.bootme.common.exception.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@Builder
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorResponse {

    private final int errorCode;
    private final String message;

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "errorCode=" + errorCode +
                ", message='" + message + '\'' +
                '}';
    }
}
