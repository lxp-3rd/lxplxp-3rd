'use client';

import Link from 'next/link';
import { MOCK_COURSES } from '@/app/courses/mockData';

const STATUS_MAP: Record<string, { label: string; cls: string }> = {
  PUBLISHED: { label: '공개', cls: 'bg-secondary-container text-on-secondary-container' },
  DRAFT:     { label: '비공개', cls: 'bg-surface-container-high text-on-surface-variant' },
  ARCHIVED:  { label: '보관됨', cls: 'bg-error-container text-on-error-container' },
};

export default function AdminCoursesPage() {
  const courses = MOCK_COURSES;

  return (
    <div className="mt-20 px-gutter pb-xl flex-grow max-w-[1280px] mx-auto w-full">

      <div className="flex flex-col md:flex-row justify-between items-start md:items-center mb-xl gap-4">
        <div>
          <h1 className="font-headline-lg text-headline-lg text-on-surface">강좌 관리</h1>
          <p className="text-on-surface-variant font-body-md text-body-md">학습 콘텐츠를 관리하고 수강생 등록 현황을 추적합니다. (총 {courses.length}개)</p>
        </div>
      </div>

      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-lg">
        {courses.map((course) => {
          const statusInfo = STATUS_MAP[course.status] ?? STATUS_MAP['DRAFT'];
          return (
            <div key={course.id} className="bento-card overflow-hidden flex flex-col group">
              {/* 썸네일 */}
              <div className="relative aspect-video overflow-hidden bg-surface-container">
                <img
                  src={course.thumbnail}
                  alt={course.title}
                  className="w-full h-full object-cover group-hover:scale-105 transition-transform duration-500"
                />
                <span className={`absolute top-3 left-3 px-sm py-1 rounded-full text-label-sm font-label-sm shadow-sm ${statusInfo.cls}`}>
                  {statusInfo.label}
                </span>
              </div>

              {/* 내용 */}
              <div className="p-lg flex flex-col flex-grow">
                <h3 className="text-headline-sm font-bold text-on-surface line-clamp-2 group-hover:text-primary transition-colors mb-xs">
                  {course.title}
                </h3>
                <p className="text-body-sm font-body-sm text-on-surface-variant mb-md">{course.instructor} 강사</p>

                <div className="flex items-center gap-lg mt-auto mb-lg">
                  <div className="flex items-center gap-xs text-on-surface-variant">
                    <span className="material-symbols-outlined text-[16px]">group</span>
                    <span className="text-label-md font-label-md">{course.enrollmentCount.toLocaleString()}명</span>
                  </div>
                  <div className="flex items-center gap-xs text-tertiary">
                    <span className="material-symbols-outlined text-[16px]" style={{ fontVariationSettings: "'FILL' 1" }}>star</span>
                    <span className="text-label-md font-label-md">{course.rating}</span>
                  </div>
                </div>

                {/* 액션 버튼 */}
                <div className="flex items-center justify-between pt-md border-t border-outline-variant">
                  <div className="flex items-center gap-sm">
                    <Link
                      href={`/courses/${course.id}/edit`}
                      className="flex items-center gap-xs px-sm py-1.5 rounded-lg border border-outline-variant text-on-surface text-label-sm font-label-sm hover:bg-surface-container-low transition-all"
                    >
                      <span className="material-symbols-outlined text-[16px]">edit</span>수정
                    </Link>
                    <Link
                      href={`/courses/${course.id}/stats`}
                      className="flex items-center gap-xs px-sm py-1.5 rounded-lg border border-outline-variant text-on-surface text-label-sm font-label-sm hover:bg-surface-container-low transition-all"
                    >
                      <span className="material-symbols-outlined text-[16px]">bar_chart</span>통계
                    </Link>
                  </div>
                  <div className="flex items-center gap-xs">
                    <button
                      title="숨김/공개 전환"
                      className="p-1.5 rounded-lg hover:bg-surface-container text-on-surface-variant hover:text-primary transition-colors"
                    >
                      <span className="material-symbols-outlined text-[18px]">
                        {course.status === 'PUBLISHED' ? 'visibility' : 'visibility_off'}
                      </span>
                    </button>
                    <button
                      title="강좌 삭제"
                      className="p-1.5 rounded-lg hover:bg-error-container text-on-surface-variant hover:text-error transition-colors"
                    >
                      <span className="material-symbols-outlined text-[18px]">delete</span>
                    </button>
                  </div>
                </div>
              </div>
            </div>
          );
        })}
      </div>

    </div>
  );
}
