'use client';

import Link from 'next/link';
import { TopNavBar } from '@/components/TopNavBar';
import { Footer } from '@/components/Footer';
import { MOCK_ENROLLMENTS } from '@/app/enrollments/mockData';

const MOCK_CONTENTS = [
  { id: 'c1', week: 1, title: '오리엔테이션 및 UX 디자인 개요', duration: '32분', type: 'VIDEO', done: true },
  { id: 'c2', week: 1, title: 'UI/UX 트렌드 분석 (2024)', duration: '45분', type: 'VIDEO', done: true },
  { id: 'c3', week: 2, title: '사용자 조사 방법론', duration: '38분', type: 'VIDEO', done: true },
  { id: 'c4', week: 2, title: '퍼소나 & 사용자 여정 지도', duration: '41분', type: 'VIDEO', done: false },
  { id: 'c5', week: 3, title: '와이어프레임 실습', duration: '55분', type: 'VIDEO', done: false },
  { id: 'c6', week: 3, title: '실습 과제: 와이어프레임 제출', duration: '', type: 'DOCUMENT', done: false },
  { id: 'c7', week: 4, title: '프로토타이핑 도구 Figma 심화', duration: '60분', type: 'VIDEO', done: false },
];

export default function EnrollmentDetailPage() {
  const enrollment = MOCK_ENROLLMENTS[0];
  const doneCount = MOCK_CONTENTS.filter((c) => c.done).length;
  const currentIdx = MOCK_CONTENTS.findIndex((c) => !c.done);

  const weeks = [...new Set(MOCK_CONTENTS.map((c) => c.week))];

  return (
    <>
      <TopNavBar />
      <main className="pt-16 min-h-screen bg-background">
        <div className="max-w-[1100px] mx-auto px-gutter pt-xl pb-xxl">

          {/* 뒤로가기 */}
          <div className="mb-lg">
            <Link href="/enrollments" className="flex items-center gap-xs text-on-surface-variant hover:text-primary transition-colors">
              <span className="material-symbols-outlined text-[20px]">arrow_back</span>
              <span className="text-label-md font-label-md">내 강의로</span>
            </Link>
          </div>

          {/* 강좌 헤더 */}
          <div className="bg-surface-container-lowest border border-outline-variant rounded-xl p-xl mb-xl">
            <div className="flex flex-col md:flex-row gap-lg items-start">
              <img
                src={enrollment.thumbnail}
                alt={enrollment.courseTitle}
                className="w-full md:w-48 h-32 object-cover rounded-lg flex-shrink-0"
              />
              <div className="flex-1">
                <p className="text-label-sm font-label-sm text-primary mb-xs">{enrollment.instructor} 강사</p>
                <h1 className="text-headline-md font-headline-md text-on-surface mb-md">{enrollment.courseTitle}</h1>
                <div className="flex items-center gap-md mb-md">
                  <span className={`px-sm py-1 rounded-full text-label-sm font-label-sm ${
                    enrollment.status === 'COMPLETED'
                      ? 'bg-secondary-container text-on-secondary-container'
                      : 'bg-primary-container text-on-primary-container'
                  }`}>
                    {enrollment.status === 'COMPLETED' ? '수강 완료' : '수강 중'}
                  </span>
                  <span className="text-label-sm font-label-sm text-on-surface-variant">마지막 접속: {enrollment.lastAccessedAt}</span>
                </div>
                {/* 진행률 바 */}
                <div className="flex items-center gap-md">
                  <div className="flex-1 h-2 bg-surface-container-high rounded-full overflow-hidden">
                    <div
                      className="h-full bg-primary rounded-full transition-all"
                      style={{ width: `${enrollment.progress}%` }}
                    />
                  </div>
                  <span className="text-label-md font-label-md text-primary whitespace-nowrap">{enrollment.progress}%</span>
                </div>
                <p className="text-label-sm font-label-sm text-on-surface-variant mt-xs">{doneCount} / {MOCK_CONTENTS.length}개 완료</p>
              </div>
              {currentIdx >= 0 && (
                <div className="flex-shrink-0">
                  <Link
                    href={`/courses/${enrollment.courseId}/contents/${MOCK_CONTENTS[currentIdx].id}`}
                    className="bg-primary text-on-primary px-xl py-sm rounded-lg text-label-md font-label-md hover:opacity-90 transition-opacity flex items-center gap-xs whitespace-nowrap"
                  >
                    <span className="material-symbols-outlined text-[18px]">play_circle</span>
                    이어서 학습
                  </Link>
                </div>
              )}
            </div>
          </div>

          {/* 커리큘럼 */}
          <div>
            <h2 className="text-headline-sm font-headline-md text-on-surface mb-md">커리큘럼</h2>
            <div className="space-y-md">
              {weeks.map((week) => {
                const items = MOCK_CONTENTS.filter((c) => c.week === week);
                const weekDone = items.filter((c) => c.done).length;
                return (
                  <div key={week} className="bg-surface-container-lowest border border-outline-variant rounded-xl overflow-hidden">
                    <div className="bg-surface-container-low px-lg py-md flex items-center justify-between">
                      <h3 className="text-label-md font-label-md text-on-surface">{week}주차</h3>
                      <span className="text-label-sm font-label-sm text-on-surface-variant">{weekDone}/{items.length} 완료</span>
                    </div>
                    <div className="divide-y divide-outline-variant">
                      {items.map((item) => (
                        <div key={item.id} className="px-lg py-md flex items-center gap-md group">
                          <div className={`w-6 h-6 rounded-full flex items-center justify-center flex-shrink-0 ${item.done ? 'bg-primary' : 'bg-surface-container-high'}`}>
                            {item.done
                              ? <span className="material-symbols-outlined text-on-primary text-[14px]" style={{ fontVariationSettings: "'FILL' 1" }}>check</span>
                              : <span className="material-symbols-outlined text-on-surface-variant text-[14px]">{item.type === 'VIDEO' ? 'play_arrow' : 'description'}</span>
                            }
                          </div>
                          <div className="flex-1 min-w-0">
                            <p className={`text-body-md font-body-md truncate ${item.done ? 'text-on-surface-variant line-through' : 'text-on-surface'}`}>
                              {item.title}
                            </p>
                          </div>
                          <div className="flex items-center gap-sm flex-shrink-0">
                            {item.duration && (
                              <span className="text-label-sm font-label-sm text-on-surface-variant">{item.duration}</span>
                            )}
                            <Link
                              href={`/courses/${enrollment.courseId}/contents/${item.id}`}
                              className="text-label-sm font-label-sm text-primary opacity-0 group-hover:opacity-100 transition-opacity"
                            >
                              {item.done ? '다시 보기' : '학습하기'}
                            </Link>
                          </div>
                        </div>
                      ))}
                    </div>
                  </div>
                );
              })}
            </div>
          </div>

        </div>
      </main>
      <Footer />
    </>
  );
}
