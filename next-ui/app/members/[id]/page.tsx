'use client';

import Link from 'next/link';
import { TopNavBar } from '@/components/TopNavBar';
import { Footer } from '@/components/Footer';
import { MOCK_MEMBERS } from '@/app/members/mockData';
import { MOCK_ENROLLMENTS } from '@/app/enrollments/mockData';
import { getImageUrl } from '@/lib/imageUrl';

export default function MemberDetailPage() {
  const member = MOCK_MEMBERS[0];
  const enrollments = MOCK_ENROLLMENTS;

  return (
    <>
      <TopNavBar />
      <main className="pt-16 min-h-screen bg-background">
        <div className="max-w-[1000px] mx-auto px-gutter pt-xl pb-xxl">

          {/* 뒤로가기 */}
          <div className="mb-lg">
            <Link href="/members" className="flex items-center gap-xs text-on-surface-variant hover:text-primary transition-colors">
              <span className="material-symbols-outlined text-[20px]">arrow_back</span>
              <span className="text-label-md font-label-md">회원 목록</span>
            </Link>
          </div>

          <div className="grid grid-cols-1 md:grid-cols-3 gap-xl">

            {/* 왼쪽: 프로필 카드 */}
            <div className="space-y-lg">
              <div className="bg-surface-container-lowest border border-outline-variant rounded-xl p-xl">
                <div className="flex flex-col items-center text-center mb-lg">
                  <div className="w-20 h-20 rounded-full bg-primary text-on-primary flex items-center justify-center text-headline-md font-bold mb-md">
                    {member.name[0]}
                  </div>
                  <h1 className="text-headline-sm font-headline-md text-on-surface">{member.name}</h1>
                  <p className="text-body-sm font-body-md text-on-surface-variant">{member.email}</p>
                  <span className={`mt-sm px-sm py-1 rounded-full text-label-sm font-label-sm ${
                    member.status === 'ACTIVE'
                      ? 'bg-secondary-container text-on-secondary-container'
                      : 'bg-surface-container-high text-on-surface-variant'
                  }`}>
                    {member.status === 'ACTIVE' ? '활성' : '비활성'}
                  </span>
                </div>
                <div className="border-t border-outline-variant pt-lg space-y-sm">
                  <div className="flex justify-between items-center">
                    <span className="text-label-sm font-label-sm text-on-surface-variant">역할</span>
                    <span className="text-label-sm font-label-sm text-on-surface">일반 회원</span>
                  </div>
                  <div className="flex justify-between items-center">
                    <span className="text-label-sm font-label-sm text-on-surface-variant">가입일</span>
                    <span className="text-label-sm font-label-sm text-on-surface">{member.joinedAt}</span>
                  </div>
                  <div className="flex justify-between items-center">
                    <span className="text-label-sm font-label-sm text-on-surface-variant">수강 강좌</span>
                    <span className="text-label-sm font-label-sm text-on-surface">{member.courseCount}개</span>
                  </div>
                </div>
              </div>

              {/* 관리 액션 */}
              <div className="bg-surface-container-lowest border border-outline-variant rounded-xl p-lg space-y-sm">
                <h2 className="text-label-md font-label-md text-on-surface-variant uppercase tracking-wider mb-md">계정 관리</h2>
                <button type="button" className="w-full border border-outline-variant text-on-surface-variant py-sm rounded-lg text-label-sm font-label-sm hover:bg-surface-container transition-colors">
                  비밀번호 초기화 이메일 발송
                </button>
                <button type="button" className="w-full border border-error text-error py-sm rounded-lg text-label-sm font-label-sm hover:bg-error-container/30 transition-colors">
                  계정 비활성화
                </button>
              </div>
            </div>

            {/* 오른쪽: 수강 내역 */}
            <div className="md:col-span-2">
              <h2 className="text-headline-sm font-headline-md text-on-surface mb-lg">수강 내역</h2>
              <div className="space-y-md">
                {enrollments.map((e) => (
                  <div key={e.id} className="bg-surface-container-lowest border border-outline-variant rounded-xl p-lg flex items-center gap-lg">
                    <img src={getImageUrl(e.thumbnail)} alt={e.courseTitle} className="w-20 h-14 object-cover rounded-lg flex-shrink-0" />
                    <div className="flex-1 min-w-0">
                      <p className="text-label-md font-label-md text-on-surface mb-xs truncate">{e.courseTitle}</p>
                      <p className="text-label-sm font-label-sm text-on-surface-variant mb-md">{e.instructor} 강사</p>
                      <div className="flex items-center gap-md">
                        <div className="flex-1 h-1.5 bg-surface-container-high rounded-full overflow-hidden">
                          <div className="h-full bg-primary rounded-full" style={{ width: `${e.progress}%` }} />
                        </div>
                        <span className="text-label-sm font-label-sm text-primary whitespace-nowrap">{e.progress}%</span>
                      </div>
                    </div>
                    <span className={`flex-shrink-0 px-sm py-1 rounded-full text-label-sm font-label-sm ${
                      e.status === 'COMPLETED'
                        ? 'bg-secondary-container text-on-secondary-container'
                        : 'bg-primary-container text-on-primary-container'
                    }`}>
                      {e.status === 'COMPLETED' ? '완료' : '수강 중'}
                    </span>
                  </div>
                ))}
              </div>
            </div>

          </div>

        </div>
      </main>
      <Footer />
    </>
  );
}
