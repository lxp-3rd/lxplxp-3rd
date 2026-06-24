'use client';

import { useEffect, useState } from 'react';
import Link from 'next/link';
import { TopNavBar } from '@/components/TopNavBar';
import { Footer } from '@/components/Footer';
import { questionApi } from '@/app/questions/api';
import type { Question } from '@/app/questions/types';

export default function QuestionDetailPage({ params }: { params: { id: string } }) {
  const [question, setQuestion] = useState<Question | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const controller = new AbortController();
    setLoading(true);
    setQuestion(null);
    setError(null);

    questionApi.getById(Number(params.id))
      .then((data) => {
        if (!controller.signal.aborted) setQuestion(data);
      })
      .catch(() => {
        if (!controller.signal.aborted) setError('질문을 찾을 수 없습니다.');
      })
      .finally(() => {
        if (!controller.signal.aborted) setLoading(false);
      });

    return () => controller.abort();
  }, [params.id]);

  if (loading) {
    return (
      <>
        <TopNavBar />
        <main className="pt-16 min-h-screen flex items-center justify-center">
          <p className="text-on-surface-variant">불러오는 중...</p>
        </main>
        <Footer />
      </>
    );
  }

  if (error || !question) {
    return (
      <>
        <TopNavBar />
        <main className="pt-16 min-h-screen flex items-center justify-center">
          <p className="text-on-surface-variant">{error ?? '질문을 찾을 수 없습니다.'}</p>
        </main>
        <Footer />
      </>
    );
  }

  return (
    <>
      <TopNavBar />
      <main className="pt-16 min-h-screen bg-background">
        <div className="max-w-[1000px] mx-auto px-gutter pt-xl pb-xxl">

          {/* 뒤로가기 */}
          <div className="mb-lg">
            <Link href="/questions" className="flex items-center gap-xs text-on-surface-variant hover:text-primary transition-colors">
              <span className="material-symbols-outlined text-[20px]">arrow_back</span>
              <span className="text-label-md font-label-md">질문 목록으로</span>
            </Link>
          </div>

          {/* 질문 카드 */}
          <div className="bg-surface-container-lowest border border-outline-variant rounded-xl p-xl mb-xl">
            <div className="flex items-center gap-sm mb-md flex-wrap">
              <span className={`px-sm py-1 rounded-full text-label-sm font-label-sm ml-auto ${question.answer ? 'bg-secondary-container text-on-secondary-container' : 'bg-error-container text-on-error-container'}`}>
                {question.answer ? '답변 완료' : '답변 대기'}
              </span>
            </div>
            <h1 className="text-headline-md font-headline-md text-on-surface mb-lg">{question.title}</h1>
            <p className="text-body-lg font-body-md text-on-surface-variant leading-relaxed mb-xl whitespace-pre-wrap">{question.content}</p>
            <div className="flex items-center gap-md text-label-md font-label-md text-on-surface-variant border-t border-outline-variant pt-md">
              <span className="flex items-center gap-xs">
                <span className="material-symbols-outlined text-[16px]">person</span>
                회원 #{question.memberId}
              </span>
              <span>·</span>
              <span className="flex items-center gap-xs">
                <span className="material-symbols-outlined text-[16px]">schedule</span>
                {question.createdAt}
              </span>
              <span>·</span>
              <span className="flex items-center gap-xs">
                <span className="material-symbols-outlined text-[16px]">book</span>
                강좌 #{question.courseId}
              </span>
            </div>
          </div>

          {/* 답변 */}
          <div className="mb-xl">
            <h2 className="text-headline-sm font-headline-md text-on-surface mb-md flex items-center gap-sm">
              <span className="material-symbols-outlined text-primary">forum</span>
              답변 {question.answer ? 1 : 0}개
            </h2>
            {question.answer ? (
              <div className="bg-surface-container-lowest border border-primary rounded-xl p-lg bg-primary-fixed/10">
                <div className="flex items-center gap-xs mb-sm text-primary">
                  <span className="material-symbols-outlined text-[18px]" style={{ fontVariationSettings: "'FILL' 1" }}>check_circle</span>
                  <span className="text-label-sm font-label-sm">강사 답변</span>
                </div>
                <div className="flex items-center gap-sm mb-md">
                  <div className="w-8 h-8 rounded-full bg-primary text-on-primary flex items-center justify-center text-label-sm font-bold">
                    강
                  </div>
                  <div>
                    <p className="text-label-md font-label-md text-on-surface">강사 #{question.answeredBy}</p>
                    {question.answeredAt && (
                      <p className="text-label-sm font-label-sm text-on-surface-variant">{question.answeredAt}</p>
                    )}
                  </div>
                </div>
                <p className="text-body-md font-body-md text-on-surface-variant leading-relaxed whitespace-pre-wrap">{question.answer}</p>
              </div>
            ) : (
              <div className="bg-surface-container-lowest border border-outline-variant rounded-xl p-lg text-on-surface-variant">
                아직 등록된 답변이 없습니다.
              </div>
            )}
          </div>

        </div>
      </main>
      <Footer />
    </>
  );
}
