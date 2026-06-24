'use client';

import { PageHeader } from '@/components/ui';
import { AdminPageContainer } from '../components/AdminPageContainer';
import { AdminRoadmapTable } from './components/AdminRoadmapTable';
import { useAdminRoadmaps } from './useAdminRoadmaps';

export default function AdminRoadmapsPage() {
  const { roadmaps, toggleHidden, removeRoadmap } = useAdminRoadmaps();

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
