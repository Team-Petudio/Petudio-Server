package com.nice.petudio.common.exception.model;

import com.nice.petudio.common.exception.error.ErrorCode;

public class BadGatewayException extends CustomException {

    public BadGatewayException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
