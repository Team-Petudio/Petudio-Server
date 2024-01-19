package com.nice.petudio.global.auth.handler;

import com.nice.petudio.api.controller.member.service.MemberServiceUtils;
import com.nice.petudio.domain.member.Member;
import com.nice.petudio.domain.member.MemberRole;
import com.nice.petudio.domain.member.repository.MemberRepository;
import com.nice.petudio.global.auth.jwt.JwtUtils;
import com.nice.petudio.global.exception.model.ForbiddenException;
import com.nice.petudio.global.exception.model.UnAuthorizedException;
import com.nice.petudio.global.exception.model.ValidationException;
import com.nice.petudio.global.exception.error.ErrorCode;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AuthCheckHandler {

    private final JwtUtils jwtUtils;
    private final MemberRepository memberRepository;
    private Long memberId;

    private static final String JWT_ACCESS_TOKEN_COOKIE_NAME = "accessToken";

    public Long validateAuthority(HttpServletRequest request, List<MemberRole> requiredRoles) {
        String jwtAccessToken = getJwtAccessTokenFromHttpCookie(request);
        if (hasAuthority(jwtAccessToken, requiredRoles)) {
            return memberId;
        }
        throw new ForbiddenException(ErrorCode.FORBIDDEN_EXCEPTION,
                String.format("memberId(%d)의 접근 권한이 없어, 요청이 수행되지 않았습니다.", memberId));
    }

    private String getJwtAccessTokenFromHttpCookie(HttpServletRequest request) {
        Optional<Cookie> jwtAccessTokenCookie = getJwtAccessTokenCookieFromHttpRequest(request);
        if (jwtAccessTokenCookie.isPresent()) {
            return jwtAccessTokenCookie.get().getValue();
        }
        throw new ValidationException(ErrorCode.INVALID_JWT_TOKEN_EXCEPTION,
                ErrorCode.INVALID_JWT_TOKEN_EXCEPTION.getMessage());
    }

    private Optional<Cookie> getJwtAccessTokenCookieFromHttpRequest(HttpServletRequest request) {
        return Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(JWT_ACCESS_TOKEN_COOKIE_NAME))
                .findFirst();
    }

    public boolean hasAuthority(String jwtAccessToken, List<MemberRole> requiredRoles) {
        if (jwtUtils.validateToken(jwtAccessToken)) {
            Optional<Long> memberId = jwtUtils.parseMemberId(jwtAccessToken);
            if (memberId.isPresent()) {
                Member member = MemberServiceUtils.findMemberById(memberRepository, memberId.get());
                this.memberId = memberId.get();
                return isRoleMatch(member, requiredRoles);
            }
        }
        throw new UnAuthorizedException(ErrorCode.UNAUTHORIZED_JWT_EXCEPTION,
                ErrorCode.UNAUTHORIZED_JWT_EXCEPTION.getMessage());
    }

    private static boolean isRoleMatch(Member member, List<MemberRole> requiredRoles) {
        return requiredRoles.contains(member.getRole());
    }

}
