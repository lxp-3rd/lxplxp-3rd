// Question 도메인 타입 정의
export type Question = {
  id: string;
  courseId: string;
  memberId: string;
  title: string;
  content: string;
  createdAt: string;
  updatedAt: string;
};

export type Answer = {
  id: string;
  questionId: string;
  authorId: string;
  content: string;
  createdAt: string;
  updatedAt: string;
};
