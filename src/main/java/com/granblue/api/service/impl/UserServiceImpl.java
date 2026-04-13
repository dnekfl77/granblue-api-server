package com.granblue.api.service.impl;

import com.granblue.api.dto.response.DuplicateCheckResponse;
import com.granblue.api.repository.UserRepository;
import com.granblue.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public DuplicateCheckResponse checkEmailDuplicate(String email) {
        return DuplicateCheckResponse.builder()
                .available(!userRepository.existsByEmail(email))
                .build();
    }

    @Override
    public DuplicateCheckResponse checkAccountIdDuplicate(String accountId) {
        return DuplicateCheckResponse.builder()
                .available(!userRepository.existsByAccountId(accountId))
                .build();
    }
}