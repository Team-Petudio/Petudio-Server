package com.nice.petudio.api.controller.auth.service;


import com.nice.petudio.api.controller.auth.dto.request.LoginRequest;
import com.nice.petudio.api.controller.auth.dto.request.SignUpRequest;

public interface AuthService {

	Long signUp(SignUpRequest request);

	Long login(LoginRequest request);
}
