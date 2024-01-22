package com.nice.petudio.common.exception.model;

import com.nice.petudio.common.exception.error.ErrorCode;

public class UnAuthorizedException extends CustomException {

    public UnAuthorizedException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
