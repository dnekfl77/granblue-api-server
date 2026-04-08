package com.granblue.api.controller;

import com.granblue.api.common.CommonResponse;
import com.granblue.api.dto.request.PostCreateRequest;
import com.granblue.api.dto.response.PostResponse;
import com.granblue.api.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
@Tag(name = "Post API", description = "게시판 API")
public class PostController {
    private final PostService postService;

    @Operation(summary = "게시글 작성", description = "로그인한 사용자가 새로운 게시글을 작성합니다.")
    @PostMapping
    public ResponseEntity<CommonResponse<PostResponse>> createPost(
            @Valid @RequestBody PostCreateRequest request,
            Authentication authentication) {
        return ResponseEntity.ok(CommonResponse.success(postService.createPost(request, authentication.getName())));
    }

    @Operation(summary = "게시글 목록 조회", description = "전체 게시글 목록을 최신순으로 조회합니다.")
    @GetMapping
    public ResponseEntity<CommonResponse<List<PostResponse>>> getAllPosts() {
        return ResponseEntity.ok(CommonResponse.success(postService.getAllPosts()));
    }
}