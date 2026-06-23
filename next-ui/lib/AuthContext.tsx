'use client';

import {
  createContext,
  useCallback,
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
import { fetcher } from './fetcher';
import {
  clearStoredAuth,
  getStoredAccessToken,
  getStoredAuthUser,
  getStoredRefreshToken,
  setStoredAccessToken,
  setStoredAuthUser,
  setStoredRefreshToken,
  StoredAuthUser,
} from './authStorage';

type BackendRole = 'LEARNER' | 'INSTRUCTOR' | 'ADMIN';

export interface LoginSession {
  accessToken: string;
  refreshToken: string;
  email: string;
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
  logout: (redirectTo?: string) => Promise<void>;
  updateProfileUser: (profile: { memberId: number; email: string; nickname: string; role: string }) => void;
}

const AuthContext = createContext<AuthContextValue>({
  user: null,
  role: null,
  isLoggedIn: false,
  isInitializing: true,
  accessToken: null,
  refreshToken: null,
  login: () => {},
  logout: async () => {},
  updateProfileUser: () => {},
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
    const savedAccessToken = getStoredAccessToken();
    const savedRefreshToken = getStoredRefreshToken();
    const savedUser = getStoredAuthUser();

    if (savedAccessToken && savedRefreshToken && savedUser) {
      setAccessToken(savedAccessToken);
      setRefreshToken(savedRefreshToken);
      setStoredUser(savedUser);
    } else {
      clearStoredAuth();
      setAccessToken(null);
      setRefreshToken(null);
      setStoredUser(null);
    }

    setIsInitializing(false);
  }, []);

  const login = useCallback((session: LoginSession) => {
    const payload = decodeJwtPayload(session.accessToken);
    const role = toFrontRole(payload?.role);
    const user: StoredAuthUser = {
      id: payload?.memberId ? String(payload.memberId) : session.email,
      email: session.email,
      nickname: session.email,
      role,
    };

    setStoredAccessToken(session.accessToken);
    setStoredRefreshToken(session.refreshToken);
    setStoredAuthUser(user);

    setAccessToken(session.accessToken);
    setRefreshToken(session.refreshToken);
    setStoredUser(user);
    setIsInitializing(false);
    router.push(ROLE_HOME[role]);
  }, [router]);

  const logout = useCallback(async (redirectTo = '/login') => {
    const currentRefreshToken = refreshToken ?? getStoredRefreshToken();
    if (currentRefreshToken) {
      try {
        await fetcher.post<void>('/api/auth/logout', { refreshToken: currentRefreshToken });
      } catch {
        // 로컬 세션 정리는 실패 여부와 무관하게 진행한다.
      }
    }

    clearStoredAuth();
    setAccessToken(null);
    setRefreshToken(null);
    setStoredUser(null);
    setIsInitializing(false);
    router.push(redirectTo);
  }, [refreshToken, router]);

  const updateProfileUser = useCallback((profile: { memberId: number; email: string; nickname: string; role: string }) => {
    const role = toFrontRole(profile.role as BackendRole);
    const user: StoredAuthUser = {
      id: String(profile.memberId),
      email: profile.email,
      nickname: profile.nickname,
      role,
    };

    setStoredAuthUser(user);
    setStoredUser(user);
  }, []);

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
        updateProfileUser,
      }}
    >
      {children}
    </AuthContext.Provider>
  );
}

export function useAuth() {
  return useContext(AuthContext);
}
