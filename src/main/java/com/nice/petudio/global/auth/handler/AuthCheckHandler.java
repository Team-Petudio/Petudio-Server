package com.nice.petudio.global.auth.handler;

import com.nice.petudio.api.member.service.MemberServiceUtils;
import com.nice.petudio.domain.member.Member;
import com.nice.petudio.domain.member.MemberRole;
import com.nice.petudio.domain.member.repository.MemberRepository;
import com.nice.petudio.global.jwt.JwtTokenService;
import com.nice.petudio.global.exception.ForbiddenException;
import com.nice.petudio.global.exception.UnauthorizedException;
import com.nice.petudio.global.exception.ValidationException;
import com.nice.petudio.global.exception.error.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
@Component
public class AuthCheckHandler {

    private final JwtTokenService jwtTokenService;
    private final MemberRepository memberRepository;

    public final String AUTH_HEADER = "Authorization";
    public final String TOKEN_PREFIX = "Bearer ";
    private Long memberId;

    public Long validateAuthority(HttpServletRequest request, List<MemberRole> requiredRoles) {
        String jwtAccessToken = getJwtAccessTokenFromHttpHeader(request);
        if (hasAuthority(jwtAccessToken, requiredRoles)) {
            return memberId;
        }
        throw new ForbiddenException(ErrorCode.FORBIDDEN_EXCEPTION, "");
    }

    private String getJwtAccessTokenFromHttpHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTH_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(TOKEN_PREFIX.length());
        }
        throw new ValidationException(ErrorCode.INVALID_TOKEN_EXCEPTION, "");
    }

    public boolean hasAuthority(String jwtAccessToken, List<MemberRole> requiredRoles) {
        if (jwtTokenService.validateToken(jwtAccessToken)) {
            Optional<Long> memberId = jwtTokenService.parseMemberId(jwtAccessToken);
            if(memberId.isPresent()) {
                Member member = MemberServiceUtils.findMemberById(memberRepository, memberId.get());
                this.memberId = memberId.get();
                return isRoleMatch(member, requiredRoles);
            }
        }
        throw new UnauthorizedException(ErrorCode.UNAUTHORIZED_TOKEN_EXCEPTION, "");
    }

    private static boolean isRoleMatch(Member member, List<MemberRole> requiredRoles) {
        return requiredRoles.contains(member.getRole());
    }

}
