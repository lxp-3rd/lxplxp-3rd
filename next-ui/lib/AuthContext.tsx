'use client';

import {
  createContext,
  useContext,
  useEffect,
  useState,
  ReactNode,
} from 'react';
import {
  Role,
  MockUser,
  MOCK_USERS,
  ROLE_HOME,
  getStoredRole,
  storeRole,
  clearRole,
} from './mockAuth';
import { useRouter } from 'next/navigation';

interface AuthContextValue {
  user: MockUser | null;
  role: Role | null;
  isLoggedIn: boolean;
  login: (role: Role) => void;
  logout: () => void;
}

const AuthContext = createContext<AuthContextValue>({
  user: null,
  role: null,
  isLoggedIn: false,
  login: () => {},
  logout: () => {},
});

export function AuthProvider({ children }: { children: ReactNode }) {
  const [role, setRole] = useState<Role | null>(null);
  const router = useRouter();

  useEffect(() => {
    const stored = getStoredRole();
    if (stored) setRole(stored);
  }, []);

  const login = (r: Role) => {
    storeRole(r);
    setRole(r);
    router.push(ROLE_HOME[r]);
  };

  const logout = () => {
    clearRole();
    setRole(null);
    router.push('/login');
  };

  const user = role ? MOCK_USERS[role] : null;

  return (
    <AuthContext.Provider value={{ user, role, isLoggedIn: !!role, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
}

export function useAuth() {
  return useContext(AuthContext);
}
