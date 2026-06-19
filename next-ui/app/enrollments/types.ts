// Enrollment 도메인 타입 정의
export type Enrollment = {
  id: string;
  memberId: string;
  courseId: string;
  status: 'IN_PROGRESS' | 'COMPLETED';
  createdAt: string;
  updatedAt: string;
};

export type ContentLearning = {
  id: string;
  enrollmentId: string;
  contentId: string;
  isCompleted: boolean;
  completedAt: string | null;
  createdAt: string;
  updatedAt: string;
};
