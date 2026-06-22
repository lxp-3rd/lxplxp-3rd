'use client';

import { useState } from 'react';
import { MOCK_ROADMAPS } from '@/app/roadmaps/mockData';

type Roadmap = typeof MOCK_ROADMAPS[number] & { hidden?: boolean };

export default function AdminRoadmapsPage() {
  const [roadmaps, setRoadmaps] = useState<Roadmap[]>(MOCK_ROADMAPS);

  const handleToggleHidden = (id: string) => {
    setRoadmaps((prev) =>
      prev.map((r) => r.id === id ? { ...r, hidden: !r.hidden } : r),
    );
  };

  const handleDelete = (id: string) => {
    setRoadmaps((prev) => prev.filter((r) => r.id !== id));
  };

  return (
    <div className="flex-grow w-full max-w-[1280px] mx-auto px-gutter pt-xl pb-xxl">

      <div className="mb-xl flex flex-col md:flex-row md:items-end justify-between gap-md">
        <div>
          <h1 className="font-headline-lg text-headline-lg text-on-surface mb-xs">로드맵 관리</h1>
          <p className="font-body-md text-body-md text-on-surface-variant">플랫폼에 등록된 로드맵을 관리합니다.</p>
        </div>
      </div>

      <div className="bg-surface-container-lowest border border-outline-variant rounded-xl overflow-hidden">
        <div className="overflow-x-auto">
          <table className="w-full text-left border-collapse">
            <colgroup>
              <col className="w-[40%]" />
              <col className="w-[120px]" />
              <col className="w-[80px]" />
              <col className="w-[100px]" />
              <col className="w-[120px]" />
              <col className="w-[80px]" />
            </colgroup>
            <thead className="bg-surface-container-low border-b border-outline-variant">
              <tr>
                <th className="px-xl py-lg text-label-md font-label-md text-on-surface-variant uppercase tracking-wider">로드맵명</th>
                <th className="px-xl py-lg text-label-md font-label-md text-on-surface-variant uppercase tracking-wider">카테고리</th>
                <th className="px-xl py-lg text-label-md font-label-md text-on-surface-variant uppercase tracking-wider text-center">강좌 수</th>
                <th className="px-xl py-lg text-label-md font-label-md text-on-surface-variant uppercase tracking-wider text-center">참여자</th>
                <th className="px-xl py-lg text-label-md font-label-md text-on-surface-variant uppercase tracking-wider">제작자</th>
                <th className="px-xl py-lg text-label-md font-label-md text-on-surface-variant uppercase tracking-wider text-right">관리</th>
              </tr>
            </thead>
            <tbody className="divide-y divide-outline-variant">
              {roadmaps.map((r) => (
                <tr key={r.id} className={`hover:bg-surface-container-low transition-colors ${r.hidden ? 'opacity-50' : ''}`}>
                  <td className="px-xl py-lg">
                    <p className="text-body-md font-body-md text-on-surface line-clamp-1">{r.title}</p>
                    {r.hidden && (
                      <span className="text-label-sm font-label-sm text-on-surface-variant">(숨김)</span>
                    )}
                  </td>
                  <td className="px-xl py-lg">
                    <span className="px-sm py-xs rounded-full bg-secondary-container text-on-secondary-container text-label-sm font-label-sm whitespace-nowrap">
                      {r.category}
                    </span>
                  </td>
                  <td className="px-xl py-lg text-body-md font-body-md text-on-surface text-center">{r.courseCount}개</td>
                  <td className="px-xl py-lg text-body-md font-body-md text-on-surface text-center">{r.enrollmentCount.toLocaleString()}명</td>
                  <td className="px-xl py-lg text-body-md font-body-md text-on-surface-variant">{r.creatorName}</td>
                  <td className="px-xl py-lg">
                    <div className="flex items-center justify-end gap-sm">
                      <button
                        type="button"
                        onClick={() => handleToggleHidden(r.id)}
                        className="p-xs rounded-full hover:bg-surface-container transition-colors text-on-surface-variant hover:text-primary"
                        title={r.hidden ? '공개' : '숨김'}
                      >
                        <span className="material-symbols-outlined text-[20px]">
                          {r.hidden ? 'visibility_off' : 'visibility'}
                        </span>
                      </button>
                      <button
                        type="button"
                        onClick={() => handleDelete(r.id)}
                        className="p-xs rounded-full hover:bg-error-container transition-colors text-on-surface-variant hover:text-error"
                        title="삭제"
                      >
                        <span className="material-symbols-outlined text-[20px]">delete</span>
                      </button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>

        <div className="px-xl py-md border-t border-outline-variant flex items-center justify-between">
          <span className="text-label-sm font-label-sm text-on-surface-variant">
            전체 {MOCK_ROADMAPS.length}개 중 {roadmaps.length}개 표시
          </span>
          <div className="flex items-center gap-xs">
            <button className="p-xs rounded hover:bg-surface-container text-on-surface-variant transition-colors">
              <span className="material-symbols-outlined text-[18px]">chevron_left</span>
            </button>
            <span className="w-8 h-8 flex items-center justify-center rounded bg-primary text-on-primary text-label-md font-label-md">1</span>
            <button className="p-xs rounded hover:bg-surface-container text-on-surface-variant transition-colors">
              <span className="material-symbols-outlined text-[18px]">chevron_right</span>
            </button>
          </div>
        </div>
      </div>

    </div>
  );
}
