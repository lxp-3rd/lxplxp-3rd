import type { SignUpRequest, SignUpResponse } from './types';

async function postJson<T>(path: string, body: unknown): Promise<T> {
  const response = await fetch(path, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(body),
  });

  if (!response.ok) {
    throw new Error(await response.text());
  }

  return response.json() as Promise<T>;
}

export const authApi = {
  signUp: (data: SignUpRequest) => postJson<SignUpResponse>('/api/auth/signup', data),
};
