package com.nice.petudio.global.exception.model;

import com.nice.petudio.global.exception.error.ErrorCode;

public class InternalServerException extends CustomException {

    public InternalServerException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
