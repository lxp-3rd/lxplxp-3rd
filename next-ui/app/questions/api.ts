// 담당: A
import { fetcher } from '@/lib/fetcher';
import type { LegacyQuestion, Question } from './types';
import { MOCK_QUESTIONS, getQuestionById as findById } from './mockData';

export const questionApi = {
  getAll: () => fetcher.get<Question[]>('/api/questions'),
  getByCourse: (courseId: number) => fetcher.get<Question[]>(`/api/questions?courseId=${courseId}`),
  getById: (id: number) => fetcher.get<Question>(`/api/questions/${id}`),
  create: (data: Partial<Question>) => fetcher.post<Question>('/api/questions', data),
  update: (id: number, data: Partial<Question>) => fetcher.put<Question>(`/api/questions/${id}`, data),
  remove: (id: number, memberId: number) => fetcher.delete(`/api/questions/${id}?memberId=${memberId}`),
};

// Mock 헬퍼 (백엔드 연동 전 개발용)
export const getQuestions = (courseId?: string): LegacyQuestion[] =>
  courseId ? MOCK_QUESTIONS.filter((q) => q.courseId === courseId) : [...MOCK_QUESTIONS];

export const getQuestionById = (id: string): LegacyQuestion | null => findById(id);
