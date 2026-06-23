# 워크플로우 템플릿

## PR Body 템플릿

5단계에서 `gh pr create --body "..."` 에 사용한다. 실제 내용으로 채워서 사용한다.

### 백엔드 PR

```
## 🔗 관련 이슈
- Closes #{이슈번호}

### 📂 주요 변경 파일
- src/main/java/com/ohgiraffers/lxp/{domain}/domain/model/entity/
- src/main/java/com/ohgiraffers/lxp/{domain}/application/
- src/main/java/com/ohgiraffers/lxp/{domain}/infrastructure/
- src/main/java/com/ohgiraffers/lxp/{domain}/presentation/

## ✨ 작업 내용
- 

## 🤔 고민한 부분 (선택)
- 

## ✅ 체크리스트
- [x] 코드 리뷰 완료
- [x] 커밋 메시지 컨벤션 준수
- [x] 불필요한 코드/로그 제거
- [ ] 문서 업데이트 (필요 시)
- [x] 테스트 코드 작성
```

### 프론트엔드 PR

```
## 🔗 관련 이슈
- Closes #{이슈번호}

### 📂 주요 변경 파일
- next-ui/app/{domain}/page.tsx
- next-ui/app/{domain}/api.ts
- next-ui/app/{domain}/types.ts
- next-ui/app/{domain}/components/

## ✨ 작업 내용
- 

## 🤔 고민한 부분 (선택)
- 

## ✅ 체크리스트
- [x] 코드 리뷰 완료
- [x] 커밋 메시지 컨벤션 준수
- [x] 불필요한 코드/로그 제거
- [ ] 문서 업데이트 (필요 시)
- [x] ESLint 통과
```

---

## 커밋 메시지 패턴

`docs/GIT.md` 의 Conventional Commits 형식을 따른다.

```
feat: {기능 설명} (#{이슈번호})
fix: {버그 설명} (#{이슈번호})
docs: {문서 설명}
test: {테스트 설명} (#{이슈번호})
chore: {빌드/설정 설명}
refactor: {리팩토링 설명} (#{이슈번호})
```

**예시:**
```
feat: 수강생 질문 CRUD 백엔드 구현 (#38)
feat: 수강생 질문 CRUD 프론트엔드 연동 (#38)
docs: Q&A API 엔드포인트 문서 업데이트
test: QuestionService 단위 테스트 추가 (#38)
```
