# API 컨벤션

## 기본 설정

- Base URL: `http://localhost:8080` (로컬 개발)
- 환경변수: `NEXT_PUBLIC_API_URL`
- 인증: JWT Bearer Token (`Authorization: Bearer {token}`)
- Content-Type: `application/json`

---

## URL 구조

```
/api/{domain}/{resource}
```

| HTTP 메서드 | 패턴 | 설명 |
|-------------|------|------|
| `GET` | `/api/courses` | 목록 조회 |
| `GET` | `/api/courses/{id}` | 단건 조회 |
| `POST` | `/api/courses` | 생성 |
| `PUT` | `/api/courses/{id}` | 전체 수정 |
| `PATCH` | `/api/courses/{id}` | 부분 수정 |
| `DELETE` | `/api/courses/{id}` | 삭제 |

---

## 응답 형식

### 성공 (단건)
```json
{
  "id": 1,
  "title": "Java 기초",
  "createdAt": "2026-06-23T00:00:00"
}
```

### 성공 (목록)
```json
[
  { "id": 1, "title": "Java 기초" },
  { "id": 2, "title": "Spring 심화" }
]
```

### 에러
```json
{
  "code": "INVALID_INPUT",
  "message": "잘못된 입력값입니다."
}
```

- `code`: `ErrorCode` enum 이름 (예: `INVALID_INPUT`, `COURSE_NOT_FOUND`, `ENROLLMENT_NOT_FOUND`)
- `message`: 사람이 읽을 수 있는 한국어 메시지
- HTTP 상태 코드는 응답 헤더에 담긴다 (body에 `status` 필드 없음)

주요 `ErrorCode` 값:

| code | HTTP | 설명 |
|------|------|------|
| `INVALID_INPUT` | 400 | 유효성 검사 실패, 필수 파라미터 누락 |
| `UNAUTHORIZED` | 401 | 인증 필요 |
| `FORBIDDEN` | 403 | 권한 없음 |
| `ENTITY_NOT_FOUND` | 404 | 공통 조회 실패 |
| `COURSE_NOT_FOUND` | 404 | 강좌 없음 |
| `ENROLLMENT_NOT_FOUND` | 404 | 수강 내역 없음 |
| `QUESTION_NOT_FOUND` | 404 | 질문 없음 |
| `ENROLLMENT_ALREADY_EXISTS` | 409 | 이미 수강 중 |
| `INTERNAL_SERVER_ERROR` | 500 | 서버 오류 |

---

## 프론트엔드 API 호출 규칙

### api.ts 구조
```typescript
// next-ui/app/{domain}/api.ts
import { fetcher } from '@/lib/fetcher';
import type { CourseResponse, CreateCourseRequest } from './types';

export const courseApi = {
  getAll: () => fetcher.get<CourseResponse[]>('/api/courses'),
  getById: (id: number) => fetcher.get<CourseResponse>(`/api/courses/${id}`),
  create: (data: CreateCourseRequest) => fetcher.post<CourseResponse>('/api/courses', data),
  update: (id: number, data: Partial<CreateCourseRequest>) =>
    fetcher.put<CourseResponse>(`/api/courses/${id}`, data),
  delete: (id: number) => fetcher.delete(`/api/courses/${id}`),
};
```

### types.ts 구조
```typescript
// next-ui/app/{domain}/types.ts
export interface CourseResponse {
  id: number;
  title: string;
  description: string;
  instructorId: number;
  createdAt: string;
}

export interface CreateCourseRequest {
  title: string;
  description: string;
}
```

### 호출 방법 (컴포넌트)
```typescript
// ✅ 올바른 방법 — api.ts를 통해 호출
import { courseApi } from './api';
const courses = await courseApi.getAll();

// ❌ 금지 — 컴포넌트에서 직접 fetch
const res = await fetch('/api/courses');
```

---

## 백엔드 엔드포인트 목록

### 인증 (`/api/auth`)
| 메서드 | URL | 설명 | 인증 |
|--------|-----|------|------|
| POST | `/api/auth/login` | 로그인 | 불필요 |
| POST | `/api/auth/register` | 회원가입 | 불필요 |
| POST | `/api/auth/refresh` | 토큰 갱신 | 불필요 |
| POST | `/api/auth/logout` | 로그아웃 | 필요 |

### 회원 (`/api/members`)
| 메서드 | URL | 설명 | 인증 |
|--------|-----|------|------|
| GET | `/api/members/me` | 내 정보 조회 | 필요 |
| PUT | `/api/members/me` | 내 정보 수정 | 필요 |

### 강좌 (`/api/courses`)
| 메서드 | URL | 설명 | 인증 |
|--------|-----|------|------|
| GET | `/api/courses` | 전체 강좌 목록 | 불필요 |
| GET | `/api/courses/{id}` | 강좌 상세 | 불필요 |
| POST | `/api/courses` | 강좌 생성 | 강사 |
| PUT | `/api/courses/{id}` | 강좌 수정 | 강사(소유자) |
| DELETE | `/api/courses/{id}` | 강좌 삭제 | 강사(소유자) |

### 수강 (`/api/enrollments`)
| 메서드 | URL | 설명 | 인증 |
|--------|-----|------|------|
| GET | `/api/enrollments` | 내 수강 목록 | 필요 |
| POST | `/api/enrollments` | 수강 신청 | 필요 |
| DELETE | `/api/enrollments/{id}` | 수강 취소 | 필요 |

### 로드맵 (`/api/roadmaps`)
| 메서드 | URL | 설명 | 인증 |
|--------|-----|------|------|
| GET | `/api/roadmaps` | 로드맵 목록 | 불필요 |
| GET | `/api/roadmaps/{id}` | 로드맵 상세 | 불필요 |
| POST | `/api/roadmaps` | 로드맵 생성 | 관리자 |
| PUT | `/api/roadmaps/{id}` | 로드맵 수정 | 관리자 |
| DELETE | `/api/roadmaps/{id}` | 로드맵 삭제 | 관리자 |

### Q&A (`/api/questions`)
| 메서드 | URL | 설명 | 인증 |
|--------|-----|------|------|
| GET | `/api/questions?courseId={courseId}` | 강좌별 질문 목록 | 불필요 |
| GET | `/api/questions/{id}` | 질문 상세 | 불필요 |
| POST | `/api/questions` | 질문 등록 | 필요 |
| PUT | `/api/questions/{id}` | 질문 수정 | 작성자 |
| DELETE | `/api/questions/{id}?memberId={memberId}` | 질문 삭제 | 작성자 |

**Request / Response**

```
POST /api/questions
{
  "courseId": 1,       // Long, 필수
  "memberId": 1,       // Long, 필수 — TODO: 인증 구현 후 JWT 추출로 교체 (#39)
  "title": "string",   // 2~200자
  "content": "string"  // 최대 500자
}

PUT /api/questions/{id}
{
  "memberId": 1,       // Long, 필수 — TODO: 인증 구현 후 JWT 추출로 교체 (#39)
  "title": "string",   // 2~200자
  "content": "string"  // 최대 500자
}

Response (단건)
{
  "id": 1,
  "courseId": 1,
  "memberId": 1,
  "title": "string",
  "content": "string",
  "status": "PUBLISHED",   // enum: PUBLISHED | HIDDEN
  "createdAt": "2026-06-23T00:00:00",
  "updatedAt": "2026-06-23T00:00:00"
}
```

### 공지사항 (`/api/announcements`)
| 메서드 | URL | 설명 | 인증 |
|--------|-----|------|------|
| GET | `/api/announcements` | 공지사항 목록 | 불필요 |
| GET | `/api/announcements/{id}` | 공지사항 상세 | 불필요 |
| POST | `/api/announcements` | 공지사항 작성 | 관리자 |
| PUT | `/api/announcements/{id}` | 공지사항 수정 | 관리자 |
| DELETE | `/api/announcements/{id}` | 공지사항 삭제 | 관리자 |

---

> 새 엔드포인트 추가 시 이 파일에 반드시 업데이트할 것.