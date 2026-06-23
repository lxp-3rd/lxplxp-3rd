import {
  clearStoredAuth,
  getStoredAccessToken,
  getStoredRefreshToken,
  setStoredAccessToken,
} from './authStorage';

// 공통 HTTP 통신 설정
const BASE_URL = process.env.NEXT_PUBLIC_API_URL ?? '';

export class ApiError extends Error {
  constructor(
    message: string,
    public readonly status: number,
    public readonly body: string
  ) {
    super(message);
  }
}

async function reissueAccessToken() {
  const refreshToken = getStoredRefreshToken();
  if (!refreshToken) return null;

  const response = await fetch(`${BASE_URL}/api/auth/reissue`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ refreshToken }),
  });

  if (!response.ok) {
    clearStoredAuth();
    return null;
  }

  const data = await response.json() as { accessToken: string };
  setStoredAccessToken(data.accessToken);
  return data.accessToken;
}

async function request<T>(
  method: string,
  path: string,
  body?: unknown,
  retryOnUnauthorized = true
): Promise<T> {
  const headers: Record<string, string> = {};
  if (body !== undefined) {
    headers['Content-Type'] = 'application/json';
  }

  const accessToken = getStoredAccessToken();
  if (accessToken) {
    headers.Authorization = `Bearer ${accessToken}`;
  }

  const response = await fetch(`${BASE_URL}${path}`, {
    method,
    headers,
    body: body !== undefined ? JSON.stringify(body) : undefined,
  });

  if (response.status === 401 && retryOnUnauthorized && path !== '/api/auth/reissue') {
    const reissuedAccessToken = await reissueAccessToken();
    if (reissuedAccessToken) {
      return request<T>(method, path, body, false);
    }
  }

  if (!response.ok) {
    const errorBody = await response.text();
    throw new ApiError(`[${method}] ${path} → ${response.status}`, response.status, errorBody);
  }

  if (response.status === 204 || response.headers.get('content-length') === '0') {
    return undefined as T;
  }
  return response.json() as Promise<T>;
}

export const fetcher = {
  get:    <T>(path: string)                => request<T>('GET', path),
  post:   <T>(path: string, body?: unknown) => request<T>('POST', path, body),
  patch:  <T>(path: string, body?: unknown) => request<T>('PATCH', path, body),
  put:    <T>(path: string, body: unknown) => request<T>('PUT', path, body),
  delete: <T = void>(path: string, body?: unknown) => request<T>('DELETE', path, body),
};
