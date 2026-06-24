import type { Instructor } from './types';

export const MOCK_INSTRUCTORS: Instructor[] = [
  {
    id: 'u2',
    memberId: 'u2',
    name: '이하연',
    nickname: 'instructor@lxp.com',
    expertise: ['UI/UX', '웹개발', 'UX Writing'],
    bio: '10년 경력의 UX 디자이너. 삼성전자, 카카오 UX 리드를 거쳐 현재 강의 중.',
    courseCount: 3,
    studentCount: 3620,
    rating: 4.7,
    isActive: true,
    avatarUrl: '',
    createdAt: '2025-09-01',
    updatedAt: '2026-06-01',
  },
  {
    id: 'u4',
    memberId: 'u4',
    name: '박민준',
    nickname: 'minjun@lxp.com',
    expertise: ['AI/ML', 'Python', '딥러닝'],
    bio: 'ML 엔지니어 출신 강사. 네이버 클로바 AI팀 근무 경험.',
    courseCount: 1,
    studentCount: 3820,
    rating: 4.9,
    isActive: true,
    avatarUrl: '',
    createdAt: '2025-10-15',
    updatedAt: '2026-05-20',
  },
  {
    id: 'u5',
    memberId: 'u5',
    name: '최서준',
    nickname: 'seojun@lxp.com',
    expertise: ['클라우드', 'AWS', 'DevOps'],
    bio: 'AWS 공인 솔루션스 아키텍트. 클라우드 인프라 컨설팅 전문.',
    courseCount: 1,
    studentCount: 890,
    rating: 4.6,
    isActive: true,
    avatarUrl: '',
    createdAt: '2025-11-01',
    updatedAt: '2026-06-10',
  },
];

export const getInstructorById = (id: string) =>
  MOCK_INSTRUCTORS.find((i) => i.id === id) ?? null;
