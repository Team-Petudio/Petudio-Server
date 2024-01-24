package com.nice.petudio.api.controller.auth.service;


import com.nice.petudio.api.controller.auth.dto.request.ReissueRequest;
import com.nice.petudio.api.controller.auth.vo.TokenVO;
import com.nice.petudio.api.controller.member.service.MemberServiceUtils;
import com.nice.petudio.common.auth.jwt.JwtUtils;
import com.nice.petudio.common.config.redis.constant.RedisKey;
import com.nice.petudio.common.exception.error.ErrorCode;
import com.nice.petudio.common.exception.model.UnAuthorizedException;
import com.nice.petudio.common.exception.model.ValidationException;
import com.nice.petudio.domain.member.Member;
import com.nice.petudio.domain.member.repository.MemberRepository;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class CreateTokenService {

    private final MemberRepository memberRepository;

    private final RedisTemplate redisTemplate;

    private final JwtUtils jwtUtils;

    public TokenVO createTokenInfo(Long memberId) {
        List<String> tokens = jwtUtils.createTokenInfo(memberId);
        return TokenVO.of(tokens.get(0), tokens.get(1));
    }

    public TokenVO reissueToken(ReissueRequest request) {
        Long memberId = jwtUtils.parseMemberId(request.getAccessToken())
                .orElseThrow(() -> new ValidationException(ErrorCode.INVALID_JWT_TOKEN_EXCEPTION,
                        String.format("JWT AccessToken 내에 MemberId가 존재하지 않습니다.")));
        Member member = MemberServiceUtils.findMemberById(memberRepository, memberId);

        if (!jwtUtils.validateToken(request.getRefreshToken())) {
            throw new UnAuthorizedException(ErrorCode.UNAUTHORIZED_JWT_EXCEPTION,
                    String.format("MemberId(%d)의 토큰 갱신 요청에 포함된 Refresh Token이 유효하지 않아, Token Refresh가 수행되지 않았습니다.",
                            memberId));
        }
        String refreshToken = (String) redisTemplate.opsForValue().get(RedisKey.REFRESH_TOKEN.getKey() + memberId);
        if (Objects.isNull(refreshToken)) {
            throw new UnAuthorizedException(ErrorCode.UNAUTHORIZED_JWT_EXCEPTION,
                    String.format("보관 중인 MemberId(%d)의 Refresh Token이 존재하지 않아, Token Refresh가 수행되지 않았습니다.", memberId));
        }
        if (!refreshToken.equals(request.getRefreshToken())) {
            jwtUtils.expireRefreshToken(member.getId());
            throw new UnAuthorizedException(ErrorCode.UNAUTHORIZED_JWT_EXCEPTION,
                    String.format("보관 중인 MemberId(%d)의 Refresh Token이 유효하지 않아, Token Refresh가 수행되지 않았습니다.", memberId));
        }
        List<String> tokens = jwtUtils.createTokenInfo(memberId);
        return TokenVO.of(tokens.get(0), tokens.get(1));
    }
}
