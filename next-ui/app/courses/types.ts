// Course 도메인 타입 정의
export type Course = {
  id: string;
  instructorId: string;
  title: string;
  description: string;
  likeCount: number;
  createdAt: string;
  updatedAt: string;
};

export type Content = {
  id: string;
  courseId: string;
  title: string;
  contentUrl: string;
  order: number;
  createdAt: string;
  updatedAt: string;
};
