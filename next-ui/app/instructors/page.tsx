'use client';

import Link from 'next/link';
import { TopNavBar } from '@/components/TopNavBar';
import { Footer } from '@/components/Footer';
import { MOCK_INSTRUCTORS } from '@/app/instructors/mockData';

export default function InstructorsPage() {
  return (
    <>
      <TopNavBar />
      <main className="pt-16 min-h-screen bg-background">
        <div className="max-w-container-max mx-auto px-gutter pt-xl pb-xxl">

          <div className="mb-xl">
            <h1 className="text-headline-lg font-headline-lg text-on-surface mb-xs">강사 소개</h1>
            <p className="text-body-md font-body-md text-on-surface-variant">LXP의 전문 강사진을 만나보세요.</p>
          </div>

          <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-lg">
            {MOCK_INSTRUCTORS.map((instructor) => (
              <Link
                key={instructor.id}
                href={`/instructors/${instructor.id}`}
                className="bg-surface-container-lowest border border-outline-variant rounded-xl p-xl hover:shadow-md hover:border-primary/30 transition-all group"
              >
                {/* 아바타 */}
                <div className="flex items-center gap-lg mb-lg">
                  <div className="w-16 h-16 rounded-full bg-primary text-on-primary flex items-center justify-center text-headline-sm font-bold flex-shrink-0">
                    {instructor.name[0]}
                  </div>
                  <div>
                    <h2 className="text-label-md font-label-md text-on-surface group-hover:text-primary transition-colors">{instructor.name}</h2>
                    <p className="text-label-sm font-label-sm text-on-surface-variant">{instructor.nickname}</p>
                  </div>
                </div>

                {/* 전문 분야 */}
                <div className="flex flex-wrap gap-xs mb-lg">
                  {instructor.expertise.map((tag) => (
                    <span key={tag} className="bg-primary-fixed text-primary px-sm py-0.5 rounded-full text-label-sm font-label-sm">{tag}</span>
                  ))}
                </div>

                {/* 한줄 소개 */}
                <p className="text-body-sm font-body-md text-on-surface-variant line-clamp-2 mb-lg">{instructor.bio}</p>

                {/* 통계 */}
                <div className="flex items-center gap-lg border-t border-outline-variant pt-md">
                  <div className="flex items-center gap-xs text-on-surface-variant">
                    <span className="material-symbols-outlined text-[16px]">book</span>
                    <span className="text-label-sm font-label-sm">{instructor.courseCount}개 강좌</span>
                  </div>
                  <div className="flex items-center gap-xs text-on-surface-variant">
                    <span className="material-symbols-outlined text-[16px]">group</span>
                    <span className="text-label-sm font-label-sm">{instructor.studentCount.toLocaleString()}명</span>
                  </div>
                  <div className="flex items-center gap-xs text-tertiary ml-auto">
                    <span className="material-symbols-outlined text-[16px]" style={{ fontVariationSettings: "'FILL' 1" }}>star</span>
                    <span className="text-label-sm font-label-sm font-bold">{instructor.rating}</span>
                  </div>
                </div>
              </Link>
            ))}
          </div>

        </div>
      </main>
      <Footer />
    </>
  );
}
