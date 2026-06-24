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
  badge?: string;
  likeCount: number;
  rating: number;
  status: string;
  description: string;
  contents: Content[];
  createdAt?: string;
  updatedAt?: string;
};
