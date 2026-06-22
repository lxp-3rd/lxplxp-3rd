'use client';

import { useState } from 'react';
import Link from 'next/link';
import { useRouter } from 'next/navigation';

import { TopNavBar } from '@/components/TopNavBar';
import { Footer } from '@/components/Footer';
import { getCourseById, MOCK_COURSES } from '@/app/courses/mockData';

export default function QuestionNewPage({ params }: { params: { id: string } }) {
  const course = getCourseById(params.id) ?? MOCK_COURSES[0];
  const router = useRouter();

  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');


  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    // BE 연동 후 실제 저장
    router.push(`/courses/${params.id}`);
  };

  return (
    <>
      <TopNavBar />
      <main className="pt-16 min-h-screen bg-background">
        <div className="max-w-[760px] mx-auto px-gutter pt-xl pb-xxl">

          {/* 뒤로가기 */}
          <div className="mb-lg">
            <Link href={`/courses/${params.id}`} className="flex items-center gap-xs text-on-surface-variant hover:text-primary transition-colors">
              <span className="material-symbols-outlined text-[20px]">arrow_back</span>
              <span className="text-label-md font-label-md">강좌로 돌아가기</span>
            </Link>
          </div>

          {/* 강좌 정보 */}
          <div className="bg-primary-fixed/30 border border-primary/20 rounded-xl px-lg py-md mb-xl flex items-center gap-md">
            <span className="material-symbols-outlined text-primary text-[20px]">book</span>
            <p className="text-label-md font-label-md text-on-surface">{course.title}</p>
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
                placeholder="질문 제목을 입력하세요"
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
                rows={8}
                placeholder="궁금한 내용을 자세히 설명해 주세요. 어떤 부분에서 막혔는지, 어떤 것을 시도해 봤는지 함께 작성하면 빠른 답변을 받을 수 있어요."
                className="w-full bg-surface-container-low border border-outline-variant rounded-lg px-md py-sm text-body-md font-body-md text-on-surface resize-none focus:border-primary focus:outline-none transition-all"
              />
            </div>

            {/* 버튼 */}
            <div className="flex items-center justify-end gap-md">
              <Link
                href={`/courses/${params.id}`}
                className="border border-outline-variant text-on-surface px-xl py-sm rounded-lg text-label-md font-label-md hover:bg-surface-container transition-colors"
              >
                취소
              </Link>
              <button
                type="submit"
                disabled={!title || !content}
                className="bg-primary text-on-primary px-xl py-sm rounded-lg text-label-md font-label-md hover:opacity-90 transition-opacity disabled:opacity-40 disabled:cursor-not-allowed"
              >
                질문 등록
              </button>
            </div>

          </form>
        </div>
      </main>
      <Footer />
    </>
  );
}
