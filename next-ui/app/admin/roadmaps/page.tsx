'use client';

import { PageHeader } from '@/components/ui';
import { AdminPageContainer } from '../components/AdminPageContainer';
import { AdminRoadmapTable } from './components/AdminRoadmapTable';
import { useAdminRoadmaps } from './useAdminRoadmaps';

export default function AdminRoadmapsPage() {
  const { roadmaps, isLoading, error, toggleHidden, removeRoadmap } = useAdminRoadmaps();

  if (isLoading) {
    return <AdminPageContainer>Loading...</AdminPageContainer>;
  }

  if (error) {
    return <AdminPageContainer>Failed to load roadmaps.</AdminPageContainer>;
  }

  return (
    <AdminPageContainer>
      <PageHeader
        title="로드맵 관리"
        subtitle="플랫폼에 등록된 로드맵을 관리합니다."
      />
      <AdminRoadmapTable
        roadmaps={roadmaps}
        onToggleHidden={toggleHidden}
        onRemove={removeRoadmap}
      />
    </AdminPageContainer>
  );
}
