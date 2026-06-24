// 담당: D
import { fetcher } from '@/lib/fetcher';
import type { CreateEnrollmentRequest, Enrollment } from './types';

export const enrollmentApi = {
  getAll: () => fetcher.get<Enrollment[]>('/api/enrollments'),
  getMine: (memberId: number) => fetcher.get<Enrollment[]>(`/api/enrollments?memberId=${memberId}`),
  getByCourse: (courseId: number) => fetcher.get<Enrollment[]>(`/api/enrollments?courseId=${courseId}`),
  getById: (id: number) => fetcher.get<Enrollment>(`/api/enrollments/${id}`),
  create: (data: CreateEnrollmentRequest) => fetcher.post<Enrollment>('/api/enrollments', data),
  updateProgress: (id: number) => fetcher.post<Enrollment>(`/api/enrollments/${id}/progress`),
  remove: (id: number) => fetcher.delete<Enrollment>(`/api/enrollments/${id}`),
};
