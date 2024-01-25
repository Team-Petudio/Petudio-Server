package com.nice.petudio.common.auth.handler;

import com.nice.petudio.api.controller.member.service.MemberServiceUtils;
import com.nice.petudio.common.auth.jwt.JwtUtils;
import com.nice.petudio.common.exception.error.ErrorCode;
import com.nice.petudio.common.exception.model.ForbiddenException;
import com.nice.petudio.common.exception.model.UnAuthorizedException;
import com.nice.petudio.common.exception.model.ValidationException;
import com.nice.petudio.domain.member.Member;
import com.nice.petudio.domain.member.MemberRole;
import com.nice.petudio.domain.member.repository.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
@Component
public class AuthCheckHandler {

    private final JwtUtils jwtUtils;
    private final MemberRepository memberRepository;
    private Long memberId;


    public Long validateAuthority(HttpServletRequest request, List<MemberRole> requiredRoles) {
        String jwtAccessToken = getJwtAccessTokenFromHttpHeader(request);
        if (hasAuthority(jwtAccessToken, requiredRoles)) {
            return memberId;
        }
        throw new ForbiddenException(ErrorCode.FORBIDDEN_EXCEPTION,
                String.format("memberId(%d)의 접근 권한이 없어, 요청이 수행되지 않았습니다.", memberId));
    }

    private String getJwtAccessTokenFromHttpHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring("Bearer ".length());
        }
        throw new ValidationException(ErrorCode.INVALID_JWT_TOKEN_EXCEPTION,
                ErrorCode.INVALID_JWT_TOKEN_EXCEPTION.getMessage());
    }

    public boolean hasAuthority(String jwtAccessToken, List<MemberRole> requiredRoles) {
        if (jwtUtils.validateToken(jwtAccessToken)) {
            Optional<Long> memberId = jwtUtils.parseMemberId(jwtAccessToken);
            if (memberId.isPresent()) {
                Member member = MemberServiceUtils.findMemberById(memberRepository, memberId.get());
                this.memberId = memberId.get();
                return isRoleMatch(member, requiredRoles);
            }
            throw new ValidationException(ErrorCode.INVALID_JWT_TOKEN_EXCEPTION,
                    "JWT AccessToken 내에 MemberId가 존재하지 않습니다.");
        }
        throw new UnAuthorizedException(ErrorCode.UNAUTHORIZED_JWT_EXCEPTION,
                String.format("입력받은 JWT 토큰이 유효하지 않습니다. (ACCESS_TOKEN: %s)", jwtAccessToken));
    }

    private static boolean isRoleMatch(Member member, List<MemberRole> requiredRoles) {
        return requiredRoles.contains(member.getRole());
    }

}
