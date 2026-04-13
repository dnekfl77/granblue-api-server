package com.granblue.api.service;

import com.granblue.api.dto.response.DuplicateCheckResponse;

public interface UserService {
    DuplicateCheckResponse checkEmailDuplicate(String email);
    DuplicateCheckResponse checkAccountIdDuplicate(String accountId);
}