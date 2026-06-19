// InstructorApplication 도메인 타입 정의
export type InstructorApplication = {
  id: string;
  memberId: string;
  status: 'PENDING' | 'APPROVED' | 'REJECTED';
  reason: string;
  createdAt: string;
  updatedAt: string;
};
