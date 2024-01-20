package com.nice.petudio.global.exception.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {
    // Validation Exception
    VALIDATION_EXCEPTION("V001", "유효하지 않은 요청 값입니다."),
    BAD_REQUEST_EXCEPTION("V002", "잘못된 요청입니다."),
    METHOD_NOT_ALLOWED_EXCEPTION("V003", "지원하지 않는 HTTP 메소드입니다."),
    UNSUPPORTED_MEDIA_TYPE("V004", "허용하지 않는 미디어 타입입니다."),
    INVALID_JWT_TOKEN_EXCEPTION("V005", "존재하지 않거나 잘못된 JWT 토큰 형식입니다."),
    INVALID_OAUTH2_TOKEN_EXCEPTION("V006", "존재하지 않거나 잘못된 OAuth2 토큰 입니다."),

    // UnAuthorized Exception
    UNAUTHORIZED_JWT_EXCEPTION("U001", "JWT 토큰이 유효하지 않습니다. 다시 로그인 해주세요."),

    // Forbidden Exception
    FORBIDDEN_EXCEPTION("F001", "접근 권한이 존재하지 않습니다."),

    // NotFound Exception
    NOT_FOUND_EXCEPTION("N001", "존재하지 않는 요청입니다."),
    NOT_FOUND_MEMBER_INFO_EXCEPTION("N002", "탈퇴했거나 존재하지 않는 회원 정보입니다."),
    NOT_FOUND_CONCEPT_EXCEPTION("N003", "존재하지 않는 컨셉입니다."),

    // Conflict Exception
    CONFLICT_EXCEPTION("C001", "이미 존재하는 데이터입니다."),
    CONFLICT_MEMBER_EXCEPTION("C002", "이미 해당 계정으로 회원가입이 되어있습니다.\n로그인 해주세요."),

    // Internal Server Exception
    INTERNAL_SERVER_EXCEPTION("I001", "서버 내부에서 에러가 발생하였습니다."),

    // Bad Gateway Exception
    BAD_GATEWAY_EXCEPTION("B001", "외부 연동 중 에러가 발생하였습니다.");


    private final String code;
    private final String message;
}
