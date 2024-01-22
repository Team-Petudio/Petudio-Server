package com.nice.petudio.common.exception.model;

import com.nice.petudio.common.exception.error.ErrorCode;

public class ValidationException extends CustomException {

    public ValidationException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
