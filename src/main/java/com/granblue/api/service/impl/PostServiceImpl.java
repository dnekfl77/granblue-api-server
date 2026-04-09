package com.granblue.api.service.impl;

import com.granblue.api.dto.converter.PostConverter;
import com.granblue.api.dto.request.PostCreateRequest;
import com.granblue.api.dto.request.PostUpdateRequest;
import com.granblue.api.dto.response.PostResponse;
import com.granblue.api.entity.Post;
import com.granblue.api.entity.User;
import com.granblue.api.repository.PostRepository;
import com.granblue.api.repository.UserRepository;
import com.granblue.api.service.PostService;
import lombok.RequiredArgsConstructor;
import com.granblue.api.exception.ForbiddenException;
import com.granblue.api.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostConverter postConverter;

    @Transactional
    @Override
    public PostResponse createPost(PostCreateRequest request, String userEmail) {
        User user = findUserByEmail(userEmail);

        Post post = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .author(user)
                .build();

        return postConverter.toResponse(postRepository.save(post));
    }

    @Override
    public Page<PostResponse> getAllPosts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return postRepository.findAll(pageable).map(postConverter::toResponse);
    }

    @Override
    public PostResponse getPostById(Long id) {
        Post post = findPostById(id);
        return postConverter.toResponse(post);
    }

    @Transactional
    @Override
    public PostResponse updatePost(Long id, PostUpdateRequest request, String userEmail) {
        User user = findUserByEmail(userEmail);
        Post post = findPostById(id);

        validatePostOwner(post, user);

        post.setTitle(request.getTitle());
        post.setContent(request.getContent());

        return postConverter.toResponse(post);
    }

    @Transactional
    @Override
    public void deletePost(Long id, String userEmail) {
        User user = findUserByEmail(userEmail);
        Post post = findPostById(id);
        validatePostOwner(post, user);
        postRepository.delete(post);
    }

    private Post findPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("해당 ID의 게시글을 찾을 수 없습니다. id: " + id));
    }

    private void validatePostOwner(Post post, User user) {
        if (!post.getAuthor().getId().equals(user.getId())) {
            throw new ForbiddenException("게시글에 대한 권한이 없습니다.");
        }
    }

    private User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("사용자를 찾을 수 없습니다. email: " + email));
    }
}
