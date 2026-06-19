// 담당: C
import { fetcher } from '@/lib/fetcher';
import type { InstructorApplication } from './types';

// TODO: 실제 엔드포인트로 교체
export const instructorapplicationApi = {
  getAll: () => fetcher.get<InstructorApplication[]>('/instructorapplications'),
  getById: (id: string) => fetcher.get<InstructorApplication>(`/instructorapplications/${id}`),
  create: (data: Partial<InstructorApplication>) => fetcher.post<InstructorApplication>('/instructorapplications', data),
  update: (id: string, data: Partial<InstructorApplication>) => fetcher.put<InstructorApplication>(`/instructorapplications/${id}`, data),
  remove: (id: string) => fetcher.delete(`/instructorapplications/${id}`),
};
