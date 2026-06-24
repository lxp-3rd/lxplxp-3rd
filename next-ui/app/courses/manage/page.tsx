'use client';

import Link from 'next/link';
import { useRouter } from 'next/navigation';
import { TopNavBar } from '@/components/TopNavBar';
import { Footer } from '@/components/Footer';
import { PageHeader } from '@/components/ui';
import { getMyCourses } from '@/app/courses/mockData';
import { useAuth } from '@/lib/AuthContext';

const STATUS_LABEL: Record<string, { label: string; cls: string }> = {
  PUBLISHED:  { label: '공개', cls: 'bg-primary-container text-on-primary-container' },
  DRAFT:      { label: '비공개', cls: 'bg-tertiary-container text-on-tertiary-container' },
  ARCHIVED:   { label: '보관됨', cls: 'bg-surface-container-high text-on-surface-variant' },
};

export default function CourseManagePage() {
  const { user } = useAuth();
  const router = useRouter();
  const courses = getMyCourses(user?.id ?? 'u2');

  return (
    <>
      <TopNavBar />
      <main className="pt-16 min-h-screen bg-background">
        <div className="pt-xl pb-xl px-gutter max-w-[1280px] mx-auto mt-16">

          <PageHeader
            title="본인 강좌 관리"
            subtitle={`강사님이 생성한 총 ${courses.length}개의 강좌를 관리하고 있습니다.`}
            actions={
              <Link
                href="/courses/new"
                className="bg-primary-container text-on-primary-container px-lg py-sm rounded-lg font-label-md text-label-md flex items-center gap-xs hover:opacity-90 transition-opacity"
              >
                <span className="material-symbols-outlined" style={{ fontVariationSettings: "'FILL' 1" }}>add_circle</span>
                새 강좌 개설하기
              </Link>
            }
          />

          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-lg">
            {courses.map((course) => {
              const statusInfo = STATUS_LABEL[course.status] ?? STATUS_LABEL['DRAFT'];
              return (
                <div
                  key={course.id}
                  className={`relative bg-surface-container-lowest border border-outline-variant rounded-xl overflow-hidden flex flex-col group transition-transform duration-300 hover:-translate-y-1 ${course.status !== 'PUBLISHED' ? 'opacity-80' : ''}`}
                >
                  {/* 카드 전체 클릭 → 강좌 수정 */}
                  <Link href={`/courses/${course.id}/edit`} className="absolute inset-0 z-10" aria-label={course.title} />

                  <div className={`relative h-48 overflow-hidden ${course.status !== 'PUBLISHED' ? 'grayscale' : ''}`}>
                    <div
                      className="bg-cover bg-center w-full h-full transition-transform duration-500 group-hover:scale-105"
                      style={{ backgroundImage: `url('${course.thumbnail}')` }}
                    />
                    <div className="absolute top-md right-md">
                      <span className={`text-label-sm font-label-sm px-sm py-xs rounded-full ${statusInfo.cls}`}>{statusInfo.label}</span>
                    </div>
                  </div>
                  <div className="p-md flex flex-col flex-grow relative z-20 pointer-events-none">
                    <h3 className="font-headline-md text-headline-md text-on-surface mb-xs line-clamp-1">{course.title}</h3>
                    <div className="flex items-center gap-xs text-on-surface-variant mb-md">
                      <span className="material-symbols-outlined text-[18px]">group</span>
                      <span className="text-label-md font-label-md">{course.enrollmentCount.toLocaleString()}명 수강</span>
                    </div>
                    <div className="mt-auto pointer-events-auto">
                      <button
                        type="button"
                        onClick={(e) => { e.stopPropagation(); router.push(`/courses/${course.id}/stats`); }}
                        className="flex items-center gap-xs bg-surface-container text-on-surface-variant px-sm py-xs rounded-lg text-label-sm font-label-sm hover:bg-surface-container-high transition-colors"
                      >
                        <span className="material-symbols-outlined text-[16px]">bar_chart</span>
                        통계 보기
                      </button>
                    </div>
                  </div>
                </div>
              );
            })}

            {/* 새 강좌 추가 카드 */}
            <Link
              href="/courses/new"
              className="bg-surface-container-lowest border-2 border-dashed border-outline-variant rounded-xl flex flex-col items-center justify-center h-64 hover:border-primary hover:bg-primary-fixed/5 transition-all group"
            >
              <span className="material-symbols-outlined text-[40px] text-outline-variant group-hover:text-primary transition-colors mb-md">add_circle</span>
              <span className="text-label-md font-label-md text-on-surface-variant group-hover:text-primary transition-colors">새 강좌 개설하기</span>
            </Link>
          </div>

        </div>
      </main>
      <Footer />
    </>
  );
}
