package com.nice.petudio.global.exception.model;

import com.nice.petudio.global.exception.error.ErrorCode;

public class BadGatewayException extends CustomException {

    public BadGatewayException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
