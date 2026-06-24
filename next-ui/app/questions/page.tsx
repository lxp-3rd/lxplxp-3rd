'use client';

import { useEffect, useState } from 'react';
import { TopNavBar } from '@/components/TopNavBar';
import { Footer } from '@/components/Footer';
import { Button } from '@/components/ui';
import { QuestionCard } from './components/QuestionCard';
import { questionApi } from './api';
import type { Question } from './types';

export default function QuestionsPage() {
  const [questions, setQuestions] = useState<Question[]>([]);
  const [selectedId, setSelectedId] = useState<number | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const selected = questions.find((q) => q.id === selectedId) ?? questions[0];

  useEffect(() => {
    const controller = new AbortController();
    setLoading(true);
    setError(null);
    setQuestions([]);
    setSelectedId(null);

    questionApi.getAll()
      .then((data) => {
        if (controller.signal.aborted) return;
        setQuestions(data);
        setSelectedId(data[0]?.id ?? null);
      })
      .catch(() => {
        if (!controller.signal.aborted) setError('질문 목록을 불러올 수 없습니다.');
      })
      .finally(() => {
        if (!controller.signal.aborted) setLoading(false);
      });

    return () => controller.abort();
  }, []);

  return (
    <>
      <TopNavBar />
      <main className="pt-16 min-h-screen bg-background">
        <div className="max-w-[1200px] mx-auto px-gutter py-xl mt-16">
          <div className="flex gap-lg h-[calc(100vh-140px)]">

            {/* 왼쪽: 질문 목록 */}
            <aside className="w-[320px] flex-shrink-0 flex flex-col gap-md">
              <div className="flex justify-between items-center mb-xs">
                <h2 className="text-headline-md font-headline-md text-on-surface">질문 목록</h2>
                <Button size="sm">
                  <span className="material-symbols-outlined text-[18px]">edit</span>
                  질문하기
                </Button>
              </div>
              {loading ? (
                <div className="flex-1 flex items-center justify-center">
                  <span className="text-on-surface-variant text-body-md font-body-md">불러오는 중...</span>
                </div>
              ) : error ? (
                <div className="flex-1 flex items-center justify-center text-center p-lg">
                  <span className="text-error text-body-md font-body-md">{error}</span>
                </div>
              ) : questions.length === 0 ? (
                <div className="flex flex-col items-center justify-center flex-1 text-center p-xl">
                  <span className="material-symbols-outlined text-[48px] text-on-surface-variant mb-md">forum</span>
                  <p className="text-body-md font-body-md text-on-surface-variant">등록된 질문이 없습니다.</p>
                </div>
              ) : (
                <div className="overflow-y-auto flex flex-col gap-sm pr-1">
                  {questions.map((q) => (
                    <QuestionCard
                      key={q.id}
                      data={q}
                      isSelected={q.id === selectedId}
                      onClick={() => setSelectedId(q.id)}
                    />
                  ))}
                </div>
              )}
            </aside>

            {/* 오른쪽: 선택된 질문 상세 */}
            {selected && (
              <section className="flex-1 bg-surface-container-lowest rounded-xl border border-outline-variant flex flex-col overflow-hidden shadow-sm">
                <div className="p-xl border-b border-outline-variant flex-shrink-0">
                  <div className="flex items-center gap-sm mb-md flex-wrap">
                    <span className={[
                      'text-label-sm font-label-sm px-md py-1 rounded-full',
                      selected.answer
                        ? 'bg-secondary-container text-on-secondary-container'
                        : 'bg-primary-container text-on-primary-container',
                    ].join(' ')}>
                      {selected.answer ? '답변 완료' : '답변 대기 중'}
                    </span>
                  </div>
                  <h1 className="text-headline-md font-headline-md text-on-surface mb-md">{selected.title}</h1>
                  <div className="flex items-center gap-md text-on-surface-variant text-body-sm font-body-sm">
                    <span className="flex items-center gap-xs">
                      <span className="material-symbols-outlined text-[16px]">person</span>
                      회원 #{selected.memberId}
                    </span>
                    <span>·</span>
                    <span>{selected.createdAt}</span>
                    <span>·</span>
                    <span>강좌 #{selected.courseId}</span>
                  </div>
                </div>

                <div className="flex-1 overflow-y-auto p-xl flex flex-col gap-xl">
                  <p className="text-body-lg font-body-lg text-on-surface leading-relaxed whitespace-pre-wrap">
                    {selected.content}
                  </p>

                  {selected.answer && (
                    <div className="flex flex-col gap-lg">
                      <h2 className="text-headline-sm font-headline-sm text-on-surface border-t border-outline-variant pt-lg">
                        답변
                      </h2>
                      <div className="flex flex-col gap-md">
                        <div className="flex items-center gap-md">
                          <div className="w-10 h-10 rounded-full bg-primary text-on-primary flex items-center justify-center font-bold text-label-md flex-shrink-0">
                            강
                          </div>
                          <div className="flex items-center gap-sm">
                            <span className="text-label-md font-label-md text-on-surface font-bold">강사 #{selected.answeredBy}</span>
                            <span className="bg-primary-container text-on-primary-container text-label-sm font-label-sm px-sm py-0.5 rounded-full">강사</span>
                            {selected.answeredAt && (
                              <span className="text-label-sm font-label-sm text-on-surface-variant">{selected.answeredAt}</span>
                            )}
                          </div>
                        </div>
                        <p className="text-body-md font-body-md text-on-surface leading-relaxed whitespace-pre-wrap pl-[56px]">
                          {selected.answer}
                        </p>
                      </div>
                    </div>
                  )}
                </div>

                <div className="p-lg bg-surface-container border-t border-outline-variant flex justify-center items-center flex-shrink-0">
                  <p className="text-on-surface-variant text-label-sm font-label-sm flex items-center gap-xs">
                    <span className="material-symbols-outlined text-tertiary text-[20px]">info</span>
                    답변은 강사만 작성할 수 있습니다.
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
