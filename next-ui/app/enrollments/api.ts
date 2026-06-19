// 담당: D
import { fetcher } from '@/lib/fetcher';
import type { Enrollment } from './types';

// TODO: 실제 엔드포인트로 교체
export const enrollmentApi = {
  getAll: () => fetcher.get<Enrollment[]>('/enrollments'),
  getById: (id: string) => fetcher.get<Enrollment>(`/enrollments/${id}`),
  create: (data: Partial<Enrollment>) => fetcher.post<Enrollment>('/enrollments', data),
  update: (id: string, data: Partial<Enrollment>) => fetcher.put<Enrollment>(`/enrollments/${id}`, data),
  remove: (id: string) => fetcher.delete(`/enrollments/${id}`),
};
