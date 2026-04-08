package com.granblue.api.service;

import com.granblue.api.dto.request.PostCreateRequest;
import com.granblue.api.dto.response.PostResponse;
import java.util.List;

public interface PostService {
    PostResponse createPost(PostCreateRequest request, String email);
    List<PostResponse> getAllPosts();
}