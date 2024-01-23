package com.nice.petudio.api.controller.auth.dto.request;

public record ReissueRequest(String accessToken, String refreshToken) {
}
