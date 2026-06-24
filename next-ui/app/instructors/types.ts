// Instructor 도메인 타입 정의 (API 응답 기준)
export type Instructor = {
  id: string;
  memberId: string;
  name: string;           // member join
  nickname: string;       // member join
  bio: string;
  expertise: string[];
  courseCount: number;    // computed
  studentCount: number;   // computed
  rating: number;         // computed
  isActive: boolean;
  avatarUrl: string;
  createdAt: string;
  updatedAt: string;
};
