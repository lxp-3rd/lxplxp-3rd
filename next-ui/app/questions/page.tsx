'use client';

import { useState } from 'react';
import Link from 'next/link';
import { TopNavBar } from '@/components/TopNavBar';
import { Footer } from '@/components/Footer';
import { MOCK_QUESTIONS } from '@/app/questions/mockData';

export default function QuestionsPage() {
  const [selectedId, setSelectedId] = useState(MOCK_QUESTIONS[0]?.id ?? '');
  const selected = MOCK_QUESTIONS.find((q) => q.id === selectedId) ?? MOCK_QUESTIONS[0];

  return (
    <>
      <TopNavBar />
      <main className="pt-16 min-h-screen bg-background">
        <div className="pt-16 min-h-screen">
          <div className="max-w-[1200px] mx-auto px-gutter py-xl flex gap-lg">

            {/* 질문 목록 사이드바 */}
            <aside className="w-1/3 flex flex-col gap-md h-[calc(100vh-140px)] sticky top-[88px]">
              <div className="flex justify-between items-end mb-xs">
                <h2 className="text-headline-md text-on-surface">질문 목록</h2>
                <button type="button" className="bg-primary-container text-on-primary px-md py-sm rounded-lg text-label-md font-bold hover:opacity-90 transition-all flex items-center gap-xs">
                  <span className="material-symbols-outlined text-[18px]">edit</span>
                  질문하기
                </button>
              </div>
              <div className="overflow-y-auto pr-2 flex flex-col gap-sm">
                {MOCK_QUESTIONS.map((q) => (
                  <button
                    key={q.id}
                    type="button"
                    onClick={() => setSelectedId(q.id)}
                    className={`text-left p-md rounded-xl border cursor-pointer transition-all ${
                      q.id === selectedId
                        ? 'border-primary-container bg-surface-container-lowest shadow-md'
                        : 'border-surface-container-high hover:border-primary-container hover:shadow-sm opacity-80'
                    }`}
                  >
                    <div className="flex justify-between items-start mb-xs">
                      <span className={`text-label-sm px-2 py-0.5 rounded-full ${q.isAnswered ? 'bg-surface-container-high text-on-surface-variant' : 'bg-primary-fixed text-on-primary-container'}`}>
                        {q.isAnswered ? 'Completed' : 'Waiting'}
                      </span>
                      <span className="text-tertiary text-label-sm">{q.createdAt}</span>
                    </div>
                    <h3 className={`text-body-md text-on-surface line-clamp-2 mb-xs ${q.id === selectedId ? 'font-bold' : ''}`}>{q.title}</h3>
                    <div className="flex items-center gap-xs text-on-surface-variant text-label-sm">
                      <span className="material-symbols-outlined text-[16px]">person</span>
                      {q.authorName}
                    </div>
                  </button>
                ))}
              </div>
            </aside>

            {/* 선택된 질문 상세 */}
            {selected && (
              <section className="flex-1 bg-surface-container-lowest rounded-xl border border-surface-container-high flex flex-col overflow-hidden shadow-sm">
                <div className="p-xl border-b border-surface-container-high">
                  <div className="flex items-center gap-sm mb-md flex-wrap">
                    <span className={`text-label-sm px-md py-1 rounded-full ${selected.isAnswered ? 'bg-secondary-container text-on-secondary-container' : 'bg-primary-container text-on-primary'}`}>
                      {selected.isAnswered ? '답변 완료' : '답변 대기 중'}
                    </span>
                    {selected.tags.map((tag) => (
                      <span key={tag} className="bg-primary-fixed text-primary text-label-sm px-md py-1 rounded-full">{tag}</span>
                    ))}
                  </div>
                  <h1 className="text-headline-md text-on-surface mb-md">{selected.title}</h1>
                  <div className="flex items-center gap-md text-on-surface-variant text-body-sm">
                    <span className="flex items-center gap-xs">
                      <span className="material-symbols-outlined text-[16px]">person</span>
                      {selected.authorName}
                    </span>
                    <span>·</span>
                    <span>{selected.createdAt}</span>
                    <span>·</span>
                    <span>{selected.courseTitle}</span>
                  </div>
                </div>

                <div className="p-xl overflow-y-auto flex-1">
                  <div className="text-on-surface text-body-lg leading-relaxed mb-xl">
                    <p>{selected.content}</p>
                  </div>
                  <div className="border-t border-outline-variant pt-lg flex items-center justify-between">
                    <p className="text-label-md font-label-md text-on-surface-variant">
                      답변 {selected.answerCount}개
                    </p>
                    <Link
                      href={`/questions/${selected.id}`}
                      className="bg-primary text-on-primary px-lg py-sm rounded-lg text-label-md font-label-md hover:opacity-90 transition-opacity"
                    >
                      상세보기
                    </Link>
                  </div>
                </div>

                <div className="p-lg bg-surface-container border-t border-surface-container-high flex justify-center items-center">
                  <p className="text-on-surface-variant text-label-sm flex items-center gap-xs">
                    <span className="material-symbols-outlined text-tertiary text-[20px]">info</span>
                    답변은 강사와 수강생만 작성할 수 있습니다.
                  </p>
                </div>
              </section>
            )}

          </div>
        </div>
      </main>
      <Footer />
    </>
  );
}
