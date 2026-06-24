import Link from 'next/link';
import { BackLink } from '@/components/ui';
import type { AdminAnnouncement } from '../../../types';

interface AdminAnnouncementDetailViewProps {
  announcement: AdminAnnouncement;
  onDelete: () => void;
  isDeleting: boolean;
}

function formatDate(iso: string) {
  return iso.slice(0, 10).replace(/-/g, '.');
}

export function AdminAnnouncementDetailView({ announcement, onDelete, isDeleting }: AdminAnnouncementDetailViewProps) {
  const idPadded = String(announcement.id).padStart(4, '0');

  return (
    <div className="max-w-[1280px] mx-auto px-margin-desktop py-xl min-h-[calc(100vh-128px)]">
      <BackLink href="/admin/announcements" className="mb-lg">
        공지사항 목록으로 돌아가기
      </BackLink>

      <article className="bento-card rounded-xl p-xl mb-lg">
        <div className="flex flex-col gap-sm mb-lg">
          <div className="flex items-center gap-sm">
            <span className={`px-md py-xs rounded-full font-label-sm text-label-sm ${announcement.status === 'PUBLISH' ? 'bg-secondary-container text-on-secondary-container' : 'bg-surface-container text-on-surface-variant'}`}>
              {announcement.status === 'PUBLISH' ? '공개' : '비공개'}
            </span>
            <span className="text-outline font-label-md text-label-md">ID: #NOT-{idPadded}</span>
          </div>
          <h1 className="font-headline-lg text-headline-lg text-on-surface tracking-tight">{announcement.title}</h1>
          <div className="flex flex-wrap items-center gap-md mt-xs">
            <MetaItem icon="calendar_today" label={formatDate(announcement.createdAt)} />
            <MetaItem icon="update" label={`수정: ${formatDate(announcement.updatedAt)}`} />
          </div>
        </div>

        <hr />

        <div className="content-area font-body-lg text-body-lg text-on-surface-variant leading-relaxed whitespace-pre-wrap py-lg">
          {announcement.content}
        </div>

        <div className="flex flex-col md:flex-row justify-between items-center mt-xl pt-xl border-t border-surface-container-highest gap-md">
          <div className="flex gap-md w-full md:w-auto">
            <Link href="/admin/announcements" className="flex-1 md:flex-none px-xl h-11 flex items-center justify-center border-2 border-outline-variant text-on-surface hover:bg-surface-container-low transition-all font-label-md text-label-md rounded-lg active:scale-95">
              목록
            </Link>
            <Link href={`/admin/announcements/${announcement.id}/edit`} className="flex-1 md:flex-none px-xl h-11 flex items-center justify-center border-2 border-primary-container bg-surface-container-lowest text-primary hover:bg-primary-fixed transition-all font-label-md text-label-md rounded-lg active:scale-95">
              수정
            </Link>
          </div>
          <button
            onClick={onDelete}
            disabled={isDeleting}
            className="w-full md:w-auto px-xl h-11 flex items-center justify-center border-2 border-error-container text-error hover:bg-error-container transition-all font-label-md text-label-md rounded-lg active:scale-95 disabled:opacity-50"
          >
            {isDeleting ? '삭제 중...' : '삭제'}
          </button>
        </div>
      </article>
    </div>
  );
}

function MetaItem({ icon, label }: { icon: string; label: string }) {
  return (
    <div className="flex items-center gap-xs">
      <span className="material-symbols-outlined text-[18px] text-primary">{icon}</span>
      <span className="font-label-md text-label-md text-on-surface-variant">{label}</span>
    </div>
  );
}
