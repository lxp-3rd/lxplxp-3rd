'use client';

import { useState } from 'react';
import Link from 'next/link';
import { useRouter } from 'next/navigation';

import { TopNavBar } from '@/components/TopNavBar';
import { Footer } from '@/components/Footer';
import { MOCK_ROADMAPS } from '@/app/roadmaps/mockData';

type Tab = 'available' | 'enrolled' | 'mine';

const TABS: { key: Tab; label: string }[] = [
  { key: 'available', label: '참여 가능한 로드맵' },
  { key: 'enrolled',  label: '참여 중인 로드맵' },
  { key: 'mine',      label: '내가 만든 로드맵' },
];

const TAB_TITLE: Record<Tab, string> = {
  available: '참여 가능한 로드맵',
  enrolled:  '참여 중인 로드맵',
  mine:      '내가 만든 로드맵',
};


export default function RoadmapsPage() {
  const [activeTab, setActiveTab] = useState<Tab>('available');
  const router = useRouter();

  const visibleRoadmaps = MOCK_ROADMAPS.filter((r) => {
    if (activeTab === 'available') return !r.isEnrolled;
    if (activeTab === 'enrolled')  return r.isEnrolled;
    if (activeTab === 'mine')      return r.createdBy === 'u2'; // 현재 사용자
    return false;
  });

  return (
    <>
      <TopNavBar />
      <main className="pt-16 min-h-screen bg-background">
        <div className="flex-grow max-w-[1280px] mx-auto w-full px-margin-desktop py-xl">

          <div className="flex flex-col gap-lg mb-xl">
            <div className="flex flex-col md:flex-row md:items-end justify-between gap-md">
              <div>
                <h1 className="font-headline-lg text-headline-lg text-on-surface">{TAB_TITLE[activeTab]}</h1>
                <p className="font-body-md text-body-md text-on-surface-variant mt-xs">나만의 맞춤형 학습 경로를 관리하고 확인하세요.</p>
              </div>
              {activeTab === 'mine' && (
                <Link
                  href="/roadmaps/new"
                  className="flex items-center gap-sm bg-primary-container text-on-primary-container px-lg py-3 rounded-xl font-label-md text-label-md hover:opacity-90 active:scale-95 transition-all shadow-md"
                >
                  <span className="material-symbols-outlined">add</span>
                  새 로드맵 생성
                </Link>
              )}
            </div>

            {/* 탭 */}
            <div className="flex flex-wrap items-center gap-sm bg-surface-container-low p-1 rounded-full w-fit">
              {TABS.map((tab) => (
                <button
                  key={tab.key}
                  onClick={() => setActiveTab(tab.key)}
                  className={`px-lg py-2 rounded-full font-label-md text-label-md transition-all ${
                    activeTab === tab.key
                      ? 'bg-primary-container text-on-primary-container shadow-sm'
                      : 'text-on-surface-variant hover:bg-surface-container'
                  }`}
                >
                  {tab.label}
                </button>
              ))}
            </div>
          </div>

          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-lg">
            {visibleRoadmaps.map((r) => (
              <div
                key={r.id}
                className="group relative bg-surface-container-lowest border border-surface-container-high rounded-xl overflow-hidden card-hover-effect transition-all duration-300 flex flex-col"
              >
                {/* 카드 전체 클릭 영역 */}
                <Link href={`/roadmaps/${r.id}`} className="absolute inset-0 z-10" aria-label={r.title} />

                {/* 썸네일 */}
                <div className="relative h-48">
                  <img className="w-full h-full object-cover" alt={r.title} src={r.thumbnail} />
                  {r.category && (
                    <div className="absolute top-4 left-4 z-20">
                      <span className="bg-secondary-container text-on-secondary-container px-3 py-1 rounded-full font-label-sm text-label-sm shadow-sm">{r.category}</span>
                    </div>
                  )}
                  {r.isEnrolled && (
                    <div className="absolute top-4 right-4 z-20">
                      <span className="bg-primary-container text-on-primary-container px-3 py-1 rounded-full font-label-sm text-label-sm shadow-sm">참여 중</span>
                    </div>
                  )}
                </div>

                {/* 콘텐츠 */}
                <div className="p-md flex-grow flex flex-col relative z-20 pointer-events-none">
                  <h3 className="font-headline-md text-headline-md text-on-surface mb-sm group-hover:text-primary transition-colors line-clamp-1">{r.title}</h3>

                  <div className="flex items-center gap-lg mt-auto pb-md">
                    <div className="flex items-center gap-xs text-on-surface-variant">
                      <span className="material-symbols-outlined text-lg">menu_book</span>
                      <span className="font-label-md text-label-md">{r.courseCount}개 강좌</span>
                    </div>
                    <div className="flex items-center gap-xs text-on-surface-variant">
                      <span className="material-symbols-outlined text-lg">group</span>
                      <span className="font-label-md text-label-md">{r.enrollmentCount.toLocaleString()}명 참여</span>
                    </div>
                  </div>

                  {r.isEnrolled && (
                    <div className="pt-md border-t border-surface-container">
                      <div className="flex justify-between items-center mb-xs">
                        <span className="text-label-sm font-label-sm text-on-surface-variant">진행률</span>
                        <span className="text-label-sm font-label-sm text-primary font-bold">{r.progress}%</span>
                      </div>
                      <div className="w-full h-1.5 bg-surface-container rounded-full overflow-hidden">
                        <div className="h-full bg-primary rounded-full" style={{ width: `${r.progress}%` }} />
                      </div>
                    </div>
                  )}

                  {activeTab === 'mine' && (
                    <div className="flex items-center justify-between pt-md border-t border-surface-container pointer-events-auto">
                      <button
                        onClick={() => router.push(`/roadmaps/${r.id}/edit`)}
                        className="font-label-md text-label-md text-primary hover:underline flex items-center gap-xs"
                      >
                        콘텐츠 수정 <span className="material-symbols-outlined text-sm">chevron_right</span>
                      </button>
                      <button className="p-2 text-error hover:bg-error-container rounded-lg transition-colors" title="로드맵 삭제">
                        <span className="material-symbols-outlined">delete</span>
                      </button>
                    </div>
                  )}
                </div>
              </div>
            ))}
          </div>

        </div>
      </main>
      <Footer />
    </>
  );
}
