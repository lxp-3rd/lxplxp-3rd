import type { AdminInstructorApplication } from '../../types';
import { InstructorApplicationStatus } from './InstructorApplicationStatus';

interface InstructorApplicationDetailProps {
  application: AdminInstructorApplication | null;
  onApprove: (id: string) => void;
  onRejectClick: (application: AdminInstructorApplication) => void;
}

export function InstructorApplicationDetail({
  application,
  onApprove,
  onRejectClick,
}: InstructorApplicationDetailProps) {
  if (!application) {
    return (
      <aside className="bg-surface-container-lowest border border-outline-variant rounded-xl p-lg">
        <p className="text-body-md text-on-surface-variant">검토할 강사 신청을 선택하세요.</p>
      </aside>
    );
  }

  return (
    <aside className="bg-surface-container-lowest border border-outline-variant rounded-xl p-lg">
      <div className="flex items-start justify-between gap-md mb-md">
        <div>
          <h2 className="text-title-lg font-title-lg text-on-surface">{application.displayName}</h2>
          <p className="text-body-md text-on-surface-variant">@{application.nickname}</p>
        </div>
        <InstructorApplicationStatus status={application.status} />
      </div>

      <dl className="space-y-md">
        <div>
          <dt className="text-label-md text-on-surface-variant">전문 분야</dt>
          <dd className="text-body-md text-on-surface">{application.expertise}</dd>
        </div>
        <div>
          <dt className="text-label-md text-on-surface-variant">신청일</dt>
          <dd className="text-body-md text-on-surface">{application.appliedAt}</dd>
        </div>
        <div>
          <dt className="text-label-md text-on-surface-variant">소개</dt>
          <dd className="text-body-md text-on-surface-variant leading-relaxed">{application.bio}</dd>
        </div>
        {application.rejectReason && (
          <div>
            <dt className="text-label-md text-error">반려 사유</dt>
            <dd className="text-body-md text-on-surface-variant">{application.rejectReason}</dd>
          </div>
        )}
      </dl>

      {application.status === 'PENDING' && (
        <div className="flex gap-sm mt-lg">
          <button
            type="button"
            onClick={() => onApprove(application.id)}
            className="px-lg py-sm bg-primary text-on-primary rounded-lg font-label-md hover:brightness-110"
          >
            승인
          </button>
          <button
            type="button"
            onClick={() => onRejectClick(application)}
            className="px-lg py-sm border border-error text-error rounded-lg font-label-md hover:bg-error-container"
          >
            반려
          </button>
        </div>
      )}
    </aside>
  );
}
