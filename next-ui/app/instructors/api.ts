// 담당: C
import { fetcher } from '@/lib/fetcher';
import type { Instructor } from './types';

// TODO: 실제 엔드포인트로 교체
export const instructorApi = {
  getAll: () => fetcher.get<Instructor[]>('/instructors'),
  getById: (id: string) => fetcher.get<Instructor>(`/instructors/${id}`),
  create: (data: Partial<Instructor>) => fetcher.post<Instructor>('/instructors', data),
  update: (id: string, data: Partial<Instructor>) => fetcher.put<Instructor>(`/instructors/${id}`, data),
  remove: (id: string) => fetcher.delete(`/instructors/${id}`),
};
