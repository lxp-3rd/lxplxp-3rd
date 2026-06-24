import { fetcher } from '@/lib/fetcher';
import type { RoadmapSummary } from './types';

export const roadmapApi = {
  getAll: () => fetcher.get<RoadmapSummary[]>('/api/roadmaps'),           // 공개
  getAvailable: () => fetcher.get<RoadmapSummary[]>('/api/roadmaps/available'),
  getParticipating: () => fetcher.get<RoadmapSummary[]>('/api/roadmaps/participating'),
  getCreated: () => fetcher.get<RoadmapSummary[]>('/api/roadmaps/created'),
  getById: (id: string) => fetcher.get<RoadmapSummary>(`/api/roadmaps/${id}`), // 공개
  participate: (id: string) => fetcher.post<void>(`/api/roadmaps/${id}/participate`, {}),
  create: (data: { name: string; introduction: string; courseIds: number[] }) =>
    fetcher.post<RoadmapSummary>('/api/roadmaps', data),
  update: (id: string, data: { name: string; introduction: string; courseIds: number[] }) =>
    fetcher.put<RoadmapSummary>(`/api/roadmaps/${id}`, data),
  remove: (id: string) => fetcher.delete(`/api/roadmaps/${id}`),
};
