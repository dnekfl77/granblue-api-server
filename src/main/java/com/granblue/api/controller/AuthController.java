package com.granblue.api.controller;

import com.granblue.api.common.CommonResponse;
import com.granblue.api.dto.request.LoginRequest;
import com.granblue.api.dto.request.SignUpRequest;
import com.granblue.api.dto.response.TokenResponse;
import com.granblue.api.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Auth API", description = "회원가입 및 로그인 API")
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "회원가입", description = "새로운 회원을 등록합니다.")
    @PostMapping("/sign-up")
    public ResponseEntity<CommonResponse<Void>> signUp(@Valid @RequestBody SignUpRequest request) {
        authService.signUp(request);
        return ResponseEntity.ok(CommonResponse.success(null));
    }

    @Operation(summary = "로그인", description = "로그인 인증 후 JWT 토큰을 반환합니다.")
    @PostMapping("/sign-in")
    public ResponseEntity<CommonResponse<TokenResponse>> signIn(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(CommonResponse.success(authService.signIn(request)));
    }
}