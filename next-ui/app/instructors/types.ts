// GET /api/instructors/{id}/profile 응답 형태
export type InstructorProfile = {
  instructorId: number;
  profileImageUrl: string;
  bio: string;
};

// Instructor 도메인 타입 정의 (API 응답 기준)
export type Instructor = {
  id: string;
  name: string;          // member join
  nickname: string;      // member join (이메일로 사용)
  bio: string;
  expertise: string[];
  courseCount: number;   // computed
  studentCount: number;  // computed
  isActive: boolean;
};
