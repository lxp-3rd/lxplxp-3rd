// 담당: C
import { fetcher } from '@/lib/fetcher';
import type { Course } from './types';

// TODO: 실제 엔드포인트로 교체
export const courseApi = {
  getAll: () => fetcher.get<Course[]>('/courses'),
  getById: (id: string) => fetcher.get<Course>(`/courses/${id}`),
  create: (data: Partial<Course>) => fetcher.post<Course>('/courses', data),
  update: (id: string, data: Partial<Course>) => fetcher.put<Course>(`/courses/${id}`, data),
  remove: (id: string) => fetcher.delete(`/courses/${id}`),
};
