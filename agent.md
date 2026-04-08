# Project Overview
본 프로젝트는 React, Svelte 등의 클라이언트 애플리케이션을 위한 RESTful API 서버입니다. 
확장 가능한 아키텍처를 지향하며, 아래의 기술 스택과 규칙을 엄격히 준수하여 코드를 생성해야 합니다.

## Tech Stack
- **Framework:** Spring Boot 3.x (안정화된 최신 버전), Java 17 이상
- **Build Tool:** Gradle
- **Database:** PostgreSQL (Docker), Spring Data JPA
- **Security:** Spring Security, JWT (Access Token 기반)
- **Libraries:** Lombok, Apache Commons Lang3, Apache Commons Collections4, Guava, MapStruct(권장)
- **Logging:** SLF4J (Logback), JPA/Hibernate SQL 쿼리 로깅 및 바인딩 파라미터 확인 설정 포함

## Architecture & Coding Conventions
1. **Layered Architecture:** Controller -> Service -> Repository -> DB 구조를 엄격히 분리합니다.
2. **DTO Pattern:** Entity 객체를 절대 Controller의 응답으로 직접 반환하지 않습니다. 모든 요청과 응답은 RequestDTO, ResponseDTO를 사용합니다.
3. **Extensibility:** 공통 응답 포맷(`CommonResponse<T>`)과 전역 예외 처리(`GlobalExceptionHandler`)를 적용합니다.
4. **Security:** 비밀번호는 BCrypt로 암호화하며, `SecurityContextHolder`를 통해 인증 객체를 관리합니다.
5. **Clean Code:** 명확한 변수명/메서드명을 사용하고, 불필요한 `@Data` 사용 대신 `@Getter`, `@Builder`, `@NoArgsConstructor(access = AccessLevel.PROTECTED)`를 조합합니다.
6. **API Specs:** 모든 API는 Swagger/OpenAPI 3.0 어노테이션을 포함해야 합니다.
7. **Validation:** `@Valid`와 Jakarta Validation 어노테이션을 사용하여 입력을 검증합니다.
8. **User Spec:** 회원가입 시 이름(String), 아이디(String), 나이(Integer), 성별(Enum), 생일(String), 이메일(String), 비밀번호(String)를 필수 항목으로 처리합니다.
9. **Persistence Policy:** - 모든 엔티티는 생성/수정 시간을 자동 기록하는 `BaseTimeEntity`를 상속합니다.
   - Enum 타입은 반드시 `@Enumerated(EnumType.STRING)`을 사용합니다.
10. **Testing:** 신규 기능 구현 시 반드시 JUnit5 및 AssertJ 기반의 서비스 테스트 코드를 작성합니다.
11. **Object Mapping (MapStruct):**
   - Entity와 DTO 간의 변환은 MapStruct를 사용함을 원칙으로 합니다.
   - MyBatis 등 DB 매퍼와의 명칭 혼동을 방지하기 위해 인터페이스 명명 시 ~Converter 접미사를 사용합니다. (예: UserConverter)
   - 모든 Converter는 @Mapper(componentModel = "spring") 설정을 사용하여 Spring Bean으로 관리합니다.
   - Lombok과의 충돌 방지를 위해 빌드 설정(Gradle)에서 Annotation Processor 순서를 Lombok -> MapStruct 순으로 구성합니다.

## Project Structure & Packaging
1. **Directory Structure:** 표준 Maven/Gradle 레이아웃을 준수합니다.
   - `src/main/java`: 소스 코드 (java 파일)
   - `src/main/resources`: 설정 파일 (application.yml), 정적 리소스
   - `src/test/java`: 테스트 코드
2. **Package Naming:** 기본 패키지는 `com.granblue.api`로 시작합니다.
   - `controller`: API 엔드포인트 및 요청 매핑
   - `service`: 비즈니스 로직 (인터페이스와 구현체 `impl` 분리 권장)
   - `repository`: JPA Repository
   - `entity`: JPA Entity (DB 매핑)
   - `dto`: `request`, `response` 하위 패키지 분리
      - `dto.converter`: MapStruct 인터페이스 (~Converter.java) 위치
   - `exception`: 커스텀 예외 및 에러 응답 객체
   - `config`: Security, Swagger, JPA, Querydsl 등 설정
   - `common`: 공통 유틸, 상수, `CommonResponse` 포맷
3. **Resource Rules:**
   - 기본 설정파일은 `application.yml`
   - 환경별 설정은 `application-local.yml`, `application-dev.yml`, `application-prod.yml`로 관리하며, 민감 정보는 환경 변수를 사용합니다.

## API & Database Rules
1. **RESTful Convention:**
   - URL은 `kebab-case`를 사용합니다. (예: `/api/v1/user-members`)
   - 자원은 복수형 명사를 사용하며, 행위는 HTTP Method(GET, POST, PUT, DELETE)로 표현합니다.
2. **Error Response:**
   - 에러 응답 시 커스텀 에러 코드, 메시지, 그리고 Validation 실패 시 상세 이유(`errors`)를 포함합니다.
3. **Soft Delete:**
   - 데이터 삭제 시 실제 DELETE 대신 `is_deleted` 또는 `deleted_at` 컬럼을 사용하는 논리 삭제를 기본 전략으로 고려합니다.

---

### 추가 팁
* **Granblue Fantasy 관련:** 패키지명에 `granblue`가 포함된 것으로 보아 해당 게임 관련 API라면, 관련 도메인 용어(Character, Weapon 등)에 대한 사전 정의를 `entity` 섹션에 추가하면 AI가 더 정확한 도메인 모델을 설계합니다.
* **Querydsl:** 만약 동적 쿼리가 빈번하다면 `Tech Stack`에 Querydsl을 명시하고 `repository` 패키지 구조에 `Custom` 인터페이스 패턴을 추가하는 것이 좋습니다.

---

## [추가 사항: MapStruct 기반 코드 작성 예시]
AI가 코드를 생성할 때 참고할 수 있도록 아래 관례를 적용합니다.

// 예시: com.granblue.api.domain.user.dto.converter.UserConverter
@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    injectionStrategy = InjectionStrategy.CONSTRUCTOR
)public interface UserConverter {
    // Entity -> ResponseDTO
    UserResponseDto toResponseDto(User user);

    // RequestDTO -> Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true) // 보안상 별도 처리 권장
    User toEntity(UserRequestDto requestDto);
}