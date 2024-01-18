package com.nice.petudio.global.exception;

import com.nice.petudio.global.exception.error.ErrorCode;

public class UnauthorizedException extends CustomException {

    public UnauthorizedException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
