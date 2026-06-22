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

        <div className="bg-surface-container-lowest border border-outline-variant rounded-xl overflow-hidden mb-xl">
          <div className="overflow-x-auto">
            <table className="w-full text-left border-collapse">
              <thead className="bg-surface-container-low border-b border-outline-variant">
                <tr>
                  <th className="px-lg py-md text-label-md font-label-md text-on-surface-variant uppercase tracking-wider">강좌</th>
                  <th className="px-lg py-md text-label-md font-label-md text-on-surface-variant uppercase tracking-wider">강사</th>
                  <th className="px-lg py-md text-label-md font-label-md text-on-surface-variant uppercase tracking-wider text-center">수강생</th>
                  <th className="px-lg py-md text-label-md font-label-md text-on-surface-variant uppercase tracking-wider text-center">평점</th>
                  <th className="px-lg py-md text-label-md font-label-md text-on-surface-variant uppercase tracking-wider text-center">상태</th>
                  <th className="px-lg py-md text-label-md font-label-md text-on-surface-variant uppercase tracking-wider"></th>
                </tr>
              </thead>
              <tbody className="divide-y divide-outline-variant">
                {courses.map((course) => {
                  const statusInfo = STATUS_MAP[course.status] ?? STATUS_MAP['DRAFT'];
                  return (
                    <tr key={course.id} className="hover:bg-surface-container/50 transition-colors group">
                      <td className="px-lg py-md">
                        <div className="flex items-center gap-md">
                          <img src={course.thumbnail} alt={course.title} className="w-14 h-10 object-cover rounded-lg flex-shrink-0" />
                          <div className="min-w-0">
                            <p className="text-body-md font-body-md text-on-surface line-clamp-1">{course.title}</p>
                            <p className="text-label-sm font-label-sm text-on-surface-variant">{course.category} · {course.level}</p>
                          </div>
                        </div>
                      </td>
                      <td className="px-lg py-md text-body-md font-body-md text-on-surface-variant">{course.instructor}</td>
                      <td className="px-lg py-md text-body-md font-body-md text-on-surface text-center">{course.enrollmentCount.toLocaleString()}</td>
                      <td className="px-lg py-md text-center">
                        <span className="flex items-center justify-center gap-xs text-tertiary">
                          <span className="material-symbols-outlined text-[14px]" style={{ fontVariationSettings: "'FILL' 1" }}>star</span>
                          <span className="text-body-md font-body-md">{course.rating}</span>
                        </span>
                      </td>
                      <td className="px-lg py-md text-center">
                        <span className={`px-sm py-1 rounded-full text-label-sm font-label-sm ${statusInfo.cls}`}>{statusInfo.label}</span>
                      </td>
                      <td className="px-lg py-md">
                        <div className="flex items-center gap-sm opacity-0 group-hover:opacity-100 transition-opacity">
                          <Link href={`/courses/${course.id}/edit`} className="text-primary text-label-sm font-label-sm hover:underline">수정</Link>
                          <span className="text-outline-variant">|</span>
                          <Link href={`/courses/${course.id}/stats`} className="text-primary text-label-sm font-label-sm hover:underline">통계</Link>
                        </div>
                      </td>
                    </tr>
                  );
                })}
              </tbody>
            </table>
          </div>
        </div>

      </div>
  );
}
