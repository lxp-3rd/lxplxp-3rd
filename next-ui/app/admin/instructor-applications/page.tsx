'use client';

import { useState } from 'react';

const MOCK_APPLICATIONS = [
  { id: 'a1', nickname: 'PixelMaster',      displayName: 'Kim Ji-hun',    expertise: 'UI/UX 디자인',  appliedAt: '2024.05.12', status: 'PENDING', bio: '저는 확장 가능한 클라우드 솔루션을 구축하는 데 10년 이상의 경험을 가진 열정적인 교육자입니다. LXP 플랫폼에서의 심습 중심 학습 경험을 통해 학술적 이론과 실무 엔지니어링 관련 사이의 간극을 메우는 것이 저의 목표입니다.' },
  { id: 'a2', nickname: 'CodeNinja',        displayName: 'Lee Min-ho',    expertise: '풀스택 개발',    appliedAt: '2024.05.11', status: 'PENDING', bio: '10년 이상의 풀스택 개발 경험을 보유한 시니어 엔지니어로, React, Node.js, 클라우드 아키텍처를 전문으로 합니다.' },
  { id: 'a3', nickname: 'DataGuru',         displayName: 'Sarah Park',    expertise: '데이터 사이언스', appliedAt: '2024.05.10', status: 'PENDING', bio: 'Python과 ML을 활용한 데이터 분석 전문가입니다. 대학원 연구 및 기업 프로젝트를 통해 다양한 데이터 파이프라인 구축 경험이 있습니다.' },
  { id: 'a4', nickname: 'MarketingWiz',     displayName: 'Park Jun-su',   expertise: '그로스 마케팅', appliedAt: '2024.05.10', status: 'PENDING', bio: '스타트업에서 그로스 해킹, SEO, 퍼포먼스 마케팅을 담당해 온 마케터입니다. 실전 중심 커리큘럼을 제공하고자 합니다.' },
  { id: 'a5', nickname: 'CloudArchitect',   displayName: 'Choi Da-in',    expertise: 'AWS/클라우드',  appliedAt: '2024.05.09', status: 'PENDING', bio: 'AWS, GCP 환경에서 대규모 인프라를 설계·운영한 경험을 가진 클라우드 아키텍트입니다.' },
  { id: 'a6', nickname: 'SecureGuru',       displayName: 'Jung Han-byeol', expertise: '사이버 보안',  appliedAt: '2024.05.08', status: 'PENDING', bio: '화이트햇 해커 출신의 보안 전문가입니다. 침투 테스트 및 보안 취약점 분석 분야에서 다양한 프로젝트를 수행했습니다.' },
];

type Application = typeof MOCK_APPLICATIONS[number];

export default function AdminInstructorApplicationsPage() {
  const [applications, setApplications] = useState(MOCK_APPLICATIONS);
  const [selected, setSelected] = useState<Application | null>(null);

  const handleApprove = (id: string) => {
    setApplications((prev) => prev.map((a) => a.id === id ? { ...a, status: 'APPROVED' } : a));
    setSelected(null);
  };

  const handleReject = (id: string) => {
    setApplications((prev) => prev.map((a) => a.id === id ? { ...a, status: 'REJECTED' } : a));
    setSelected(null);
  };

  const statusBadge = (status: string) => {
    if (status === 'APPROVED') return <span className="flex items-center gap-xs text-label-sm font-label-sm text-primary"><span className="w-2 h-2 rounded-full bg-primary inline-block" />승인됨</span>;
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
                <th className="px-lg py-md font-label-md text-label-md text-on-surface-variant">신청자 닉네임</th>
                <th className="px-lg py-md font-label-md text-label-md text-on-surface-variant">표시 이름</th>
                <th className="px-lg py-md font-label-md text-label-md text-on-surface-variant">전문 분야</th>
                <th className="px-lg py-md font-label-md text-label-md text-on-surface-variant">신청일</th>
                <th className="px-lg py-md font-label-md text-label-md text-on-surface-variant">상태</th>
                <th className="px-lg py-md font-label-md text-label-md text-on-surface-variant text-right">관리</th>
              </tr>
            </thead>
            <tbody className="divide-y divide-outline-variant">
              {applications.map((app) => (
                <tr key={app.id} className="hover:bg-surface-container-low transition-colors">
                  <td className="px-lg py-md font-body-md text-body-md text-on-surface">{app.nickname}</td>
                  <td className="px-lg py-md font-body-md text-body-md text-on-surface-variant">{app.displayName}</td>
                  <td className="px-lg py-md">
                    <span className="px-sm py-xs rounded-full bg-secondary-container text-on-secondary-container text-label-sm font-label-sm">{app.expertise}</span>
                  </td>
                  <td className="px-lg py-md font-body-md text-body-md text-on-surface-variant">{app.appliedAt}</td>
                  <td className="px-lg py-md">{statusBadge(app.status)}</td>
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
                        disabled={app.status !== 'PENDING'}
                        className="p-xs rounded-full hover:bg-primary-container transition-colors text-on-surface-variant hover:text-primary disabled:opacity-30 disabled:cursor-not-allowed"
                        title="승인"
                      >
                        <span className="material-symbols-outlined text-[20px]">check_circle</span>
                      </button>
                      {/* 반려 */}
                      <button
                        onClick={() => handleReject(app.id)}
                        disabled={app.status !== 'PENDING'}
                        className="p-xs rounded-full hover:bg-error-container transition-colors text-on-surface-variant hover:text-error disabled:opacity-30 disabled:cursor-not-allowed"
                        title="반려"
                      >
                        <span className="material-symbols-outlined text-[20px]">cancel</span>
                      </button>
                    </div>
                  </td>
                </tr>
              ))}
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
                <p className="font-headline-md text-headline-md text-on-surface">{selected.nickname}</p>
                <p className="font-body-md text-body-md text-on-surface-variant">{selected.displayName}</p>
              </div>
            </div>

            {/* 전문 분야 */}
            <div className="mb-md">
              <p className="text-label-sm font-label-sm text-on-surface-variant mb-xs">전문 분야</p>
              <p className="font-body-md text-body-md text-on-surface">업계 10년 이상 · {selected.expertise}</p>
            </div>

            {/* 자기소개 */}
            <div className="mb-xl">
              <p className="text-label-sm font-label-sm text-on-surface-variant mb-xs">자기소개</p>
              <p className="font-body-md text-body-md text-on-surface-variant leading-relaxed">{selected.bio}</p>
            </div>

            {/* 액션 버튼 */}
            {selected.status === 'PENDING' ? (
              <div className="flex gap-sm">
                <button
                  onClick={() => handleApprove(selected.id)}
                  className="flex-1 flex items-center justify-center gap-xs py-sm rounded-xl bg-primary text-on-primary font-label-md text-label-md hover:brightness-110 transition-all"
                >
                  <span className="material-symbols-outlined text-[18px]">check_circle</span>
                  강사 승인
                </button>
                <button
                  onClick={() => handleReject(selected.id)}
                  className="px-lg py-sm rounded-xl bg-surface-container-high text-error font-label-md text-label-md hover:bg-error-container transition-all"
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
