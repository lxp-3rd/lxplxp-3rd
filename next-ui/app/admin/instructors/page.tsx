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
          <p className="font-body-md text-body-md text-on-surface-variant">강사 자격, 강의 수 및 플랫폼 권한을 관리합니다.</p>
        </div>
      </div>

      <div className="bg-surface-container-lowest border border-outline-variant rounded-xl overflow-hidden">
        <div className="overflow-x-auto">
          <table className="w-full text-left border-collapse">
            <thead className="bg-surface-container-low border-b border-outline-variant">
              <tr>
                <th className="px-xl py-lg text-label-md font-label-md text-on-surface-variant uppercase tracking-wider">닉네임</th>
                <th className="px-xl py-lg text-label-md font-label-md text-on-surface-variant uppercase tracking-wider">표시 이름</th>
                <th className="px-xl py-lg text-label-md font-label-md text-on-surface-variant uppercase tracking-wider">전문 분야</th>
                <th className="px-xl py-lg text-label-md font-label-md text-on-surface-variant uppercase tracking-wider text-center">강좌 수</th>
                <th className="px-xl py-lg text-label-md font-label-md text-on-surface-variant uppercase tracking-wider text-center">상태</th>
                <th className="px-xl py-lg text-label-md font-label-md text-on-surface-variant uppercase tracking-wider"></th>
              </tr>
            </thead>
            <tbody className="divide-y divide-outline-variant">
              {instructors.map((inst) => (
                <tr key={inst.id} className="hover:bg-surface-container/50 transition-colors group">
                  <td className="px-xl py-lg">
                    <span className="text-body-md font-body-md text-on-surface-variant">@{inst.nickname.split('@')[0]}</span>
                  </td>
                  <td className="px-xl py-lg">
                    <div className="flex items-center gap-md">
                      <span className="text-body-md font-body-md text-on-surface">{inst.name}</span>
                    </div>
                  </td>
                  <td className="px-xl py-lg">
                    <span className="px-sm py-xs rounded-full bg-secondary-container text-on-secondary-container text-label-sm font-label-sm whitespace-nowrap">
                      {inst.expertise[0]}
                    </span>
                  </td>
                  <td className="px-xl py-lg text-body-md font-body-md text-on-surface text-center">{inst.courseCount}</td>
                  <td className="px-xl py-lg text-center">
                    {inst.isActive
                      ? <span className="flex items-center justify-center gap-xs text-label-sm font-label-sm text-green-600"><span className="w-2 h-2 rounded-full bg-green-600 inline-block" />활성</span>
                      : <span className="flex items-center justify-center gap-xs text-label-sm font-label-sm text-on-surface-variant"><span className="w-2 h-2 rounded-full bg-outline-variant inline-block" />비활성</span>
                    }
                  </td>
                  <td className="px-xl py-lg">
                    <Link
                      href={`/admin/instructors/${inst.id}`}
                      className="text-primary text-label-sm font-label-sm hover:underline"
                    >
                      프로필 보기
                    </Link>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>

        {/* 하단 요약 + 페이지네이션 */}
        <div className="px-xl py-md bg-surface-container-lowest border-t border-outline-variant flex justify-between items-center">
          <span className="text-label-sm font-label-sm text-on-surface-variant">
            전체 {instructors.length}명의 강사 중 {instructors.length}명 표시 중
          </span>
          <div className="flex items-center gap-xs">
            <button className="w-8 h-8 flex items-center justify-center rounded-lg hover:bg-surface-container transition-colors" disabled>
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
