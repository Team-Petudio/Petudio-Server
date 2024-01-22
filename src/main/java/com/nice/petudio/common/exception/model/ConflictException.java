package com.nice.petudio.common.exception.model;

import com.nice.petudio.common.exception.error.ErrorCode;

public class ConflictException extends CustomException {

    public ConflictException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
