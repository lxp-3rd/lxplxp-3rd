'use client';

import {
  createContext,
  useContext,
  useEffect,
  useState,
  ReactNode,
} from 'react';
import { useRouter } from 'next/navigation';
import {
  Role,
  MockUser,
  ROLE_HOME,
  ROLE_LABEL,
} from './mockAuth';

const ACCESS_TOKEN_STORAGE_KEY = 'lxp_access_token';
const REFRESH_TOKEN_STORAGE_KEY = 'lxp_refresh_token';
const AUTH_USER_STORAGE_KEY = 'lxp_auth_user';

type BackendRole = 'LEARNER' | 'INSTRUCTOR' | 'ADMIN';

export interface LoginSession {
  accessToken: string;
  refreshToken: string;
  email: string;
}

interface StoredAuthUser {
  id: string;
  email: string;
  nickname: string;
  role: Role;
}

interface JwtPayload {
  memberId?: number;
  role?: BackendRole;
}

interface AuthContextValue {
  user: MockUser | null;
  role: Role | null;
  isLoggedIn: boolean;
  isInitializing: boolean;
  accessToken: string | null;
  refreshToken: string | null;
  login: (session: LoginSession) => void;
  logout: () => void;
}

const AuthContext = createContext<AuthContextValue>({
  user: null,
  role: null,
  isLoggedIn: false,
  isInitializing: true,
  accessToken: null,
  refreshToken: null,
  login: () => {},
  logout: () => {},
});

function decodeJwtPayload(token: string): JwtPayload | null {
  try {
    const [, payload] = token.split('.');
    if (!payload) return null;
    const normalizedPayload = payload.replace(/-/g, '+').replace(/_/g, '/');
    const json = decodeURIComponent(
      atob(normalizedPayload)
        .split('')
        .map((char) => `%${char.charCodeAt(0).toString(16).padStart(2, '0')}`)
        .join('')
    );
    return JSON.parse(json) as JwtPayload;
  } catch {
    return null;
  }
}

function toFrontRole(role?: BackendRole): Role {
  if (role === 'ADMIN') return 'admin';
  if (role === 'INSTRUCTOR') return 'instructor';
  return 'member';
}

function readStoredUser(): StoredAuthUser | null {
  if (typeof window === 'undefined') return null;
  const rawUser = localStorage.getItem(AUTH_USER_STORAGE_KEY);
  if (!rawUser) return null;

  try {
    return JSON.parse(rawUser) as StoredAuthUser;
  } catch {
    localStorage.removeItem(AUTH_USER_STORAGE_KEY);
    return null;
  }
}

function toMockUser(storedUser: StoredAuthUser | null): MockUser | null {
  if (!storedUser) return null;
  const displayName = storedUser.nickname || storedUser.email || ROLE_LABEL[storedUser.role];

  return {
    id: storedUser.id,
    name: displayName,
    email: storedUser.email,
    nickname: storedUser.nickname,
    role: storedUser.role,
    avatarInitial: displayName.charAt(0) || 'U',
  };
}

export function AuthProvider({ children }: { children: ReactNode }) {
  const [accessToken, setAccessToken] = useState<string | null>(null);
  const [refreshToken, setRefreshToken] = useState<string | null>(null);
  const [storedUser, setStoredUser] = useState<StoredAuthUser | null>(null);
  const [isInitializing, setIsInitializing] = useState(true);
  const router = useRouter();

  useEffect(() => {
    setAccessToken(localStorage.getItem(ACCESS_TOKEN_STORAGE_KEY));
    setRefreshToken(localStorage.getItem(REFRESH_TOKEN_STORAGE_KEY));
    setStoredUser(readStoredUser());
    setIsInitializing(false);
  }, []);

  const login = (session: LoginSession) => {
    const payload = decodeJwtPayload(session.accessToken);
    const role = toFrontRole(payload?.role);
    const user: StoredAuthUser = {
      id: payload?.memberId ? String(payload.memberId) : session.email,
      email: session.email,
      nickname: session.email,
      role,
    };

    localStorage.setItem(ACCESS_TOKEN_STORAGE_KEY, session.accessToken);
    localStorage.setItem(REFRESH_TOKEN_STORAGE_KEY, session.refreshToken);
    localStorage.setItem(AUTH_USER_STORAGE_KEY, JSON.stringify(user));

    setAccessToken(session.accessToken);
    setRefreshToken(session.refreshToken);
    setStoredUser(user);
    setIsInitializing(false);
    router.push(ROLE_HOME[role]);
  };

  const logout = () => {
    localStorage.removeItem(ACCESS_TOKEN_STORAGE_KEY);
    localStorage.removeItem(REFRESH_TOKEN_STORAGE_KEY);
    localStorage.removeItem(AUTH_USER_STORAGE_KEY);
    setAccessToken(null);
    setRefreshToken(null);
    setStoredUser(null);
    setIsInitializing(false);
    router.push('/login');
  };

  const user = toMockUser(storedUser);
  const role = storedUser?.role ?? null;

  return (
    <AuthContext.Provider
      value={{
        user,
        role,
        isLoggedIn: !!accessToken,
        isInitializing,
        accessToken,
        refreshToken,
        login,
        logout,
      }}
    >
      {children}
    </AuthContext.Provider>
  );
}

export function useAuth() {
  return useContext(AuthContext);
}
