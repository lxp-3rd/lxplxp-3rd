# /workflow

구현할 기능: $ARGUMENTS

아래 6단계 워크플로우를 순서대로 진행한다. 각 단계 완료 후 다음 단계로 넘어가기 전에 반드시 명확히 표시한다.

---

## 1단계: 기능 탐색 및 컨텍스트 확립

### 1-1. 요구사항 구체화
`$ARGUMENTS`를 분석해 구현 범위를 파악한다. 아래 항목이 불명확하면 사용자에게 질문해 명확히 한다:
- 기대 동작 및 화면 흐름
- 예외/에러 케이스
- 백엔드 / 프론트엔드 / 둘 다 필요 여부
- 연관된 도메인 및 의존성

요구사항이 충분히 구체화될 때까지 사용자와 대화한다. 확인 없이 다음 단계로 넘어가지 않는다.

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
사용자에게 다음을 요약해서 확인 요청:
- 확정된 요구사항
- 연결된 이슈 번호
- 생성된 브랜치 이름

사용자 확인 후 2단계로 진행한다.

---

## 2단계: 계획

### 2-1. 관련 문서 확인
```
docs/ARCHITECTURE.md  — 도메인 구조, 파일 위치 파악
docs/API.md           — 기존 엔드포인트 및 컨벤션 확인
```

변경이 필요한 파일 목록과 새로 만들 파일 목록을 머릿속에 정리한다.

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
2단계 계획을 그대로 구현한다. 아래 컨벤션을 반드시 준수한다:

**백엔드:**
- 도메인 → application → infrastructure → presentation 의존성 방향 준수
- UseCase 인터페이스 → Service 구현체 → Controller DTO 순으로 작성
- `AGENTS.md` 네이밍 컨벤션 준수

**프론트엔드:**
- 도메인 폴더 하위 `api.ts` / `types.ts` / `components/` 구조 유지
- 컴포넌트에서 직접 fetch 금지 — `api.ts` 경유 필수

### 3-2. 테스트 실행 → 코드 리뷰 자동 진행
구현이 완료되면 변경 범위에 따라 테스트를 작성하고, Bash로 테스트를 직접 실행한다:

```bash
# 백엔드만 변경한 경우
cd {repo-root} && ./gradlew test 2>&1

# 프론트엔드만 변경한 경우
cd {repo-root}/next-ui && npm run lint 2>&1

# 둘 다 변경한 경우: 백엔드 먼저, 이어서 프론트엔드
cd {repo-root} && ./gradlew test 2>&1
cd {repo-root}/next-ui && npm run lint 2>&1
```

테스트 결과를 확인한다:
- **실패 시**: 원인을 파악해 수정하고 다시 테스트를 실행한다. 통과할 때까지 반복한다.
- **통과 시**: 사용자 입력 없이 즉시 4단계 코드 리뷰로 넘어간다.

---

## 4단계: 코드 검토

### 4-1. 코드 리뷰 (테스트 통과 후 자동 실행)
테스트가 통과되면 사용자 확인 없이 바로 `/code-review` 를 실행한다. 스킬이 git diff 기반으로 8개 앵글(라인별 스캔, 삭제 동작 감사, 크로스파일 추적 등) + 검증 패스를 수행하고 결과를 반환한다.

### 4-2. 사용자 확인
리뷰 결과를 사용자에게 제시하고 확인을 요청한다:
- 수정이 필요한 항목이 있으면 → 3단계로 돌아가 수정 후 테스트 재실행 → 코드 리뷰 재실행
- 수정 불필요하다고 판단하면 → 5단계로 진행

---

## 5단계: 커밋 및 PR

> **규칙: 백엔드와 프론트엔드는 반드시 분리한다.**
> - 백엔드(`src/`) 변경과 프론트엔드(`next-ui/`) 변경을 같은 커밋에 포함하지 않는다.
> - 같은 PR에 올리지 않는다. 백엔드 PR과 프론트엔드 PR을 별도로 생성한다.
> - 백엔드만 변경됐다면 백엔드 PR 하나만, 프론트엔드만 변경됐다면 프론트엔드 PR 하나만 생성한다.

### 5-1. 변경 내역 확인
```bash
git diff --stat
git status
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

### 5-4. PR 생성 — 백엔드와 프론트엔드 각각 별도 브랜치·PR로 분리

백엔드와 프론트엔드 변경이 모두 있는 경우, 현재 브랜치에서 각각의 브랜치를 분기해 별도 PR을 생성한다:

```bash
# 현재 브랜치 기준으로 백엔드 전용 브랜치 생성 후 PR
git checkout -b feat/#{이슈번호}-{description}
git push -u origin feat/#{이슈번호}-{description}
gh pr create \
  --title "feat: 기능 설명 (#{이슈번호})" \
  --body "$(cat .github/PULL_REQUEST_TEMPLATE.md)"

# 프론트엔드 전용 브랜치 생성 후 PR
git checkout -b feat/#{이슈번호}-{description}-fe
git push -u origin feat/#{이슈번호}-{description}-fe
gh pr create \
  --title "feat: [FE] 기능 설명 (#{이슈번호})" \
  --body "$(cat .github/PULL_REQUEST_TEMPLATE.md)"
```

+PR 생성 후 `.github/PULL_REQUEST_TEMPLATE.md` 의 각 항목(`관련 이슈`, `주요 변경 파일`, `작업 내용` 등)을 실제 내용으로 채워서 편집한다.

---

## 6단계: 문서 최신화

### 6-1. 변경 사항 점검
구현 과정에서 아래 항목이 변경됐는지 확인한다:

| 변경 내용 | 업데이트 대상 |
|-----------|--------------|
| 새 API 엔드포인트 추가/변경 | `docs/API.md` |
| 아키텍처 구조 변경 | `docs/ARCHITECTURE.md` |
| 새 공통 컴포넌트 추가 | `docs/ARCHITECTURE.md` |
| Git/브랜치 전략 변경 | `docs/GIT.md` |
| 프로젝트 구조나 컨벤션 변경 | `AGENTS.md` |

### 6-2. 문서 수정 및 커밋
변경된 문서가 있으면 커밋한다:
```bash
git add docs/
git commit -m "docs: {기능명} 관련 문서 업데이트"
git push
```

### 6-3. 워크플로우 완료 요약
사용자에게 아래 내용을 요약해서 전달한다:
- 구현한 기능 요약
- 생성/수정된 파일 목록
- PR 링크
- 업데이트된 문서 목록 (있는 경우)
