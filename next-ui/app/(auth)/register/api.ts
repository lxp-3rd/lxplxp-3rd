// 담당: E
import { fetcher } from '@/lib/fetcher';
import type { Auth } from './types';

// TODO: 실제 엔드포인트로 교체
export const authApi = {
  getAll: () => fetcher.get<Auth[]>('/auths'),
  getById: (id: string) => fetcher.get<Auth>(`/auths/${id}`),
  create: (data: Partial<Auth>) => fetcher.post<Auth>('/auths', data),
  update: (id: string, data: Partial<Auth>) => fetcher.put<Auth>(`/auths/${id}`, data),
  remove: (id: string) => fetcher.delete(`/auths/${id}`),
};
