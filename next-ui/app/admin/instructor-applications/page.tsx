'use client';

import { useEffect, useState } from 'react';
import { adminInstructorApplicationApi } from '../api';
import type { AdminInstructorApplication } from '../types';

function formatDate(iso: string) {
  return iso.slice(0, 10).replace(/-/g, '.');
}

export default function AdminInstructorApplicationsPage() {
  const [applications, setApplications] = useState<AdminInstructorApplication[]>([]);
  const [isLoading, setIsLoading] = useState(true);
  const [selected, setSelected] = useState<AdminInstructorApplication | null>(null);
  const [rejectTarget, setRejectTarget] = useState<AdminInstructorApplication | null>(null);
  const [rejectInput, setRejectInput] = useState('');
  const [isSubmitting, setIsSubmitting] = useState(false);

  useEffect(() => {
    adminInstructorApplicationApi.getApplications()
      .then(setApplications)
      .catch(() => setApplications([]))
      .finally(() => setIsLoading(false));
  }, []);

  const handleApprove = async (id: number) => {
    setIsSubmitting(true);
    try {
      await adminInstructorApplicationApi.review(id, { action: 'APPROVE' });
      const updated = await adminInstructorApplicationApi.getApplications();
      setApplications(updated);
      setSelected(null);
    } catch {
      alert('승인 처리 중 오류가 발생했습니다.');
    } finally {
      setIsSubmitting(false);
    }
  };

  const handleReject = async (id: number, reason: string) => {
    setIsSubmitting(true);
    try {
      await adminInstructorApplicationApi.review(id, { action: 'REJECT', rejectionReason: reason });
      const updated = await adminInstructorApplicationApi.getApplications();
      setApplications(updated);
      setRejectTarget(null);
      setRejectInput('');
      setSelected(null);
    } catch {
      alert('반려 처리 중 오류가 발생했습니다.');
    } finally {
      setIsSubmitting(false);
    }
  };

  const openRejectModal = (app: AdminInstructorApplication) => {
    setRejectTarget(app);
    setRejectInput('');
  };

  const statusBadge = (status: string) => {
    if (status === 'APPROVED') return <span className="flex items-center gap-xs text-label-sm font-label-sm text-green-600"><span className="w-2 h-2 rounded-full bg-green-600 inline-block" />승인됨</span>;
    if (status === 'REJECTED') return <span className="flex items-center gap-xs text-label-sm font-label-sm text-error"><span className="w-2 h-2 rounded-full bg-error inline-block" />반려됨</span>;
    return <span className="flex items-center gap-xs text-label-sm font-label-sm text-on-surface-variant"><span className="w-2 h-2 rounded-full bg-outline-variant inline-block" />대기 중</span>;
  };

  return (
    <div className="pt-xl pb-xl px-gutter max-w-[1280px] mx-auto w-full">

      {/* 헤더 */}
      <div className="mb-xl flex flex-col md:flex-row md:items-end justify-between gap-md">
        <div>
          <h1 className="font-headline-lg text-headline-lg text-on-surface mb-xs">강사 신청 목록</h1>
          <p className="font-body-md text-body-md text-on-surface-variant">플랫폼의 새로운 강사 후보자 신청을 검토하고 관리합니다.</p>
        </div>
        <div className="flex gap-sm">
          <div className="relative">
            <span className="material-symbols-outlined absolute left-3 top-1/2 -translate-y-1/2 text-on-surface-variant text-[20px]">search</span>
            <input
              className="pl-10 pr-4 py-2 rounded-lg border border-outline-variant bg-surface-container-lowest focus:ring-2 focus:ring-primary-container focus:border-primary outline-none transition-all font-body-md text-body-md w-full md:w-64"
              placeholder="신청자 검색..."
              type="text"
            />
          </div>
          <button className="bg-surface-container-high text-on-surface px-4 py-2 rounded-lg font-label-md text-label-md flex items-center gap-xs hover:bg-surface-variant transition-all">
            <span className="material-symbols-outlined text-[20px]">filter_list</span>
            필터
          </button>
        </div>
      </div>

      {/* 테이블 */}
      <div className="bg-surface-container-lowest rounded-xl border border-outline-variant overflow-hidden">
        <div className="overflow-x-auto">
          <table className="w-full text-left border-collapse">
            <thead>
              <tr className="bg-surface-container-low border-b border-outline-variant">
                <th className="px-lg py-md font-label-md text-label-md text-on-surface-variant">강사명</th>
                <th className="px-lg py-md font-label-md text-label-md text-on-surface-variant">전문 분야</th>
                <th className="px-lg py-md font-label-md text-label-md text-on-surface-variant">신청일</th>
                <th className="px-lg py-md font-label-md text-label-md text-on-surface-variant">상태</th>
                <th className="px-lg py-md font-label-md text-label-md text-on-surface-variant">반려 사유</th>
                <th className="px-lg py-md font-label-md text-label-md text-on-surface-variant text-right">관리</th>
              </tr>
            </thead>
            <tbody className="divide-y divide-outline-variant">
              {isLoading ? (
                <tr>
                  <td colSpan={6} className="px-lg py-xl text-center text-on-surface-variant font-body-md text-body-md">
                    불러오는 중...
                  </td>
                </tr>
              ) : applications.length === 0 ? (
                <tr>
                  <td colSpan={6} className="px-lg py-xl text-center text-on-surface-variant font-body-md text-body-md">
                    강사 신청 내역이 없습니다.
                  </td>
                </tr>
              ) : (
                applications.map((app) => (
                  <tr key={app.id} className="hover:bg-surface-container-low transition-colors">
                    <td className="px-lg py-md font-body-md text-body-md text-on-surface">{app.instructorName}</td>
                    <td className="px-lg py-md">
                      <span className="px-sm py-xs rounded-full bg-secondary-container text-on-secondary-container text-label-sm font-label-sm">{app.expertise}</span>
                    </td>
                    <td className="px-lg py-md font-body-md text-body-md text-on-surface-variant">{formatDate(app.createdAt)}</td>
                    <td className="px-lg py-md">{statusBadge(app.status)}</td>
                    <td className="px-lg py-md font-body-md text-body-md max-w-[200px]">
                      {app.status === 'REJECTED' && app.rejectionReason
                        ? <span className="text-error line-clamp-2">{app.rejectionReason}</span>
                        : <span className="text-on-surface-variant">-</span>
                      }
                    </td>
                    <td className="px-lg py-md">
                      <div className="flex items-center justify-end gap-sm">
                        {/* 눈 아이콘 — 모달 열기 */}
                        <button
                          onClick={() => setSelected(app)}
                          className="p-xs rounded-full hover:bg-surface-container transition-colors text-on-surface-variant hover:text-primary"
                          title="상세 보기"
                        >
                          <span className="material-symbols-outlined text-[20px]">visibility</span>
                        </button>
                        {/* 승인 */}
                        <button
                          onClick={() => handleApprove(app.id)}
                          disabled={app.status !== 'PENDING' || isSubmitting}
                          className="p-xs rounded-full hover:bg-primary-container transition-colors text-on-surface-variant hover:text-primary disabled:opacity-30 disabled:cursor-not-allowed"
                          title="승인"
                        >
                          <span className="material-symbols-outlined text-[20px]">check_circle</span>
                        </button>
                        {/* 반려 */}
                        <button
                          onClick={() => openRejectModal(app)}
                          disabled={app.status !== 'PENDING' || isSubmitting}
                          className="p-xs rounded-full hover:bg-error-container transition-colors text-on-surface-variant hover:text-error disabled:opacity-30 disabled:cursor-not-allowed"
                          title="반려"
                        >
                          <span className="material-symbols-outlined text-[20px]">cancel</span>
                        </button>
                      </div>
                    </td>
                  </tr>
                ))
              )}
            </tbody>
          </table>
        </div>

        {/* 페이지네이션 */}
        <div className="px-lg py-md border-t border-outline-variant flex items-center justify-between">
          <span className="text-label-sm font-label-sm text-on-surface-variant">전체 {applications.length}건 중 {applications.length}건 표시</span>
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

      {/* 반려 사유 입력 모달 */}
      {rejectTarget && (
        <div className="fixed inset-0 z-[60] flex items-center justify-center bg-black/40" onClick={() => { setRejectTarget(null); setRejectInput(''); }}>
          <div
            className="bg-surface-container-lowest rounded-2xl shadow-xl w-full max-w-sm mx-md p-xl relative"
            onClick={(e) => e.stopPropagation()}
          >
            <button
              onClick={() => { setRejectTarget(null); setRejectInput(''); }}
              className="absolute top-md right-md p-xs rounded-full hover:bg-surface-container transition-colors text-on-surface-variant"
            >
              <span className="material-symbols-outlined">close</span>
            </button>

            <div className="flex items-center gap-sm mb-lg">
              <div className="p-sm bg-error-container rounded-lg">
                <span className="material-symbols-outlined text-error text-[20px]">cancel</span>
              </div>
              <div>
                <p className="font-headline-sm text-headline-sm text-on-surface">반려 사유 입력</p>
                <p className="text-label-sm font-label-sm text-on-surface-variant">{rejectTarget.instructorName}</p>
              </div>
            </div>

            <textarea
              autoFocus
              value={rejectInput}
              onChange={(e) => setRejectInput(e.target.value)}
              rows={4}
              placeholder="반려 사유를 입력하세요. 신청자에게 전달되는 내용입니다."
              className="w-full px-md py-sm border border-outline-variant rounded-lg text-body-md font-body-md text-on-surface resize-none focus:border-error focus:outline-none transition-all mb-lg"
            />

            <div className="flex gap-sm">
              <button
                onClick={() => { setRejectTarget(null); setRejectInput(''); }}
                className="flex-1 py-sm rounded-xl border border-outline-variant text-on-surface font-label-md text-label-md hover:bg-surface-container transition-all"
              >
                취소
              </button>
              <button
                onClick={() => handleReject(rejectTarget.id, rejectInput.trim())}
                disabled={!rejectInput.trim() || isSubmitting}
                className="flex-1 py-sm rounded-xl bg-error text-on-error font-label-md text-label-md hover:opacity-90 transition-all disabled:opacity-40 disabled:cursor-not-allowed"
              >
                {isSubmitting ? '처리 중...' : '반려 확정'}
              </button>
            </div>
          </div>
        </div>
      )}

      {/* 상세 모달 */}
      {selected && (
        <div className="fixed inset-0 z-50 flex items-center justify-center bg-black/40" onClick={() => setSelected(null)}>
          <div
            className="bg-surface-container-lowest rounded-2xl shadow-xl w-full max-w-md mx-md p-xl relative"
            onClick={(e) => e.stopPropagation()}
          >
            {/* 닫기 버튼 */}
            <button
              onClick={() => setSelected(null)}
              className="absolute top-md right-md p-xs rounded-full hover:bg-surface-container transition-colors text-on-surface-variant"
            >
              <span className="material-symbols-outlined">close</span>
            </button>

            {/* 프로필 헤더 */}
            <div className="flex items-center gap-md mb-lg">
              <div className="w-14 h-14 rounded-full bg-secondary-container flex items-center justify-center text-on-secondary-container">
                <span className="material-symbols-outlined text-[32px]">person</span>
              </div>
              <div>
                <p className="font-headline-md text-headline-md text-on-surface">{selected.instructorName}</p>
                <p className="font-body-md text-body-md text-on-surface-variant">신청일 {formatDate(selected.createdAt)}</p>
              </div>
            </div>

            {/* 전문 분야 */}
            <div className="mb-md">
              <p className="text-label-sm font-label-sm text-on-surface-variant mb-xs">전문 분야</p>
              <p className="font-body-md text-body-md text-on-surface">{selected.expertise}</p>
            </div>

            {/* 자기소개 */}
            <div className="mb-xl">
              <p className="text-label-sm font-label-sm text-on-surface-variant mb-xs">자기소개</p>
              <p className="font-body-md text-body-md text-on-surface-variant leading-relaxed">{selected.introduction}</p>
            </div>

            {/* 액션 버튼 */}
            {selected.status === 'PENDING' ? (
              <div className="flex gap-sm">
                <button
                  onClick={() => handleApprove(selected.id)}
                  disabled={isSubmitting}
                  className="flex-1 flex items-center justify-center gap-xs py-sm rounded-xl bg-primary text-on-primary font-label-md text-label-md hover:brightness-110 transition-all disabled:opacity-50"
                >
                  <span className="material-symbols-outlined text-[18px]">check_circle</span>
                  {isSubmitting ? '처리 중...' : '강사 승인'}
                </button>
                <button
                  onClick={() => openRejectModal(selected)}
                  disabled={isSubmitting}
                  className="px-lg py-sm rounded-xl bg-surface-container-high text-error font-label-md text-label-md hover:bg-error-container transition-all disabled:opacity-50"
                >
                  반려
                </button>
              </div>
            ) : (
              <div className="text-center py-sm text-label-md font-label-md text-on-surface-variant">
                {selected.status === 'APPROVED' ? '✓ 승인 완료' : '✗ 반려됨'}
              </div>
            )}
          </div>
        </div>
      )}
    </div>
  );
}
