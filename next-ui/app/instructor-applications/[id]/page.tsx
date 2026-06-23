'use client';

import Link from 'next/link';
import { TopNavBar } from '@/components/TopNavBar';
import { Footer } from '@/components/Footer';

const MOCK_APPLICATION = {
  id: 'app1',
  applicantName: '한수민',
  email: 'soomin.han@email.com',
  phone: '010-1234-5678',
  appliedAt: '2026-06-10',
  status: 'PENDING',
  expertise: ['React', 'TypeScript', 'Next.js'],
  career: '웹 프론트엔드 개발자 7년 (현 카카오페이 시니어 개발자)',
  motivation: '실무 중심의 프론트엔드 강의를 통해 취업 준비생과 주니어 개발자들이 실제 업무에서 바로 활용할 수 있는 역량을 키울 수 있도록 돕고 싶습니다. 특히 Next.js 풀스택과 팀 협업 워크플로우에 집중한 커리큘럼을 계획하고 있습니다.',
  proposedCourse: 'Next.js 실전 풀스택 개발 — App Router & Server Actions 마스터',
  portfolioUrl: 'https://github.com/soomin-han',
};

const TIMELINE = [
  { date: '2026-06-10', event: '강사 신청서 제출', status: 'done' },
  { date: '2026-06-12', event: '서류 검토 시작', status: 'done' },
  { date: '2026-06-18', event: '1차 서류 통과', status: 'done' },
  { date: '', event: '운영팀 최종 검토', status: 'current' },
  { date: '', event: '승인 / 반려 결정', status: 'pending' },
];

const STATUS_MAP: Record<string, { label: string; color: string }> = {
  PENDING: { label: '검토 중', color: 'bg-tertiary-container text-on-tertiary-container' },
  APPROVED: { label: '승인됨', color: 'bg-secondary-container text-on-secondary-container' },
  REJECTED: { label: '반려됨', color: 'bg-error-container text-on-error-container' },
};

export default function InstructorApplicationDetailPage() {
  const app = MOCK_APPLICATION;
  const statusInfo = STATUS_MAP[app.status] ?? STATUS_MAP['PENDING'];

  return (
    <>
      <TopNavBar />
      <main className="pt-16 min-h-screen bg-background">
        <div className="max-w-[1000px] mx-auto px-gutter pt-xl pb-xxl">

          {/* 뒤로가기 */}
          <div className="mb-lg">
            <Link href="/instructor-applications" className="flex items-center gap-xs text-on-surface-variant hover:text-primary transition-colors">
              <span className="material-symbols-outlined text-[20px]">arrow_back</span>
              <span className="text-label-md font-label-md">신청 목록으로</span>
            </Link>
          </div>

          {/* 헤더 */}
          <div className="flex items-start justify-between mb-xl flex-wrap gap-md">
            <div>
              <h1 className="text-headline-md font-headline-md text-on-surface mb-xs">강사 신청 상세</h1>
              <p className="text-body-md font-body-md text-on-surface-variant">신청 ID: {app.id} · 신청일: {app.appliedAt}</p>
            </div>
            <span className={`px-md py-sm rounded-lg text-label-md font-label-md ${statusInfo.color}`}>{statusInfo.label}</span>
          </div>

          <div className="grid grid-cols-1 md:grid-cols-3 gap-xl">
            {/* 신청자 정보 + 심사 내용 */}
            <div className="md:col-span-2 space-y-lg">

              {/* 신청자 기본 정보 */}
              <div className="bg-surface-container-lowest border border-outline-variant rounded-xl p-xl">
                <h2 className="text-label-md font-label-md text-on-surface-variant uppercase tracking-wider mb-lg flex items-center gap-xs">
                  <span className="material-symbols-outlined text-[18px] text-primary">person</span>
                  신청자 정보
                </h2>
                <div className="flex items-center gap-lg mb-lg">
                  <div className="w-16 h-16 rounded-full bg-primary text-on-primary flex items-center justify-center text-headline-sm font-bold">
                    {app.applicantName[0]}
                  </div>
                  <div>
                    <p className="text-headline-sm font-headline-md text-on-surface">{app.applicantName}</p>
                    <p className="text-body-md font-body-md text-on-surface-variant">{app.email}</p>
                    <p className="text-body-sm font-body-md text-on-surface-variant">{app.phone}</p>
                  </div>
                </div>
                <div className="border-t border-outline-variant pt-lg">
                  <p className="text-label-sm font-label-sm text-on-surface-variant mb-sm">전문 분야</p>
                  <div className="flex flex-wrap gap-sm">
                    {app.expertise.map((tag) => (
                      <span key={tag} className="bg-primary-fixed text-primary px-sm py-1 rounded-full text-label-sm font-label-sm">{tag}</span>
                    ))}
                  </div>
                </div>
              </div>

              {/* 경력 */}
              <div className="bg-surface-container-lowest border border-outline-variant rounded-xl p-xl">
                <h2 className="text-label-md font-label-md text-on-surface-variant uppercase tracking-wider mb-md flex items-center gap-xs">
                  <span className="material-symbols-outlined text-[18px] text-primary">work</span>
                  경력 사항
                </h2>
                <p className="text-body-md font-body-md text-on-surface">{app.career}</p>
              </div>

              {/* 지원 동기 */}
              <div className="bg-surface-container-lowest border border-outline-variant rounded-xl p-xl">
                <h2 className="text-label-md font-label-md text-on-surface-variant uppercase tracking-wider mb-md flex items-center gap-xs">
                  <span className="material-symbols-outlined text-[18px] text-primary">edit_note</span>
                  지원 동기
                </h2>
                <p className="text-body-md font-body-md text-on-surface-variant leading-relaxed">{app.motivation}</p>
              </div>

              {/* 강좌 계획 */}
              <div className="bg-surface-container-lowest border border-outline-variant rounded-xl p-xl">
                <h2 className="text-label-md font-label-md text-on-surface-variant uppercase tracking-wider mb-md flex items-center gap-xs">
                  <span className="material-symbols-outlined text-[18px] text-primary">book</span>
                  제안 강좌
                </h2>
                <p className="text-body-md font-body-md text-on-surface font-medium mb-sm">{app.proposedCourse}</p>
                {app.portfolioUrl && (
                  <a href={app.portfolioUrl} target="_blank" rel="noreferrer" className="text-primary text-label-md font-label-md flex items-center gap-xs hover:underline">
                    <span className="material-symbols-outlined text-[16px]">link</span>
                    포트폴리오 링크
                  </a>
                )}
              </div>

              {/* 관리자 메모 */}
              <div className="bg-surface-container-lowest border border-outline-variant rounded-xl p-xl">
                <h2 className="text-label-md font-label-md text-on-surface-variant uppercase tracking-wider mb-md flex items-center gap-xs">
                  <span className="material-symbols-outlined text-[18px] text-primary">sticky_note_2</span>
                  검토 메모
                </h2>
                <textarea
                  className="w-full bg-surface-container-low border border-outline-variant rounded-lg p-md text-body-md font-body-md text-on-surface resize-none focus:border-primary focus:outline-none"
                  rows={3}
                  placeholder="검토 의견을 입력하세요..."
                />
              </div>

            </div>

            {/* 사이드: 타임라인 + 액션 */}
            <div className="space-y-lg">

              {/* 액션 버튼 */}
              <div className="bg-surface-container-lowest border border-outline-variant rounded-xl p-lg space-y-sm">
                <h2 className="text-label-md font-label-md text-on-surface-variant uppercase tracking-wider mb-md">처리</h2>
                <button type="button" className="w-full bg-primary text-on-primary py-sm rounded-lg text-label-md font-label-md hover:opacity-90 transition-opacity flex items-center justify-center gap-xs">
                  <span className="material-symbols-outlined text-[18px]" style={{ fontVariationSettings: "'FILL' 1" }}>check_circle</span>
                  승인
                </button>
                <button type="button" className="w-full bg-error-container text-on-error-container py-sm rounded-lg text-label-md font-label-md hover:opacity-90 transition-opacity flex items-center justify-center gap-xs">
                  <span className="material-symbols-outlined text-[18px]" style={{ fontVariationSettings: "'FILL' 1" }}>cancel</span>
                  반려
                </button>
                <button type="button" className="w-full border border-outline-variant text-on-surface-variant py-sm rounded-lg text-label-md font-label-md hover:bg-surface-container transition-colors flex items-center justify-center gap-xs">
                  <span className="material-symbols-outlined text-[18px]">schedule</span>
                  보류
                </button>
              </div>

              {/* 진행 타임라인 */}
              <div className="bg-surface-container-lowest border border-outline-variant rounded-xl p-lg">
                <h2 className="text-label-md font-label-md text-on-surface-variant uppercase tracking-wider mb-lg">진행 현황</h2>
                <div className="space-y-0">
                  {TIMELINE.map((step, idx) => (
                    <div key={idx} className="flex gap-md">
                      <div className="flex flex-col items-center">
                        <div className={`w-5 h-5 rounded-full flex-shrink-0 flex items-center justify-center z-10 ${
                          step.status === 'done' ? 'bg-primary' :
                          step.status === 'current' ? 'bg-tertiary border-2 border-tertiary' :
                          'bg-surface-container-high border-2 border-outline-variant'
                        }`}>
                          {step.status === 'done' && (
                            <span className="material-symbols-outlined text-on-primary text-[12px]" style={{ fontVariationSettings: "'FILL' 1" }}>check</span>
                          )}
                          {step.status === 'current' && (
                            <span className="w-2 h-2 rounded-full bg-on-tertiary block" />
                          )}
                        </div>
                        {idx < TIMELINE.length - 1 && (
                          <div className={`w-0.5 h-8 mt-1 ${step.status === 'done' ? 'bg-primary' : 'bg-outline-variant'}`} />
                        )}
                      </div>
                      <div className="pb-lg">
                        <p className={`text-body-sm font-body-md ${step.status === 'pending' ? 'text-on-surface-variant' : 'text-on-surface'}`}>{step.event}</p>
                        {step.date && <p className="text-label-sm font-label-sm text-on-surface-variant">{step.date}</p>}
                      </div>
                    </div>
                  ))}
                </div>
              </div>

            </div>
          </div>

        </div>
      </main>
      <Footer />
    </>
  );
}
