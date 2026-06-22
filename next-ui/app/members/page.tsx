'use client';

import { useState } from 'react';
import Link from 'next/link';
import { TopNavBar } from '@/components/TopNavBar';
import { Footer } from '@/components/Footer';
import { MOCK_MEMBERS } from '@/app/members/mockData';

export default function MembersPage() {
  const [search, setSearch] = useState('');

  const filtered = MOCK_MEMBERS.filter(
    (m) =>
      m.name.includes(search) || m.email.includes(search)
  );

  return (
    <>
      <TopNavBar />
      <main className="pt-16 min-h-screen bg-background">
        <div className="max-w-container-max mx-auto px-gutter pt-xl pb-xxl">

          <div className="mb-xl flex flex-col md:flex-row md:items-end justify-between gap-md">
            <div>
              <h1 className="text-headline-lg font-headline-lg text-on-surface mb-xs">회원 관리</h1>
              <p className="text-body-md font-body-md text-on-surface-variant">총 {MOCK_MEMBERS.length}명의 회원</p>
            </div>
            <div className="flex items-center gap-sm">
              <div className="relative">
                <span className="material-symbols-outlined absolute left-3 top-1/2 -translate-y-1/2 text-on-surface-variant text-[20px]">search</span>
                <input
                  type="text"
                  value={search}
                  onChange={(e) => setSearch(e.target.value)}
                  placeholder="이름 또는 이메일 검색..."
                  className="pl-10 pr-md py-sm bg-surface-container-lowest border border-outline-variant rounded-lg text-body-md font-body-md text-on-surface focus:border-primary focus:outline-none transition-all w-64"
                />
              </div>
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
                  {filtered.length === 0 ? (
                    <tr>
                      <td colSpan={6} className="px-xl py-xxl text-center text-on-surface-variant text-body-md font-body-md">
                        검색 결과가 없습니다.
                      </td>
                    </tr>
                  ) : (
                    filtered.map((member) => (
                      <tr key={member.id} className="hover:bg-surface-container-lowest/50 transition-colors group">
                        <td className="px-xl py-lg">
                          <div className="flex items-center gap-md">
                            <div className="w-9 h-9 rounded-full bg-primary/20 text-primary flex items-center justify-center text-label-md font-bold flex-shrink-0">
                              {member.name[0]}
                            </div>
                            <span className="text-body-md font-body-md text-on-surface">{member.name}</span>
                          </div>
                        </td>
                        <td className="px-xl py-lg text-body-md font-body-md text-on-surface-variant">{member.email}</td>
                        <td className="px-xl py-lg text-body-md font-body-md text-on-surface text-center">{member.courseCount}</td>
                        <td className="px-xl py-lg text-body-md font-body-md text-on-surface-variant">{member.joinedAt}</td>
                        <td className="px-xl py-lg text-center">
                          <span className={`px-sm py-1 rounded-full text-label-sm font-label-sm ${
                            member.status === 'ACTIVE'
                              ? 'bg-secondary-container text-on-secondary-container'
                              : 'bg-surface-container-high text-on-surface-variant'
                          }`}>
                            {member.status === 'ACTIVE' ? '활성' : '비활성'}
                          </span>
                        </td>
                        <td className="px-xl py-lg">
                          <Link
                            href={`/members/${member.id}`}
                            className="text-primary text-label-sm font-label-sm opacity-0 group-hover:opacity-100 transition-opacity hover:underline"
                          >
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
      </main>
      <Footer />
    </>
  );
}
