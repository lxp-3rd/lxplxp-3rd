import { fetcher } from '@/lib/fetcher';
import type { AdminMemberResponse, ChangeMemberStatusRequest } from './types';

export const adminMemberApi = {
  getMembers: () => fetcher.get<AdminMemberResponse[]>('/api/admin/members'),
  getMember: (memberId: number) => fetcher.get<AdminMemberResponse>(`/api/admin/members/${memberId}`),
  changeStatus: (memberId: number, data: ChangeMemberStatusRequest) =>
    fetcher.patch<AdminMemberResponse>(`/api/admin/members/${memberId}/status`, data),
};
