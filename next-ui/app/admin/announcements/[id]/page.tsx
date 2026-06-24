'use client';

import Link from 'next/link';
import { useState } from 'react';
import { useParams, useRouter } from 'next/navigation';
import { AdminAnnouncementDetailView } from './components/AdminAnnouncementDetailView';
import { useAdminAnnouncementDetail } from './useAdminAnnouncementDetail';
import { adminAnnouncementApi } from '../../api';

export default function AdminAnnouncementDetailPage() {
  const params = useParams();
  const router = useRouter();
  const id = Array.isArray(params.id) ? params.id[0] : (params.id as string);
  const { announcement, isLoading, error } = useAdminAnnouncementDetail(id);
  const [isDeleting, setIsDeleting] = useState(false);

  const handleDelete = async () => {
    if (!confirm('이 공지사항을 삭제하시겠습니까?')) return;
    setIsDeleting(true);
    try {
      await adminAnnouncementApi.remove(id);
      router.push('/admin/announcements');
    } catch {
      alert('삭제에 실패했습니다. 다시 시도해 주세요.');
      setIsDeleting(false);
    }
  };

  if (isLoading) {
    return <div className="max-w-[1280px] mx-auto px-margin-desktop py-xl">Loading...</div>;
  }

  if (error) {
    return <div className="max-w-[1280px] mx-auto px-margin-desktop py-xl">Failed to load announcement.</div>;
  }

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

  return (
    <AdminAnnouncementDetailView
      announcement={announcement}
      onDelete={handleDelete}
      isDeleting={isDeleting}
    />
  );
}
