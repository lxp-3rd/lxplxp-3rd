// Instructor 도메인 타입 정의
export type Instructor = {
  id: string;
  memberId: string;
  isActive: boolean;
  bio: string;
  expertise: string[];
  createdAt: string;
  updatedAt: string;
};
