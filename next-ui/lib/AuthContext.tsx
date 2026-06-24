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

interface MemberProfile {
  memberId: number;
  email: string;
  nickname: string;
  role: BackendRole;
}

interface AuthContextValue {
  user: MockUser | null;
  role: Role | null;
  isLoggedIn: boolean;
  isInitializing: boolean;
  accessToken: string | null;
  refreshToken: string | null;
  login: (session: LoginSession) => Promise<void>;
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
  login: async () => {},
  logout: async () => {},
  updateProfileUser: () => {},
});

function toFrontRole(role?: BackendRole): Role {
  if (role === 'ADMIN') return 'admin';
  if (role === 'INSTRUCTOR') return 'instructor';
  return 'member';
}

function toStoredAuthUser(profile: MemberProfile): StoredAuthUser {
  return {
    id: String(profile.memberId),
    email: profile.email,
    nickname: profile.nickname,
    role: toFrontRole(profile.role),
  };
}

async function getCurrentMemberProfile() {
  return fetcher.get<MemberProfile>('/api/members/me');
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
    let isMounted = true;

    const initializeAuth = async () => {
      const savedAccessToken = getStoredAccessToken();
      const savedRefreshToken = getStoredRefreshToken();

      if (!savedAccessToken || !savedRefreshToken) {
        clearStoredAuth();
        if (isMounted) {
          setAccessToken(null);
          setRefreshToken(null);
          setStoredUser(null);
          setIsInitializing(false);
        }
        return;
      }

      try {
        const profile = await getCurrentMemberProfile();
        const user = toStoredAuthUser(profile);
        setStoredAuthUser(user);

        if (isMounted) {
          setAccessToken(getStoredAccessToken());
          setRefreshToken(savedRefreshToken);
          setStoredUser(user);
        }
      } catch {
        clearStoredAuth();
        if (isMounted) {
          setAccessToken(null);
          setRefreshToken(null);
          setStoredUser(null);
        }
      } finally {
        if (isMounted) {
          setIsInitializing(false);
        }
      }
    };

    initializeAuth();

    return () => {
      isMounted = false;
    };
  }, []);

  const login = useCallback(async (session: LoginSession) => {
    setIsInitializing(true);
    setStoredAccessToken(session.accessToken);
    setStoredRefreshToken(session.refreshToken);

    try {
      const profile = await getCurrentMemberProfile();
      const user = toStoredAuthUser(profile);

      setStoredAuthUser(user);
      setAccessToken(getStoredAccessToken());
      setRefreshToken(session.refreshToken);
      setStoredUser(user);
      setIsInitializing(false);
      router.push(ROLE_HOME[user.role]);
    } catch (error) {
      clearStoredAuth();
      setAccessToken(null);
      setRefreshToken(null);
      setStoredUser(null);
      setIsInitializing(false);
      throw error;
    }
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
