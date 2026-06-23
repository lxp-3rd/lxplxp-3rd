import type { SignUpRequest, SignUpResponse } from './types';
import { fetcher } from '@/lib/fetcher';

export const authApi = {
  signUp: (data: SignUpRequest) => fetcher.post<SignUpResponse>('/api/auth/signup', data),
};
