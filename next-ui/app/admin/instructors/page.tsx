'use client';

import { PageHeader } from '@/components/ui';
import { AdminPageContainer } from '../components/AdminPageContainer';
import { AdminInstructorTable } from './components/AdminInstructorTable';
import { useAdminInstructors } from './useAdminInstructors';

export default function AdminInstructorsPage() {
  const { instructors, isLoading, error } = useAdminInstructors();

  if (isLoading) {
    return <AdminPageContainer className="mt-20">Loading...</AdminPageContainer>;
  }

  if (error) {
    return <AdminPageContainer className="mt-20">Failed to load instructors.</AdminPageContainer>;
  }

  return (
    <AdminPageContainer className="mt-20">
      <PageHeader
        title="강사 관리"
        subtitle="강사 자격, 강의 수 및 플랫폼 권한을 관리합니다."
      />
      <AdminInstructorTable instructors={instructors} />
    </AdminPageContainer>
  );
}
