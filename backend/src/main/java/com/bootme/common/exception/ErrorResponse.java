package com.bootme.common.exception;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static com.bootme.common.exception.ErrorType.INVALID_METHOD_ARGUMENT;


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

    public static ErrorResponse of(ErrorType errorType){
        return ErrorResponse.builder().
                errorCode(errorType.getErrorCode()).
                message(errorType.getMessage()).
                build();
    }

    public static ErrorResponse of(String message){
        return ErrorResponse.builder().
                errorCode(INVALID_METHOD_ARGUMENT.errorCode).
                message(message).
                build();
    }

}
