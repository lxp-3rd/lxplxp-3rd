'use client';

import { BackLink } from '@/components/ui';
import { AdminAnnouncementForm } from '../components/AdminAnnouncementForm';
import { useAdminAnnouncementCreate } from './useAdminAnnouncementCreate';

export default function AdminAnnouncementNewPage() {
  const form = useAdminAnnouncementCreate();

  return (
    <div className="flex-grow pt-24 pb-12 px-gutter max-w-[1280px] mx-auto w-full">
      <div className="flex flex-col md:flex-row md:items-end justify-between mb-xl gap-md">
        <div>
          <BackLink href="/admin/announcements" className="mb-xs">
            공지사항 목록으로 돌아가기
          </BackLink>
          <h1 className="font-headline-lg text-headline-lg text-on-background">새 공지사항 작성</h1>
          <p className="font-body-md text-on-surface-variant mt-sm">LXP 플랫폼 사용자들에게 전달할 새로운 소식을 작성하세요.</p>
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
            className="px-lg py-sm font-label-md bg-primary-container text-white rounded-lg shadow-sm hover:shadow-md transition-all active:scale-95"
          >
            저장하기
          </button>
        </div>
      </div>

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
