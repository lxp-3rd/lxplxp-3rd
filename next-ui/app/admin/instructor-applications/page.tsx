'use client';

import { PageHeader } from '@/components/ui';
import { AdminPageContainer } from '../components/AdminPageContainer';
import { InstructorApplicationDetail } from './components/InstructorApplicationDetail';
import { InstructorApplicationTable } from './components/InstructorApplicationTable';
import { RejectApplicationModal } from './components/RejectApplicationModal';
import { useAdminInstructorApplications } from './useAdminInstructorApplications';

export default function AdminInstructorApplicationsPage() {
  const {
    applications,
    selected,
    rejectTarget,
    rejectInput,
    pendingCount,
    setSelected,
    setRejectInput,
    setRejectTarget,
    approve,
    reject,
    openRejectModal,
  } = useAdminInstructorApplications();

  return (
    <AdminPageContainer>
      <PageHeader
        title="강사 신청 목록"
        subtitle={`플랫폼의 새로운 강사 후보자 신청을 검토합니다. 대기 ${pendingCount}건`}
      />

      <div className="grid grid-cols-1 xl:grid-cols-[1fr_360px] gap-lg">
        <InstructorApplicationTable
          applications={applications}
          onSelect={setSelected}
        />
        <InstructorApplicationDetail
          application={selected}
          onApprove={approve}
          onRejectClick={openRejectModal}
        />
      </div>

      <RejectApplicationModal
        target={rejectTarget}
        reason={rejectInput}
        onReasonChange={setRejectInput}
        onClose={() => setRejectTarget(null)}
        onSubmit={reject}
      />
    </AdminPageContainer>
  );
}
