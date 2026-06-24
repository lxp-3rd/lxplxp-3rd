'use client';

import Link from 'next/link';
import { useParams } from 'next/navigation';
import { getAnnouncementById } from '@/app/announcements/mockData';

// TODO: API 연동 필요
export default function AdminAnnouncementDetailPage() {
  const params = useParams();
  const id = Array.isArray(params.id) ? params.id[0] : (params.id as string);
  const announcement = getAnnouncementById(id);

  if (!announcement) {
    return (
      <div className="max-w-[1280px] mx-auto px-margin-desktop py-xl flex items-center justify-center min-h-[calc(100vh-128px)]">
        <div className="text-center">
          <h1 className="text-headline-lg font-headline-lg text-on-surface mb-md">공지를 찾을 수 없습니다</h1>
          <Link href="/admin/announcements" className="text-primary hover:underline text-label-md font-label-md">목록으로 돌아가기</Link>
        </div>
      </div>
    );
  }

  return (

<div className="max-w-[1280px] mx-auto px-margin-desktop py-xl min-h-[calc(100vh-128px)]">

<div className="flex items-center gap-xs mb-lg group">
<a className="flex items-center text-on-surface-variant hover:text-primary transition-colors duration-150" href="/admin/announcements">
<span className="material-symbols-outlined text-[20px] mr-xs">arrow_back</span>
<span className="font-label-md text-label-md">공지사항 목록으로 돌아가기</span>
</a>
</div>
<div className="grid grid-cols-12 gap-gutter">

<div className="col-span-12 lg:col-span-9">
<article className="bento-card rounded-xl p-xl mb-lg">

<div className="flex flex-col gap-sm mb-lg">
<div className="flex items-center gap-sm">
<span className="bg-secondary-container text-on-secondary-container px-md py-xs rounded-full font-label-sm text-label-sm">{announcement.category}</span>
<span className="text-outline font-label-md text-label-md">ID: #NOT-{announcement.id.padStart(4, '0')}</span>
</div>
<h1 className="font-headline-lg text-headline-lg text-on-surface tracking-tight">{announcement.title}</h1>
<div className="flex items-center gap-md mt-xs">
<div className="flex items-center gap-xs">
<span className="material-symbols-outlined text-[18px] text-primary">person</span>
<span className="font-label-md text-label-md text-on-surface-variant">{announcement.authorName}</span>
</div>
<div className="w-1 h-1 bg-outline-variant rounded-full"></div>
<div className="flex items-center gap-xs">
<span className="material-symbols-outlined text-[18px] text-primary">calendar_today</span>
<span className="font-label-md text-label-md text-on-surface-variant">{announcement.createdAt}</span>
</div>
<div className="w-1 h-1 bg-outline-variant rounded-full"></div>
<div className="flex items-center gap-xs">
<span className="material-symbols-outlined text-[18px] text-primary">visibility</span>
<span className="font-label-md text-label-md text-on-surface-variant">{announcement.views.toLocaleString()}회 조회</span>
</div>
</div>
</div>

<hr />

<div className="content-area font-body-lg text-body-lg text-on-surface-variant leading-relaxed whitespace-pre-wrap">
{announcement.content}
</div>

<div className="flex flex-col md:flex-row justify-between items-center mt-xl pt-xl border-t border-surface-container-highest gap-md">
<div className="flex gap-md w-full md:w-auto">
<Link href="/admin/announcements" className="flex-1 md:flex-none px-xl h-11 flex items-center justify-center border-2 border-outline-variant text-on-surface hover:bg-surface-container-low transition-all font-label-md text-label-md rounded-lg active:scale-95">
  공지사항 목록으로 돌아가기
</Link>
<Link href={`/admin/announcements/${id}/edit`} className="flex-1 md:flex-none px-xl h-11 flex items-center justify-center border-2 border-primary-container bg-surface-container-lowest text-primary hover:bg-primary-fixed transition-all font-label-md text-label-md rounded-lg active:scale-95">
  공지사항 수정
</Link>
</div>
<button className="w-full md:w-auto px-xl h-11 flex items-center justify-center border-2 border-error-container text-error hover:bg-error-container transition-all font-label-md text-label-md rounded-lg active:scale-95">
<span className="material-symbols-outlined mr-xs text-[20px]">delete</span>
                        공지사항 삭제
                    </button>
</div>
</article>
</div>

<aside className="col-span-12 lg:col-span-3 space-y-lg">
<div className="bento-card rounded-xl p-lg">
<h3 className="font-label-md text-label-md text-on-surface mb-md">게시 상세 정보</h3>
<div className="space-y-md">
<div className="flex justify-between">
<span className="text-on-surface-variant font-label-md text-label-md">상태</span>
<span className="text-primary font-bold font-label-md text-label-md">게시됨</span>
</div>
<div className="flex justify-between">
<span className="text-on-surface-variant font-label-md text-label-md">대상</span>
<span className="text-on-surface font-label-md text-label-md">전체 사용자</span>
</div>
<div className="flex justify-between">
<span className="text-on-surface-variant font-label-md text-label-md">우선순위</span>
<span className="text-secondary font-label-md text-label-md">높음</span>
</div>
</div>
</div>

</aside>
</div>
</div>

  );
}
