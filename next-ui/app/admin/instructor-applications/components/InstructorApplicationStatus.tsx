import type { AdminInstructorApplicationStatus } from '../../types';

const statusMap: Record<AdminInstructorApplicationStatus, { label: string; className: string; dot: string }> = {
  APPROVED: {
    label: '승인됨',
    className: 'text-green-600',
    dot: 'bg-green-600',
  },
  REJECTED: {
    label: '반려됨',
    className: 'text-error',
    dot: 'bg-error',
  },
  PENDING: {
    label: '대기 중',
    className: 'text-on-surface-variant',
    dot: 'bg-outline-variant',
  },
};

export function InstructorApplicationStatus({ status }: { status: AdminInstructorApplicationStatus }) {
  const item = statusMap[status];

  return (
    <span className={`flex items-center gap-xs text-label-sm font-label-sm ${item.className}`}>
      <span className={`w-2 h-2 rounded-full inline-block ${item.dot}`} />
      {item.label}
    </span>
  );
}
