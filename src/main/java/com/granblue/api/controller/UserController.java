package com.granblue.api.controller;

import com.granblue.api.common.ApiResponse;
import com.granblue.api.dto.response.DuplicateCheckResponse;
import com.granblue.api.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Validated
@Tag(name = "User API", description = "사용자 프로필 및 정보 관련 API")
public class UserController {
    
    private final UserService userService;

    @Operation(summary = "이메일 중복 확인", description = "회원가입 전 이메일의 사용 가능 여부를 확인합니다.")
    @GetMapping("/check-email")
    public ResponseEntity<ApiResponse<DuplicateCheckResponse>> checkEmail(
            @RequestParam @NotBlank(message = "이메일은 필수입니다.") @Email(message = "이메일 형식이 올바르지 않습니다.") String email) {
        return ResponseEntity.ok(ApiResponse.success(userService.checkEmailDuplicate(email)));
    }

    @Operation(summary = "아이디 중복 확인", description = "회원가입 전 아이디(accountId)의 사용 가능 여부를 확인합니다.")
    @GetMapping("/check-account-id")
    public ResponseEntity<ApiResponse<DuplicateCheckResponse>> checkAccountId(
            @RequestParam @NotBlank(message = "아이디는 필수입니다.") String accountId) {
        return ResponseEntity.ok(ApiResponse.success(userService.checkAccountIdDuplicate(accountId)));
    }
}