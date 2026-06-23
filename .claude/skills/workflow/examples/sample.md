# 워크플로우 실행 예시: 수강생 질문 CRUD

실제로 진행된 `/workflow 수강생 질문 crud` 실행 결과.

---

## 1단계 결과

**확정 요구사항:**
- 수강생이 강좌별로 질문을 등록/조회/수정/삭제할 수 있다
- 작성자만 수정·삭제 가능
- 제목 2~200자, 내용 최대 500자 제한
- 백엔드 + 프론트엔드 모두 구현

**연결 이슈:** #38 `[FEAT] 수강생 질문 CRUD`

**생성 브랜치:** `feat/#38-student-question-crud`

---

## 2단계 결과 (계획 요약)

**백엔드 변경 파일 (신규):**
```
src/main/java/com/ohgiraffers/lxp/qna/
├── domain/model/entity/Question.java
├── domain/model/entity/QuestionStatus.java
├── application/port/in/QuestionUseCase.java
├── application/port/out/QuestionRepositoryPort.java
├── application/port/out/CourseQueryPort.java
├── application/port/out/EnrollmentQueryPort.java
├── application/port/command/CreateQuestionCommand.java
├── application/port/command/UpdateQuestionCommand.java
├── application/dto/QuestionResult.java
├── application/service/QuestionService.java
├── infrastructure/persistence/jpa/QuestionJpaEntity.java
├── infrastructure/persistence/jpa/QuestionJpaRepository.java
├── infrastructure/persistence/jpa/QuestionPersistenceAdapter.java
└── presentation/web/QuestionController.java
    └── dto/CreateQuestionRequest.java / UpdateQuestionRequest.java / QuestionResponse.java
```

**프론트엔드 변경 파일 (신규):**
```
next-ui/app/courses/[id]/questions/
├── api.ts
├── types.ts
├── page.tsx           (목록)
├── new/page.tsx       (작성)
└── [qid]/page.tsx     (상세/수정/삭제)
next-ui/lib/formatDate.ts
```

**API 계약:**
```
GET    /questions?courseId={id}   강좌별 질문 목록
GET    /questions/{id}            질문 단건
POST   /questions                 질문 등록
PUT    /questions/{id}            질문 수정 (작성자)
DELETE /questions/{id}?memberId=  질문 삭제 (작성자)
```

---

## 3단계 결과

**백엔드 테스트:** `./gradlew test --tests "com.ohgiraffers.lxp.qna.*"` → BUILD SUCCESSFUL

**프론트엔드 lint:** `npm run lint` → 수정한 파일에서 에러 없음

---

## 4단계 결과 (코드 리뷰 주요 지적)

| # | 파일 | 지적 내용 | 처리 |
|---|------|-----------|------|
| 1 | `QuestionController` | memberId를 쿼리 파라미터로 받아 IDOR 취약점 | 이슈 #39로 분리 (auth 미구현) |
| 2 | `Question.create()` | `Objects.requireNonNull` 대신 `BusinessException` 던져야 함 | 수정 완료 |
| 3 | `GlobalExceptionHandler` | `MissingServletRequestParameterException` 핸들러 없음 | 추가 완료 |

---

## 5단계 결과

**백엔드 PR:** [#40 feat: [BE] 수강생 질문 CRUD 구현 (#38)](https://github.com/lxp-3rd/lxplxp-3rd/pull/40)

**프론트엔드 PR:** [#41 feat: [FE] 수강생 질문 CRUD 프론트엔드 연동 (#38)](https://github.com/lxp-3rd/lxplxp-3rd/pull/41)

---

## 6단계 결과

**업데이트된 문서:**
- `docs/API.md` — Q&A 엔드포인트 섹션 추가
- `docs/ARCHITECTURE.md` — `formatDate.ts` 유틸 항목 추가
