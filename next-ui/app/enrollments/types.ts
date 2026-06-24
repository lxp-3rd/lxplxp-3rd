// Enrollment 도메인 타입 정의
export type Enrollment = {
  id: number;
  memberId: number;
  courseId: number;
  status: 'ACTIVE' | 'COMPLETED' | 'CANCELED';
  createdAt: string;
};

export type CreateEnrollmentRequest = {
  memberId: number;
  courseId: number;
};
