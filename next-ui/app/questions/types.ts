// QnA 도메인 타입 정의 (API 응답 기준)
export type Answer = {
  id: string;
  questionId: string;
  authorId: string;
  authorName: string;   // member join
  avatarSrc?: string;   // instructor profile image
  content: string;
  createdAt: string;
  updatedAt: string;
};

export type Question = {
  id: string;
  courseId: string;
  courseTitle: string;  // course join
  memberId: string;
  authorName: string;   // member join
  title: string;
  content: string;
  isAnswered: boolean;  // answer 존재 여부 computed
  tags: string[];
  answer: Answer | null;
  createdAt: string;
  updatedAt: string;
};
