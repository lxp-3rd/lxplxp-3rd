// Roadmap 도메인 타입 정의 (API 응답 기준)
export type Roadmap = {
  id: string;
  title: string;
  description: string;
  category: string;
  courseCount: number;      // computed
  enrollmentCount: number;  // computed
  createdBy: string;        // memberId
  creatorName: string;      // member join
  progress: number;         // 현재 사용자의 진행률 (enrollment join)
  isEnrolled: boolean;      // 현재 사용자 수강 여부
  thumbnail: string;
  createdAt: string;
  updatedAt: string;
};

export type RoadmapCourse = {
  id: string;
  roadmapId: string;
  courseId: string;
  order: number;
  createdAt: string;
  updatedAt: string;
};

export type RoadmapLearning = {
  id: string;
  roadmapId: string;
  memberId: string;
  progress: number;
  completedAt: string | null;
  createdAt: string;
  updatedAt: string;
};
