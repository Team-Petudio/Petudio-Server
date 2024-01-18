package com.nice.petudio.api.controller.auth.service;


import com.nice.petudio.api.controller.auth.vo.TokenVO;
import com.nice.petudio.api.controller.member.service.MemberServiceUtils;
import com.nice.petudio.domain.member.Member;
import com.nice.petudio.domain.member.repository.MemberRepository;
import com.nice.petudio.global.auth.jwt.JwtTokenService;
import com.nice.petudio.global.config.redis.constant.RedisKey;
import com.nice.petudio.global.exception.UnAuthorizedException;
import com.nice.petudio.global.exception.error.ErrorCode;
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

    private final JwtTokenService jwtTokenService;

    public TokenVO createTokenInfo(Long memberId) {
        List<String> tokens = jwtTokenService.createTokenInfo(memberId);
        return TokenVO.of(tokens.get(0), tokens.get(1));
    }

    public TokenVO reissueToken(TokenVO tokenVO) {
        Long memberId = jwtTokenService.parseMemberId(tokenVO.getAccessToken())
                .orElseThrow();
        Member member = MemberServiceUtils.findMemberById(memberRepository, memberId);

        if (!jwtTokenService.validateToken(tokenVO.getRefreshToken())) {
            throw new UnAuthorizedException(ErrorCode.UNAUTHORIZED_JWT_EXCEPTION,
                    String.format("MemberId(%d)의 토큰 갱신 요청에 포함된 Refresh Token이 유효하지 않아, Token Refresh가 수행되지 않았습니다.", memberId));
        }
        String refreshToken = (String) redisTemplate.opsForValue().get(RedisKey.REFRESH_TOKEN + memberId.toString());
        if (Objects.isNull(refreshToken)) {
            throw new UnAuthorizedException(ErrorCode.UNAUTHORIZED_JWT_EXCEPTION,
                    String.format("보관 중인 MemberId(%d)의 Refresh Token이 존재하지 않아, Token Refresh가 수행되지 않았습니다.", memberId));
        }
        if (!refreshToken.equals(tokenVO.getRefreshToken())) {
            jwtTokenService.expireRefreshToken(member.getId());
            throw new UnAuthorizedException(ErrorCode.UNAUTHORIZED_JWT_EXCEPTION,
                    String.format("보관 중인 MemberId(%d)의 Refresh Token이 유효하지 않아, Token Refresh가 수행되지 않았습니다.", memberId));
        }
        List<String> tokens = jwtTokenService.createTokenInfo(memberId);
        return TokenVO.of(tokens.get(0), tokens.get(1));
    }
}
