// 담당: C
import { fetcher } from '@/lib/fetcher';
import type { Course, CourseSummary, CourseDetail, CourseContent } from './types';

export const courseApi = {
  getAll: () => fetcher.get<CourseSummary[]>('/api/courses'),
  getById: (id: string) => fetcher.get<CourseDetail>(`/api/courses/${id}`),
  create: (data: Partial<Course>) => fetcher.post<Course>('/api/courses', data),
  update: (id: string, data: Partial<Course>) => fetcher.put<Course>(`/api/courses/${id}`, data),
  remove: (id: string) => fetcher.delete(`/api/courses/${id}`),
};

export const contentApi = {
  getByCourse: (courseId: number) =>
    fetcher.get<CourseContent[]>(`/api/courses/${courseId}/contents`),
  getById: (courseId: number, contentId: number) =>
    fetcher.get<CourseContent>(`/api/courses/${courseId}/contents/${contentId}`),
};
