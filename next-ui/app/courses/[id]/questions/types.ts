export type QuestionStatus = 'PUBLISHED';

export interface QuestionResponse {
  id: number;
  courseId: number;
  memberId: number;
  title: string;
  content: string;
  status: QuestionStatus;
  createdAt: string;
  updatedAt: string;
}

export interface CreateQuestionRequest {
  courseId: number;
  memberId: number;
  title: string;
  content: string;
}

export interface UpdateQuestionRequest {
  memberId: number;
  title: string;
  content: string;
}
