import { MOCK_COURSES } from '@/app/courses/mockData';
import { fetcher } from '@/lib/fetcher';
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

const cloneCourse = (course: AdminCourse): AdminCourse => ({
  ...course,
  contents: course.contents.map((content) => ({ ...content })),
});

const cloneRoadmap = (roadmap: AdminRoadmap): AdminRoadmap => ({ ...roadmap });

const cloneInstructor = (instructor: AdminInstructorDetail['instructor']): AdminInstructorDetail['instructor'] => ({
  ...instructor,
  expertise: [...instructor.expertise],
});

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
      value: '-',
      icon: 'pending_actions',
      tone: 'danger',
      trendLabel: '검토 필요',
      progress: 100,
    },
  ],
  getMenuItems: async (): Promise<AdminMenuItem[]> => ADMIN_MENU_ITEMS.map((item) => ({ ...item })),
};

export const adminCourseMockApi = {
  getCourses: async (): Promise<AdminCourse[]> => MOCK_COURSES.map((course) => ({ ...cloneCourse(course), hidden: false })),
  getCourseEnrollments: async (courseId: string): Promise<AdminCourseEnrollmentDetail | null> => {
    const course = MOCK_COURSES.find((item) => item.id === courseId);
    if (!course) return null;

    return {
      course: cloneCourse(course),
      enrollments: ADMIN_COURSE_ENROLLMENTS.map((enrollment) => ({ ...enrollment })),
    };
  },
};

export const adminRoadmapMockApi = {
  getRoadmaps: async (): Promise<AdminRoadmap[]> => MOCK_ROADMAPS.map((roadmap) => ({ ...cloneRoadmap(roadmap), hidden: false })),
};

export const adminInstructorMockApi = {
  getInstructors: async () => MOCK_INSTRUCTORS.map(cloneInstructor),
  getInstructorDetail: async (id: string): Promise<AdminInstructorDetail | null> => {
    const instructor = getInstructorById(id);
    if (!instructor) return null;

    return {
      instructor: cloneInstructor(instructor),
      courses: MOCK_COURSES.filter((course) => course.instructorId === instructor.id).map(cloneCourse),
    };
  },
};

export const adminInstructorApplicationApi = {
  getApplications: () => fetcher.get<AdminInstructorApplication[]>('/api/instructors/applications'),
  review: (id: number, data: { action: 'APPROVE' | 'REJECT'; rejectionReason?: string }) =>
    fetcher.patch<void>(`/api/instructors/applications/${id}`, data),
};

export const adminAnnouncementApi = {
  getAll: () => fetcher.get<AdminAnnouncement[]>('/api/announcements'),
  getById: (id: string) => fetcher.get<AdminAnnouncement>(`/api/announcements/${id}`),
  create: (data: { adminId: number; title: string; content: string; status: 'PUBLISH' | 'HIDDEN' }) =>
    fetcher.post<AdminAnnouncement>('/api/announcements', data),
  update: (id: string, data: { title: string; content: string; status: 'PUBLISH' | 'HIDDEN' }) =>
    fetcher.put<AdminAnnouncement>(`/api/announcements/${id}`, data),
  remove: (id: string) => fetcher.delete(`/api/announcements/${id}`),
};
