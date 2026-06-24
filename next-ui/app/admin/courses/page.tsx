'use client';

import { PageHeader } from '@/components/ui';
import { AdminPageContainer } from '../components/AdminPageContainer';
import { AdminCourseTable } from './components/AdminCourseTable';
import { useAdminCourses } from './useAdminCourses';

export default function AdminCoursesPage() {
  const { courses, isLoading, errorMessage, toggleVisibility, removeCourse } = useAdminCourses();

  if (isLoading) {
    return <AdminPageContainer>Loading...</AdminPageContainer>;
  }

  if (errorMessage) {
    return <AdminPageContainer>{errorMessage}</AdminPageContainer>;
  }

  return (
    <AdminPageContainer>
      <PageHeader
        title="강좌 관리"
        subtitle="학습 콘텐츠를 관리하고 수강생 등록 현황을 추적합니다."
      />
      <AdminCourseTable
        courses={courses}
        onToggleVisibility={toggleVisibility}
        onRemove={removeCourse}
      />
    </AdminPageContainer>
  );
}
