package com.nice.petudio.global.exception;

import com.nice.petudio.global.exception.error.ErrorCode;

public class UnAuthorizedException extends CustomException {

    public UnAuthorizedException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
