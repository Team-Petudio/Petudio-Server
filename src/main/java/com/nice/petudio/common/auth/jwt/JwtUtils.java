package com.nice.petudio.common.auth.jwt;

import com.nice.petudio.common.auth.jwt.constant.JwtKey;
import com.nice.petudio.common.config.redis.constant.RedisKey;
import com.nice.petudio.common.exception.error.ErrorCode;
import com.nice.petudio.common.exception.model.UnAuthorizedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.DecodingException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JwtUtils {

    private final RedisTemplate<String, Object> redisTemplate;
    private final SecretKey secretKey;
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30; // 30분
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 30L; // 30일
    private static final long EXPIRED_TIME = 1;

    public JwtUtils(@Value("${jwt.secret}") String secretKey, RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }


    public List<String> createTokenInfo(Long memberId) {
        long now = (new Date()).getTime();
        Date accessTokenExpiryTime = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
        Date refreshTokenExpiryTime = new Date(now + REFRESH_TOKEN_EXPIRE_TIME);

        // Access Token 생성
        String accessToken = Jwts.builder()
                .claim(JwtKey.MEMBER_ID.getKey(), memberId)
                .setExpiration(accessTokenExpiryTime)
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();

        String refreshToken = Jwts.builder()
                .setExpiration(refreshTokenExpiryTime)
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();

        redisTemplate.opsForValue()
                .set(RedisKey.REFRESH_TOKEN.getKey() + memberId, refreshToken, REFRESH_TOKEN_EXPIRE_TIME,
                        TimeUnit.MILLISECONDS);

        return List.of(accessToken, refreshToken);
    }

    public void expireRefreshToken(Long memberId) {
        redisTemplate.opsForValue()
                .set(RedisKey.REFRESH_TOKEN.getKey() + memberId, "", EXPIRED_TIME, TimeUnit.MILLISECONDS);
    }

    public Optional<Long> parseMemberId(String accessToken) {
        return Optional.ofNullable(parseClaims(accessToken).get(JwtKey.MEMBER_ID.getKey(), Long.class));
    }

    private Claims parseClaims(String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(secretKey).build()
                    .parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException exception) {
            return exception.getClaims();
        } catch (SignatureException exception) {
            throw new UnAuthorizedException(ErrorCode.UNAUTHORIZED_JWT_EXCEPTION,
                    String.format("입력받은 JWT 토큰의 Signature가 잘못되었습니다. (TOKEN: %s)", token));
        }
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException | DecodingException e) {
            log.warn("Invalid JWT token ", e);
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token", e);
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token", e);
        } catch (IllegalArgumentException e) {
            log.error("JWT claims is empty.", e);
        } catch (Exception e) {
            log.error("Unhandled JWT exception", e);
        }
        return false;
    }
}
