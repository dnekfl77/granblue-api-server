# Granblue API Server - Project Context

이 파일은 프로젝트의 현재까지 진행된 전체 상태와 주요 소스 코드를 요약한 파일입니다. 
새로운 세션이나 다른 환경에서 작업을 이어나가기 위한 **Context**로 활용할 수 있습니다.

---

## 🤖 Task-Specific Model Usage
* **Gemini 3.1 Pro Preview**: 아키텍처 설계, 복잡한 비즈니스 로직 구현, 대규모 리팩토링 및 코드 리뷰.
* **Gemini 3 Flash Preview**: 실시간 스트리밍 UI 구현, 빠른 텍스트 처리, 단위 함수 작성.
* **Gemini Code Assist (VS Code)**: 인라인 코드 완성, 실시간 버그 수정 및 심볼 참조.

---

## 1. 프로젝트 개요 및 아키텍처 규칙
- **Tech Stack:** Spring Boot 3.2.3, Java 17, PostgreSQL (Docker), Spring Security, JWT, MapStruct, Lombok
- **Architecture:**
  - Controller -> Service -> Repository 의 Layered Architecture
  - 모든 응답은 `ApiResponse<T>` 로 래핑
  - 전역 예외 처리는 `@RestControllerAdvice` 기반 `GlobalExceptionHandler` 적용
  - DTO와 Entity 간의 변환은 **MapStruct**를 활용
  - SecurityContextHolder를 통해 현재 로그인한 사용자(인증 객체) 관리

---

## 2. 주요 설정 파일

### `docker-compose.yml`
```yaml
version: '3.8'
services:
  postgres:
    image: postgres:15-alpine
    container_name: granblue-postgres
    ports:
      - "5432:5432"
    environment:
      POSTGRES_DB: granblue_db
      POSTGRES_USER: granblue_user
      POSTGRES_PASSWORD: granblue_password
    volumes:
      - postgres_data:/var/lib/postgresql/data
volumes:
  postgres_data:
```

### `build.gradle` (주요 의존성 요약)
```gradle
dependencies {
    developmentOnly 'org.springframework.boot:spring-boot-devtools'
    implementation 'org.springframework.boot:spring-boot-starter-web'
    implementation 'org.springframework.boot:spring-boot-starter-validation'
    implementation 'org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0'
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    runtimeOnly 'org.postgresql:postgresql'
    implementation 'org.springframework.boot:spring-boot-starter-security'
    implementation 'io.jsonwebtoken:jjwt-api:0.11.5'
    runtimeOnly 'io.jsonwebtoken:jjwt-impl:0.11.5'
    runtimeOnly 'io.jsonwebtoken:jjwt-jackson:0.11.5'
    implementation 'org.mapstruct:mapstruct:1.5.5.Final'
    annotationProcessor 'org.mapstruct:mapstruct-processor:1.5.5.Final'
    annotationProcessor 'org.projectlombok:lombok-mapstruct-binding:0.2.0'
    compileOnly 'org.projectlombok:lombok'
    annotationProcessor 'org.projectlombok:lombok'
}
```

### `application.yml`
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/granblue_db
    username: granblue_user
    password: granblue_password
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        format_sql: true
        highlight_sql: true
jwt:
  secret: v1TqO1N0h3R6t4w1o8V9f5P1k2N0h3R6t4w1o8V9f5P1k2N0h3R6t4w1o8V9f5P1
logging:
  level:
    org.hibernate.SQL: DEBUG
    org.hibernate.orm.jdbc.bind: TRACE
```

---

## 3. 공통 모듈 (Common & Exception)

### `ApiResponse.java`
```java
@Getter
@Builder
public class ApiResponse<T> {
    private int status;
    private String message;
    private T data;

    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder().status(200).message("SUCCESS").data(data).build();
    }
    public static <T> ApiResponse<T> error(int status, String message) {
        return ApiResponse.<T>builder().status(status).message(message).data(null).build();
    }
}
```

### `GlobalExceptionHandler.java` (요약)
- `IllegalArgumentException` -> 400 Bad Request
- `MethodArgumentNotValidException` -> 400 Bad Request (Validation 오류)
- `AuthenticationException`, `AccessDeniedException` -> 401 Unauthorized
- `Exception` -> 500 Internal Server Error

---

## 4. 보안 및 인증 (Security & JWT)

- `JwtTokenProvider`: JWT 생성 및 파싱, 인증 객체(`UsernamePasswordAuthenticationToken`) 생성 기능 제공.
- `JwtAuthenticationFilter`: 요청 헤더에서 토큰을 추출해 유효성 검증 후 `SecurityContextHolder`에 저장.
- `SecurityConfig`: 
  - CSRF 비활성화, 세션 Stateless 설정
  - `/api/v1/auth/**`, Swagger UI 관련 경로는 허용(permitAll), 나머지는 인증 필요(authenticated)
  - `JwtAuthenticationFilter`를 `UsernamePasswordAuthenticationFilter` 이전에 등록.

---

## 5. 도메인별 세부 컨텍스트
각 도메인의 세부 엔티티, 컨트롤러, 서비스 로직은 아래 분리된 문서를 참고하세요.
- 👉 회원/인증 도메인 (Auth & User)
- 👉 게시판 도메인 (Post)

---

## 6. 현재까지 개발된 API 엔드포인트 요약

| HTTP Method | Endpoint | Description | Auth Required |
|-------------|----------|-------------|---------------|
| POST | `/api/v1/auth/sign-up` | 회원가입 | No |
| POST | `/api/v1/auth/sign-in` | 로그인 (JWT 발급) | No |
| POST | `/api/v1/posts` | 게시글 작성 | Yes |
| GET | `/api/v1/posts` | 전체 게시글 조회 | Yes |

---

## 7. 다음 진행 추천 작업 (Next Steps)
1. 특정 게시글 단건 조회 API 및 수정/삭제 기능 구현.
2. JPA 동적 쿼리(Querydsl) 초기 설정 및 검색 기능 도입.
3. 게시글과 댓글(Comment) 간 연관관계 도메인 설계.
4. 통합 테스트(Integration Test) 작성 및 CI 파이프라인 구축.