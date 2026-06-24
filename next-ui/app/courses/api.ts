// 담당: C
import { fetcher } from '@/lib/fetcher';
import { getStoredAccessToken } from '@/lib/authStorage';
import type { Course, CourseSummary, CourseDetail, CourseContent } from './types';

const BASE_URL = process.env.NEXT_PUBLIC_API_URL ?? '';

export type RegisterCourseData = {
  instructorId: number;
  title: string;
  description: string;
  thumbnailUrl?: string;
  contents: string[];
};

export const courseApi = {
  getAll: () => fetcher.get<CourseSummary[]>('/api/courses'),
  getById: (id: string) => fetcher.get<CourseDetail>(`/api/courses/${id}`),
  create: (data: RegisterCourseData) => fetcher.post<{ courseId: number }>('/api/courses', data),
  update: (id: string, data: Partial<Course>) => fetcher.put<Course>(`/api/courses/${id}`, data),
  remove: (id: string) => fetcher.delete(`/api/courses/${id}`),
};

export const contentApi = {
  getByCourse: (courseId: number) =>
    fetcher.get<CourseContent[]>(`/api/courses/${courseId}/contents`),
  getById: (courseId: number, contentId: number) =>
    fetcher.get<CourseContent>(`/api/courses/${courseId}/contents/${contentId}`),
};

export const imageApi = {
  upload: async (file: File): Promise<{ imageUrl: string }> => {
    const formData = new FormData();
    formData.append('image', file);

    const headers: Record<string, string> = {};
    const token = getStoredAccessToken();
    if (token) headers['Authorization'] = `Bearer ${token}`;

    const res = await fetch(`${BASE_URL}/api/images`, {
      method: 'POST',
      headers,
      body: formData,
    });
    if (!res.ok) throw new Error('이미지 업로드에 실패했습니다.');
    return res.json() as Promise<{ imageUrl: string }>;
  },
};

export const instructorApi = {
  getMe: () => fetcher.get<{ id: number }>('/api/instructors/me'),
};
