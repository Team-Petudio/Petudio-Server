package com.nice.petudio.external.client.auth.kakao;

import com.nice.petudio.external.client.auth.kakao.dto.response.KakaoProfileResponse;
import com.nice.petudio.common.exception.model.BadGatewayException;
import com.nice.petudio.common.exception.model.ValidationException;
import com.nice.petudio.common.exception.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class WebClientKakaoCaller implements KakaoApiCaller {

    private final WebClient webClient;

    @Override
    public KakaoProfileResponse getProfileInfo(String accessToken) {
        return webClient.get()
                .uri("https://kapi.kakao.com/v2/user/me")
                .headers(headers -> headers.setBearerAuth(accessToken))
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse ->
                        Mono.error(new ValidationException(ErrorCode.INVALID_OAUTH2_TOKEN_EXCEPTION,
                                String.format("잘못된 OAuth2 토큰 입니다. (TOKEN_VALUE: %s)", accessToken))))
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse ->
                        Mono.error(new BadGatewayException(ErrorCode.BAD_GATEWAY_EXCEPTION,
                                "Kakao OAuth2 서버 연동 과정에서 에러가 발생했습니다.")))
                .bodyToMono(KakaoProfileResponse.class)
                .block();
    }

}
