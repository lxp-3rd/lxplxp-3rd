import type { AdminCourse } from '../types';

export const ADMIN_COURSE_STATUS_BADGE: Record<string, { label: string; cls: string }> = {
  PUBLISHED: { label: '공개', cls: 'bg-secondary-container text-on-secondary-container' },
  DRAFT: { label: '비공개', cls: 'bg-surface-container-high text-on-surface-variant' },
  ARCHIVED: { label: '보관됨', cls: 'bg-error-container text-on-error-container' },
};

export function getAdminCourseStatusBadge(status: AdminCourse['status']) {
  return ADMIN_COURSE_STATUS_BADGE[status] ?? ADMIN_COURSE_STATUS_BADGE.DRAFT;
}
