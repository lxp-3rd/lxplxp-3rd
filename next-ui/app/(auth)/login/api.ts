import type { LoginRequest, LoginResponse } from './types';
import { fetcher } from '@/lib/fetcher';

export const authApi = {
  login: (data: LoginRequest) => fetcher.post<LoginResponse>('/api/auth/login', data),
};
