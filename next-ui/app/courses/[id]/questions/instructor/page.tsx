'use client';

import { useState, useEffect } from 'react';
import Link from 'next/link';
import { useParams } from 'next/navigation';
import { TopNavBar } from '@/components/TopNavBar';
import { Footer } from '@/components/Footer';
import { questionApi } from '../api';
import type { QuestionResponse } from '../types';
import { formatDate } from '@/lib/formatDate';

export default function InstructorQuestionsPage() {
  const { id } = useParams<{ id: string }>();

  const [questions, setQuestions] = useState<QuestionResponse[]>([]);
  const [loading, setLoading] = useState(true);
  const [selectedId, setSelectedId] = useState<number | null>(null);
  const [answerText, setAnswerText] = useState('');
  const [submitting, setSubmitting] = useState(false);
  const [error, setError] = useState<string | null>(null);

  // TODO: 인증 구현 후 JWT에서 instructorId 추출로 교체
  const instructorId = 1;

  useEffect(() => {
    setLoading(true);
    questionApi.getAll(Number(id))
      .then((data) => {
        setQuestions(data);
        if (data.length > 0) setSelectedId(data[0].id);
      })
      .catch(() => setError('질문 목록을 불러올 수 없습니다.'))
      .finally(() => setLoading(false));
  }, [id]);

  const selected = questions.find((q) => q.id === selectedId) ?? null;

  const handleSubmitAnswer = async () => {
    if (!selected || !answerText.trim() || submitting) return;
    setSubmitting(true);
    setError(null);
    try {
      const updated = await questionApi.answer(selected.id, { instructorId, content: answerText.trim() });
      setQuestions((prev) => prev.map((q) => (q.id === updated.id ? updated : q)));
      setAnswerText('');
    } catch {
      setError('답변 등록 중 오류가 발생했습니다.');
    } finally {
      setSubmitting(false);
    }
  };

  if (loading) {
    return (
      <>
        <TopNavBar />
        <main className="pt-16 min-h-screen flex items-center justify-center">
          <p className="text-on-surface-variant text-body-md font-body-md">불러오는 중...</p>
        </main>
        <Footer />
      </>
    );
  }

  return (
    <>
      <TopNavBar />
      <main className="pt-16 min-h-screen bg-background">
        <div className="max-w-[1200px] mx-auto px-gutter py-xl mt-16">

          {/* 뒤로가기 */}
          <div className="flex items-center gap-xs mb-lg">
            <Link
              href={`/courses/${id}/edit`}
              className="inline-flex items-center gap-xs text-label-md font-label-md text-primary hover:opacity-80 transition-opacity"
            >
              <span className="material-symbols-outlined text-[18px]">arrow_back</span>
              강좌 편집
            </Link>
            <span className="text-on-surface-variant text-label-md font-label-md">/ Q&amp;A 관리</span>
          </div>

          <div className="flex gap-lg h-[calc(100vh-200px)]">

            {/* 왼쪽: 질문 목록 */}
            <aside className="w-[320px] flex-shrink-0 flex flex-col gap-md">
              <div className="flex justify-between items-center mb-xs">
                <h2 className="text-headline-md font-headline-md text-on-surface">질문 목록</h2>
                <span className="text-label-sm font-label-sm text-on-surface-variant bg-surface-container px-sm py-xs rounded-full">
                  미답변 {questions.filter((q) => !q.answer).length}개
                </span>
              </div>

              {questions.length === 0 ? (
                <div className="flex flex-col items-center justify-center flex-1 text-center p-xl">
                  <span className="material-symbols-outlined text-[48px] text-on-surface-variant mb-md">forum</span>
                  <p className="text-body-md font-body-md text-on-surface-variant">등록된 질문이 없습니다.</p>
                </div>
              ) : (
                <div className="overflow-y-auto flex flex-col gap-sm pr-1">
                  {questions.map((q) => (
                    <button
                      key={q.id}
                      type="button"
                      onClick={() => { setSelectedId(q.id); setAnswerText(''); setError(null); }}
                      className={`text-left p-md rounded-xl border transition-all ${
                        q.id === selectedId
                          ? 'border-primary bg-surface-container-lowest shadow-md'
                          : 'border-outline-variant bg-surface-container-lowest hover:border-primary/50 hover:shadow-sm'
                      }`}
                    >
                      <div className="flex justify-between items-start mb-xs">
                        <span className={`text-label-sm font-label-sm px-sm py-0.5 rounded-full ${
                          q.answer
                            ? 'bg-surface-container-high text-on-surface-variant'
                            : 'bg-error-container text-on-error-container'
                        }`}>
                          {q.answer ? '답변 완료' : '미답변'}
                        </span>
                        <span className="text-label-sm font-label-sm text-on-surface-variant">{formatDate(q.createdAt)}</span>
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
                {/* 질문 헤더 */}
                <div className="p-xl border-b border-outline-variant flex-shrink-0">
                  <div className="flex items-center gap-sm mb-md flex-wrap">
                    <span className={`text-label-sm font-label-sm px-md py-1 rounded-full ${
                      selected.answer
                        ? 'bg-secondary-container text-on-secondary-container'
                        : 'bg-error-container text-on-error-container'
                    }`}>
                      {selected.answer ? '답변 완료' : '미답변'}
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

                {/* 질문 본문 + 기존 답변 */}
                <div className="flex-1 overflow-y-auto p-xl flex flex-col gap-xl">
                  <p className="text-body-lg font-body-lg text-on-surface leading-relaxed whitespace-pre-wrap">
                    {selected.content}
                  </p>

                  {selected.answer && (
                    <div className="flex flex-col gap-md border-t border-outline-variant pt-lg">
                      <h2 className="text-headline-sm font-headline-sm text-on-surface">내 답변</h2>
                      <div className="flex items-center gap-md">
                        <div className="w-10 h-10 rounded-full bg-primary text-on-primary flex items-center justify-center font-bold text-label-md flex-shrink-0">
                          강
                        </div>
                        <div className="flex items-center gap-sm">
                          <span className="text-label-md font-label-md text-on-surface font-bold">강사</span>
                          <span className="bg-primary-container text-on-primary-container text-label-sm font-label-sm px-sm py-0.5 rounded-full">강사</span>
                          <span className="text-label-sm font-label-sm text-on-surface-variant">
                            {selected.answeredAt && formatDate(selected.answeredAt)}
                          </span>
                        </div>
                      </div>
                      <p className="text-body-md font-body-md text-on-surface leading-relaxed whitespace-pre-wrap pl-[56px]">
                        {selected.answer}
                      </p>
                    </div>
                  )}
                </div>

                {/* 답변 입력 (미답변인 경우만) */}
                {!selected.answer && (
                  <div className="p-lg border-t border-outline-variant bg-surface-container flex-shrink-0 flex flex-col gap-sm">
                    <p className="text-label-md font-label-md text-on-surface">답변 작성</p>
                    {error && <p className="text-error text-label-md font-label-md">{error}</p>}
                    <textarea
                      value={answerText}
                      onChange={(e) => setAnswerText(e.target.value)}
                      rows={4}
                      maxLength={2000}
                      placeholder="수강생의 질문에 답변해 주세요."
                      className="w-full bg-surface-container-lowest border border-outline-variant rounded-lg px-md py-sm text-body-md font-body-md text-on-surface resize-none focus:border-primary focus:outline-none transition-all"
                    />
                    <div className="flex justify-end">
                      <button
                        type="button"
                        onClick={handleSubmitAnswer}
                        disabled={!answerText.trim() || submitting}
                        className="bg-primary text-on-primary px-xl py-sm rounded-lg text-label-md font-label-md hover:opacity-90 transition-opacity disabled:opacity-40 disabled:cursor-not-allowed"
                      >
                        {submitting ? '등록 중...' : '답변 등록'}
                      </button>
                    </div>
                  </div>
                )}
              </section>
            ) : (
              <section className="flex-1 bg-surface-container-lowest rounded-xl border border-outline-variant flex items-center justify-center shadow-sm">
                <p className="text-on-surface-variant text-body-md font-body-md">질문을 선택하세요.</p>
              </section>
            )}

          </div>
        </div>
      </main>
      <Footer />
    </>
  );
}
