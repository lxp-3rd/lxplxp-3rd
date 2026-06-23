import { Role } from './mockAuth';

export const ACCESS_TOKEN_STORAGE_KEY = 'lxp_access_token';
export const REFRESH_TOKEN_STORAGE_KEY = 'lxp_refresh_token';
export const AUTH_USER_STORAGE_KEY = 'lxp_auth_user';

export interface StoredAuthUser {
  id: string;
  email: string;
  nickname: string;
  role: Role;
}

function canUseStorage() {
  if (typeof window === 'undefined') return false;

  try {
    const testKey = '__lxp_storage_test__';
    window.localStorage.setItem(testKey, testKey);
    window.localStorage.removeItem(testKey);
    return true;
  } catch {
    return false;
  }
}

function getStorageItem(key: string) {
  if (!canUseStorage()) return null;

  try {
    return window.localStorage.getItem(key);
  } catch {
    return null;
  }
}

function setStorageItem(key: string, value: string) {
  if (!canUseStorage()) return;

  try {
    window.localStorage.setItem(key, value);
  } catch {
    // 저장소 접근이 막힌 환경에서는 인증 저장만 생략한다.
  }
}

function removeStorageItem(key: string) {
  if (!canUseStorage()) return;

  try {
    window.localStorage.removeItem(key);
  } catch {
    // 저장소 접근이 막힌 환경에서는 삭제 실패를 무시한다.
  }
}

export function getStoredAccessToken() {
  return getStorageItem(ACCESS_TOKEN_STORAGE_KEY);
}

export function getStoredRefreshToken() {
  return getStorageItem(REFRESH_TOKEN_STORAGE_KEY);
}

export function setStoredAccessToken(accessToken: string) {
  setStorageItem(ACCESS_TOKEN_STORAGE_KEY, accessToken);
}

export function setStoredRefreshToken(refreshToken: string) {
  setStorageItem(REFRESH_TOKEN_STORAGE_KEY, refreshToken);
}

export function getStoredAuthUser() {
  const rawUser = getStorageItem(AUTH_USER_STORAGE_KEY);
  if (!rawUser) return null;

  try {
    return JSON.parse(rawUser) as StoredAuthUser;
  } catch {
    removeStorageItem(AUTH_USER_STORAGE_KEY);
    return null;
  }
}

export function setStoredAuthUser(user: StoredAuthUser) {
  setStorageItem(AUTH_USER_STORAGE_KEY, JSON.stringify(user));
}

export function clearStoredAuth() {
  removeStorageItem(ACCESS_TOKEN_STORAGE_KEY);
  removeStorageItem(REFRESH_TOKEN_STORAGE_KEY);
  removeStorageItem(AUTH_USER_STORAGE_KEY);
}
