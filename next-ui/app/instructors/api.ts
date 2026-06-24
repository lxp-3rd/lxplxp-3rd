// 담당: C
import { fetcher } from '@/lib/fetcher';
import type { Instructor, InstructorProfile } from './types';

export const instructorApi = {
  getAll: () => fetcher.get<Instructor[]>('/api/instructors'),
  getById: (id: string) => fetcher.get<Instructor>(`/api/instructors/${id}`),
  getProfile: (instructorId: number) => fetcher.get<InstructorProfile>(`/api/instructors/${instructorId}/profile`),
  create: (data: Partial<Instructor>) => fetcher.post<Instructor>('/api/instructors', data),
  update: (id: string, data: Partial<Instructor>) => fetcher.put<Instructor>(`/api/instructors/${id}`, data),
  remove: (id: string) => fetcher.delete(`/api/instructors/${id}`),
};
