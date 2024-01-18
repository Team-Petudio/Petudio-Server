package com.nice.petudio.api.dto;

import com.nice.petudio.global.exception.error.ErrorCode;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResponse<T> {

    private final boolean isSuccess;
    private final String code;
    private final String message;
    private T data;

    private static final String SUCCESS_MESSAGE = "요청이 성공적으로 수행되었습니다.";
    public static final String SUCCESS_CODE = "S001";

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, SUCCESS_CODE, SUCCESS_MESSAGE, data);
    }

    public static <T> ApiResponse<T> success() {
        return new ApiResponse<>(true, SUCCESS_CODE, SUCCESS_MESSAGE);
    }

    public static <T> ApiResponse<T> error(ErrorCode errorCode) {
        return new ApiResponse<>(false, errorCode.getCode(), errorCode.getMessage());
    }
}
