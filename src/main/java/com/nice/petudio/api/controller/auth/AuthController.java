package com.nice.petudio.api.controller.auth;

import com.nice.petudio.api.controller.auth.dto.request.LoginRequest;
import com.nice.petudio.api.controller.auth.dto.request.SignUpRequest;
import com.nice.petudio.api.controller.auth.vo.TokenVO;
import com.nice.petudio.api.controller.auth.service.AuthService;
import com.nice.petudio.api.controller.auth.service.AuthServiceProvider;
import com.nice.petudio.api.controller.auth.service.CommonAuthService;
import com.nice.petudio.api.controller.auth.service.CreateTokenService;
import com.nice.petudio.api.dto.ApiResponse;
import com.nice.petudio.global.auth.auth.Auth;
import com.nice.petudio.global.auth.resolver.MemberId;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class AuthController {
    private final AuthServiceProvider authServiceProvider;
    private final CreateTokenService createTokenService;
    private final CommonAuthService commonAuthService;


    @Operation(summary = "OAuth2 소셜 회원가입")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/auth/signup")
    public ApiResponse<?> signUp(@Valid @RequestBody SignUpRequest request, HttpServletResponse response) {
        AuthService authService = authServiceProvider.getAuthService(request.getSocialType());
        Long memberId = authService.signUp(request);

        addTokensToCookie(createTokenService.createTokenInfo(memberId), response);

        return ApiResponse.success();
    }

    @Operation(summary = "OAuth2 소셜 로그인")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/auth/login")
    public ApiResponse<?> login(@Valid @RequestBody LoginRequest request, HttpServletResponse response) {
        AuthService authService = authServiceProvider.getAuthService(request.getSocialType());
        Long memberId = authService.login(request);

        addTokensToCookie(createTokenService.createTokenInfo(memberId), response);

        return ApiResponse.success();
    }

    @Operation(summary = "[인증] 로그아웃")
    @Auth
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/auth/logout")
    public ApiResponse<String> logout(@MemberId Long memberId) {
        commonAuthService.logout(memberId);

        return ApiResponse.success();
    }

    @Operation(summary = "JWT 토큰 갱신")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/auth/reissue")
    public ApiResponse<?> reissue(@CookieValue String accessToken, @CookieValue String refreshToken, HttpServletResponse response) {
        TokenVO tokenVO = TokenVO.of(accessToken, refreshToken);
        addTokensToCookie(createTokenService.reissueToken(tokenVO), response);

        return ApiResponse.success();
    }

    private void addTokensToCookie(TokenVO tokenVO, HttpServletResponse response) {
        addTokenToCookie("accessToken", tokenVO.getAccessToken(), response);
        addTokenToCookie("refreshToken", tokenVO.getRefreshToken(), response);
    }

    private void addTokenToCookie(String cookieName, String token, HttpServletResponse response) {
        Cookie cookie = new Cookie(cookieName, token);
        //cookie.setSecure(true);
        cookie.setHttpOnly(true);

        response.addCookie(cookie);
    }
}
