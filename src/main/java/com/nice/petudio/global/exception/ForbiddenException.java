package com.nice.petudio.global.exception;

import com.nice.petudio.global.exception.error.ErrorCode;

public class ForbiddenException extends CustomException {

    public ForbiddenException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
