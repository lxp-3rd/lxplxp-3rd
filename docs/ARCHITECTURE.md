# 아키텍처

## 프로젝트 구성

Spring Boot 백엔드 + Next.js 프론트엔드 모노레포.

```
lxplxp-3rd/
├── src/                    # 백엔드 (Spring Boot 3, Java 17)
├── next-ui/                # 프론트엔드 (Next.js 14, TypeScript)
└── docs/                   # 프로젝트 문서
```

---

## 백엔드

### 기술 스택
- Java 17, Spring Boot 3
- JPA (Hibernate), MySQL
- Spring Security + JWT

### 헥사고날 아키텍처

각 도메인은 `src/main/java/com/ohgiraffers/lxp/{domain}/` 하위에 위치.

```
{domain}/
├── domain/              # 핵심 비즈니스 모델 (Entity, Value Object)
│   └── model/
├── application/         # 유스케이스 조율
│   ├── port/
│   │   ├── in/          # 인바운드 포트 (UseCase 인터페이스)
│   │   └── out/         # 아웃바운드 포트 (Repository 인터페이스)
│   └── service/         # 유스케이스 구현체
├── infrastructure/      # 외부 시스템 연동
│   ├── persistence/     # JPA 어댑터, Repository 구현
│   └── security/        # 보안 관련 어댑터
└── presentation/        # 인바운드 어댑터
    └── web/             # REST 컨트롤러, DTO
```

### 도메인 목록
| 도메인 | 설명 |
|--------|------|
| `auth` | 인증/인가 (JWT, 로그인, 회원가입) |
| `member` | 회원 정보 관리 |
| `course` | 강좌 생성/편집/수강신청 |
| `roadmap` | 학습 로드맵 |
| `qna` | Q&A 게시판 |
| `announcement` | 공지사항 |
| `enrollment` | 수강 이력 |

### 의존성 방향
```
presentation → application (port/in) → domain
infrastructure → application (port/out) → domain
```
도메인은 외부 의존성 없음. 프레임워크/JPA 어노테이션은 infrastructure 레이어까지만.

### 네이밍 컨벤션
- 클래스: `PascalCase`
- 메서드/필드: `camelCase`
- 패키지: `lowercase`
- DTO: `CreateQuestionRequest`, `QuestionResponse` (동사+명사+역할)
- UseCase: `CreateQuestionUseCase`, `GetQuestionUseCase`
- Service: `QuestionService`

---

## 프론트엔드

### 기술 스택
- Next.js 14 (App Router), TypeScript
- Tailwind CSS (디자인 토큰 커스터마이징)
- `@/` alias (`next-ui/` 기준)

### 폴더 구조

```
next-ui/
├── app/
│   ├── (auth)/          # 로그인, 회원가입 (레이아웃 분리)
│   ├── admin/           # 관리자 페이지
│   │   └── layout.tsx   # AdminNavBar 자동 포함
│   ├── {domain}/        # 도메인별 라우트 폴더
│   │   ├── page.tsx     # 화면 진입점 (컴포넌트 조합만)
│   │   ├── components/  # 해당 화면 전용 컴포넌트
│   │   ├── api.ts       # 백엔드 API 호출 함수
│   │   └── types.ts     # 요청/응답 타입 정의
│   └── layout.tsx       # 루트 레이아웃
├── components/          # 도메인 공통 UI 컴포넌트
│   └── ui/              # 버튼, 인풋 등 기초 컴포넌트
└── lib/
    ├── fetcher.ts       # 네이티브 fetch 래퍼 (get/post/put/delete)
    └── formatDate.ts    # 날짜 포매팅 유틸 (ko-KR 로케일)
```

### 핵심 규칙
- **컴포넌트에서 직접 `fetch` 금지** → 반드시 같은 도메인의 `api.ts`를 통해 호출
- 클라이언트 로직이 복잡한 경우에만 `logic.ts` 파일 추가 분리

### 공통 컴포넌트
| 파일 | 설명 |
|------|------|
| `components/TopNavBar.tsx` | 일반 사용자 상단 헤더 |
| `components/Footer.tsx` | 사이트 푸터 |
| `components/admin/AdminNavBar.tsx` | 관리자 상단 헤더 |
| `components/ui/Button.tsx` | `primary / secondary / danger / outline / ghost` |
| `components/ui/Input.tsx` | label, error, icon 지원 |
| `components/ui/ProgressBar.tsx` | 진도 바 |
| `components/ui/Badge.tsx` | 상태 태그 |

### 자주 쓰는 Tailwind 클래스
- 컨테이너: `page-container` (max-w + px-gutter)
- 헤더 여백: `page-top` (pt-16)
- 카드: `bg-surface-container-lowest border border-outline-variant rounded-lg p-md`

---

## 팀 개발 방식
- 팀원별로 도메인을 배정해 FE + BE를 함께 담당 (도메인 단위 풀스택)
- 담당 도메인 목록: `docs/topics/fe-screen-dev.md` 참고