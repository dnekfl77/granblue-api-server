# Domain Context: Auth & User

## `User` Entity (`entity.User`)
- **Fields**: id, accountId(unique), email(unique), password, name, age, gender(Enum), birth, role(Enum), termsAgreed(Boolean)
- `BaseTimeEntity` 상속 (createdAt, updatedAt)

## `AuthController` & `AuthService` (`controller.AuthController`, `service.AuthService`)
- **POST `/api/v1/auth/sign-up`**
  - `SignUpRequest` (`dto.request.SignUpRequest`) DTO로 입력받아 비밀번호를 BCrypt로 암호화 및 필수 약관 동의(`termsAgreed`) 여부를 포함하여 User 저장.
  - 중복된 이메일/아이디 방어 로직 존재.
- **POST `/api/v1/auth/sign-in`**
  - `LoginRequest` (`dto.request.LoginRequest`) DTO로 입력받아 검증 후 `JwtTokenProvider`를 통해 `TokenResponse` (`dto.response.TokenResponse`) 반환.
  
> *추가 예정 작업: 현재 로그인한 사용자의 프로필 정보 조회 API 구현 등*