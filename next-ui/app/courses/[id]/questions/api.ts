import { fetcher } from '@/lib/fetcher';
import type { QuestionResponse, CreateQuestionRequest, UpdateQuestionRequest, AnswerQuestionRequest } from './types';

export const questionApi = {
  getAll: (courseId: number) =>
    fetcher.get<QuestionResponse[]>(`/api/questions?courseId=${courseId}`),
  getById: (questionId: number) =>
    fetcher.get<QuestionResponse>(`/api/questions/${questionId}`),
  create: (data: CreateQuestionRequest) =>
    fetcher.post<QuestionResponse>('/api/questions', data),
  update: (questionId: number, data: UpdateQuestionRequest) =>
    fetcher.put<QuestionResponse>(`/api/questions/${questionId}`, data),
  remove: (questionId: number, memberId: number) =>
    fetcher.delete(`/api/questions/${questionId}?memberId=${memberId}`),
  answer: (questionId: number, data: AnswerQuestionRequest) =>
    fetcher.post<QuestionResponse>(`/api/questions/${questionId}/answers`, data),
};
