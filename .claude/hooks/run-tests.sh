#!/bin/bash
# 워크플로우 3단계(구현) 완료 후 자동으로 실행되는 테스트 스크립트
# 센티넬 파일 .claude/.run-tests-now 가 존재할 때만 동작

SENTINEL=".claude/.run-tests-now"
SCOPE_FILE=".claude/.test-scope"
ROOT="$(git rev-parse --show-toplevel 2>/dev/null || pwd)"

cd "$ROOT" || exit 0

[ -f "$SENTINEL" ] || exit 0

SCOPE=$(cat "$SCOPE_FILE" 2>/dev/null || echo "all")
rm -f "$SENTINEL" "$SCOPE_FILE"

echo ""
echo "========================================"
echo "  워크플로우 테스트 실행"
echo "  scope: $SCOPE"
echo "========================================"

FAILED=0

if [[ "$SCOPE" == "backend" || "$SCOPE" == "all" ]]; then
  echo ""
  echo "[백엔드] ./gradlew test"
  if ./gradlew test --console=plain 2>&1 | tail -20; then
    echo "✓ 백엔드 테스트 통과"
  else
    echo "✗ 백엔드 테스트 실패"
    FAILED=1
  fi
fi

if [[ "$SCOPE" == "frontend" || "$SCOPE" == "all" ]]; then
  echo ""
  echo "[프론트엔드] npm run lint"
  if cd next-ui && npm run lint 2>&1; then
    echo "✓ 프론트엔드 lint 통과"
  else
    echo "✗ 프론트엔드 lint 실패"
    FAILED=1
  fi
fi

echo ""
if [ "$FAILED" -eq 0 ]; then
  echo "========================================"
  echo "  모든 테스트 통과 — 4단계(검토)로 진행"
  echo "========================================"
else
  echo "========================================"
  echo "  테스트 실패 — 3단계(구현)로 돌아가서 수정 필요"
  echo "========================================"
  exit 1
fi