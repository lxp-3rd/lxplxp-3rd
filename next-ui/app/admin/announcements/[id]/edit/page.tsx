'use client';

import Link from 'next/link';
import { useParams } from 'next/navigation';
import { BackLink } from '@/components/ui';
import { AdminAnnouncementForm } from '../../components/AdminAnnouncementForm';
import { useAdminAnnouncementEdit } from './useAdminAnnouncementEdit';

export default function AdminAnnouncementEditPage() {
  const params = useParams();
  const id = Array.isArray(params.id) ? params.id[0] : (params.id as string);
  const form = useAdminAnnouncementEdit(id);

  if (form.isLoading) {
    return <div className="max-w-[1280px] mx-auto px-gutter py-xl">Loading...</div>;
  }

  if (form.error) {
    return <div className="max-w-[1280px] mx-auto px-gutter py-xl">Failed to load announcement.</div>;
  }

  if (!form.isFound) {
    return (
      <div className="max-w-[1280px] mx-auto px-gutter py-xl flex items-center justify-center">
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
    <div className="flex-grow pt-xl pb-xxl px-gutter max-w-[1280px] mx-auto w-full">
      <div className="flex flex-col md:flex-row md:items-end justify-between mb-xl gap-md">
        <div>
          <BackLink href="/admin/announcements" className="mb-xs">
            공지사항 목록으로 돌아가기
          </BackLink>
          <h1 className="font-headline-lg text-headline-lg text-on-surface">공지사항 수정</h1>
          <p className="font-body-md text-on-surface-variant mt-sm">공지사항 내용을 수정합니다.</p>
        </div>
        <div className="flex items-center gap-md">
          <button
            type="button"
            onClick={form.cancel}
            className="px-lg py-sm font-label-md text-on-surface-variant border border-outline-variant rounded-lg hover:bg-surface-container-low transition-colors"
          >
            취소
          </button>
          <button
            type="button"
            onClick={form.save}
            disabled={form.isSubmitting}
            className="px-lg py-sm font-label-md bg-primary text-on-primary rounded-lg shadow-sm hover:brightness-110 transition-all active:scale-95 disabled:opacity-50 disabled:cursor-not-allowed"
          >
            {form.isSubmitting ? '저장 중...' : '저장하기'}
          </button>
        </div>
      </div>

      {form.errorMessage && (
        <p className="mb-lg font-body-md text-error" role="alert">
          {form.errorMessage}
        </p>
      )}

      <AdminAnnouncementForm
        title={form.title}
        content={form.content}
        author={form.author}
        isPublic={form.isPublic}
        onTitleChange={form.setTitle}
        onContentChange={form.setContent}
        onAuthorChange={form.setAuthor}
        onPublicChange={form.setIsPublic}
      />
    </div>
  );
}
