'use client';

import { useState, useEffect } from 'react';
import Link from 'next/link';
import { useParams } from 'next/navigation';
import { TopNavBar } from '@/components/TopNavBar';
import { Footer } from '@/components/Footer';
import { questionApi } from './api';
import type { QuestionResponse } from './types';
import { formatDate } from '@/lib/formatDate';

export default function CourseQuestionsPage() {
  const { id } = useParams<{ id: string }>();
  const [questions, setQuestions] = useState<QuestionResponse[]>([]);
  const [selectedId, setSelectedId] = useState<number | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    questionApi.getAll(Number(id))
      .then((data) => {
        setQuestions(data);
        if (data.length > 0) setSelectedId(data[0].id);
      })
      .catch(() => setError('질문 목록을 불러올 수 없습니다.'))
      .finally(() => setLoading(false));
  }, [id]);

  const selected = questions.find((q) => q.id === selectedId);

  return (
    <>
      <TopNavBar />
      <main className="pt-16 min-h-screen bg-background">
        <div className="max-w-[1200px] mx-auto px-gutter py-xl mt-16">

          {/* 뒤로가기 + 강좌명 */}
          <div className="flex items-center gap-xs mb-lg">
            <Link
              href={`/courses/${id}`}
              className="inline-flex items-center gap-xs text-label-md font-label-md text-primary hover:opacity-80 transition-opacity"
            >
              <span className="material-symbols-outlined text-[18px]">arrow_back</span>
              강좌로 돌아가기
            </Link>
            <span className="text-on-surface-variant text-label-md font-label-md">/ Q&amp;A</span>
          </div>

          <div className="flex gap-lg h-[calc(100vh-200px)]">

            {/* 왼쪽: 질문 목록 */}
            <aside className="w-[320px] flex-shrink-0 flex flex-col gap-md">
              <div className="flex justify-between items-center mb-xs">
                <h2 className="text-headline-md font-headline-md text-on-surface">질문 목록</h2>
                <Link
                  href={`/courses/${id}/questions/new`}
                  className="bg-primary text-on-primary px-md py-sm rounded-lg text-label-md font-label-md hover:opacity-90 transition-all flex items-center gap-xs"
                >
                  <span className="material-symbols-outlined text-[18px]">edit</span>
                  질문하기
                </Link>
              </div>

              {loading ? (
                <div className="flex-1 flex items-center justify-center">
                  <span className="text-on-surface-variant text-body-md font-body-md">불러오는 중...</span>
                </div>
              ) : error ? (
                <div className="flex-1 flex items-center justify-center">
                  <span className="text-error text-body-md font-body-md">{error}</span>
                </div>
              ) : questions.length === 0 ? (
                <div className="flex flex-col items-center justify-center flex-1 text-center p-xl">
                  <span className="material-symbols-outlined text-[48px] text-on-surface-variant mb-md">forum</span>
                  <p className="text-body-md font-body-md text-on-surface-variant">아직 등록된 질문이 없습니다.</p>
                  <p className="text-label-sm font-label-sm text-on-surface-variant mt-xs">궁금한 점이 있으면 질문해 보세요!</p>
                </div>
              ) : (
                <div className="overflow-y-auto flex flex-col gap-sm pr-1">
                  {questions.map((q) => (
                    <button
                      key={q.id}
                      type="button"
                      onClick={() => setSelectedId(q.id)}
                      className={`text-left p-md rounded-xl border transition-all ${
                        q.id === selectedId
                          ? 'border-primary bg-surface-container-lowest shadow-md'
                          : 'border-outline-variant bg-surface-container-lowest hover:border-primary/50 hover:shadow-sm'
                      }`}
                    >
                      <div className="flex justify-between items-start mb-xs">
                        <span className="text-label-sm font-label-sm px-sm py-0.5 rounded-full bg-primary-fixed text-on-primary-container">
                          답변 대기
                        </span>
                        <span className="text-label-sm font-label-sm text-on-surface-variant">
                          {formatDate(q.createdAt)}
                        </span>
                      </div>
                      <h3 className={`text-body-md text-on-surface line-clamp-2 mb-xs ${q.id === selectedId ? 'font-bold' : ''}`}>
                        {q.title}
                      </h3>
                      <div className="flex items-center gap-xs text-on-surface-variant text-label-sm font-label-sm">
                        <span className="material-symbols-outlined text-[16px]">person</span>
                        회원 #{q.memberId}
                      </div>
                    </button>
                  ))}
                </div>
              )}
            </aside>

            {/* 오른쪽: 선택된 질문 상세 */}
            {selected ? (
              <section className="flex-1 bg-surface-container-lowest rounded-xl border border-outline-variant flex flex-col overflow-hidden shadow-sm">
                <div className="p-xl border-b border-outline-variant flex-shrink-0">
                  <div className="flex items-center gap-sm mb-md">
                    <span className="text-label-sm font-label-sm px-md py-1 rounded-full bg-primary-container text-on-primary-container">
                      답변 대기 중
                    </span>
                  </div>
                  <h1 className="text-headline-md font-headline-md text-on-surface mb-md">{selected.title}</h1>
                  <div className="flex items-center gap-md text-on-surface-variant text-body-sm font-body-sm">
                    <span className="flex items-center gap-xs">
                      <span className="material-symbols-outlined text-[16px]">person</span>
                      회원 #{selected.memberId}
                    </span>
                    <span>·</span>
                    <span>{formatDate(selected.createdAt)}</span>
                  </div>
                </div>

                <div className="flex-1 overflow-y-auto p-xl flex flex-col gap-xl">
                  <p className="text-body-lg font-body-lg text-on-surface leading-relaxed whitespace-pre-wrap">
                    {selected.content}
                  </p>
                </div>

                <div className="p-lg bg-surface-container border-t border-outline-variant flex justify-between items-center flex-shrink-0">
                  <p className="text-on-surface-variant text-label-sm font-label-sm flex items-center gap-xs">
                    <span className="material-symbols-outlined text-tertiary text-[20px]">info</span>
                    답변은 강사만 작성할 수 있습니다.
                  </p>
                  <Link
                    href={`/courses/${id}/questions/${selected.id}`}
                    className="text-primary text-label-md font-label-md hover:underline flex items-center gap-xs"
                  >
                    상세 보기
                    <span className="material-symbols-outlined text-[16px]">chevron_right</span>
                  </Link>
                </div>
              </section>
            ) : (
              !loading && (
                <section className="flex-1 bg-surface-container-lowest rounded-xl border border-outline-variant flex items-center justify-center shadow-sm">
                  <p className="text-on-surface-variant text-body-md font-body-md">질문을 선택하세요.</p>
                </section>
              )
            )}

          </div>
        </div>
      </main>
      <Footer />
    </>
  );
}
