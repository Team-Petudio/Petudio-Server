package com.nice.petudio.global.exception;

import com.nice.petudio.global.exception.error.ErrorCode;

public class NotFoundException extends CustomException {

    public NotFoundException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
