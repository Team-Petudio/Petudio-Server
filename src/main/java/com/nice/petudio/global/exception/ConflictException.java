package com.nice.petudio.global.exception;

import com.nice.petudio.global.exception.error.ErrorCode;

public class ConflictException extends CustomException {

    public ConflictException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
