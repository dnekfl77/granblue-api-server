package com.granblue.api.repository;

import com.granblue.api.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import com.granblue.api.config.JpaAuditingConfig;
import com.granblue.api.entity.Gender;
import com.granblue.api.entity.Role;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(JpaAuditingConfig.class)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("사용자를 정상적으로 저장하고 이메일로 조회할 수 있다.")
    void saveAndFindByEmailTest() {
        // given
        User user = User.builder()
                .accountId("granblue_user")
                .email("test@granblue.com")
                .password("encoded_password")
                .name("그랑")
                .age(20)
                .birth("2000-01-01")
                .gender(Gender.MALE)
                .role(Role.ROLE_USER)
                .build();

        userRepository.save(user);

        // when
        // AuthService의 로그인 등에서 사용될 findByEmail 메서드 가정
        Optional<User> foundUser = userRepository.findByEmail("test@granblue.com");

        // then
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getAccountId()).isEqualTo("granblue_user");
        assertThat(foundUser.get().getName()).isEqualTo("그랑");
    }

    @Test
    @DisplayName("존재하지 않는 이메일 조회 시 빈 Optional을 반환한다.")
    void findByEmail_NotFoundTest() {
        // when
        Optional<User> foundUser = userRepository.findByEmail("unknown@granblue.com");

        // then
        assertThat(foundUser).isEmpty();
    }
}
