package com.nice.petudio.external.client.auth.google;


import com.nice.petudio.external.client.auth.google.dto.response.GoogleProfileResponse;

public interface GoogleApiCaller {

	GoogleProfileResponse getProfileInfo(String accessToken);

}
