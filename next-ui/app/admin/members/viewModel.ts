import type { AdminMemberRole, AdminMemberStatus } from './types';

export const ADMIN_MEMBER_ROLE_BADGE: Record<AdminMemberRole, { label: string; cls: string }> = {
  ADMIN: { label: '관리자', cls: 'bg-error-container text-on-error-container' },
  INSTRUCTOR: { label: '강사', cls: 'bg-secondary-container text-on-secondary-container' },
  LEARNER: { label: '학생', cls: 'bg-primary-container text-on-primary-container' },
};

export const ADMIN_MEMBER_STATUS_BADGE: Record<AdminMemberStatus, { label: string; cls: string }> = {
  ACTIVE: { label: '활성', cls: 'bg-secondary-container text-on-secondary-container' },
  SUSPENDED: { label: '정지', cls: 'bg-error-container/30 text-error' },
  WITHDRAWN: { label: '탈퇴', cls: 'bg-surface-container-high text-on-surface-variant' },
};

export const ADMIN_MEMBER_ROLE_LABEL: Record<AdminMemberRole, string> = {
  ADMIN: '관리자',
  INSTRUCTOR: '강사',
  LEARNER: '일반 회원',
};

export const ADMIN_MEMBER_STATUS_LABEL: Record<AdminMemberStatus, string> = {
  ACTIVE: '활성',
  SUSPENDED: '정지',
  WITHDRAWN: '탈퇴',
};

export const ADMIN_MEMBER_STATUS_OPTIONS: { value: AdminMemberStatus; label: string }[] = [
  { value: 'ACTIVE', label: '활성' },
  { value: 'SUSPENDED', label: '정지' },
  { value: 'WITHDRAWN', label: '탈퇴' },
];

export function normalizeAdminMemberId(id?: string | string[]) {
  const rawId = Array.isArray(id) ? id[0] : id;
  if (!rawId || !/^\d+$/.test(rawId)) return null;

  const memberId = Number(rawId);
  return Number.isSafeInteger(memberId) && memberId > 0 ? memberId : null;
}

export function formatAdminMemberDate(value?: string) {
  return value?.slice(0, 10) ?? '-';
}
