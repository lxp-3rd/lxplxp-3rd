export type Role = 'member' | 'instructor' | 'admin';

export interface MockUser {
  id: string;
  name: string;
  email: string;
  role: Role;
  avatarInitial: string;
}

export const MOCK_USERS: Record<Role, MockUser> = {
  member: {
    id: 'u1',
    name: '김지혜',
    nickname: 'member@lxp.com',
    role: 'member',
    avatarInitial: '김',
  },
  instructor: {
    id: 'u2',
    name: '이하연',
    nickname: 'instructor@lxp.com',
    role: 'instructor',
    avatarInitial: '이',
  },
  admin: {
    id: 'u3',
    name: '관리자',
    nickname: 'admin@lxp.com',
    role: 'admin',
    avatarInitial: '관',
  },
};

export const ROLE_HOME: Record<Role, string> = {
  member: '/',
  instructor: '/courses/manage',
  admin: '/admin',
};

export const ROLE_LABEL: Record<Role, string> = {
  member: '일반 회원',
  instructor: '강사',
  admin: '관리자',
};

export const AUTH_STORAGE_KEY = 'lxp_mock_role';

export function getStoredRole(): Role | null {
  if (typeof window === 'undefined') return null;
  const stored = localStorage.getItem(AUTH_STORAGE_KEY);
  if (stored === 'member' || stored === 'instructor' || stored === 'admin') {
    return stored;
  }
  return null;
}

export function storeRole(role: Role): void {
  localStorage.setItem(AUTH_STORAGE_KEY, role);
}

export function clearRole(): void {
  localStorage.removeItem(AUTH_STORAGE_KEY);
}
