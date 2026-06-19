// Member 도메인 타입 정의
export type Member = {
  id: string;
  email: string;
  name: string;
  role: 'LEARNER' | 'INSTRUCTOR' | 'ADMIN';
  createdAt: string;
  updatedAt: string;
};
