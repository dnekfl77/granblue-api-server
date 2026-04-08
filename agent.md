# Project Overview
본 프로젝트는 React, Svelte 등의 클라이언트 애플리케이션을 위한 RESTful API 서버입니다. 
확장 가능한 아키텍처를 지향하며, 아래의 기술 스택과 규칙을 엄격히 준수하여 코드를 생성해야 합니다.

## Tech Stack
- **Framework:** Spring Boot 3.x (안정화된 최신 버전), Java 17 이상
- **Build Tool:** Gradle
- **Database:** PostgreSQL (Docker를 통한 구동), Spring Data JPA
- **Security:** Spring Security, JWT (JSON Web Token)
- **Libraries:** Lombok, Apache Commons Lang3, Apache Commons Collections4, Guava
- **Logging:** SLF4J (Logback), 특히 JPA/Hibernate SQL 쿼리 로깅 및 바인딩 파라미터 확인 설정 포함

## Architecture & Coding Conventions
1. **Layered Architecture:** Controller -> Service -> Repository -> DB 구조를 엄격히 분리합니다.
2. **DTO Pattern:** Entity 객체를 절대 Controller의 응답으로 직접 반환하지 않습니다. 모든 요청과 응답은 RequestDTO, ResponseDTO를 사용합니다.
3. **Extensibility:** 향후 기능 확장을 고려하여 공통 응답 포맷(CommonResponse)과 전역 예외 처리(GlobalExceptionHandler)를 적용합니다.
4. **Security:** 비밀번호는 반드시 BCrypt로 암호화하며, JWT는 Access Token 구조로 구현합니다.
5. **Clean Code:** 명확한 변수명/메서드명을 사용하고, 비즈니스 로직이 복잡할 경우 주석을 간결하게 추가합니다.