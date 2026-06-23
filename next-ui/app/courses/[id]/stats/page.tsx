'use client';

import Link from 'next/link';
import { useParams } from 'next/navigation';

import { TopNavBar } from '@/components/TopNavBar';
import { Footer } from '@/components/Footer';
// TODO: API 연동 필요

export default function CourseStatsPage() {
  const params = useParams();
  const id = Array.isArray(params.id) ? params.id[0] : (params.id as string);

  return (
    <>
      <TopNavBar />
      <main className="pt-16 min-h-screen bg-background">
        <div className="pt-24 pb-xl px-gutter max-w-[1280px] mx-auto">

          {/* 헤더 */}
          <div className="flex flex-col md:flex-row md:items-end justify-between mb-xl gap-md">
            <div>
              <nav className="flex items-center gap-xs text-on-surface-variant mb-xs">
                <span className="text-label-sm font-label-sm">강사 대시보드</span>
                <span className="material-symbols-outlined text-[16px]">chevron_right</span>
                <span className="text-label-sm font-label-sm">강의 통계</span>
              </nav>
              <h1 className="text-headline-lg font-headline-lg text-on-surface tracking-tight">AI 프로그래밍 기초 통계</h1>
            </div>
            <Link
              href={`/courses/${id}/edit`}
              className="flex items-center gap-sm px-lg py-md bg-surface border border-primary text-primary font-label-md text-label-md rounded-lg hover:bg-primary-fixed transition-all active:opacity-80"
            >
              <span className="material-symbols-outlined">edit</span>
              강의 편집으로 돌아가기
            </Link>
          </div>

          <div className="bento-grid">

            {/* 카드 1: 총 수강 인원 */}
            <div className="col-span-12 md:col-span-6 lg:col-span-4 bento-card p-lg flex flex-col justify-between min-h-[180px]">
              <div className="flex justify-between items-start">
                <div className="p-sm bg-primary-container/30 rounded-lg">
                  <span className="material-symbols-outlined text-primary" style={{ fontVariationSettings: "'FILL' 1" }}>group</span>
                </div>
                <span className="text-label-sm font-label-sm text-primary flex items-center bg-primary-container/20 px-sm py-xs rounded-full">
                  <span className="material-symbols-outlined text-[14px] mr-xs">trending_up</span>
                  +12% 이번 달
                </span>
              </div>
              <div className="mt-xl">
                <p className="text-label-md font-label-md text-on-surface-variant">총 수강 인원</p>
                <h2 className="text-display-lg font-display-lg text-on-surface mt-xs">1,284명</h2>
              </div>
            </div>

            {/* 카드 2: 평균 진행률 */}
            <div className="col-span-12 md:col-span-6 lg:col-span-4 bento-card p-lg min-h-[180px]">
              <div className="mb-md">
                <div className="p-sm bg-primary-container/30 rounded-lg w-fit">
                  <span className="material-symbols-outlined text-primary" style={{ fontVariationSettings: "'FILL' 1" }}>insights</span>
                </div>
              </div>
              <p className="text-label-md font-label-md text-on-surface-variant">평균 진행률</p>
              <div className="flex items-end gap-sm mt-xs">
                <h2 className="text-display-lg font-display-lg text-on-surface">68.5%</h2>
                <span className="text-label-md font-label-md text-on-surface-variant mb-sm">목표 대비 높음</span>
              </div>
              <div className="w-full h-2 bg-surface-container rounded-full mt-md overflow-hidden">
                <div className="h-full bg-primary rounded-full transition-all duration-700" style={{ width: '68.5%' }}></div>
              </div>
            </div>

            {/* 카드 3: 완료 수강생 수 */}
            <div className="col-span-12 md:col-span-6 lg:col-span-4 bento-card p-lg min-h-[180px]">
              <div className="mb-md">
                <div className="p-sm bg-surface-container-high rounded-lg w-fit">
                  <span className="material-symbols-outlined text-on-surface-variant" style={{ fontVariationSettings: "'FILL' 1" }}>verified</span>
                </div>
              </div>
              <p className="text-label-md font-label-md text-on-surface-variant">완료 수강생 수</p>
              <h2 className="text-display-lg font-display-lg text-on-surface mt-xs">412명</h2>
              <p className="text-body-md font-body-md text-on-surface-variant mt-sm">전체 인원의 32% 수료</p>
            </div>

            {/* 카드 4: 질문 통계 */}
            <div className="col-span-12 lg:col-span-8 bento-card p-lg flex flex-col md:flex-row gap-xl items-center">
              <div className="flex-1 w-full">
                <div className="flex items-center gap-sm mb-lg">
                  <div className="p-sm bg-primary-container/30 rounded-lg">
                    <span className="material-symbols-outlined text-primary">forum</span>
                  </div>
                  <p className="text-label-md font-label-md text-on-surface-variant">질문 통계 개요</p>
                </div>
                <div className="grid grid-cols-2 gap-lg">
                  <div>
                    <p className="text-label-sm font-label-sm text-on-surface-variant mb-xs">총 Q&A 질문 수</p>
                    <h3 className="text-headline-lg font-headline-lg text-on-surface">856건</h3>
                  </div>
                  <div>
                    <p className="text-label-sm font-label-sm text-error mb-xs">답변 대기 수</p>
                    <h3 className="text-headline-lg font-headline-lg text-error">24건</h3>
                  </div>
                </div>
              </div>

              {/* 원형 차트: 97% 답변율 */}
              <div className="w-full md:w-44 aspect-square flex-shrink-0 relative flex items-center justify-center">
                <svg className="w-full h-full -rotate-90" viewBox="0 0 100 100">
                  <circle
                    cx="50" cy="50" r="38"
                    fill="transparent"
                    stroke="currentColor"
                    strokeWidth="8"
                    className="text-surface-container"
                  />
                  <circle
                    cx="50" cy="50" r="38"
                    fill="transparent"
                    stroke="currentColor"
                    strokeWidth="8"
                    strokeDasharray="238.76"
                    strokeDashoffset="7.16"
                    strokeLinecap="round"
                    className="text-primary transition-all duration-1000"
                  />
                </svg>
                <div className="absolute inset-0 flex flex-col items-center justify-center">
                  <span className="text-headline-md font-headline-md text-on-surface">97%</span>
                  <span className="text-label-sm font-label-sm text-on-surface-variant">답변율</span>
                </div>
              </div>
            </div>

            {/* 카드 5: 최근 24시간 수강 신청 */}
            <div className="col-span-12 lg:col-span-4 bg-primary text-on-primary p-lg rounded-xl shadow-lg flex flex-col justify-between overflow-hidden relative group min-h-[200px]">
              <div className="absolute top-0 right-0 p-lg opacity-10 group-hover:scale-110 transition-transform duration-500">
                <span className="material-symbols-outlined text-[120px]">person_add</span>
              </div>
              <div className="relative z-10">
                <p className="text-on-primary/80 font-label-md text-label-md">최근 24시간 수강 신청</p>
                <h2 className="text-display-lg font-display-lg mt-sm">+42명</h2>
              </div>
              <div className="relative z-10 mt-xl flex -space-x-3">
                <div className="w-10 h-10 rounded-full border-2 border-primary overflow-hidden">
                  <img
                    className="w-full h-full object-cover"
                    alt="수강생"
                    src="https://lh3.googleusercontent.com/aida-public/AB6AXuAvXvm4dji1RRAYjysDkMtvZWJ0o7ucvY97MdkyOinN2eevlx2SpBvUavdzUdhPvQZZx9W6W2s4yrFEqqYUxX-NRgvBxq9KqtrxYcna-1CIbpc4S4eg6UKLbj5DYJMj_KLlFtThtBJuhcrIbKp3wvp6Oq18NQ7QV91Do0cgxWKRNXPcPXKTF0ySd9uE148-MBFt8oppxCf5djN3m18_f2UDierGLC4jiGK0m8f-Abg18Jt4207hfT_6yIV6TxmNF1vTi4aem4YTv07r"
                  />
                </div>
                <div className="w-10 h-10 rounded-full border-2 border-primary overflow-hidden">
                  <img
                    className="w-full h-full object-cover"
                    alt="수강생"
                    src="https://lh3.googleusercontent.com/aida-public/AB6AXuAVK-VwP5TbwI0hhOSk-2OcrEivROOdnGEOcVFlFe8h0gSKXtH_RoGq06E3UNzZEGJA4V6Q5V-XpU2KMPFEjAR67CmNW7_7DfuBEOLFVYyPEbFSgKlOLy4Cq87dwtkmUbf9z0ECkrD3KgGSqWYIvZABLumTDFyxO0UVKCd4kOqoQ_v3ZRGE9nEUQTJrfqHPLKH8o3-aJRNBLFVDDpIAA0OQMPXlrBm7Q-ZPQJQ5aP1rrQy8qA2mW84Jb7Fw7cNp-LdUaVe7wN7zg"
                  />
                </div>
                <div className="w-10 h-10 rounded-full border-2 border-primary overflow-hidden">
                  <img
                    className="w-full h-full object-cover"
                    alt="수강생"
                    src="https://lh3.googleusercontent.com/aida-public/AB6AXuDRqqVNnuEr9710Q_jYeEeFtjR1YQU526MT8SynhmQOEzPS4VX-a1uYHXOC_pqkYXHhE_g9iEP6cbUEsHG6CPfRLQbCiROBXAVPJR93JB1q5E_fBDMNJjyPipEQtD8u4qf5RYk7lI9HCT1NW1Wdtf0_3r-6KpXjVSVlmBsmMl2EYqvxvqK7v5Gn8GWxnB4r8jUq43H7xHYN0o7cePXRgRmrXdUxGYxlBdYGR5hqy1T18HaA3Rz_3B5q0BKbEHtq-XTj5P8Q"
                  />
                </div>
                <div className="w-10 h-10 rounded-full bg-white/20 border-2 border-white/30 flex items-center justify-center text-on-primary text-label-sm font-label-sm">
                  +39
                </div>
              </div>
            </div>

          </div>
        </div>
      </main>
      <Footer />
    </>
  );
}
