'use client';

import Link from 'next/link';
import { useParams } from 'next/navigation';
import { useAdminMemberDetail } from './useAdminMemberDetail';
import type { AdminMemberStatus } from '../types';
import {
  ADMIN_MEMBER_ROLE_LABEL,
  ADMIN_MEMBER_STATUS_LABEL,
  ADMIN_MEMBER_STATUS_OPTIONS,
  formatAdminMemberDate,
  normalizeAdminMemberId,
} from '../viewModel';

export default function MemberDetailPage() {
  const params = useParams<{ id: string }>();
  const memberId = normalizeAdminMemberId(params.id);
  const {
    member,
    selectedStatus,
    setSelectedStatus,
    isLoading,
    isSaving,
    message,
    errorMessage,
    handleStatusChange,
  } = useAdminMemberDetail(memberId);

  return (
    <div className="max-w-[1000px] mx-auto px-gutter pt-xl pb-xxl">
      <div className="mb-lg">
        <Link href="/admin/members" className="flex items-center gap-xs text-on-surface-variant hover:text-primary transition-colors">
          <span className="material-symbols-outlined text-[20px]">arrow_back</span>
          <span className="text-label-md font-label-md">회원 목록</span>
        </Link>
      </div>

      {isLoading ? (
        <p className="text-body-md font-body-md text-on-surface-variant">회원 정보를 불러오는 중입니다.</p>
      ) : errorMessage && !member ? (
        <p role="alert" className="px-lg py-sm rounded-lg bg-error-container/30 text-error text-label-md font-label-md">
          {errorMessage}
        </p>
      ) : member ? (
        <div className="grid grid-cols-1 md:grid-cols-3 gap-xl">
          <div className="space-y-lg">
            <div className="bg-surface-container-lowest border border-outline-variant rounded-xl p-xl">
              <div className="flex flex-col items-center text-center mb-lg">
                <div className="w-20 h-20 rounded-full bg-primary text-on-primary flex items-center justify-center text-headline-md font-bold mb-md">
                  {member.nickname.charAt(0)}
                </div>
                <h1 className="text-headline-sm font-headline-md text-on-surface">{member.nickname}</h1>
                <p className="text-body-sm font-body-md text-on-surface-variant">{member.email}</p>
                <span className="mt-sm px-sm py-1 rounded-full text-label-sm font-label-sm bg-secondary-container text-on-secondary-container">
                  {ADMIN_MEMBER_STATUS_LABEL[member.status]}
                </span>
              </div>
              <div className="border-t border-outline-variant pt-lg space-y-sm">
                <div className="flex justify-between items-center">
                  <span className="text-label-sm font-label-sm text-on-surface-variant">회원 ID</span>
                  <span className="text-label-sm font-label-sm text-on-surface">{member.memberId}</span>
                </div>
                <div className="flex justify-between items-center">
                  <span className="text-label-sm font-label-sm text-on-surface-variant">역할</span>
                  <span className="text-label-sm font-label-sm text-on-surface">{ADMIN_MEMBER_ROLE_LABEL[member.role]}</span>
                </div>
                <div className="flex justify-between items-center">
                  <span className="text-label-sm font-label-sm text-on-surface-variant">가입일</span>
                  <span className="text-label-sm font-label-sm text-on-surface">{formatAdminMemberDate(member.createdAt)}</span>
                </div>
              </div>
            </div>

            <div className="bg-surface-container-lowest border border-outline-variant rounded-xl p-lg space-y-sm">
              <h2 className="text-label-md font-label-md text-on-surface-variant uppercase tracking-wider mb-md">계정 상태 관리</h2>
              <select
                className="w-full border border-outline-variant rounded-lg px-md py-sm text-body-md font-body-md"
                value={selectedStatus}
                onChange={(event) => setSelectedStatus(event.target.value as AdminMemberStatus)}
              >
                <option value="" disabled>상태 선택</option>
                {ADMIN_MEMBER_STATUS_OPTIONS.map((option) => (
                  <option key={option.value} value={option.value}>
                    {option.label}
                  </option>
                ))}
              </select>
              <button
                type="button"
                disabled={isSaving}
                onClick={handleStatusChange}
                className="w-full bg-primary text-on-primary py-sm rounded-lg text-label-sm font-label-sm hover:opacity-90 disabled:opacity-60 transition-colors"
              >
                {isSaving ? '변경 중...' : '상태 변경'}
              </button>
            </div>
          </div>

          <div className="md:col-span-2">
            <h2 className="text-headline-sm font-headline-md text-on-surface mb-lg">회원 상세 정보</h2>
            <div className="bg-surface-container-lowest border border-outline-variant rounded-xl p-xl space-y-md">
              <div className="flex justify-between border-b border-outline-variant pb-md">
                <span className="text-label-md font-label-md text-on-surface-variant">이메일</span>
                <span className="text-body-md font-body-md text-on-surface">{member.email}</span>
              </div>
              <div className="flex justify-between border-b border-outline-variant pb-md">
                <span className="text-label-md font-label-md text-on-surface-variant">닉네임</span>
                <span className="text-body-md font-body-md text-on-surface">{member.nickname}</span>
              </div>
              <div className="flex justify-between border-b border-outline-variant pb-md">
                <span className="text-label-md font-label-md text-on-surface-variant">수정일</span>
                <span className="text-body-md font-body-md text-on-surface">{formatAdminMemberDate(member.updatedAt)}</span>
              </div>
              {(message || errorMessage) && (
                <p role="alert" className={`px-lg py-sm rounded-lg text-label-md font-label-md ${errorMessage ? 'bg-error-container/30 text-error' : 'bg-secondary-container text-on-secondary-container'}`}>
                  {errorMessage || message}
                </p>
              )}
            </div>
          </div>
        </div>
      ) : null}
    </div>
  );
}
