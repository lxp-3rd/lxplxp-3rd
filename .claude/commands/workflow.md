---
description: 새 기능 개발 시 6단계 워크플로우. 요구사항 구체화 → GitHub 이슈/브랜치 → 계획 → 구현(테스트 포함) → 코드 리뷰 → PR → 문서 업데이트 순으로 진행한다. 기능 개발을 시작할 때 /workflow [기능 설명] 으로 직접 호출한다.
argument-hint: [기능 설명]
disable-model-invocation: true
allowed-tools: Bash Read Edit Write Glob Grep
---

# /workflow

구현할 기능: **$ARGUMENTS**

## 현재 Git 상태
!`git status --short && echo "---" && git log --oneline -3`

아래 6단계 워크플로우를 순서대로 진행한다. 각 단계 완료 후 다음 단계로 넘어가기 전에 반드시 명확히 표시한다.

---

## 1단계: 기능 탐색 및 컨텍스트 확립

### 1-1. 요구사항 구체화
`$ARGUMENTS`를 분석해 구현 범위를 파악한다. 아래 항목이 불명확하면 사용자에게 질문한다:
- 기대 동작 및 화면 흐름
- 예외/에러 케이스
- 백엔드 / 프론트엔드 / 둘 다 필요 여부
- 연관된 도메인 및 의존성

요구사항이 충분히 구체화될 때까지 대화한다. 확인 없이 다음 단계로 넘어가지 않는다.

### 1-2. GitHub 이슈 확인 또는 생성
```bash
gh issue list --search "$ARGUMENTS 관련 키워드"
```

- 관련 이슈가 있으면: 이슈 번호와 제목 확인
- 없으면: feature 템플릿으로 이슈 생성
  ```bash
  gh issue create \
    --title "[FEAT] 기능 설명" \
    --label "enhancement" \
    --body "$(cat .github/ISSUE_TEMPLATE/✨-feature.md | sed '/^---$/,/^---$/d')"
  ```

### 1-3. 브랜치 생성 및 이동
`docs/GIT.md`의 브랜치 네이밍 규칙에 따라:
```bash
git checkout -b feat/#{이슈번호}-{brief-description}
```

### 1-4. 단계 완료 확인
사용자에게 요약 후 확인 요청 (확정 요구사항 / 이슈 번호 / 브랜치 이름).
사용자 확인 후 2단계로 진행한다.

---

## 2단계: 계획

### 2-1. 관련 문서 확인
```
docs/ARCHITECTURE.md  — 도메인 구조, 파일 위치
docs/API.md           — 기존 엔드포인트 및 컨벤션
```

### 2-2. 구현 계획 수립 (Plan Mode)
PLAN MODE로 전환해 아래 항목을 포함한 구현 계획을 작성한다:
- 생성/수정할 파일 목록 (백엔드 / 프론트엔드 구분)
- API 계약 (새 엔드포인트 또는 변경 사항)
- 헥사고날 레이어별 역할 분담 (백엔드)
- 프론트엔드 도메인 폴더 구조 (프론트엔드)
- 테스트 전략
- 예상 복잡도 및 리스크

사용자가 계획을 승인하면 3단계로 진행한다.

---

## 3단계: 구현

### 3-1. 코드 작성
2단계 계획을 그대로 구현한다.

**백엔드:**
- 도메인 → application → infrastructure → presentation 의존성 방향 준수
- UseCase 인터페이스 → Service 구현체 → Controller DTO 순으로 작성

**프론트엔드:**
- 도메인 폴더 하위 `api.ts` / `types.ts` / `components/` 구조 유지
- 컴포넌트에서 직접 fetch 금지 — `api.ts` 경유 필수

### 3-2. 테스트 실행 → 코드 리뷰 자동 진행
구현 완료 후 테스트를 작성하고 Bash로 직접 실행한다:

```bash
# 백엔드만 변경
./gradlew test 2>&1

# 프론트엔드만 변경
cd next-ui && npm run lint 2>&1

# 둘 다 변경: 백엔드 → 프론트엔드 순
./gradlew test 2>&1 && cd next-ui && npm run lint 2>&1
```

- **실패 시**: 원인 파악 후 수정, 통과할 때까지 반복
- **통과 시**: 사용자 입력 없이 즉시 4단계로 진행

---

## 4단계: 코드 검토

### 4-1. 코드 리뷰 (테스트 통과 후 자동 실행)
테스트 통과 시 사용자 확인 없이 바로 `/code-review`를 실행한다.

### 4-2. 사용자 확인
리뷰 결과를 제시하고 확인 요청:
- 수정 필요 → 3단계로 돌아가 수정 후 테스트 재실행 → 코드 리뷰 재실행
- 수정 불필요 → 5단계로 진행

---

## 5단계: 커밋 및 PR

> **규칙: 백엔드(`src/`)와 프론트엔드(`next-ui/`)는 반드시 분리한다.**
> 같은 커밋, 같은 PR에 올리지 않는다.

### 5-1. 변경 내역 확인
```bash
git diff --stat && git status
```

### 5-2. 백엔드 커밋 (변경이 있는 경우)
```bash
git add src/
git commit -m "feat: 백엔드 기능 설명 (#{이슈번호})"
```

### 5-3. 프론트엔드 커밋 (변경이 있는 경우)
```bash
git add next-ui/
git commit -m "feat: 프론트엔드 기능 설명 (#{이슈번호})"
```

### 5-4. PR 생성 — 백엔드·프론트엔드 각각 별도 브랜치·PR로 분리

백엔드와 프론트엔드 변경이 모두 있는 경우, 현재 브랜치에서 각각의 브랜치를 분기해 별도 PR을 생성한다:

```bash
# 백엔드 전용 브랜치 → PR
git checkout -b feat/#{이슈번호}-{description}-be
git push -u origin feat/#{이슈번호}-{description}-be
gh pr create --title "feat: [BE] 기능 설명 (#{이슈번호})" \
  --body "$(cat .github/PULL_REQUEST_TEMPLATE.md)"

# 프론트엔드 전용 브랜치 → PR
git checkout -b feat/#{이슈번호}-{description}-fe
git push -u origin feat/#{이슈번호}-{description}-fe
gh pr create --title "feat: [FE] 기능 설명 (#{이슈번호})" \
  --body "$(cat .github/PULL_REQUEST_TEMPLATE.md)"
```

PR 생성 후 `.github/PULL_REQUEST_TEMPLATE.md`의 각 항목을 실제 내용으로 채운다.

---

## 6단계: 문서 최신화

### 6-1. 변경 사항 점검

| 변경 내용 | 업데이트 대상 |
|-----------|--------------|
| 새 API 엔드포인트 추가/변경 | `docs/API.md` |
| 아키텍처 구조 변경 | `docs/ARCHITECTURE.md` |
| 새 공통 컴포넌트 추가 | `docs/ARCHITECTURE.md` |
| Git/브랜치 전략 변경 | `docs/GIT.md` |

### 6-2. 문서 수정 및 커밋
```bash
git add docs/
git commit -m "docs: {기능명} 관련 문서 업데이트"
git push
```

### 6-3. 워크플로우 완료 요약
사용자에게 전달:
- 구현한 기능 요약
- 생성/수정된 파일 목록
- PR 링크
- 업데이트된 문서 목록
