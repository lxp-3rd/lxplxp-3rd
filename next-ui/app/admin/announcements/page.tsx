'use client';

import Link from 'next/link';
import { PageHeader } from '@/components/ui';
import { AdminPageContainer } from '../components/AdminPageContainer';
import { AdminAnnouncementTable } from './components/AdminAnnouncementTable';
import { useAdminAnnouncements } from './useAdminAnnouncements';

export default function AdminAnnouncementsPage() {
  const { announcements, isLoading, error } = useAdminAnnouncements();

  if (isLoading) {
    return <AdminPageContainer className="pb-xl">Loading...</AdminPageContainer>;
  }

  if (error) {
    return <AdminPageContainer className="pb-xl">Failed to load announcements.</AdminPageContainer>;
  }

  return (
    <AdminPageContainer className="pb-xl">
      <PageHeader
        title="공지사항 관리"
        subtitle={`플랫폼의 모든 주요 안내 및 소식을 관리합니다. 총 ${announcements.length}건`}
        actions={(
          <Link
            href="/admin/announcements/new"
            className="inline-flex items-center gap-xs px-lg py-md bg-primary text-on-primary font-label-md text-label-md rounded-lg shadow-sm hover:brightness-110 transition-all"
          >
            <span className="material-symbols-outlined text-[20px]">edit_square</span>
            공지사항 작성
          </Link>
        )}
      />

      <AdminAnnouncementTable announcements={announcements} />
    </AdminPageContainer>
  );
}
