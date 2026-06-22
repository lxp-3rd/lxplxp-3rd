export const MOCK_ANNOUNCEMENTS = [
  {
    id: '1',
    title: '5월 정기 시스템 점검 안내 (05/25 02:00~06:00)',
    category: '시스템',
    author: '시스템관리팀',
    createdAt: '2026-06-21',
    views: 1248,
    isNew: true,
    content: `안녕하세요, LXP 학습 플랫폼입니다. 더 나은 학습 환경 제공을 위해 정기 점검을 실시합니다.

**점검 일시:** 2026년 6월 25일 (토) 02:00 ~ 06:00
**점검 내용:** 서버 보안 패치, 데이터베이스 최적화, 모바일 앱 연동 개선

점검 시간 동안은 강의 시청 및 과제 제출이 불가능합니다.
    `,
  },
  {
    id: '2',
    title: '모바일 앱 안정화 버전 업데이트 안내',
    category: '서비스',
    author: '기술지원팀',
    createdAt: '2026-06-18',
    views: 842,
    isNew: false,
    content: '모바일 앱 v2.1.3이 출시되었습니다. 영상 재생 오류 및 알림 버그가 수정되었습니다.',
  },
  {
    id: '3',
    title: '커뮤니티 이용 가이드라인 개정 알림',
    category: '정책',
    author: '운영지원팀',
    createdAt: '2026-06-15',
    views: 621,
    isNew: false,
    content: '건강한 학습 커뮤니티를 위해 이용 가이드라인이 개정되었습니다. 변경 사항을 확인해 주세요.',
  },
  {
    id: '4',
    title: '신규 자격증 과정 오픈 이벤트 당첨자 발표',
    category: '이벤트',
    author: '마케팅팀',
    createdAt: '2026-06-12',
    views: 1890,
    isNew: false,
    content: '신규 자격증 과정 오픈 기념 이벤트 당첨자를 발표합니다. 이메일을 확인해 주세요.',
  },
];

export const getAnnouncementById = (id: string) =>
  MOCK_ANNOUNCEMENTS.find((a) => a.id === id) ?? null;
