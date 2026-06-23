'use client';

import Link from 'next/link';
import { useAdminMembers } from './useAdminMembers';
import {
  ADMIN_MEMBER_ROLE_BADGE,
  ADMIN_MEMBER_STATUS_BADGE,
  formatAdminMemberDate,
} from './viewModel';

export default function AdminMembersPage() {
  const {
    members,
    filteredMembers,
    activeCount,
    search,
    setSearch,
    isLoading,
    errorMessage,
  } = useAdminMembers();

  return (
    <div className="mt-16 flex-grow px-gutter py-xl max-w-[1280px] mx-auto w-full">
      <div className="mb-xl flex flex-col md:flex-row md:items-end justify-between gap-md">
        <div>
          <h1 className="font-headline-lg text-headline-lg text-on-surface mb-xs">회원 관리</h1>
          <p className="font-body-md text-body-md text-on-surface-variant">사용자 계정, 권한 및 보안 상태를 관리합니다.</p>
        </div>
      </div>

      <div className="grid grid-cols-1 sm:grid-cols-2 gap-lg mb-xl">
        <div className="bento-card p-lg flex items-center gap-lg">
          <div className="p-sm bg-primary-container/30 rounded-lg">
            <span className="material-symbols-outlined text-primary">group</span>
          </div>
          <div>
            <p className="text-label-md font-label-md text-on-surface-variant">총 회원 수</p>
            <p className="text-headline-md font-headline-md text-on-surface">{members.length}</p>
          </div>
        </div>
        <div className="bento-card p-lg flex items-center gap-lg">
          <div className="p-sm bg-secondary-container/50 rounded-lg">
            <span className="material-symbols-outlined text-secondary">person_check</span>
          </div>
          <div>
            <p className="text-label-md font-label-md text-on-surface-variant">활성 회원 수</p>
            <p className="text-headline-md font-headline-md text-on-surface">{activeCount}</p>
          </div>
        </div>
      </div>

      <div className="flex flex-col md:flex-row md:items-center justify-between gap-md mb-lg">
        <div className="relative w-64">
          <span className="material-symbols-outlined absolute left-3 top-1/2 -translate-y-1/2 text-on-surface-variant text-[20px]">search</span>
          <input
            type="text"
            value={search}
            onChange={(event) => setSearch(event.target.value)}
            placeholder="닉네임 또는 이메일 검색..."
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
                <th className="px-xl py-lg text-label-md font-label-md text-on-surface-variant uppercase tracking-wider">가입일</th>
                <th className="px-xl py-lg text-label-md font-label-md text-on-surface-variant uppercase tracking-wider text-center">역할</th>
                <th className="px-xl py-lg text-label-md font-label-md text-on-surface-variant uppercase tracking-wider text-center">계정 상태</th>
                <th className="px-xl py-lg text-label-md font-label-md text-on-surface-variant uppercase tracking-wider" />
              </tr>
            </thead>
            <tbody className="divide-y divide-outline-variant">
              {isLoading ? (
                <tr>
                  <td colSpan={6} className="px-xl py-xxl text-center text-on-surface-variant text-body-md font-body-md">회원 목록을 불러오는 중입니다.</td>
                </tr>
              ) : errorMessage ? (
                <tr>
                  <td colSpan={6} className="px-xl py-xxl text-center text-error text-body-md font-body-md">{errorMessage}</td>
                </tr>
              ) : filteredMembers.length === 0 ? (
                <tr>
                  <td colSpan={6} className="px-xl py-xxl text-center text-on-surface-variant text-body-md font-body-md">검색 결과가 없습니다.</td>
                </tr>
              ) : (
                filteredMembers.map((member) => {
                  const roleInfo = ADMIN_MEMBER_ROLE_BADGE[member.role];
                  const statusInfo = ADMIN_MEMBER_STATUS_BADGE[member.status];
                  return (
                    <tr key={member.memberId} className="hover:bg-surface-container/50 transition-colors group">
                      <td className="px-xl py-lg">
                        <div className="flex items-center gap-md">
                          <div className="w-9 h-9 rounded-full bg-primary/20 text-primary flex items-center justify-center font-bold text-label-md flex-shrink-0">
                            {member.nickname.charAt(0)}
                          </div>
                          <span className="text-body-md font-body-md text-on-surface">{member.nickname}</span>
                        </div>
                      </td>
                      <td className="px-xl py-lg text-body-md font-body-md text-on-surface-variant">{member.email}</td>
                      <td className="px-xl py-lg text-body-md font-body-md text-on-surface-variant">{formatAdminMemberDate(member.createdAt)}</td>
                      <td className="px-xl py-lg text-center">
                        <span className={`px-sm py-1 rounded-full text-label-sm font-label-sm ${roleInfo.cls}`}>
                          {roleInfo.label}
                        </span>
                      </td>
                      <td className="px-xl py-lg text-center">
                        <span className={`px-sm py-1 rounded-full text-label-sm font-label-sm ${statusInfo.cls}`}>
                          {statusInfo.label}
                        </span>
                      </td>
                      <td className="px-xl py-lg">
                        <Link href={`/admin/members/${member.memberId}`} className="text-primary text-label-sm font-label-sm hover:underline">
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

        <div className="px-xl py-md bg-surface-container-lowest border-t border-outline-variant flex justify-between items-center">
          <span className="text-label-sm font-label-sm text-on-surface-variant">
            전체 {members.length}명 중 {filteredMembers.length}명 표시 중
          </span>
        </div>
      </div>
    </div>
  );
}
