import { fetcher } from '@/lib/fetcher';
import type { QuestionResponse, CreateQuestionRequest, UpdateQuestionRequest } from './types';

export const questionApi = {
  getAll: (courseId: number) =>
    fetcher.get<QuestionResponse[]>(`/questions?courseId=${courseId}`),
  getById: (questionId: number) =>
    fetcher.get<QuestionResponse>(`/questions/${questionId}`),
  create: (data: CreateQuestionRequest) =>
    fetcher.post<QuestionResponse>('/questions', data),
  update: (questionId: number, data: UpdateQuestionRequest) =>
    fetcher.put<QuestionResponse>(`/questions/${questionId}`, data),
  remove: (questionId: number, memberId: number) =>
    fetcher.delete(`/questions/${questionId}?memberId=${memberId}`),
};
