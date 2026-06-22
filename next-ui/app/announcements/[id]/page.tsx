'use client';

import Link from 'next/link';
import { useParams } from 'next/navigation';

import { TopNavBar } from '@/components/TopNavBar';
import { Footer } from '@/components/Footer';
import { getAnnouncementById, MOCK_ANNOUNCEMENTS } from '../mockData';

export default function AnnouncementDetailPage() {
  const params = useParams();
  const id = Array.isArray(params.id) ? params.id[0] : (params.id as string);
  const announcement = getAnnouncementById(id);

  const recentAnnouncements = MOCK_ANNOUNCEMENTS.filter((a) => a.id !== id).slice(0, 3);

  if (!announcement) {
    return (
      <>
        <TopNavBar />
        <main className="pt-16 min-h-screen bg-background flex items-center justify-center">
          <div className="text-center">
            <h1 className="text-headline-lg font-headline-lg text-on-surface mb-md">공지를 찾을 수 없습니다</h1>
            <Link href="/announcements" className="text-primary hover:underline text-label-md font-label-md">목록으로 돌아가기</Link>
          </div>
        </main>
        <Footer />
      </>
    );
  }

  return (
    <>
      <TopNavBar />
      <main className="pt-16 min-h-screen bg-background">
        <div className="pt-xl pb-xl mt-16 px-gutter max-w-[1200px] mx-auto">

          <div className="mb-lg flex items-center gap-xs">
            <Link href="/announcements" className="flex items-center gap-xs text-on-surface-variant hover:text-primary transition-all group">
              <span className="material-symbols-outlined text-[20px]">arrow_back</span>
              <span className="text-label-md">목록으로 돌아가기</span>
            </Link>
          </div>

          <div className="grid grid-cols-12 gap-gutter">

            <article className="col-span-12 lg:col-span-9">
              <div className="bg-surface-container-lowest border border-outline-variant p-lg lg:p-xl rounded-xl">

                <div className="mb-lg">
                  <span className="bg-secondary-container text-on-secondary-container px-md py-xs rounded-full text-label-sm font-label-sm">
                    {announcement.category}
                  </span>
                  <h1 className="text-headline-lg font-headline-lg text-on-surface mt-sm mb-sm">
                    {announcement.title}
                  </h1>
                  <div className="flex items-center gap-md text-on-surface-variant">
                    <div className="flex items-center gap-xs">
                      <span className="material-symbols-outlined text-[16px]">person</span>
                      <span className="text-label-md font-label-md">{announcement.author}</span>
                    </div>
                    <div className="w-1 h-1 bg-outline-variant rounded-full"></div>
                    <div className="flex items-center gap-xs">
                      <span className="material-symbols-outlined text-[16px]">calendar_today</span>
                      <span className="text-label-md font-label-md">{announcement.createdAt}</span>
                    </div>
                    <div className="w-1 h-1 bg-outline-variant rounded-full"></div>
                    <div className="flex items-center gap-xs">
                      <span className="material-symbols-outlined text-[16px]">visibility</span>
                      <span className="text-label-md font-label-md">{announcement.views.toLocaleString()}회 조회</span>
                    </div>
                  </div>
                </div>

                <hr className="border-outline-variant mb-lg" />

                <div className="text-body-lg text-on-surface-variant leading-relaxed whitespace-pre-wrap">
                  {announcement.content}
                </div>

              </div>
            </article>

            <aside className="col-span-12 lg:col-span-3 space-y-lg">
              <div className="bg-surface-container-lowest border border-outline-variant p-lg rounded-xl">
                <h4 className="text-label-md text-on-surface-variant uppercase tracking-wider mb-md">최근 공지사항</h4>
                <ul className="space-y-md">
                  {recentAnnouncements.map((a, i) => (
                    <li key={a.id} className={i > 0 ? 'border-t border-outline-variant pt-md' : ''}>
                      <Link href={`/announcements/${a.id}`} className="group block">
                        <p className="text-[14px] font-semibold text-on-surface line-clamp-2 group-hover:text-primary mb-1">{a.title}</p>
                        <span className="text-label-sm text-on-surface-variant">{a.createdAt}</span>
                      </Link>
                    </li>
                  ))}
                </ul>
                <Link href="/announcements" className="block w-full mt-lg text-primary text-label-md hover:underline font-bold text-center">전체 보기</Link>
              </div>
            </aside>

          </div>
        </div>
      </main>
      <Footer />
    </>
  );
}
