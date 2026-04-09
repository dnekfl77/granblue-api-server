package com.granblue.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
@Schema(description = "게시글 수정 요청 DTO")
public class PostUpdateRequest {
    @NotBlank(message = "제목은 필수입니다.")
    @Schema(description = "게시글 제목", example = "수정된 업데이트 소식")
    private String title;

    @NotBlank(message = "내용은 필수입니다.")
    @Schema(description = "게시글 내용", example = "수정된 패치 노트 공유합니다...")
    private String content;
}
