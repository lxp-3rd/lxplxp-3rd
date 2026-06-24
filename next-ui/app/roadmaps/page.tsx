'use client';

import { useState, useEffect } from 'react';
import Link from 'next/link';

import { TopNavBar } from '@/components/TopNavBar';
import { Footer } from '@/components/Footer';
import { Button } from '@/components/ui';
import { roadmapApi } from './api';
import type { Roadmap, RoadmapSummary } from './types';
import { RoadmapCard } from './components/RoadmapCard';

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

const PLACEHOLDER_THUMBNAIL = 'https://www.gstatic.com/labs-code/stitch/stitch-placeholder-300x300.svg';

function toRoadmap(r: RoadmapSummary, isEnrolled: boolean): Roadmap {
  return {
    id: String(r.id),
    title: r.name,
    description: r.introduction,
    category: '',
    courseCount: r.courseIds.length,
    enrollmentCount: 0,
    createdBy: String(r.memberId),
    creatorName: '',
    progress: 0,
    isEnrolled,
    thumbnail: PLACEHOLDER_THUMBNAIL,
    createdAt: r.createdAt,
    updatedAt: r.updatedAt,
  };
}

export default function RoadmapsPage() {
  const [activeTab, setActiveTab] = useState<Tab>('available');
  const [available, setAvailable] = useState<Roadmap[]>([]);
  const [enrolled, setEnrolled] = useState<Roadmap[]>([]);
  const [mine, setMine] = useState<Roadmap[]>([]);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    setIsLoading(true);
    Promise.allSettled([
      roadmapApi.getAll(),          // 공개 — 비로그인도 조회 가능
      roadmapApi.getParticipating(), // 인증 필요 — 실패 시 빈 배열로 처리
      roadmapApi.getCreated(),       // 인증 필요 — 실패 시 빈 배열로 처리
    ]).then(([allResult, participResult, createdResult]) => {
      if (allResult.status === 'fulfilled') {
        setAvailable(allResult.value.map((r) => toRoadmap(r, false)));
      }
      if (participResult.status === 'fulfilled') {
        setEnrolled(participResult.value.map((r) => toRoadmap(r, true)));
      }
      if (createdResult.status === 'fulfilled') {
        setMine(createdResult.value.map((r) => toRoadmap(r, false)));
      }
    }).finally(() => setIsLoading(false));
  }, []);

  const visibleRoadmaps =
    activeTab === 'available' ? available
    : activeTab === 'enrolled' ? enrolled
    : mine;

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

          {isLoading ? (
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-lg">
              {Array.from({ length: 6 }).map((_, i) => (
                <div key={i} className="animate-pulse bg-surface-container rounded-xl overflow-hidden">
                  <div className="h-48 bg-surface-container-high" />
                  <div className="p-md space-y-sm">
                    <div className="h-5 w-3/4 bg-surface-container-high rounded" />
                    <div className="h-4 w-1/2 bg-surface-container-high rounded" />
                  </div>
                </div>
              ))}
            </div>
          ) : visibleRoadmaps.length === 0 ? (
            <div className="flex flex-col items-center justify-center gap-md py-[120px] text-on-surface-variant">
              <span className="material-symbols-outlined text-5xl">route</span>
              <p className="font-body-lg text-body-lg">로드맵이 없습니다.</p>
            </div>
          ) : (
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-lg">
              {visibleRoadmaps.map((roadmap) => (
                <RoadmapCard
                  key={roadmap.id}
                  data={roadmap}
                  showActions={activeTab === 'mine'}
                />
              ))}
            </div>
          )}

        </div>
      </main>
      <Footer />
    </>
  );
}
