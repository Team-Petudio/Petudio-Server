package com.nice.petudio.global.exception.model;

import com.nice.petudio.global.exception.error.ErrorCode;

public class UnAuthorizedException extends CustomException {

    public UnAuthorizedException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
