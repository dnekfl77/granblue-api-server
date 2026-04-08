package com.granblue.api.dto.request;

import com.granblue.api.entity.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(description = "회원가입 요청 DTO")
public class SignUpRequest {
    @NotBlank(message = "아이디는 필수입니다.")
    @Schema(description = "사용자 아이디", example = "granblue123")
    private String accountId;

    @NotBlank(message = "비밀번호는 필수입니다.")
    @Schema(description = "비밀번호", example = "password123!")
    private String password;

    @NotBlank(message = "이름은 필수입니다.")
    @Schema(description = "사용자 이름", example = "홍길동")
    private String name;

    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    @Schema(description = "이메일", example = "user@example.com")
    private String email;

    @NotNull(message = "나이는 필수입니다.")
    @Schema(description = "나이", example = "25")
    private Integer age;

    @NotNull(message = "성별은 필수입니다.")
    @Schema(description = "성별 (MALE, FEMALE)", example = "MALE")
    private Gender gender;

    @NotBlank(message = "생일은 필수입니다.")
    @Schema(description = "생년월일 (YYYYMMDD)", example = "19990101")
    private String birth;
}