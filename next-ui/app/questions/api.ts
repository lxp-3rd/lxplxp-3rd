// 담당: A
import { fetcher } from '@/lib/fetcher';
import type { Question, Answer } from './types';

// TODO: 실제 엔드포인트로 교체
export const questionApi = {
  getAll: () => fetcher.get<Question[]>('/api/questions'),
  getById: (id: string) => fetcher.get<Question>(`/api/questions/${id}`),
  create: (data: Partial<Question>) => fetcher.post<Question>('/api/questions', data),
  update: (id: string, data: Partial<Question>) => fetcher.put<Question>(`/api/questions/${id}`, data),
  remove: (id: string) => fetcher.delete(`/api/questions/${id}`),
};

export const answerApi = {
  getByQuestion: (questionId: string) => fetcher.get<Answer[]>(`/api/questions/${questionId}/answers`),
  create: (questionId: string, data: Partial<Answer>) =>
    fetcher.post<Answer>(`/api/questions/${questionId}/answers`, data),
  update: (questionId: string, id: string, data: Partial<Answer>) =>
    fetcher.put<Answer>(`/api/questions/${questionId}/answers/${id}`, data),
  remove: (questionId: string, id: string) =>
    fetcher.delete(`/api/questions/${questionId}/answers/${id}`),
};
