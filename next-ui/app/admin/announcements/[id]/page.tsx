'use client';

import Link from 'next/link';
import { useParams } from 'next/navigation';
import { AdminAnnouncementDetailView } from './components/AdminAnnouncementDetailView';
import { useAdminAnnouncementDetail } from './useAdminAnnouncementDetail';

export default function AdminAnnouncementDetailPage() {
  const params = useParams();
  const id = Array.isArray(params.id) ? params.id[0] : (params.id as string);
  const { announcement } = useAdminAnnouncementDetail(id);

  if (!announcement) {
    return (
      <div className="max-w-[1280px] mx-auto px-margin-desktop py-xl flex items-center justify-center min-h-[calc(100vh-128px)]">
        <div className="text-center">
          <h1 className="text-headline-lg font-headline-lg text-on-surface mb-md">공지를 찾을 수 없습니다</h1>
          <Link href="/admin/announcements" className="text-primary hover:underline text-label-md font-label-md">
            목록으로 돌아가기
          </Link>
        </div>
      </div>
    );
  }

  return <AdminAnnouncementDetailView announcement={announcement} />;
}
