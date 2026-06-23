// 공통 HTTP 통신 설정
// TODO: axios 또는 ky 등 실제 라이브러리로 교체

const BASE_URL = process.env.NEXT_PUBLIC_API_URL ?? 'http://localhost:8080';

async function request<T>(method: string, path: string, body?: unknown): Promise<T> {
  const res = await fetch(`${BASE_URL}${path}`, {
    method,
    headers: { 'Content-Type': 'application/json' },
    body: body ? JSON.stringify(body) : undefined,
  });
  if (!res.ok) throw new Error(`[${method}] ${path} → ${res.status}`);
  if (res.status === 204 || res.headers.get('content-length') === '0') return undefined as T;
  return res.json() as Promise<T>;
}

export const fetcher = {
  get:    <T>(path: string)                    => request<T>('GET', path),
  post:   <T>(path: string, body: unknown)     => request<T>('POST', path, body),
  put:    <T>(path: string, body: unknown)     => request<T>('PUT', path, body),
  delete: (path: string)                       => request<void>('DELETE', path),
};
