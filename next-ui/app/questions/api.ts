// 담당: A
import { fetcher } from '@/lib/fetcher';
import type { Question, Answer } from './types';

// TODO: 실제 엔드포인트로 교체
export const questionApi = {
  getAll: () => fetcher.get<Question[]>('/questions'),
  getById: (id: string) => fetcher.get<Question>(`/questions/${id}`),
  create: (data: Partial<Question>) => fetcher.post<Question>('/questions', data),
  update: (id: string, data: Partial<Question>) => fetcher.put<Question>(`/questions/${id}`, data),
  remove: (id: string) => fetcher.delete(`/questions/${id}`),
};

export const answerApi = {
  getByQuestion: (questionId: string) => fetcher.get<Answer[]>(`/questions/${questionId}/answers`),
  create: (questionId: string, data: Partial<Answer>) =>
    fetcher.post<Answer>(`/questions/${questionId}/answers`, data),
  update: (questionId: string, id: string, data: Partial<Answer>) =>
    fetcher.put<Answer>(`/questions/${questionId}/answers/${id}`, data),
  remove: (questionId: string, id: string) =>
    fetcher.delete(`/questions/${questionId}/answers/${id}`),
};
