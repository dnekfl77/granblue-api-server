package com.granblue.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "중복 검사 응답 DTO")
public class DuplicateCheckResponse {
    
    @Schema(description = "사용 가능 여부 (true: 사용 가능, false: 중복됨)", example = "true")
    private boolean available;
}