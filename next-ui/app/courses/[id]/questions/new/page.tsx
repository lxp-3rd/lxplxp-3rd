'use client';

import { useState } from 'react';
import Link from 'next/link';
import { useRouter } from 'next/navigation';
import { TopNavBar } from '@/components/TopNavBar';
import { Footer } from '@/components/Footer';
import { questionApi } from '../api';

export default function QuestionNewPage({ params }: { params: { id: string } }) {
  const router = useRouter();
  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');
  const [submitting, setSubmitting] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setSubmitting(true);
    setError(null);

    // TODO: 인증 구현 후 JWT에서 memberId 추출로 교체
    const memberId = 1;

    try {
      await questionApi.create({
        courseId: Number(params.id),
        memberId,
        title,
        content,
      });
      router.push(`/courses/${params.id}/questions`);
    } catch {
      setError('질문 등록 중 오류가 발생했습니다. 다시 시도해 주세요.');
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <>
      <TopNavBar />
      <main className="pt-16 min-h-screen bg-background">
        <div className="max-w-[760px] mx-auto px-gutter pt-xl pb-xxl">

          {/* 뒤로가기 */}
          <div className="mb-lg">
            <Link href={`/courses/${params.id}/questions`} className="flex items-center gap-xs text-on-surface-variant hover:text-primary transition-colors">
              <span className="material-symbols-outlined text-[20px]">arrow_back</span>
              <span className="text-label-md font-label-md">질문 목록으로</span>
            </Link>
          </div>

          <h1 className="text-headline-md font-headline-md text-on-surface mb-xl">질문하기</h1>

          <form onSubmit={handleSubmit} className="space-y-lg">

            {/* 제목 */}
            <div className="bg-surface-container-lowest border border-outline-variant rounded-xl p-lg">
              <label htmlFor="title" className="text-label-md font-label-md text-on-surface-variant block mb-sm">
                제목 <span className="text-error">*</span>
              </label>
              <input
                id="title"
                type="text"
                value={title}
                onChange={(e) => setTitle(e.target.value)}
                required
                minLength={2}
                maxLength={200}
                placeholder="질문 제목을 입력하세요 (2~200자)"
                className="w-full bg-surface-container-low border border-outline-variant rounded-lg px-md py-sm text-body-md font-body-md text-on-surface focus:border-primary focus:outline-none transition-all"
              />
            </div>

            {/* 내용 */}
            <div className="bg-surface-container-lowest border border-outline-variant rounded-xl p-lg">
              <label htmlFor="content" className="text-label-md font-label-md text-on-surface-variant block mb-sm">
                질문 내용 <span className="text-error">*</span>
              </label>
              <textarea
                id="content"
                value={content}
                onChange={(e) => setContent(e.target.value)}
                required
                maxLength={500}
                rows={8}
                placeholder="궁금한 내용을 자세히 설명해 주세요. (최대 500자)"
                className="w-full bg-surface-container-low border border-outline-variant rounded-lg px-md py-sm text-body-md font-body-md text-on-surface resize-none focus:border-primary focus:outline-none transition-all"
              />
              <p className="text-label-sm font-label-sm text-on-surface-variant text-right mt-xs">
                {content.length} / 500
              </p>
            </div>

            {error && (
              <p className="text-error text-label-md font-label-md">{error}</p>
            )}

            {/* 버튼 */}
            <div className="flex items-center justify-end gap-md">
              <Link
                href={`/courses/${params.id}/questions`}
                className="border border-outline-variant text-on-surface px-xl py-sm rounded-lg text-label-md font-label-md hover:bg-surface-container transition-colors"
              >
                취소
              </Link>
              <button
                type="submit"
                disabled={!title || !content || submitting}
                className="bg-primary text-on-primary px-xl py-sm rounded-lg text-label-md font-label-md hover:opacity-90 transition-opacity disabled:opacity-40 disabled:cursor-not-allowed"
              >
                {submitting ? '등록 중...' : '질문 등록'}
              </button>
            </div>

          </form>
        </div>
      </main>
      <Footer />
    </>
  );
}
