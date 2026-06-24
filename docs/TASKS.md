# API 연동 태스크

각 체크박스는 하나의 API 연동 작업을 나타낸다. 연동 완료 시 `[ ]` → `[x]` 로 변경.

> **범례**
> - 🔓 인증 불필요 (public)
> - 🔐 JWT 필요 (로그인 상태)
> - 🔑 JWT 필요 (특정 역할: 강사 / 관리자)

---

## 인증 (Auth)

**`(auth)/login/page.tsx`**
- [ ] 🔓 `POST /api/auth/login` — 로그인 후 토큰 저장

**`(auth)/register/page.tsx`**
- [ ] 🔓 `POST /api/auth/signup` — 회원가입

---

## 강좌 (Course)

**`courses/page.tsx`** — 강좌 목록
- [x] 🔓 `GET /api/courses` — 전체 강좌 목록 조회 (MOCK_COURSES 교체)

**`courses/[id]/page.tsx`** — 강좌 상세
- [x] 🔓 `GET /api/courses/{id}` — 강좌 정보 (enrolled 여부, 커리큘럼 포함)
- [ ] 🔓 `GET /api/instructors/{instructorId}` — 강사 정보 (백엔드 응답에 강사 정보 없어 미연동)
- [ ] 🔓 `GET /api/questions?courseId={id}` — Q&A 미리보기 (CourseQnAPreview, 현재 mock)
- [ ] 🔐 `POST /api/enrollments` — 수강 신청 버튼

**`courses/new/page.tsx`** — 강좌 생성
- [ ] 🔑강사 `POST /api/courses` — 강좌 생성

**`courses/manage/page.tsx`** — 내 강좌 목록 (강사)
- [ ] 🔑강사 `GET /api/courses?instructorId={me}` — 내 강좌 조회 (getMyCourses mock 교체)

**`courses/[id]/edit/page.tsx`** — 강좌 수정
- [ ] 🔑강사 `GET /api/courses/{id}` — 현재 강좌 정보 로드
- [ ] 🔑강사 `PUT /api/courses/{id}` — 수정 저장

**`courses/[id]/stats/page.tsx`** — 강좌 통계
- [ ] 🔑강사 `GET /api/courses/{id}` — 기본 정보
- [ ] 🔑강사 `GET /api/enrollments?courseId={id}` — 수강생 통계

**`courses/[id]/contents/[contentId]/page.tsx`** — 콘텐츠 플레이어
- [ ] 🔐 `GET /api/courses/{id}/contents/{contentId}` — 콘텐츠 정보
- [ ] 🔐 `POST /api/courses/{id}/contents/{contentId}/complete` — 수강 완료 처리

---

## Q&A (Question)

**`courses/[id]/questions/page.tsx`** — 강좌 Q&A 목록
- [x] 🔐 `GET /api/questions?courseId={id}` — 질문 목록

**`courses/[id]/questions/[qid]/page.tsx`** — 질문 상세 / 수정 / 삭제
- [x] 🔓 `GET /api/questions/{id}` — 질문 조회
- [x] 🔐 `PUT /api/questions/{id}` — 질문 수정
- [x] 🔐 `DELETE /api/questions/{id}` — 질문 삭제

**`courses/[id]/questions/new/page.tsx`** — 질문 작성
- [x] 🔐 `POST /api/questions` — 질문 등록
- [x] 🔐 memberId를 하드코딩(1) → `/api/members/me` 조회값으로 교체

**`courses/[id]/questions/instructor/page.tsx`** — 강사 답변 관리
- [x] 🔑강사 `GET /api/questions?courseId={id}` — 질문 목록 (mock 교체)
- [x] 🔑강사 `POST /api/questions/{id}/answers` — 답변 등록

**`questions/page.tsx`** — 전체 Q&A 탐색
- [x] 🔓 `GET /api/questions` — 전체 질문 목록 (mock 교체)

**`questions/[id]/page.tsx`** — 질문 상세 (커뮤니티 뷰)
- [x] 🔓 `GET /api/questions/{id}` — 질문 + 답변 조회 (mock 교체)

---

## 수강 (Enrollment)

**`enrollments/page.tsx`** — 내 수강 목록
- [ ] 🔐 `GET /api/enrollments` — 내 수강 목록 (MOCK_ENROLLMENTS 교체)

**`enrollments/[id]/page.tsx`** — 수강 상세 / 커리큘럼
- [ ] 🔐 `GET /api/enrollments/{id}` — 수강 정보
- [ ] 🔐 `GET /api/courses/{courseId}/contents` — 커리큘럼 목록
- [ ] 🔐 `POST /api/enrollments/{id}/progress` — 학습 진도 업데이트

---

## 강사 (Instructor)

**`instructors/page.tsx`** — 강사 목록
- [ ] 🔓 `GET /api/instructors` — 강사 목록 (MOCK_INSTRUCTORS 교체)

**`instructors/[id]/page.tsx`** — 강사 공개 프로필
- [ ] 🔓 `GET /api/instructors/{id}` — 강사 상세
- [ ] 🔓 `GET /api/courses?instructorId={id}` — 강사 강좌 목록

**`instructors/[id]/profile/page.tsx`** — 강사 프로필 수정
- [ ] 🔑강사 `GET /api/instructors/{id}` — 현재 프로필 로드
- [ ] 🔑강사 `PATCH /api/instructors/{id}` — 프로필 저장

**`instructor-applications/page.tsx`** — 강사 신청
- [ ] 🔐 `GET /api/instructor-applications/me` — 내 신청 상태 조회
- [ ] 🔐 `POST /api/instructor-applications` — 강사 신청 제출

**`instructor/stats/page.tsx`** — 강사 대시보드
- [ ] 🔑강사 `GET /api/courses?instructorId={me}` — 내 강좌 목록 (mock 교체)
- [ ] 🔑강사 `GET /api/questions?courseId={id}` — 강좌별 Q&A 수 (mock 교체)

---

## 마이페이지 (My Page)

**`my-page/page.tsx`** — 내 프로필
- [x] 🔐 `GET /api/members/me` — 프로필 조회
- [x] 🔐 `PATCH /api/members/me` — 프로필 수정
- [x] 🔐 `PATCH /api/members/me/password` — 비밀번호 변경
- [x] 🔐 `DELETE /api/members/me` — 회원 탈퇴

---

## 공지사항 (Announcement)

**`announcements/page.tsx`** — 공지사항 목록
- [ ] 🔓 `GET /api/announcements` — 공지사항 목록 (MOCK_ANNOUNCEMENTS 교체)

**`announcements/[id]/page.tsx`** — 공지사항 상세
- [ ] 🔓 `GET /api/announcements/{id}` — 공지사항 상세

---

## 로드맵 (Roadmap)

**`roadmaps/page.tsx`** — 로드맵 목록
- [ ] 🔓 `GET /api/roadmaps` — 로드맵 목록 (MOCK_ROADMAPS 교체)

**`roadmaps/[id]/page.tsx`** — 로드맵 상세
- [ ] 🔓 `GET /api/roadmaps/{id}` — 로드맵 상세
- [ ] 🔐 `POST /api/roadmaps/{id}/enroll` — 로드맵 수강 신청

**`roadmaps/new/page.tsx`** — 로드맵 생성
- [ ] 🔑관리자 `POST /api/roadmaps` — 로드맵 생성
- [ ] 🔑관리자 `GET /api/courses` — 강좌 선택 목록

**`roadmaps/[id]/edit/page.tsx`** — 로드맵 수정
- [ ] 🔑관리자 `GET /api/roadmaps/{id}` — 현재 정보 로드
- [ ] 🔑관리자 `PUT /api/roadmaps/{id}` — 수정 저장

---

## 관리자 (Admin)

**`admin/page.tsx`** — 관리자 대시보드
- [ ] 🔑관리자 `GET /api/admin/stats` — 요약 통계 (회원·강사·강좌·수강신청 수)
- [ ] 🔑관리자 `GET /api/instructor-applications?status=PENDING` — 대기 중 신청 수

**`admin/courses/page.tsx`** — 강좌 관리
- [ ] 🔑관리자 `GET /api/courses` — 전체 강좌 목록 (mock 교체)
- [ ] 🔑관리자 `PATCH /api/courses/{id}` — 공개/비공개 전환
- [ ] 🔑관리자 `DELETE /api/courses/{id}` — 강좌 삭제

**`admin/announcements/page.tsx`** — 공지사항 관리
- [ ] 🔑관리자 `GET /api/announcements` — 목록 (mock 교체)
- [ ] 🔑관리자 `DELETE /api/announcements/{id}` — 삭제

**`admin/announcements/new/page.tsx`** — 공지사항 작성
- [ ] 🔑관리자 `POST /api/announcements` — 공지사항 생성

**`admin/announcements/[id]/edit/page.tsx`** — 공지사항 수정
- [ ] 🔑관리자 `GET /api/announcements/{id}` — 현재 내용 로드
- [ ] 🔑관리자 `PUT /api/announcements/{id}` — 수정 저장

**`admin/instructors/page.tsx`** — 강사 관리
- [ ] 🔑관리자 `GET /api/instructors` — 강사 목록 (mock 교체)

**`admin/instructors/[id]/page.tsx`** — 강사 상세
- [ ] 🔑관리자 `GET /api/instructors/{id}` — 강사 정보
- [ ] 🔑관리자 `GET /api/courses?instructorId={id}` — 담당 강좌
- [ ] 🔑관리자 `PATCH /api/instructors/{id}/status` — 계정 활성/정지

**`admin/instructor-applications/page.tsx`** — 강사 신청 목록
- [ ] 🔑관리자 `GET /api/instructor-applications` — 신청 목록 (필터: status)

**`instructor-applications/[id]/page.tsx`** — 강사 신청 심사
- [ ] 🔑관리자 `GET /api/instructor-applications/{id}` — 신청 상세
- [ ] 🔑관리자 `PATCH /api/instructor-applications/{id}` — 승인 / 반려

**`admin/members/page.tsx`** — 회원 관리
- [ ] 🔑관리자 `GET /api/admin/members` — 회원 목록

**`admin/members/[id]/page.tsx`** — 회원 상세
- [ ] 🔑관리자 `GET /api/admin/members/{id}` — 회원 정보
- [ ] 🔑관리자 `GET /api/enrollments?memberId={id}` — 수강 내역
- [ ] 🔑관리자 `PATCH /api/admin/members/{id}/status` — 계정 상태 변경

**`admin/roadmaps/page.tsx`** — 로드맵 관리
- [ ] 🔑관리자 `GET /api/roadmaps` — 로드맵 목록

**`admin/courses/[id]/enrollments/page.tsx`** — 강좌 수강생 목록
- [ ] 🔑관리자 `GET /api/enrollments?courseId={id}` — 수강생 목록
