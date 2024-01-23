package com.nice.petudio.common.aop.advice;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.nice.petudio.api.dto.ApiResponse;
import com.nice.petudio.common.exception.error.ErrorCode;
import com.nice.petudio.common.exception.model.BadGatewayException;
import com.nice.petudio.common.exception.model.ConflictException;
import com.nice.petudio.common.exception.model.InternalServerException;
import com.nice.petudio.common.exception.model.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@RestControllerAdvice
public class ExceptionControllerAdvice {

    // 사용자가 요청 값 전달은 성공했지만, 해당 값이 유효하지 않은 경우 발생
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    protected ApiResponse<Object> handleValidateException(ValidationException exception) {
        log.error(exception.getMessage(), exception);
        return ApiResponse.error(exception.getErrorCode());
    }

    // 중복되는 데이터 저장 요청의 경우(e.g. 같은 같은 계정으로 OAuth2 회원가입 시도) 발생
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConflictException.class)
    protected ApiResponse<Object> handleConflictException(ConflictException exception) {
        log.error(exception.getMessage(), exception);
        return ApiResponse.error(exception.getErrorCode());
    }

    // @Valid or @Validated 바인딩 에러시 발생
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ApiResponse<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        log.error(exception.getMessage(), exception);
        return ApiResponse.error(ErrorCode.BAD_REQUEST_EXCEPTION);
    }

    // API 메서드의 매개변수와 사용자 요청 값의 Type을 매핑할 수 없는 경우에 발생
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ApiResponse<Object> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException exception) {
        log.error(exception.getMessage(), exception);
        return ApiResponse.error(ErrorCode.BAD_REQUEST_EXCEPTION);
    }

    // HTTP 메시지를 읽을 수 없는 경우, 데이터의 형식이 유효한 자바 타입으로 변환되지 못할 경우, 서블릿 요청과 관련된 바인딩 오류가 발생한 경우
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            HttpMessageNotReadableException.class,
            InvalidFormatException.class,
            ServletRequestBindingException.class
    })
    protected ApiResponse<Object> handleInvalidFormatException(final Exception exception) {
        log.error(exception.getMessage(), exception);
        return ApiResponse.error(ErrorCode.BAD_REQUEST_EXCEPTION);
    }


    /**
     * 405 Method Not Supported
     */
    // 해당 요청에 대해 지원하는 메서드가 존재하지 않는 경우 발생
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ApiResponse<Object> handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException exception) {
        log.error(exception.getMessage(), exception);
        return ApiResponse.error(ErrorCode.METHOD_NOT_ALLOWED_EXCEPTION);
    }


    /**
     * 415 UnSupported Media Type
     */
    // 사용자가 보낸 요청의 미디어 타입을 서버에서 지원하는 않는 경우 발생
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(HttpMediaTypeException.class)
    protected ApiResponse<Object> handleHttpMediaTypeException(final HttpMediaTypeException exception) {
        log.error(exception.getMessage(), exception);
        return ApiResponse.error(ErrorCode.UNSUPPORTED_MEDIA_TYPE);
    }


    /**
     * 502 Bad Gateway
     */
    // 서버 내에서 외부 요청 수행 중, 문제가 발생한 경우에 발생
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    @ExceptionHandler(BadGatewayException.class)
    protected ApiResponse<Object> handleBadGatewayException(final BadGatewayException exception) {
        log.error(exception.getMessage(), exception);
        return ApiResponse.error(exception.getErrorCode());
    }


    /**
     * 500 Internal Server Error
     */
    // 서버 내부에서 오류가 발생한 경우
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(InternalServerException.class)
    protected ApiResponse<Object> handleInternalServerException(final InternalServerException exception) {
        log.error(exception.getMessage(), exception);
        return ApiResponse.error(exception.getErrorCode());
    }

    // 서버 내부에서 예상치 못한 오류가 발생한 경우
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    protected ApiResponse<Object> handleUnexpectedException(final Exception exception) {
        log.error(exception.getMessage(), exception);
        return ApiResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
    }
}
