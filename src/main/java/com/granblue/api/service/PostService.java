package com.granblue.api.service;

import com.granblue.api.dto.request.PostCreateRequest;
import com.granblue.api.dto.request.PostUpdateRequest;
import com.granblue.api.dto.response.PostResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PostService {
    PostResponse createPost(PostCreateRequest request, String userEmail);
    Page<PostResponse> getAllPosts(int page, int size);
    PostResponse getPostById(Long id);
    PostResponse updatePost(Long id, PostUpdateRequest request, String userEmail);
    void deletePost(Long id, String userEmail);
}
