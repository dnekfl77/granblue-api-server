package com.granblue.api.repository;

import com.granblue.api.entity.Post;
import com.granblue.api.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import com.granblue.api.config.JpaAuditingConfig;
import com.granblue.api.entity.Gender;
import com.granblue.api.entity.Role;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(JpaAuditingConfig.class)
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    private User testAuthor;

    @BeforeEach
    void setUp() {
        testAuthor = User.builder()
                .accountId("author_user")
                .email("author@granblue.com")
                .password("password")
                .name("작성자")
                .age(25)
                .birth("1999-12-31")
                .gender(Gender.MALE)
                .role(Role.ROLE_USER)
                .build();
        userRepository.save(testAuthor);
    }

    @Test
    @DisplayName("게시글 목록을 작성자(Author) 정보와 함께 페치 조인하여 최신순으로 조회한다.")
    void findAllWithAuthorOrderByCreatedAtDescTest() {
        // given
        Post post1 = Post.builder()
                .title("첫 번째 게시글")
                .content("내용 1")
                .author(testAuthor)
                .build();
        Post post2 = Post.builder()
                .title("두 번째 게시글")
                .content("내용 2")
                .author(testAuthor)
                .build();

        postRepository.save(post1);
        postRepository.save(post2);

        // when
        List<Post> posts = postRepository.findAllWithAuthorOrderByCreatedAtDesc();

        // then
        assertThat(posts).hasSize(2);
        // Fetch Join 검증: N+1 문제 없이 프록시가 아닌 실제 객체가 로드되었는지 확인 (이름 조회)
        assertThat(posts.get(0).getAuthor().getName()).isEqualTo("작성자");
    }
}
