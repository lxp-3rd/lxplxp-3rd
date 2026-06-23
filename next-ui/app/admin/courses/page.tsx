'use client';

import { useState } from 'react';
import Link from 'next/link';
import { MOCK_COURSES } from '@/app/courses/mockData';

type Course = typeof MOCK_COURSES[number] & { hidden?: boolean };

const STATUS_MAP: Record<string, { label: string; cls: string }> = {
  PUBLISHED: { label: '공개', cls: 'bg-secondary-container text-on-secondary-container' },
  DRAFT:     { label: '비공개', cls: 'bg-surface-container-high text-on-surface-variant' },
  ARCHIVED:  { label: '보관됨', cls: 'bg-error-container text-on-error-container' },
};

export default function AdminCoursesPage() {
  const [courses, setCourses] = useState<Course[]>(MOCK_COURSES);

  const handleToggleHidden = (id: string) => {
    setCourses((prev) =>
      prev.map((c) => c.id === id ? { ...c, hidden: !c.hidden } : c),
    );
  };

  const handleDelete = (id: string) => {
    setCourses((prev) => prev.filter((c) => c.id !== id));
  };

  return (
    <div className="flex-grow w-full max-w-[1280px] mx-auto px-gutter pt-xl pb-xxl">

      <div className="mb-xl flex flex-col md:flex-row md:items-end justify-between gap-md">
        <div>
          <h1 className="font-headline-lg text-headline-lg text-on-surface mb-xs">강좌 관리</h1>
          <p className="font-body-md text-body-md text-on-surface-variant">학습 콘텐츠를 관리하고 수강생 등록 현황을 추적합니다.</p>
        </div>
      </div>

      <div className="bg-surface-container-lowest border border-outline-variant rounded-xl overflow-hidden">
        <div className="overflow-x-auto">
          <table className="w-full text-left border-collapse">
            <colgroup>
              <col className="w-[38%]" />
              <col className="w-[120px]" />
              <col className="w-[120px]" />
              <col className="w-[100px]" />
              <col className="w-[90px]" />
              <col className="w-[120px]" />
            </colgroup>
            <thead className="bg-surface-container-low border-b border-outline-variant">
              <tr>
                <th className="px-xl py-lg text-label-md font-label-md text-on-surface-variant uppercase tracking-wider">강좌명</th>
                <th className="px-xl py-lg text-label-md font-label-md text-on-surface-variant uppercase tracking-wider">강사</th>
                <th className="px-xl py-lg text-label-md font-label-md text-on-surface-variant uppercase tracking-wider">카테고리</th>
                <th className="px-xl py-lg text-label-md font-label-md text-on-surface-variant uppercase tracking-wider text-center">수강생</th>
                <th className="px-xl py-lg text-label-md font-label-md text-on-surface-variant uppercase tracking-wider text-center">상태</th>
                <th className="px-xl py-lg text-label-md font-label-md text-on-surface-variant uppercase tracking-wider text-right">관리</th>
              </tr>
            </thead>
            <tbody className="divide-y divide-outline-variant">
              {courses.map((c) => {
                const statusInfo = STATUS_MAP[c.status] ?? STATUS_MAP['DRAFT'];
                return (
                  <tr key={c.id} className={`hover:bg-surface-container-low transition-colors ${c.hidden ? 'opacity-50' : ''}`}>
                    <td className="px-xl py-lg">
                      <div className="flex items-center gap-md">
                        <img src={c.thumbnail} alt={c.title} className="w-16 h-11 object-cover rounded-lg flex-shrink-0" />
                        <div className="min-w-0">
                          <p className="text-body-md font-body-md text-on-surface line-clamp-1">{c.title}</p>
                          {c.hidden && (
                            <span className="text-label-sm font-label-sm text-on-surface-variant">(숨김)</span>
                          )}
                        </div>
                      </div>
                    </td>
                    <td className="px-xl py-lg text-body-md font-body-md text-on-surface-variant whitespace-nowrap">{c.instructor}</td>
                    <td className="px-xl py-lg">
                      <span className="px-sm py-xs rounded-full bg-secondary-container text-on-secondary-container text-label-sm font-label-sm whitespace-nowrap">
                        {c.category}
                      </span>
                    </td>
                    <td className="px-xl py-lg text-body-md font-body-md text-on-surface text-center">{c.enrollmentCount.toLocaleString()}명</td>
                    <td className="px-xl py-lg text-center">
                      <span className={`px-sm py-1 rounded-full text-label-sm font-label-sm whitespace-nowrap ${statusInfo.cls}`}>
                        {statusInfo.label}
                      </span>
                    </td>
                    <td className="px-xl py-lg">
                      <div className="flex items-center justify-end gap-xs">
                        <Link
                          href={`/courses/${c.id}/edit`}
                          className="p-xs rounded-full hover:bg-surface-container transition-colors text-on-surface-variant hover:text-primary"
                          title="수정"
                        >
                          <span className="material-symbols-outlined text-[20px]">edit</span>
                        </Link>
                        <Link
                          href={`/courses/${c.id}/stats`}
                          className="p-xs rounded-full hover:bg-surface-container transition-colors text-on-surface-variant hover:text-primary"
                          title="통계"
                        >
                          <span className="material-symbols-outlined text-[20px]">bar_chart</span>
                        </Link>
                        <button
                          type="button"
                          onClick={() => handleToggleHidden(c.id)}
                          className="p-xs rounded-full hover:bg-surface-container transition-colors text-on-surface-variant hover:text-primary"
                          title={c.hidden ? '공개' : '숨김'}
                        >
                          <span className="material-symbols-outlined text-[20px]">
                            {c.hidden ? 'visibility_off' : 'visibility'}
                          </span>
                        </button>
                        <button
                          type="button"
                          onClick={() => handleDelete(c.id)}
                          className="p-xs rounded-full hover:bg-error-container transition-colors text-on-surface-variant hover:text-error"
                          title="삭제"
                        >
                          <span className="material-symbols-outlined text-[20px]">delete</span>
                        </button>
                      </div>
                    </td>
                  </tr>
                );
              })}
            </tbody>
          </table>
        </div>

        <div className="px-xl py-md border-t border-outline-variant flex items-center justify-between">
          <span className="text-label-sm font-label-sm text-on-surface-variant">
            전체 {MOCK_COURSES.length}개 중 {courses.length}개 표시
          </span>
          <div className="flex items-center gap-xs">
            <button className="w-8 h-8 flex items-center justify-center rounded-lg hover:bg-surface-container transition-colors">
              <span className="material-symbols-outlined text-on-surface-variant text-[18px]">chevron_left</span>
            </button>
            <button className="w-8 h-8 flex items-center justify-center rounded-lg bg-primary text-on-primary font-label-md text-label-md">1</button>
            <button className="w-8 h-8 flex items-center justify-center rounded-lg hover:bg-surface-container transition-colors">
              <span className="material-symbols-outlined text-on-surface-variant text-[18px]">chevron_right</span>
            </button>
          </div>
        </div>
      </div>

    </div>
  );
}
