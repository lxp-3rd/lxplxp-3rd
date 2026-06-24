export const MOCK_ENROLLMENTS: {
  id: string;
  courseId: string;
  courseTitle: string;
  instructor: string;
  thumbnail: string;
  progress: number;
  lastAccessedAt: string;
  status: 'IN_PROGRESS' | 'COMPLETED';
}[] = [
  {
    id: 'e1',
    courseId: '1',
    courseTitle: '데이터 중심의 UI/UX 디자인 실무 입문',
    instructor: '이하연',
    thumbnail: 'https://www.gstatic.com/labs-code/stitch/stitch-placeholder-300x300.svg',
    progress: 42,
    lastAccessedAt: '2026-06-20',
    status: 'IN_PROGRESS',
  },
  {
    id: 'e2',
    courseId: '2',
    courseTitle: 'AI 프로그래밍 기초: Python으로 시작하기',
    instructor: '박민준',
    thumbnail: 'https://www.gstatic.com/labs-code/stitch/stitch-placeholder-300x300.svg',
    progress: 100,
    lastAccessedAt: '2026-06-15',
    status: 'COMPLETED',
  },
  {
    id: 'e3',
    courseId: '3',
    courseTitle: '프론트엔드 마스터: React & TypeScript',
    instructor: '이하연',
    thumbnail: 'https://www.gstatic.com/labs-code/stitch/stitch-placeholder-300x300.svg',
    progress: 15,
    lastAccessedAt: '2026-06-18',
    status: 'IN_PROGRESS',
  },
];
