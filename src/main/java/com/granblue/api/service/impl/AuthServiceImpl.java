package com.granblue.api.service.impl;

import com.granblue.api.config.security.JwtTokenProvider;
import com.granblue.api.dto.request.LoginRequest;
import com.granblue.api.dto.request.SignUpRequest;
import com.granblue.api.dto.response.TokenResponse;
import com.granblue.api.entity.Role;
import com.granblue.api.entity.User;
import com.granblue.api.repository.UserRepository;
import com.granblue.api.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    @Transactional
    public void signUp(SignUpRequest request) {
        if (userRepository.existsByEmail(request.getEmail()) || userRepository.existsByAccountId(request.getAccountId())) {
            throw new IllegalArgumentException("이미 가입된 이메일이거나 사용 중인 아이디입니다.");
        }
        User user = User.builder()
                .accountId(request.getAccountId())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .age(request.getAge())
                .gender(request.getGender())
                .birth(request.getBirth())
                .role(Role.ROLE_USER)
                .build();
        userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public TokenResponse signIn(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new IllegalArgumentException("가입되지 않은 이메일입니다."));
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        String accessToken = jwtTokenProvider.createToken(user.getEmail(), user.getRole().name());
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getEmail());

        return new TokenResponse(accessToken, refreshToken);
    }

    @Override
    public TokenResponse refresh(String refreshToken) {
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new IllegalArgumentException("유효하지 않거나 만료된 Refresh Token 입니다.");
        }
        String email = jwtTokenProvider.getEmailFromToken(refreshToken);
        User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        String newAccessToken = jwtTokenProvider.createToken(user.getEmail(), user.getRole().name());
        return new TokenResponse(newAccessToken, refreshToken); // 보안을 높이려면 여기서 Refresh Token도 새로 발급(Rotation)할 수 있습니다.
    }
}
