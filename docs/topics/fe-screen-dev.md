# FE 화면 개발 기록

> 이 파일: 화면 개발 진행 과정, 담당 도메인, Stitch 연동 관련 논의 기록

---

## 2026-06-19 — 전체 화면 분석 및 공통 기반 세팅 완료

### Stitch 화면 전체 목록 (27개)

| 파일 | 화면명 | 라우트 | 담당 |
|------|--------|--------|------|
| 1번/_1 | 로그인 | `/(auth)/login` | E |
| 1번/_4 | 회원가입 | `/(auth)/register` | E |
| 1번/_3 | 마이페이지 | `/my-page` | E |
| 2번/_11 | 회원 관리 (관리자) | `/admin/members` | E |
| 1번/q_a | Q&A 목록+상세 통합 | `/questions` | A |
| 1번/_14 | 학습 홈 (로드맵 대시보드) | `/` | B |
| 1번/_13 | 내 로드맵 목록 | `/roadmaps` | B |
| 1번/_12 | 로드맵 상세 | `/roadmaps/[id]` | B |
| 1번/_11 | 로드맵 생성 | `/roadmaps/new` | B |
| 2번/_3 | 공지사항 목록 (일반) | `/announcements` | B |
| 2번/_2 | 공지사항 상세 (일반) | `/announcements/[id]` | B |
| 2번/_4 | 로드맵 관리 (관리자) | `/admin/roadmaps` | B |
| 2번/_9 | 공지사항 목록 (관리자) | `/admin/announcements` | B |
| 2번/_5 | 공지사항 작성 (관리자) | `/admin/announcements/new` | B |
| 2번/_1 | 공지사항 상세 (관리자) | `/admin/announcements/[id]` | B |
| 1번/_2 | 강사 신청 및 상태 | `/instructor-applications` | C |
| 1번/_5 | 전체 강좌 탐색 | `/courses` | C |
| 1번/_10 | 강좌 상세 + 수강신청 | `/courses/[id]` | C |
| 1번/_7 | 강사 강좌 관리 | `/courses/manage` | C |
| 1번/_9 | 강의 편집 | `/courses/[id]/edit` | C |
| 1번/_8 | 강좌별 통계 | `/courses/[id]/stats` | C |
| 2번/_6 | 강좌 관리 (관리자) | `/admin/courses` | C |
| 2번/_7 | 강사 관리 (관리자) | `/admin/instructors` | C |
| 2번/_12 | 강사 신청 관리 (관리자) | `/admin/instructor-applications` | C |
| 1번/_6 | 내 강의실 (수강 목록) | `/enrollments` | D |
| 2번/_10 | 강좌별 수강 관리 (관리자) | `/admin/courses/[id]/enrollments` | D |
| 2번/_8 | 관리자 대시보드 | `/admin` | 공통 |

---

### 공통 기반 세팅 내용 (배포 완료)

#### 디자인 시스템
- `tailwind.config.js` — DESIGN.md 기반 color/spacing/fontSize/borderRadius 토큰 전부 등록
  - Primary: `#ff7e21` (오렌지)
  - Font: `Hanken Grotesk`
  - Spacing: `xs(4px)` `sm(8px)` `md(16px)` `lg(24px)` `xl(40px)` `xxl(48px)` `gutter(24px)`
- `app/globals.css` — Google Fonts (Hanken Grotesk + Material Symbols Outlined) import
- `postcss.config.js` — Tailwind + Autoprefixer 설정
- `tsconfig.json` — `@/*` alias 설정
- `next.config.ts` — 이미지 도메인 허용

#### 공통 컴포넌트

| 파일 | 설명 |
|------|------|
| `components/TopNavBar.tsx` | 일반 사용자용 상단 헤더 (로고, 네비, 검색, 알림, 프로필) |
| `components/Footer.tsx` | 사이트 하단 푸터 |
| `components/admin/AdminNavBar.tsx` | 관리자용 상단 헤더 (관리 메뉴 포함) |
| `app/admin/layout.tsx` | Admin 섹션 공통 레이아웃 |
| `components/ui/Button.tsx` | variant: primary / secondary / danger / outline / ghost |
| `components/ui/Input.tsx` | label, error, icon 지원 |
| `components/ui/ProgressBar.tsx` | 오렌지 fill 진도 바 |
| `components/ui/Badge.tsx` | 상태 칩/태그 |

#### 공통 패키지
- `package.json` — Next.js 14, React 18, TypeScript, Tailwind CSS

---

### 팀원 개발 가이드

#### 페이지 기본 템플릿

```tsx
import { TopNavBar } from '@/components/TopNavBar';
import { Footer }    from '@/components/Footer';

export default function MyPage() {
  return (
    <>
      <TopNavBar />
      <main className="page-top min-h-screen">
        <div className="page-container py-xl">
          {/* 내용 */}
        </div>
      </main>
      <Footer />
    </>
  );
}
```

#### Admin 페이지 — layout.tsx가 AdminNavBar를 자동 포함하므로 본문만 작성

```tsx
// app/admin/[도메인]/page.tsx
export default function AdminSomePage() {
  return (
    <div className="page-container py-xl">
      {/* 내용 — AdminNavBar는 layout.tsx에서 자동 렌더링 */}
    </div>
  );
}
```

#### 자주 쓰는 Tailwind 클래스
- 컨테이너: `page-container` (max-w + px-gutter)
- 헤더 여백: `page-top` (pt-16)
- 카드: `bg-surface-container-lowest border border-outline-variant rounded-lg p-md`
- 타이포: `text-headline-lg`, `text-headline-md`, `text-body-md`, `text-label-md`
- 아이콘: `<span className="material-symbols-outlined">icon_name</span>`

#### API 호출 규칙
컴포넌트에서 직접 fetch 금지 → 반드시 같은 도메인 폴더의 `api.ts`를 통해 호출

```ts
// ✅ 올바른 방법
import { courseApi } from './api';
const courses = await courseApi.getAll();

// ❌ 금지
const res = await fetch('/api/courses');
```

---

### 다음 단계
- [ ] 각 팀원 담당 화면 구현 시작
- [ ] CourseCard variant (탐색/관리/수강) C↔D 간 합의
- [ ] `NEXT_PUBLIC_API_URL` 환경변수 팀 공유
- [ ] 백엔드 실제 API 엔드포인트 확정 후 api.ts 업데이트
