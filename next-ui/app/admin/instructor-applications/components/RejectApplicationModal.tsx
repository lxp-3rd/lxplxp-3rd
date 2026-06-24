import { useEffect } from 'react';

import type { AdminInstructorApplication } from '../../types';

interface RejectApplicationModalProps {
  target: AdminInstructorApplication | null;
  reason: string;
  onReasonChange: (reason: string) => void;
  onClose: () => void;
  onSubmit: (id: string, reason: string) => void;
}

export function RejectApplicationModal({
  target,
  reason,
  onReasonChange,
  onClose,
  onSubmit,
}: RejectApplicationModalProps) {
  const canSubmit = reason.trim().length > 0;

  useEffect(() => {
    if (!target) return;

    const handleKeyDown = (event: KeyboardEvent) => {
      if (event.key === 'Escape') onClose();
    };

    window.addEventListener('keydown', handleKeyDown);

    return () => {
      window.removeEventListener('keydown', handleKeyDown);
    };
  }, [onClose, target]);

  if (!target) return null;

  return (
    <div
      className="fixed inset-0 z-50 bg-black/40 flex items-center justify-center px-gutter"
      role="dialog"
      aria-modal="true"
      aria-labelledby="reject-application-title"
    >
      <div className="bg-surface-container-lowest rounded-xl border border-outline-variant p-lg w-full max-w-[480px] shadow-xl">
        <h2 id="reject-application-title" className="text-title-lg font-title-lg text-on-surface mb-sm">강사 신청 반려</h2>
        <p className="text-body-md text-on-surface-variant mb-md">
          {target.displayName}님의 신청을 반려하는 사유를 입력하세요.
        </p>
        <textarea
          className="w-full min-h-[120px] border border-outline-variant rounded-lg p-md bg-surface-container-lowest focus:outline-none focus:ring-2 focus:ring-primary-container"
          value={reason}
          onChange={(event) => onReasonChange(event.target.value)}
          aria-label="Reject reason"
          placeholder="반려 사유"
        />
        <div className="flex justify-end gap-sm mt-lg">
          <button
            type="button"
            onClick={onClose}
            className="px-lg py-sm border border-outline-variant rounded-lg text-on-surface-variant hover:bg-surface-container"
          >
            취소
          </button>
          <button
            type="button"
            onClick={() => {
              if (canSubmit) onSubmit(target.id, reason.trim());
            }}
            disabled={!canSubmit}
            className="px-lg py-sm bg-error text-on-error rounded-lg hover:brightness-110 disabled:opacity-50 disabled:cursor-not-allowed"
          >
            반려
          </button>
        </div>
      </div>
    </div>
  );
}
