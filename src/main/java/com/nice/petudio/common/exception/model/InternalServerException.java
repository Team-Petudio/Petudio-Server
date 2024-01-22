package com.nice.petudio.common.exception.model;

import com.nice.petudio.common.exception.error.ErrorCode;

public class InternalServerException extends CustomException {

    public InternalServerException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
