# Git 컨벤션

## 브랜치 전략

### 브랜치 이름 형식
```
{type}/#{issue-number}-{brief-description}
```

| type | 용도 |
|------|------|
| `feat` | 신규 기능 |
| `fix` | 버그 수정 |
| `refactor` | 리팩토링 |
| `chore` | 빌드/설정/의존성 |
| `docs` | 문서 수정 |
| `test` | 테스트 추가/수정 |

**예시:**
```
feat/#33-announcement-list
fix/#40-navigation-redirect
refactor/#12-auth-handler
```

---

## 이슈

### 이슈 제목 형식
```
[TYPE] 기능/버그에 대한 간단한 설명
```
예: `[FEAT] 공지사항 전체 목록 및 상세 조회 기능 구현`

### 이슈 생성
```bash
gh issue create --title "[FEAT] 기능 설명" --label "enhancement" --template "✨-feature.md"
```

### 이슈 번호 확인
```bash
gh issue list --search "키워드"
```

---

## 커밋 메시지

### Conventional Commits 형식
```
{type}: {설명} (#{issue-number})
```

| type | 용도 |
|------|------|
| `feat` | 신규 기능 추가 |
| `fix` | 버그 수정 |
| `refactor` | 동작 변화 없는 코드 개선 |
| `chore` | 빌드/설정/의존성 변경 |
| `docs` | 문서 수정 |
| `test` | 테스트 추가/수정 |
| `style` | 코드 포맷/들여쓰기 (로직 무관) |

**예시:**
```
feat: 공지사항 전체 목록 조회 기능 구현 (#33)
fix: 네비게이션 리다이렉트 오류 수정 (#40)
docs: API 문서 엔드포인트 추가
```

### 커밋 단위
- 하나의 커밋은 하나의 논리적 변경 단위
- 백엔드/프론트엔드 변경이 동시에 필요하면 각각 커밋
- 도메인별로 커밋 분리 권장

---

## Pull Request

### PR 제목 형식
```
feat: 기능 설명 (#{issue-number})
```

### PR 생성
```bash
gh pr create \
  --title "feat: 기능 설명 (#33)" \
  --body "$(cat .github/PULL_REQUEST_TEMPLATE.md)" \
  --base main
```

### PR 본문 필수 항목 (`.github/PULL_REQUEST_TEMPLATE.md` 기준)
- `Closes #{issue-number}` 로 이슈 자동 연결
- 주요 변경 파일 목록
- 작업 내용 설명
- 체크리스트 완료

### 머지 전 체크리스트
- [ ] 코드 리뷰 완료
- [ ] 테스트 통과 (`./gradlew test`)
- [ ] 프론트엔드 lint 통과 (`npm run lint`)
- [ ] 불필요한 console.log / 디버그 코드 제거
- [ ] 관련 문서 업데이트 필요 여부 확인