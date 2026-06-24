import { MOCK_ANNOUNCEMENTS, getAnnouncementById } from '@/app/announcements/mockData';
import { MOCK_COURSES } from '@/app/courses/mockData';
import { MOCK_INSTRUCTORS, getInstructorById } from '@/app/instructors/mockData';
import { MOCK_ROADMAPS } from '@/app/roadmaps/mockData';
import type {
  AdminAnnouncement,
  AdminCourse,
  AdminCourseEnrollmentDetail,
  AdminDashboardStat,
  AdminInstructorApplication,
  AdminInstructorDetail,
  AdminMenuItem,
  AdminRoadmap,
} from './types';

const ADMIN_INSTRUCTOR_APPLICATIONS: AdminInstructorApplication[] = [
  {
    id: 'a1',
    nickname: 'PixelMaster',
    displayName: 'Kim Ji-hun',
    expertise: 'UI/UX 디자인',
    appliedAt: '2024.05.12',
    status: 'PENDING',
    rejectReason: '',
    bio: '확장 가능한 클라우드 솔루션 구축 경험을 바탕으로 실무 중심 강의를 제공하려는 강사 후보입니다.',
  },
  {
    id: 'a2',
    nickname: 'CodeNinja',
    displayName: 'Lee Min-ho',
    expertise: '풀스택 개발',
    appliedAt: '2024.05.11',
    status: 'PENDING',
    rejectReason: '',
    bio: 'React, Node.js, 클라우드 아키텍처를 중심으로 한 풀스택 개발 경험을 보유하고 있습니다.',
  },
  {
    id: 'a3',
    nickname: 'DataGuru',
    displayName: 'Sarah Park',
    expertise: '데이터 사이언스',
    appliedAt: '2024.05.10',
    status: 'PENDING',
    rejectReason: '',
    bio: 'Python과 머신러닝 기반 데이터 분석 및 데이터 파이프라인 구축 경험이 있습니다.',
  },
  {
    id: 'a4',
    nickname: 'MarketingWiz',
    displayName: 'Park Jun-su',
    expertise: '그로스 마케팅',
    appliedAt: '2024.05.10',
    status: 'PENDING',
    rejectReason: '',
    bio: '스타트업에서 SEO, 퍼포먼스 마케팅, 그로스 해킹을 담당한 실무형 마케터입니다.',
  },
  {
    id: 'a5',
    nickname: 'CloudArchitect',
    displayName: 'Choi Da-in',
    expertise: 'AWS/클라우드',
    appliedAt: '2024.05.09',
    status: 'PENDING',
    rejectReason: '',
    bio: 'AWS, GCP 환경에서 대규모 인프라를 설계하고 운영한 클라우드 아키텍트입니다.',
  },
  {
    id: 'a6',
    nickname: 'SecureGuru',
    displayName: 'Jung Han-byeol',
    expertise: '사이버 보안',
    appliedAt: '2024.05.08',
    status: 'PENDING',
    rejectReason: '',
    bio: '침투 테스트와 보안 취약점 분석 분야 프로젝트 경험이 있는 보안 전문가입니다.',
  },
];

const ADMIN_MENU_ITEMS: AdminMenuItem[] = [
  {
    href: '/admin/instructor-applications',
    icon: 'assignment_ind',
    title: '강사 신청 목록',
    description: '신규 강사 신청과 자격 서류를 검토합니다.',
  },
  {
    href: '/admin/instructors',
    icon: 'manage_accounts',
    title: '강사 관리',
    description: '강사 권한과 프로필 정보를 관리합니다.',
  },
  {
    href: '/admin/members',
    icon: 'person_search',
    title: '회원 관리',
    description: '회원 계정 상태와 기본 정보를 확인합니다.',
  },
  {
    href: '/admin/courses',
    icon: 'book',
    title: '강좌 관리',
    description: '강좌 공개 여부와 운영 상태를 관리합니다.',
  },
  {
    href: '/admin/announcements',
    icon: 'campaign',
    title: '공지사항 관리',
    description: '학습자와 강사에게 전달할 공지를 관리합니다.',
  },
  {
    href: '/admin/roadmaps',
    icon: 'map',
    title: '로드맵 관리',
    description: '학습 경로와 로드맵 구성을 관리합니다.',
  },
];

const ADMIN_COURSE_ENROLLMENTS = [
  { id: 'e1', nickname: '김하늘', email: 'sky.kim@example.com', enrolledAt: '2024.03.15', progress: 85 },
  { id: 'e2', nickname: '이준호', email: 'junho.lee@example.com', enrolledAt: '2024.03.12', progress: 42 },
  { id: 'e3', nickname: '박지민', email: 'jimin.park@example.com', enrolledAt: '2024.03.10', progress: 100 },
  { id: 'e4', nickname: '최서연', email: 'sy.choi@example.com', enrolledAt: '2024.03.05', progress: 15 },
  { id: 'e5', nickname: '정우진', email: 'wj.jung@example.com', enrolledAt: '2024.03.01', progress: 60 },
  { id: 'e6', nickname: '윤도현', email: 'dh.yoon@example.com', enrolledAt: '2024.02.28', progress: 0 },
];

export const adminDashboardApi = {
  getStats: async (): Promise<AdminDashboardStat[]> => [
    {
      label: '전체 회원 수',
      value: '24,512',
      icon: 'group',
      tone: 'primary',
      trendLabel: '+12.5%',
      progress: 75,
    },
    {
      label: '전체 강사 수',
      value: '843',
      icon: 'school',
      tone: 'secondary',
      trendLabel: '+4.2%',
      progress: 40,
    },
    {
      label: '전체 강좌 수',
      value: '1,204',
      icon: 'auto_stories',
      tone: 'tertiary',
      trendLabel: '안정적',
      progress: 60,
    },
    {
      label: '대기 중인 강사 신청',
      value: String(ADMIN_INSTRUCTOR_APPLICATIONS.filter((item) => item.status === 'PENDING').length),
      icon: 'pending_actions',
      tone: 'danger',
      trendLabel: '검토 필요',
      progress: 100,
    },
  ],
  getMenuItems: async (): Promise<AdminMenuItem[]> => [...ADMIN_MENU_ITEMS],
};

export const adminCourseMockApi = {
  getCourses: async (): Promise<AdminCourse[]> => MOCK_COURSES.map((course) => ({ ...course, hidden: false })),
  getCourseEnrollments: async (courseId: string): Promise<AdminCourseEnrollmentDetail | null> => {
    const course = MOCK_COURSES.find((item) => item.id === courseId);
    if (!course) return null;

    return {
      course,
      enrollments: ADMIN_COURSE_ENROLLMENTS.map((enrollment) => ({ ...enrollment })),
    };
  },
};

export const adminRoadmapMockApi = {
  getRoadmaps: async (): Promise<AdminRoadmap[]> => MOCK_ROADMAPS.map((roadmap) => ({ ...roadmap, hidden: false })),
};

export const adminInstructorMockApi = {
  getInstructors: async () => [...MOCK_INSTRUCTORS],
  getInstructorDetail: async (id: string): Promise<AdminInstructorDetail | null> => {
    const instructor = getInstructorById(id);
    if (!instructor) return null;

    return {
      instructor,
      courses: MOCK_COURSES.filter((course) => course.instructorId === instructor.id),
    };
  },
};

export const adminInstructorApplicationMockApi = {
  getApplications: async () => ADMIN_INSTRUCTOR_APPLICATIONS.map((application) => ({ ...application })),
};

export const adminAnnouncementMockApi = {
  getAnnouncements: async (): Promise<AdminAnnouncement[]> => [...MOCK_ANNOUNCEMENTS],
  getAnnouncement: async (id: string): Promise<AdminAnnouncement | null> => getAnnouncementById(id),
};
