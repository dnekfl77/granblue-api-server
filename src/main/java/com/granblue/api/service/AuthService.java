package com.granblue.api.service;

import com.granblue.api.dto.request.LoginRequest;
import com.granblue.api.dto.request.SignUpRequest;
import com.granblue.api.dto.response.TokenResponse;

public interface AuthService {
    void signUp(SignUpRequest request);
    TokenResponse signIn(LoginRequest request);
    TokenResponse refresh(String refreshToken);
}