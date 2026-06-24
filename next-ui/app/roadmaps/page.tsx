'use client';

import { useState } from 'react';
import Link from 'next/link';

import { TopNavBar } from '@/components/TopNavBar';
import { Footer } from '@/components/Footer';
import { Button } from '@/components/ui';
import { RoadmapCard } from './components/RoadmapCard';
import { MOCK_ROADMAPS } from './mockData';

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
        <div className="flex-grow max-w-[1280px] mx-auto w-full px-margin-desktop py-xl mt-16">

          <div className="flex flex-col gap-lg mb-xl">
            <div className="flex flex-col md:flex-row md:items-end justify-between gap-md">
              <div>
                <h1 className="font-headline-lg text-headline-lg text-on-surface">{TAB_TITLE[activeTab]}</h1>
                <p className="font-body-md text-body-md text-on-surface-variant mt-xs">나만의 맞춤형 학습 경로를 관리하고 확인하세요.</p>
              </div>
              {activeTab === 'mine' && (
                <Link href="/roadmaps/new">
                  <Button variant="secondary">
                    <span className="material-symbols-outlined">add</span>
                    새 로드맵 생성
                  </Button>
                </Link>
              )}
            </div>

            <div className="flex flex-wrap items-center gap-sm bg-surface-container-low p-1 rounded-full w-fit">
              {TABS.map((tab) => (
                <button
                  key={tab.key}
                  onClick={() => setActiveTab(tab.key)}
                  className={[
                    'px-lg py-2 rounded-full font-label-md text-label-md transition-all',
                    activeTab === tab.key
                      ? 'bg-primary-container text-on-primary-container shadow-sm'
                      : 'text-on-surface-variant hover:bg-surface-container',
                  ].join(' ')}
                >
                  {tab.label}
                </button>
              ))}
            </div>
          </div>

          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-lg">
            {visibleRoadmaps.map((roadmap) => (
              <RoadmapCard
                key={roadmap.id}
                data={roadmap}
                showActions={activeTab === 'mine'}
              />
            ))}
          </div>

        </div>
      </main>
      <Footer />
    </>
  );
}
