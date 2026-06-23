'use client';

import { useState } from 'react';
import Link from 'next/link';
import { MOCK_MEMBERS } from '@/app/members/mockData';

const ROLE_MAP: Record<string, { label: string; cls: string }> = {
  admin:    { label: '관리자', cls: 'bg-error-container text-on-error-container' },
  instructor: { label: '강사', cls: 'bg-secondary-container text-on-secondary-container' },
  member:   { label: '학생', cls: 'bg-primary-container text-on-primary-container' },
};

type FilterTab = 'all' | 'status';

export default function AdminMembersPage() {
  const [search, setSearch] = useState('');
  const [filterTab, setFilterTab] = useState<FilterTab>('all');

  const members = MOCK_MEMBERS.filter(
    (m) => !search || m.name.includes(search) || m.email.includes(search)
  ).filter((m) => filterTab === 'all' || true);

  const activeCount = MOCK_MEMBERS.filter((m) => m.status === 'ACTIVE').length;

  return (
    <div className="mt-16 flex-grow px-gutter py-xl max-w-[1280px] mx-auto w-full">

      {/* 헤더 */}
      <div className="mb-xl flex flex-col md:flex-row md:items-end justify-between gap-md">
        <div>
          <h1 className="font-headline-lg text-headline-lg text-on-surface mb-xs">회원 관리</h1>
          <p className="font-body-md text-body-md text-on-surface-variant">사용자 계정, 권한 및 보안 상태를 관리합니다.</p>
        </div>
        <div className="flex gap-sm">
          <button
            type="button"
            className="flex items-center gap-xs px-md py-2 bg-surface-container-lowest border border-outline-variant rounded-lg text-on-surface font-label-md text-label-md hover:bg-surface-container-low transition-all"
          >
            <span className="material-symbols-outlined text-[18px]">file_download</span> CSV 내보내기
          </button>
        </div>
      </div>

      {/* 통계 카드 */}
      <div className="grid grid-cols-1 sm:grid-cols-2 gap-lg mb-xl">
        <div className="bento-card p-lg flex items-center gap-lg">
          <div className="p-sm bg-primary-container/30 rounded-lg">
            <span className="material-symbols-outlined text-primary" style={{ fontVariationSettings: "'FILL' 1" }}>group</span>
          </div>
          <div>
            <p className="text-label-md font-label-md text-on-surface-variant">총 회원 수</p>
            <div className="flex items-baseline gap-sm">
              <p className="text-headline-md font-headline-md text-on-surface">1,284</p>
              <span className="text-label-sm font-label-sm text-green-600 bg-green-50 px-xs py-0.5 rounded-full">+12%</span>
            </div>
          </div>
        </div>
        <div className="bento-card p-lg flex items-center gap-lg">
          <div className="p-sm bg-secondary-container/50 rounded-lg">
            <span className="material-symbols-outlined text-secondary" style={{ fontVariationSettings: "'FILL' 1" }}>person_check</span>
          </div>
          <div>
            <p className="text-label-md font-label-md text-on-surface-variant">활성 회원 수</p>
            <div className="flex items-baseline gap-sm">
              <p className="text-headline-md font-headline-md text-on-surface">{activeCount}</p>
              <span className="text-label-sm font-label-sm text-on-surface-variant">/ {MOCK_MEMBERS.length}명</span>
            </div>
          </div>
        </div>
      </div>

      {/* 탭 + 검색 */}
      <div className="flex flex-col md:flex-row md:items-center justify-between gap-md mb-lg">
      {/*  <div className="flex items-center gap-sm bg-surface-container-low p-1 rounded-full w-fit">*/}
      {/*    {([['all', '모든 학생'], ['status', '계정 상태']] as [FilterTab, string][]).map(([key, label]) => (*/}
      {/*      <button*/}
      {/*        key={key}*/}
      {/*        onClick={() => setFilterTab(key)}*/}
      {/*        className={`px-lg py-1.5 rounded-full font-label-md text-label-md transition-all ${*/}
      {/*          filterTab === key*/}
      {/*            ? 'bg-primary-container text-on-primary-container shadow-sm'*/}
      {/*            : 'text-on-surface-variant hover:bg-surface-container'*/}
      {/*        }`}*/}
      {/*      >*/}
      {/*        {label}*/}
      {/*      </button>*/}
      {/*    ))}*/}
      {/*  </div>*/}

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

      {/* 테이블 */}
      <div className="bg-surface-container-lowest border border-outline-variant rounded-xl overflow-hidden">
        <div className="overflow-x-auto">
          <table className="w-full text-left border-collapse">
            <thead className="bg-surface-container-low border-b border-outline-variant">
              <tr>
                <th className="px-xl py-lg text-label-md font-label-md text-on-surface-variant uppercase tracking-wider">회원</th>
                <th className="px-xl py-lg text-label-md font-label-md text-on-surface-variant uppercase tracking-wider">이메일</th>
                <th className="px-xl py-lg text-label-md font-label-md text-on-surface-variant uppercase tracking-wider">가입일</th>
                <th className="px-xl py-lg text-label-md font-label-md text-on-surface-variant uppercase tracking-wider text-center">역할</th>
                <th className="px-xl py-lg text-label-md font-label-md text-on-surface-variant uppercase tracking-wider text-center">계정 상태</th>
                <th className="px-xl py-lg text-label-md font-label-md text-on-surface-variant uppercase tracking-wider"></th>
              </tr>
            </thead>
            <tbody className="divide-y divide-outline-variant">
              {members.length === 0 ? (
                <tr>
                  <td colSpan={6} className="px-xl py-xxl text-center text-on-surface-variant text-body-md font-body-md">검색 결과가 없습니다.</td>
                </tr>
              ) : (
                members.map((m) => {
                  const roleInfo = ROLE_MAP[m.role] ?? ROLE_MAP['member'];
                  return (
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
                      <td className="px-xl py-lg text-body-md font-body-md text-on-surface-variant">{m.joinedAt}</td>
                      <td className="px-xl py-lg text-center">
                        <span className={`px-sm py-1 rounded-full text-label-sm font-label-sm ${roleInfo.cls}`}>
                          {roleInfo.label}
                        </span>
                      </td>
                      <td className="px-xl py-lg text-center">
                        <span className={`px-sm py-1 rounded-full text-label-sm font-label-sm ${
                          m.status === 'ACTIVE'
                            ? 'bg-secondary-container text-on-secondary-container'
                            : 'bg-surface-container-high text-on-surface-variant'
                        }`}>
                          {m.status === 'ACTIVE' ? '활성' : '정지됨'}
                        </span>
                      </td>
                      <td className="px-xl py-lg">
                        <Link
                          href={`/admin/members/${m.id}`}
                          className="text-primary text-label-sm font-label-sm hover:underline"
                        >
                          상세보기
                        </Link>
                      </td>
                    </tr>
                  );
                })
              )}
            </tbody>
          </table>
        </div>

        {/* 하단 요약 */}
        <div className="px-xl py-md bg-surface-container-lowest border-t border-outline-variant flex justify-between items-center">
          <span className="text-label-sm font-label-sm text-on-surface-variant">
            전체 {MOCK_MEMBERS.length}명 중 {members.length}명 표시 중
          </span>
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
