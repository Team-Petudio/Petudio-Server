package com.nice.petudio.global.exception;

import com.nice.petudio.global.exception.error.ErrorCode;

public class ValidationException extends CustomException {

    public ValidationException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
