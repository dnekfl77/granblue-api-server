package com.granblue.api.controller;

import com.granblue.api.common.ApiResponse;
import com.granblue.api.dto.request.PostCreateRequest;
import com.granblue.api.dto.request.PostUpdateRequest;
import com.granblue.api.dto.response.PostResponse;
import com.granblue.api.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    public ResponseEntity<ApiResponse<PostResponse>> createPost(
            @Valid @RequestBody PostCreateRequest request,
            Authentication authentication) {
        return ResponseEntity.ok(ApiResponse.success(postService.createPost(request, authentication.getName())));
    }

    @Operation(summary = "게시글 목록 조회", description = "전체 게시글 목록을 페이징하여 최신순으로 조회합니다.")
    @GetMapping
    public ResponseEntity<ApiResponse<Page<PostResponse>>> getAllPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(ApiResponse.success(postService.getAllPosts(page, size)));
    }

    @Operation(summary = "게시글 단건 조회", description = "ID로 특정 게시글을 조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PostResponse>> getPost(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(postService.getPostById(id)));
    }

    @Operation(summary = "게시글 수정", description = "ID로 특정 게시글을 수정합니다. 작성자만 수정할 수 있습니다.")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PostResponse>> updatePost(
            @PathVariable Long id,
            @Valid @RequestBody PostUpdateRequest request,
            Authentication authentication) {
        return ResponseEntity.ok(ApiResponse.success(postService.updatePost(id, request, authentication.getName())));
    }

    @Operation(summary = "게시글 삭제", description = "ID로 특정 게시글을 삭제합니다. 작성자만 삭제할 수 있습니다.")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePost(
            @PathVariable Long id,
            Authentication authentication) {
        postService.deletePost(id, authentication.getName());
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
