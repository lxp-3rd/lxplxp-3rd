// 담당: E
import { fetcher } from '@/lib/fetcher';
import type { AuthRequest } from './types';

// TODO: 실제 엔드포인트로 교체
export const authApi = {
  getAll: () => fetcher.get<AuthRequest[]>('/auths'),
  getById: (id: string) => fetcher.get<AuthRequest>(`/auths/${id}`),
  create: (data: Partial<AuthRequest>) => fetcher.post<AuthRequest>('/auths', data),
  update: (id: string, data: Partial<AuthRequest>) => fetcher.put<AuthRequest>(`/auths/${id}`, data),
  remove: (id: string) => fetcher.delete(`/auths/${id}`),
};
