import type { AdminCourseStatus } from './types';

export const ADMIN_COURSE_STATUS_BADGE: Record<AdminCourseStatus, { label: string; cls: string }> = {
  PUBLIC: { label: '공개', cls: 'bg-secondary-container text-on-secondary-container' },
  HIDDEN: { label: '비공개', cls: 'bg-surface-container-high text-on-surface-variant' },
  CLOSED: { label: '종료', cls: 'bg-error-container text-on-error-container' },
};

export function getAdminCourseStatusBadge(status: AdminCourseStatus) {
  return ADMIN_COURSE_STATUS_BADGE[status] ?? ADMIN_COURSE_STATUS_BADGE.HIDDEN;
}