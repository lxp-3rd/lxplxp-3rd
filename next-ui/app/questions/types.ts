export type Question = {
  id: number;
  courseId: number;
  memberId: number;
  title: string;
  content: string;
  status: 'PUBLISHED';
  createdAt: string;
  updatedAt: string;
  answer: string | null;
  answeredBy: number | null;
  answeredAt: string | null;
};

export type LegacyAnswer = {
  id: string;
  questionId: string;
  authorId: string;
  authorName: string;
  avatarSrc?: string;
  content: string;
  createdAt: string;
  updatedAt: string;
};

export type LegacyQuestion = {
  id: string;
  courseId: string;
  courseTitle: string;
  memberId: string;
  authorName: string;
  title: string;
  content: string;
  isAnswered: boolean;
  tags: string[];
  answer: LegacyAnswer | null;
  createdAt: string;
  updatedAt: string;
};
