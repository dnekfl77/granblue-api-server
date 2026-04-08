package com.granblue.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(description = "게시글 작성 요청 DTO")
public class PostCreateRequest {
    @NotBlank(message = "제목은 필수입니다.")
    @Schema(description = "게시글 제목", example = "새로운 업데이트 소식")
    private String title;

    @NotBlank(message = "내용은 필수입니다.")
    @Schema(description = "게시글 내용", example = "이번 패치 노트 공유합니다...")
    private String content;
}