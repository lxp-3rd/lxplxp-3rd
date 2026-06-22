'use client';

import { useState } from 'react';
import Link from 'next/link';

import { TopNavBar } from '@/components/TopNavBar';
import { Footer } from '@/components/Footer';
// TODO: API 연동 필요

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

const ROADMAPS = [
  { id: '1', title: '풀스택 모던 웹 아키텍트',          courses: 12, members: 1240, progress: 35,  img: 'https://lh3.googleusercontent.com/aida-public/AB6AXuCgQqyRQ7SuKAPjnnITAyw_yepF6gQEKno0WY21e-FxmjhTlSUzqqzl3jLOzdAGBhw_U1ndwILwY_qOobveMUxeQ9Qy4sOxAnaq0U4sp-4CMSJ7C3f2G3bxPw0QMcSJcSF6Pf1jjuZvWH4DsRnzdYmiaWckwZ_au8dexALWyXWIzZZFMjhCji_KjH3JmCw_Aat_SV2tCwk9px4KpXWtDXwViyOxTMHnjYCuGuX_qpYJ9B2xG3ld2cMY_Xmk9eL1rSZ2u2uwhBmmealx' },
  { id: '2', title: '고급 머신러닝 마스터리',            courses: 8,  members: 850,  progress: 60,  img: 'https://lh3.googleusercontent.com/aida-public/AB6AXuCyNP4QvvkjrGPROGSKnMlkkiESHrlmVPheJa9LffgMRFtJwfh74MEjHQbdTM3d9gyzLwatKvu5TuU_lUN-aYN2x05YxETopNOY2mt2_Sl6zSXW2SzZQco1O08MCslxRTux8NqX1DqCgzOl6Coa5zHnri3NCVcV6SOjj9-scph82wVUxhh3bu7mVg5bi0NVVUhdkF3EydyS2haaPKHE98nTp6UfKaPCP8wuqcmCyV0YXZ0abEJtAUQl3stnlqms_fQrd9fD0BlQXcdW' },
  { id: '3', title: '프로덕트 디자인 전략 2024',         courses: 15, members: 3120, progress: 10,  img: 'https://lh3.googleusercontent.com/aida-public/AB6AXuBHIgSQIFZTxZZ96kAIGQKk1OKOBqcq101k_8J8y8luhctmyMsv0fz52rFPNBHVzhUMhFT3znaySpvghQfywO4BKKXuMyUXFZHGQ4k8fO4aERxONXHFD82Lk0pGHR5qhiQ3JOuYWmzdfzF4OxgHEsE7dTZhcqjClRTXkSfqPnVFdd98kEklk0xKEAVsa7QTF9QgfsMugZKGRYA-DJzXFEZf-qfYf3c0q9wgV7KQYRzjj0CC5ib54wlOM_OAogLzRhPVojhn_FaLAFYZ' },
  { id: '4', title: '디지털 트랜스포메이션 리더십',     courses: 6,  members: 420,  progress: 80,  img: 'https://lh3.googleusercontent.com/aida-public/AB6AXuBOflCVzT456kde8hx8X4R6Z1L9NQvesXUDXgflb3SN5lyDX3fXe7K8H4XKQC7noGnNXtlHHZ2DrR-698kqdqDVUJbUpYA3fZoOzCziz8sAQcR5FjOZVvlZAKE0CKIV_QENAYwfTI7xrM3VywH5IckVwtMz0hmrkM2nRYGdmYthOivV6bXGRXNtmElB54jbzcdgOhcGRVdWchxyzywzGF25J1mrhmSjaN1PefWnyIeYTfPQowPMNIVIJaMYsap2RotopPY9smBS1Ehk', category: '비즈니스' },
  { id: '5', title: '팀을 위한 사이버 보안 에센셜',     courses: 10, members: 2050, progress: 0,   img: 'https://lh3.googleusercontent.com/aida-public/AB6AXuBFeRNT2jIX0NwLhTV7s6rCojQVRg_-j-juFzjRI65QISW0gbA_5HEQK6un4vqgpnCea_iwvdfbrX9gF66llwIqUEL9wYD98dk2oVIsTk2p-IuCVFzzuROhE9PVoA3lLsfaSeJmN4MoWCd1DzTp0bmCth2NbG2qtVpL8iV4pr46QkEOABLiY37vxWwDBuc0S4657g-Sae5Uu5B7p4KTUiIMDTLMBvMTC60oQI1oEqKkn1rP2NIkzxUyUUY1c34b0nDjK5NAAQwZhYhh' },
  { id: '6', title: '클라우드 네이티브 아키텍처 (AWS/Azure)', courses: 14, members: 1580, progress: 50, img: 'https://lh3.googleusercontent.com/aida-public/AB6AXuCYf5C358gYRHTx04VU97ReutHchGpgDpgag-hJtXkZudxw56iSYAjD3YJv1ykzrHDwPeIvf9v_V-fVITdppsWuOYHj4O8IAurzNppA1KlkvbCJYaRTxqdHzsnI8A3xIO8xzpSz0OISCfdEOEoqkKrkUtx1Yd54PG8PR-BISijdTrVbLcM5jRm6rQ5HX_3ADJkAMLYY-uZZF0bSsYPwxLGt9XT8q37AaZotN9_a0py8Ahdm-S9vOhcXspLctFEC754nQAS8akD-siYt' },
];

export default function RoadmapsPage() {
  const [activeTab, setActiveTab] = useState<Tab>('available');

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
            {ROADMAPS.map((r) => (
              <div
                key={r.id}
                className="group relative bg-surface-container-lowest border border-surface-container-high rounded-xl overflow-hidden card-hover-effect transition-all duration-300 flex flex-col"
              >
                <Link href={`/roadmaps/${r.id}`} className="block h-48 relative">
                  <img className="w-full h-full object-cover" alt={r.title} src={r.img} />
                  {r.category && (
                    <div className="absolute top-4 left-4">
                      <span className="bg-secondary-container text-on-secondary-container px-3 py-1 rounded-full font-label-sm text-label-sm shadow-sm">{r.category}</span>
                    </div>
                  )}
                  {activeTab === 'enrolled' && (
                    <div className="absolute top-4 left-4">
                      <span className="bg-primary-container text-on-primary px-3 py-1 rounded-full font-label-sm text-label-sm shadow-sm">참여 중</span>
                    </div>
                  )}
                </Link>

                <div className="p-md flex-grow flex flex-col">
                  <h3 className="font-headline-md text-headline-md text-on-surface mb-sm group-hover:text-primary transition-colors line-clamp-1">{r.title}</h3>

                  <div className="flex items-center gap-lg mt-auto pb-md">
                    <div className="flex items-center gap-xs text-on-surface-variant">
                      <span className="material-symbols-outlined text-lg">menu_book</span>
                      <span className="font-label-md text-label-md">{r.courses}개 강좌</span>
                    </div>
                    <div className="flex items-center gap-xs text-on-surface-variant">
                      <span className="material-symbols-outlined text-lg">group</span>
                      <span className="font-label-md text-label-md">{r.members.toLocaleString()}명 참여</span>
                    </div>
                  </div>

                  {/* 탭별 하단 액션 */}
                  {activeTab === 'enrolled' && (
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

                  {activeTab === 'available' && (
                    <div className="pt-md border-t border-surface-container">
                      <button className="w-full py-2 rounded-lg bg-primary-container text-on-primary-container font-label-md text-label-md hover:opacity-90 transition-all">
                        참여하기
                      </button>
                    </div>
                  )}

                  {activeTab === 'mine' && (
                    <div className="flex items-center justify-between pt-md border-t border-surface-container">
                      <button className="font-label-md text-label-md text-primary hover:underline flex items-center gap-xs">
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
