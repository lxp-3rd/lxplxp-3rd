'use client';

import Link from 'next/link';
import { useRouter } from 'next/navigation';
import { TopNavBar } from '@/components/TopNavBar';
import { Footer } from '@/components/Footer';
import { PageHeader } from '@/components/ui';
import { AnnouncementCard } from './components/AnnouncementCard';
import { MOCK_ANNOUNCEMENTS, isNewAnnouncement } from './mockData';
// TODO: API 연동 필요

export default function AnnouncementsPage() {
  const router = useRouter();
  const featured = MOCK_ANNOUNCEMENTS[0];
  const tableRows = MOCK_ANNOUNCEMENTS;

  return (
    <>
      <TopNavBar />
      <main className="pt-16 min-h-screen bg-background">
        <div className="pt-xxl pb-xxl min-h-screen">
          <div className="max-w-container-max mx-auto px-gutter mt-16">

            <PageHeader
              title="공지사항"
              subtitle="LXP의 새로운 소식과 학습 안내를 확인하세요."
            />

            <div className="grid grid-cols-1 md:grid-cols-12 gap-lg mb-xxl">
              <div className="md:col-span-8">
                <AnnouncementCard data={featured} isNew={isNewAnnouncement(featured.createdAt)} />
              </div>
            </div>

            <div className="bg-surface-container-lowest border border-outline-variant rounded-xl overflow-hidden mb-xl">
              <div className="overflow-x-auto">
                <table className="w-full text-left border-collapse">
                  <thead className="bg-surface-container-low border-b border-outline-variant">
                    <tr>
                      <th className="px-xl py-lg font-label-md text-label-md text-on-surface-variant uppercase tracking-wider w-[10%] text-center">번호</th>
                      <th className="px-xl py-lg font-label-md text-label-md text-on-surface-variant uppercase tracking-wider">제목</th>
                      <th className="px-xl py-lg font-label-md text-label-md text-on-surface-variant uppercase tracking-wider w-[15%]">작성자</th>
                      <th className="px-xl py-lg font-label-md text-label-md text-on-surface-variant uppercase tracking-wider w-[15%]">날짜</th>
                    </tr>
                  </thead>
                  <tbody className="divide-y divide-outline-variant">
                    {tableRows.map((announcement, idx) => (
                      <tr
                        key={announcement.id}
                        className="hover:bg-surface-container-lowest transition-colors cursor-pointer group"
                        onClick={() => router.push(`/announcements/${announcement.id}`)}
                      >
                        <td className="px-xl py-lg text-center font-body-md text-body-md text-on-surface-variant">
                          {tableRows.length - idx}
                        </td>
                        <td className="px-xl py-lg">
                          <div className="flex items-center gap-sm">
                            <span className="font-label-md text-label-md text-on-surface group-hover:text-primary transition-colors">
                              {announcement.title}
                            </span>
                            {isNewAnnouncement(announcement.createdAt) && (
                              <span className="bg-primary-container/20 text-primary text-[10px] px-1.5 py-0.5 rounded font-bold">NEW</span>
                            )}
                          </div>
                        </td>
                        <td className="px-xl py-lg font-body-md text-body-md text-on-surface-variant">{announcement.authorName}</td>
                        <td className="px-xl py-lg font-body-md text-body-md text-on-surface-variant">{announcement.createdAt}</td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>
            </div>

            <div className="flex justify-center items-center gap-xs">
              <button className="w-10 h-10 flex items-center justify-center rounded-lg border border-outline-variant hover:bg-surface-container transition-colors disabled:opacity-30" disabled>
                <span className="material-symbols-outlined">chevron_left</span>
              </button>
              <button className="w-10 h-10 flex items-center justify-center rounded-lg bg-primary text-on-primary font-label-md text-label-md">1</button>
              <button className="w-10 h-10 flex items-center justify-center rounded-lg border border-outline-variant hover:bg-surface-container transition-colors">
                <span className="material-symbols-outlined">chevron_right</span>
              </button>
            </div>

          </div>
        </div>
      </main>
      <Footer />
    </>
  );
}
