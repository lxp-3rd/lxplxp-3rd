'use client';

import Link from 'next/link';
import { MOCK_ANNOUNCEMENTS } from '@/app/announcements/mockData';

const CATEGORY_CLS: Record<string, string> = {
  시스템: 'bg-error-container text-on-error-container',
  서비스: 'bg-secondary-container text-on-secondary-container',
  정책:   'bg-tertiary-container text-on-tertiary-container',
  이벤트: 'bg-primary-container text-on-primary-container',
};

export default function AdminAnnouncementsPage() {
  const announcements = MOCK_ANNOUNCEMENTS;

  return (
    <div className="pt-xl pb-xl px-gutter max-w-[1280px] mx-auto w-full">

      {/* 헤더 */}
      <div className="flex flex-col md:flex-row justify-between items-start md:items-center mb-xl gap-md">
        <div>
          <h1 className="font-headline-lg text-headline-lg text-on-surface mb-xs">공지사항 관리</h1>
          <p className="font-body-md text-body-md text-on-surface-variant">플랫폼의 모든 주요 안내 및 소식을 관리합니다. (총 {announcements.length}건)</p>
        </div>
        <Link
          href="/admin/announcements/new"
          className="inline-flex items-center gap-xs px-lg py-md bg-primary text-on-primary font-label-md text-label-md rounded-lg shadow-sm hover:brightness-110 transition-all"
        >
          <span className="material-symbols-outlined text-[20px]">edit_square</span>
          공지사항 작성
        </Link>
      </div>

      {/* 목록 테이블 */}
      <div className="bg-surface-container-lowest border border-outline-variant rounded-xl overflow-hidden">
        <div className="overflow-x-auto">
          <table className="w-full text-left border-collapse">
            <thead>
              <tr className="bg-surface-container-low border-b border-outline-variant">
                <th className="px-lg py-md font-label-md text-label-md text-on-surface-variant w-16">No.</th>
                <th className="px-lg py-md font-label-md text-label-md text-on-surface-variant">제목</th>
                <th className="px-lg py-md font-label-md text-label-md text-on-surface-variant w-32">작성자</th>
                <th className="px-lg py-md font-label-md text-label-md text-on-surface-variant w-36">작성일</th>
                <th className="px-lg py-md font-label-md text-label-md text-on-surface-variant w-28 text-right">관리</th>
              </tr>
            </thead>
            <tbody className="divide-y divide-outline-variant">
              {announcements.map((ann, idx) => (
                <tr key={ann.id} className="hover:bg-surface-container-low transition-colors cursor-pointer group">
                  <td className="px-lg py-md font-body-md text-body-md text-on-surface-variant">
                    {announcements.length - idx}
                  </td>
                  <td className="px-lg py-md">
                    <div className="flex items-center gap-sm">
                      <span className={`px-xs py-[2px] rounded text-label-sm font-label-sm ${CATEGORY_CLS[ann.category] ?? 'bg-surface-container text-on-surface-variant'}`}>
                        {ann.category}
                      </span>
                      <Link
                        href={`/admin/announcements/${ann.id}`}
                        className="font-body-md text-body-md text-on-surface group-hover:text-primary transition-colors"
                      >
                        {ann.title}
                        {ann.isNew && (
                          <span className="ml-xs text-label-sm font-label-sm text-primary">NEW</span>
                        )}
                      </Link>
                    </div>
                  </td>
                  <td className="px-lg py-md font-body-md text-body-md text-on-surface-variant">{ann.author}</td>
                  <td className="px-lg py-md font-body-md text-body-md text-on-surface-variant">{ann.createdAt}</td>
                  <td className="px-lg py-md text-right">
                    <div className="flex items-center justify-end gap-xs">
                      <Link
                        href={`/admin/announcements/${ann.id}`}
                        className="p-xs rounded hover:bg-surface-container text-on-surface-variant hover:text-primary transition-colors"
                        title="보기"
                      >
                        <span className="material-symbols-outlined text-[18px]">visibility</span>
                      </Link>
                      <button
                        className="p-xs rounded hover:bg-surface-container text-on-surface-variant hover:text-primary transition-colors"
                        title="수정"
                      >
                        <span className="material-symbols-outlined text-[18px]">edit</span>
                      </button>
                      <button
                        className="p-xs rounded hover:bg-error-container text-on-surface-variant hover:text-error transition-colors"
                        title="삭제"
                      >
                        <span className="material-symbols-outlined text-[18px]">delete</span>
                      </button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>

        {/* 페이지네이션 */}
        <div className="px-lg py-md bg-surface-container-lowest border-t border-outline-variant flex justify-between items-center">
          <span className="text-label-sm font-label-sm text-on-surface-variant">전체 {announcements.length}건</span>
          <div className="flex items-center gap-xs">
            <button className="w-8 h-8 flex items-center justify-center rounded-lg hover:bg-surface-container transition-colors">
              <span className="material-symbols-outlined text-on-surface-variant text-[18px]">chevron_left</span>
            </button>
            <button className="w-8 h-8 flex items-center justify-center rounded-lg bg-primary text-on-primary font-label-md text-label-md">1</button>
            <button className="w-8 h-8 flex items-center justify-center rounded-lg hover:bg-surface-container">
              <span className="material-symbols-outlined text-on-surface-variant text-[18px]">chevron_right</span>
            </button>
          </div>
        </div>
      </div>

    </div>
  );
}
