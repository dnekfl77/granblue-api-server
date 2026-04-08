package com.granblue.api.service.impl;

import com.granblue.api.dto.request.PostCreateRequest;
import com.granblue.api.dto.response.PostResponse;
import com.granblue.api.entity.Post;
import com.granblue.api.entity.User;
import com.granblue.api.dto.converter.PostConverter;
import com.granblue.api.repository.PostRepository;
import com.granblue.api.repository.UserRepository;
import com.granblue.api.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostConverter postMapper;

    @Override
    @Transactional
    public PostResponse createPost(PostCreateRequest request, String email) {
        User author = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        Post post = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .author(author)
                .build();
        return postMapper.toResponse(postRepository.save(post));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts() {
        return postRepository.findAllWithAuthorOrderByCreatedAtDesc().stream()
                .map(postMapper::toResponse)
                .collect(Collectors.toList());
    }
}