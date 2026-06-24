import { fetcher } from '@/lib/fetcher';
import type { AdminCourseResponse, ChangeCourseStatusRequest } from './types';

export const adminCourseApi = {
  getCourses: () => fetcher.get<AdminCourseResponse[]>('/api/admin/courses'),
  changeStatus: (courseId: number, data: ChangeCourseStatusRequest) =>
    fetcher.patch<void>(`/api/courses/${courseId}/status`, data),
  remove: (courseId: number) => fetcher.delete(`/api/courses/${courseId}`),
};