'use client';

import Link from 'next/link';
import { TopNavBar } from '@/components/TopNavBar';
import { Footer } from '@/components/Footer';
import { MOCK_ENROLLMENTS } from '@/app/enrollments/mockData';

export default function EnrollmentsPage() {
  const enrollments = MOCK_ENROLLMENTS;
  const inProgress = enrollments.filter((e) => e.status === 'IN_PROGRESS').length;

  return (
    <>
      <TopNavBar />
      <main className="pt-16 min-h-screen bg-background">
        <div className="pt-xxl pb-xxl max-w-[1280px] mx-auto px-gutter mt-16">

          <div className="flex justify-between items-end mb-xl flex-wrap gap-md">
            <div>
              <h1 className="text-headline-lg font-headline-lg text-on-surface">학습 중인 강의</h1>
              <p className="text-body-md font-body-md text-on-surface-variant mt-xs">
                총 {enrollments.length}개의 강의 중 {inProgress}개 수강 중입니다. 오늘도 열공하세요!
              </p>
            </div>
            <div className="hidden md:flex gap-sm">
              <button type="button" className="px-lg py-sm bg-surface-container-lowest border border-outline-variant rounded-lg text-label-md font-label-md hover:bg-surface-container-low transition-colors">최근 수강순</button>
              <button type="button" className="px-lg py-sm bg-surface-container-lowest border border-outline-variant rounded-lg text-label-md font-label-md hover:bg-surface-container-low transition-colors">진도율순</button>
            </div>
          </div>

          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-lg">
            {enrollments.map((e) => (
              <Link
                key={e.id}
<<<<<<< HEAD
                href={`/enrollments/${e.id}`}
=======
                href={`/courses/${e.courseId}`}
>>>>>>> main
                className="group block bg-surface-container-lowest border border-outline-variant rounded-xl overflow-hidden transition-all duration-300 hover:shadow-md hover:-translate-y-1"
              >
                <div className="aspect-video relative overflow-hidden bg-surface-container">
                  <img
                    className="w-full h-full object-cover transition-transform duration-500 group-hover:scale-105"
                    alt={e.courseTitle}
                    src={e.thumbnail}
                  />
                  {e.status === 'IN_PROGRESS' && (
                    <div className="absolute top-md left-md bg-primary-container text-on-primary px-sm py-xs rounded text-label-sm font-label-sm shadow-sm">진행 중</div>
                  )}
                  {e.status === 'COMPLETED' && (
                    <div className="absolute top-md left-md bg-secondary-container text-on-secondary-container px-sm py-xs rounded text-label-sm font-label-sm shadow-sm">수강 완료</div>
                  )}
                </div>
                <div className="p-lg">
                  <h3 className="text-headline-sm font-bold text-on-surface line-clamp-1 group-hover:text-primary transition-colors mb-xs">{e.courseTitle}</h3>
                  <p className="text-body-sm font-body-sm text-on-surface-variant">{e.instructor} 강사</p>
                  <div className="mt-md">
                    <div className="flex justify-between items-center mb-xs">
                      <span className="text-label-sm font-label-sm text-on-surface-variant">학습 진도율</span>
                      <span className="text-label-sm font-label-sm text-primary font-bold">{e.progress}%</span>
                    </div>
                    <div className="w-full h-1.5 bg-surface-container rounded-full overflow-hidden">
                      <div className="h-full bg-primary rounded-full transition-all" style={{ width: `${e.progress}%` }} />
                    </div>
                  </div>
                  <p className="text-label-sm font-label-sm text-on-surface-variant mt-sm">마지막 접속: {e.lastAccessedAt}</p>
                </div>
              </Link>
            ))}
          </div>

<<<<<<< HEAD
=======
          {/* 더 보기 버튼 */}
          <div className="flex justify-center mt-xl">
            <button
              type="button"
              className="flex items-center gap-sm px-xl py-md border border-outline-variant rounded-xl text-label-md font-label-md text-on-surface hover:bg-surface-container-low transition-all"
            >
              <span className="material-symbols-outlined text-[18px]">expand_more</span>
              더 많은 강의 보기
            </button>
          </div>

>>>>>>> main
        </div>
      </main>
      <Footer />
    </>
  );
}
