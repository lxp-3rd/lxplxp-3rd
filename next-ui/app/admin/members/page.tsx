'use client';

import { useState } from 'react';
import Link from 'next/link';
import { MOCK_MEMBERS } from '@/app/members/mockData';

export default function AdminMembersPage() {
  const [search, setSearch] = useState('');
  const members = MOCK_MEMBERS.filter(
    (m) => !search || m.name.includes(search) || m.email.includes(search)
  );

  return (
      <div className="mt-16 flex-grow px-gutter py-xl max-w-[1280px] mx-auto w-full">

        <div className="mb-xl flex flex-col md:flex-row md:items-end justify-between gap-md">
          <div>
            <h1 className="font-headline-lg text-headline-lg text-on-surface mb-xs">회원 관리</h1>
            <p className="font-body-md text-body-md text-on-surface-variant">사용자 계정, 권한 및 보안 상태를 관리합니다. (총 {MOCK_MEMBERS.length}명)</p>
          </div>
          <div className="flex gap-sm">
            <button type="button" className="flex items-center gap-xs px-md py-2 bg-surface-container-lowest border border-outline-variant rounded-lg text-on-surface font-label-md text-label-md hover:bg-surface-container-low transition-all">
              <span className="material-symbols-outlined text-[18px]">file_download</span> CSV 내보내기
            </button>
          </div>
        </div>

        {/* 검색 */}
        <div className="mb-lg">
          <div className="relative w-64">
            <span className="material-symbols-outlined absolute left-3 top-1/2 -translate-y-1/2 text-on-surface-variant text-[20px]">search</span>
            <input
              type="text"
              value={search}
              onChange={(e) => setSearch(e.target.value)}
              placeholder="이름 또는 이메일 검색..."
              className="w-full pl-10 pr-md py-sm bg-surface-container-lowest border border-outline-variant rounded-lg text-body-md font-body-md text-on-surface focus:border-primary focus:outline-none transition-all"
            />
          </div>
        </div>

        <div className="bg-surface-container-lowest border border-outline-variant rounded-xl overflow-hidden">
          <div className="overflow-x-auto">
            <table className="w-full text-left border-collapse">
              <thead className="bg-surface-container-low border-b border-outline-variant">
                <tr>
                  <th className="px-xl py-lg text-label-md font-label-md text-on-surface-variant uppercase tracking-wider">회원</th>
                  <th className="px-xl py-lg text-label-md font-label-md text-on-surface-variant uppercase tracking-wider">이메일</th>
                  <th className="px-xl py-lg text-label-md font-label-md text-on-surface-variant uppercase tracking-wider text-center">수강 강좌</th>
                  <th className="px-xl py-lg text-label-md font-label-md text-on-surface-variant uppercase tracking-wider">가입일</th>
                  <th className="px-xl py-lg text-label-md font-label-md text-on-surface-variant uppercase tracking-wider text-center">상태</th>
                  <th className="px-xl py-lg text-label-md font-label-md text-on-surface-variant uppercase tracking-wider"></th>
                </tr>
              </thead>
              <tbody className="divide-y divide-outline-variant">
                {members.length === 0 ? (
                  <tr>
                    <td colSpan={6} className="px-xl py-xxl text-center text-on-surface-variant text-body-md font-body-md">검색 결과가 없습니다.</td>
                  </tr>
                ) : (
                  members.map((m) => (
                    <tr key={m.id} className="hover:bg-surface-container/50 transition-colors group">
                      <td className="px-xl py-lg">
                        <div className="flex items-center gap-md">
                          <div className="w-9 h-9 rounded-full bg-primary/20 text-primary flex items-center justify-center font-bold text-label-md flex-shrink-0">
                            {m.name[0]}
                          </div>
                          <span className="text-body-md font-body-md text-on-surface">{m.name}</span>
                        </div>
                      </td>
                      <td className="px-xl py-lg text-body-md font-body-md text-on-surface-variant">{m.email}</td>
                      <td className="px-xl py-lg text-body-md font-body-md text-on-surface text-center">{m.courseCount}</td>
                      <td className="px-xl py-lg text-body-md font-body-md text-on-surface-variant">{m.joinedAt}</td>
                      <td className="px-xl py-lg text-center">
                        <span className={`px-sm py-1 rounded-full text-label-sm font-label-sm ${m.status === 'ACTIVE' ? 'bg-secondary-container text-on-secondary-container' : 'bg-surface-container-high text-on-surface-variant'}`}>
                          {m.status === 'ACTIVE' ? '활성' : '비활성'}
                        </span>
                      </td>
                      <td className="px-xl py-lg">
                        <Link href={`/members/${m.id}`} className="text-primary text-label-sm font-label-sm opacity-0 group-hover:opacity-100 transition-opacity hover:underline">
                          상세보기
                        </Link>
                      </td>
                    </tr>
                  ))
                )}
              </tbody>
            </table>
          </div>
        </div>

      </div>
  );
}
