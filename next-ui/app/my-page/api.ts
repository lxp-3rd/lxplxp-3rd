import { fetcher } from '@/lib/fetcher';
import type {
  ChangePasswordRequest,
  MyProfileResponse,
  UpdateMyProfileRequest,
  WithdrawMemberRequest,
} from './types';

export const myPageApi = {
  getProfile: () => fetcher.get<MyProfileResponse>('/api/members/me'),
  updateProfile: (data: UpdateMyProfileRequest) =>
    fetcher.patch<MyProfileResponse>('/api/members/me', data),
  changePassword: (data: ChangePasswordRequest) =>
    fetcher.patch<void>('/api/members/me/password', data),
  withdraw: (data: WithdrawMemberRequest) =>
    fetcher.delete<void>('/api/members/me', data),
};
