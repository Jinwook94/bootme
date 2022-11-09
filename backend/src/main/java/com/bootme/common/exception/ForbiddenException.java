package com.bootme.common.exception;

import com.bootme.common.exception.errortype.ErrorType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ForbiddenException extends RuntimeException {

    private final ErrorType errorType;

}
