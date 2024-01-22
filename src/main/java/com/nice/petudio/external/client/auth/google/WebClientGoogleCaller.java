package com.nice.petudio.external.client.auth.google;

import com.nice.petudio.common.exception.error.ErrorCode;
import com.nice.petudio.common.exception.model.BadGatewayException;
import com.nice.petudio.common.exception.model.ValidationException;
import com.nice.petudio.external.client.auth.google.dto.response.GoogleProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class WebClientGoogleCaller implements GoogleApiCaller {

    private final WebClient webClient;

    @Override
    public GoogleProfileResponse getProfileInfo(String accessToken) {
        return webClient.get()
                .uri("https://www.googleapis.com/userinfo/v2/me")
                .headers(headers -> headers.setBearerAuth(accessToken))
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse ->
                        Mono.error(new ValidationException(ErrorCode.INVALID_OAUTH2_ACCESS_TOKEN_EXCEPTION,
                                String.format("잘못된 OAuth2 엑세스 토큰 입니다. (TOKEN_VALUE: %s)", accessToken))))
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse ->
                        Mono.error(new BadGatewayException(ErrorCode.BAD_GATEWAY_EXCEPTION,
                                "Google OAuth2 서버 연동 과정에서 에러가 발생했습니다.")))
                .bodyToMono(GoogleProfileResponse.class)
                .block();
    }

}
