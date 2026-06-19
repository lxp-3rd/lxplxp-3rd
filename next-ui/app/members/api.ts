// 담당: E
import { fetcher } from '@/lib/fetcher';
import type { Member } from './types';

// TODO: 실제 엔드포인트로 교체
export const memberApi = {
  getAll: () => fetcher.get<Member[]>('/members'),
  getById: (id: string) => fetcher.get<Member>(`/members/${id}`),
  create: (data: Partial<Member>) => fetcher.post<Member>('/members', data),
  update: (id: string, data: Partial<Member>) => fetcher.put<Member>(`/members/${id}`, data),
  remove: (id: string) => fetcher.delete(`/members/${id}`),
};
