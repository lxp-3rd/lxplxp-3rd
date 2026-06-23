# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## 프로젝트 개요

**LXP (Learning eXperience Platform)** — 강사가 강좌를 만들고 수강생이 수강·학습하는 온라인 교육 플랫폼.

- 수강생은 강좌를 검색하고 수강 신청 후 콘텐츠를 학습하며, 강사에게 질문할 수 있다.
- 강사는 강좌를 생성·편집하고 수강생 질문에 답변한다.
- 관리자는 회원·강좌·공지사항·로드맵을 관리한다.

**기술 스택:**
- 백엔드: Java 17, Spring Boot 3.5, JPA/Hibernate, MySQL (테스트: H2), Lombok, Spring Security (crypto)
- 프론트엔드: Next.js 14 (App Router), TypeScript, Tailwind CSS

**구현된 도메인:**

| 도메인 | 패키지 | 설명 |
|--------|--------|------|
| `auth` | `auth` | 로그인·회원가입·JWT |
| `member` | `member` | 회원 정보·프로필 |
| `instructor` | `instructor` | 강사 신청·프로필 |
| `course` | `course` | 강좌 CRUD·수강신청·콘텐츠 |
| `enrollment` | `enrollment` | 수강 이력 |
| `qna` | `qna` | 수강생 질문 CRUD |
| `announcement` | `announcement` | 공지사항 |
| `roadmap` | `roadmap` | 학습 로드맵 |

---

## Commands

**Backend (repo root)**
```bash
./gradlew bootRun          # 로컬 서버 실행 (port 8080)
./gradlew test             # 전체 테스트
./gradlew test --tests "com.ohgiraffers.lxp.qna.domain.model.entity.QuestionTest"  # 단일 테스트 클래스
./gradlew build            # 컴파일 + 테스트 + 패키징
```

**Frontend (`next-ui/` 기준)**
```bash
npm run dev    # 개발 서버
npm run lint   # ESLint 검사
npm run build  # 프로덕션 빌드
```

백엔드 API URL은 `NEXT_PUBLIC_API_URL` 환경변수로 지정한다. 없으면 `http://localhost:8080`으로 fallback.

---

## Backend Architecture

헥사고날 아키텍처. 각 도메인은 `src/main/java/com/ohgiraffers/lxp/{domain}/` 하위에 위치.

```
{domain}/
├── domain/model/entity/     # 순수 Java 객체. 프레임워크 의존성 없음
├── application/
│   ├── port/in/             # UseCase 인터페이스
│   ├── port/out/            # Repository 포트 인터페이스
│   ├── port/command/        # 유스케이스 입력 커맨드 객체
│   ├── dto/                 # 유스케이스 반환 결과 (XxxResult)
│   └── service/             # UseCase 구현체
├── infrastructure/
│   └── persistence/jpa/     # JpaEntity + PersistenceAdapter (포트 구현)
└── presentation/
    ├── web/                 # @RestController
    └── dto/                 # Request / Response DTO
```

**핵심 패턴들:**
- 도메인 객체는 정적 팩토리 메서드 `Entity.create(...)` 로 생성. 생성자 직접 호출 금지
- JpaEntity는 `from(domain)` / `toDomain()` 변환 메서드를 직접 보유
- 소프트 삭제: `deletedAt` 필드. 조회 쿼리에는 반드시 `deletedAtIsNull` 조건 포함
- 도메인 검증 실패 → `BusinessException(ErrorCode.XXX)` throw. `ErrorCode`는 `global/exception/ErrorCode.java` 에서 관리
- 크로스 도메인 의존은 out 포트(예: `CourseQueryPort`) 를 통해 간접 참조. 직접 import 금지

**전역 공통 클래스:**
- `global/exception/BusinessException` + `GlobalExceptionHandler` — 모든 도메인 예외 처리
- `global/entity/BaseEntity` — `createdAt`, `updatedAt` JPA 자동 관리

---

## Frontend Architecture

Next.js App Router. 도메인별로 라우트 폴더를 그대로 사용.

```
app/{domain}/
├── page.tsx        # 화면 진입점 (컴포넌트 조합만, 로직 없음)
├── components/     # 해당 화면 전용 컴포넌트
├── api.ts          # 백엔드 호출 함수 — fetcher 경유
├── types.ts        # 요청/응답 타입
└── mockData.ts     # 개발용 목 데이터 (실제 API 연동 전 임시)
```

**HTTP 클라이언트:** `next-ui/lib/fetcher.ts` — `fetcher.get<T>()`, `fetcher.post<T>()` 등 네이티브 fetch 래퍼. 컴포넌트에서 직접 `fetch` 호출 금지. 반드시 `api.ts` 를 경유.

**레이아웃:**
- 일반 페이지: `TopNavBar` + `Footer` 직접 포함
- 관리자 페이지 (`app/admin/**`): `app/admin/layout.tsx` 가 `AdminNavBar` 자동 포함

---

## 백엔드 개발 규칙

- 기능 개발 시 다른 도메인이 구현되어있지 않으면 infrastructure layer의 adapter에서 stub 객체를 만들어 진행한다.

---

## 테스트 컨벤션

계층별로 테스트 방식이 고정되어 있다.

**도메인 엔티티 (`domain/model/entity/`)**
```java
// 순수 Java 단위 테스트. 프레임워크 의존 없음
class QuestionTest {
    @Test void createQuestion_success() { ... }
}
```

**서비스 (`application/service/`)**
```java
@ExtendWith(MockitoExtension.class)
class QuestionServiceTest {
    @Mock QuestionRepositoryPort questionRepositoryPort;
    @InjectMocks QuestionService questionService;
}
```

**컨트롤러 (`presentation/web/`)**
```java
@WebMvcTest(QuestionController.class)
class QuestionControllerTest {
    @Autowired MockMvc mockMvc;
    @MockitoBean QuestionUseCase questionUseCase;
}
```

**예외 검증 패턴** (`.extracting` 금지, `.hasMessage` 사용):
```java
assertThatThrownBy(() -> ...)
    .isInstanceOf(BusinessException.class)
    .hasMessage(ErrorCode.QUESTION_NOT_FOUND.getMessage());
```

**테스트 실행:**
```bash
./gradlew test --tests "com.ohgiraffers.lxp.qna.*"   # 도메인 단위
./gradlew test                                         # 전체
```

---

## Development Workflow

기능 개발 시 `/workflow {기능 설명}` 커맨드로 시작한다. 상세 내용은 `AGENTS.md` 및 `.claude/commands/workflow.md` 참조.

---

## 참고 자료

| 파일 | 설명 |
|------|------|
| [`docs/ARCHITECTURE.md`](docs/ARCHITECTURE.md) | 백엔드 헥사고날 아키텍처 구조, 도메인 목록, 프론트엔드 폴더 구조 및 공통 컴포넌트 목록 |
| [`docs/API.md`](docs/API.md) | 백엔드 REST API 엔드포인트 목록, 요청/응답 형식, 프론트엔드 `api.ts` 작성 컨벤션 |
| [`docs/GIT.md`](docs/GIT.md) | 브랜치 네이밍 규칙, 커밋 메시지 형식, 이슈 생성 방법, PR 작성 가이드 |
| [`docs/ui-guide.md`](docs/ui-guide.md) | 프론트엔드 구조 결정 사항 (헥사고날 미적용 이유, 도메인 폴더 전략, API 호출 규칙) 및 팀 합의 내용 |
| [`docs/topics/fe-screen-dev.md`](docs/topics/fe-screen-dev.md) | 전체 화면 목록 27개와 팀원별 담당 도메인 배정표, 디자인 시스템 토큰, 공통 컴포넌트 개발 기록 |
