// GET /api/courses 응답 형태
export type CourseSummary = {
  id: number;
  title: string;
  thumbnailUrl: string;
  instructorName: string;
  enrollmentCount: number;
  likeCount: number;
};

// GET /api/courses/{id} 응답 형태
export type CurriculumItem = {
  id: number;
  order: number;
  title: string;
};

export type CourseContent = CurriculumItem & {
  courseId: number;
  contentUrl: string;
};

export type CourseDetail = {
  id: number;
  instructorId: number;
  instructorName: string;
  title: string;
  summary: string;
  description: string;
  thumbnailUrl: string;
  enrollmentCount: number;
  enrolled: boolean;
  curriculum: CurriculumItem[];
};

export type Content = {
  id: string;
  title: string;
  type: string;
  durationSeconds: number;
  courseId?: string;
  contentUrl?: string;
  order?: number;
  createdAt?: string;
  updatedAt?: string;
};

export type Course = {
  id: string;
  title: string;
  instructor: string;
  instructorId: string;
  category: string;
  level: string;
  thumbnail: string;
  enrollmentCount: number;
  likeCount: number;
  rating: number;
  status: string;
  description: string;
  contents: Content[];
  createdAt?: string;
  updatedAt?: string;
};
