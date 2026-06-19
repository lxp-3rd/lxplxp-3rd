// 담당: B
import { fetcher } from '@/lib/fetcher';
import type { Announcement } from './types';

// TODO: 실제 엔드포인트로 교체
export const announcementApi = {
  getAll: () => fetcher.get<Announcement[]>('/announcements'),
  getById: (id: string) => fetcher.get<Announcement>(`/announcements/${id}`),
  create: (data: Partial<Announcement>) => fetcher.post<Announcement>('/announcements', data),
  update: (id: string, data: Partial<Announcement>) => fetcher.put<Announcement>(`/announcements/${id}`, data),
  remove: (id: string) => fetcher.delete(`/announcements/${id}`),
};
