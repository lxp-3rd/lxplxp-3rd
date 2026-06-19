// 담당: B
import { fetcher } from '@/lib/fetcher';
import type { Roadmap } from './types';

// TODO: 실제 엔드포인트로 교체
export const roadmapApi = {
  getAll: () => fetcher.get<Roadmap[]>('/roadmaps'),
  getById: (id: string) => fetcher.get<Roadmap>(`/roadmaps/${id}`),
  create: (data: Partial<Roadmap>) => fetcher.post<Roadmap>('/roadmaps', data),
  update: (id: string, data: Partial<Roadmap>) => fetcher.put<Roadmap>(`/roadmaps/${id}`, data),
  remove: (id: string) => fetcher.delete(`/roadmaps/${id}`),
};
