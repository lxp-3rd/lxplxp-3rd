'use client';

import Link from 'next/link';
import { MOCK_INSTRUCTORS } from '@/app/instructors/mockData';

export default function AdminInstructorsPage() {
  const instructors = MOCK_INSTRUCTORS;

  return (
      <div className="mt-20 px-gutter pb-xl flex-grow max-w-[1280px] mx-auto w-full">

        <div className="flex flex-col md:flex-row md:items-end justify-between mb-xl gap-md">
          <div>
            <h1 className="font-headline-lg text-headline-lg text-on-surface mb-xs">강사 관리</h1>
            <p className="font-body-md text-body-md text-on-surface-variant">강사 자격, 강의 수 및 플랫폼 권한을 관리합니다. (총 {instructors.length}명)</p>
          </div>
        </div>

        <div className="bg-surface-container-lowest border border-outline-variant rounded-xl overflow-hidden">
          <div className="overflow-x-auto">
            <table className="w-full text-left border-collapse">
              <thead className="bg-surface-container-low border-b border-outline-variant">
                <tr>
                  <th className="px-xl py-lg text-label-md font-label-md text-on-surface-variant uppercase tracking-wider">강사</th>
                  <th className="px-xl py-lg text-label-md font-label-md text-on-surface-variant uppercase tracking-wider">전문 분야</th>
                  <th className="px-xl py-lg text-label-md font-label-md text-on-surface-variant uppercase tracking-wider text-center">강좌</th>
                  <th className="px-xl py-lg text-label-md font-label-md text-on-surface-variant uppercase tracking-wider text-center">수강생</th>
                  <th className="px-xl py-lg text-label-md font-label-md text-on-surface-variant uppercase tracking-wider text-center">평점</th>
                  <th className="px-xl py-lg text-label-md font-label-md text-on-surface-variant uppercase tracking-wider text-center">상태</th>
                  <th className="px-xl py-lg text-label-md font-label-md text-on-surface-variant uppercase tracking-wider"></th>
                </tr>
              </thead>
              <tbody className="divide-y divide-outline-variant">
                {instructors.map((inst) => (
                  <tr key={inst.id} className="hover:bg-surface-container/50 transition-colors group">
                    <td className="px-xl py-lg">
                      <div className="flex items-center gap-md">
                        <div className="w-10 h-10 rounded-full bg-primary text-on-primary flex items-center justify-center font-bold text-label-md flex-shrink-0">
                          {inst.name[0]}
                        </div>
                        <div>
                          <p className="text-body-md font-body-md text-on-surface">{inst.name}</p>
                          <p className="text-label-sm font-label-sm text-on-surface-variant">{inst.email}</p>
                        </div>
                      </div>
                    </td>
                    <td className="px-xl py-lg">
                      <div className="flex flex-wrap gap-xs">
                        {inst.expertise.slice(0, 2).map((tag) => (
                          <span key={tag} className="bg-primary-fixed text-primary px-sm py-0.5 rounded-full text-label-sm font-label-sm">{tag}</span>
                        ))}
                        {inst.expertise.length > 2 && (
                          <span className="text-on-surface-variant text-label-sm font-label-sm">+{inst.expertise.length - 2}</span>
                        )}
                      </div>
                    </td>
                    <td className="px-xl py-lg text-body-md font-body-md text-on-surface text-center">{inst.courseCount}</td>
                    <td className="px-xl py-lg text-body-md font-body-md text-on-surface text-center">{inst.studentCount.toLocaleString()}</td>
                    <td className="px-xl py-lg text-center">
                      <span className="flex items-center justify-center gap-xs text-tertiary">
                        <span className="material-symbols-outlined text-[14px]" style={{ fontVariationSettings: "'FILL' 1" }}>star</span>
                        <span className="text-body-md font-body-md">{inst.rating}</span>
                      </span>
                    </td>
                    <td className="px-xl py-lg text-center">
                      <span className={`px-sm py-1 rounded-full text-label-sm font-label-sm ${inst.isActive ? 'bg-secondary-container text-on-secondary-container' : 'bg-surface-container-high text-on-surface-variant'}`}>
                        {inst.isActive ? '활성' : '비활성'}
                      </span>
                    </td>
                    <td className="px-xl py-lg">
                      <Link href={`/instructors/${inst.id}`} className="text-primary text-label-sm font-label-sm opacity-0 group-hover:opacity-100 transition-opacity hover:underline">
                        프로필 보기
                      </Link>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>

      </div>
  );
}
