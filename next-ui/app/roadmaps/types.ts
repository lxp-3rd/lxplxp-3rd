// Roadmap 도메인 타입 정의
export type Roadmap = {
  id: string;
  title: string;
  description: string;
  courses: string[];
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
