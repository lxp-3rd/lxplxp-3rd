'use client';

import { useState } from 'react';
import Link from 'next/link';
import { useParams } from 'next/navigation';
import { TopNavBar } from '@/components/TopNavBar';
import { Footer } from '@/components/Footer';
import { MOCK_QUESTIONS } from '@/app/questions/mockData';
import { getCourseById, MOCK_COURSES } from '@/app/courses/mockData';

export default function CourseQuestionsPage() {
  const { id } = useParams<{ id: string }>();
  const course = getCourseById(id) ?? MOCK_COURSES[0];
  const questions = MOCK_QUESTIONS.filter((q) => q.courseId === id);

  const [selectedId, setSelectedId] = useState(questions[0]?.id ?? '');
  const selected = questions.find((q) => q.id === selectedId) ?? questions[0];

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
              {course.title}
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

              {questions.length === 0 ? (
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
                        <span className={`text-label-sm font-label-sm px-sm py-0.5 rounded-full ${
                          q.isAnswered
                            ? 'bg-surface-container-high text-on-surface-variant'
                            : 'bg-primary-fixed text-on-primary-container'
                        }`}>
                          {q.isAnswered ? 'Completed' : 'Waiting'}
                        </span>
                        <span className="text-label-sm font-label-sm text-on-surface-variant">{q.createdAt}</span>
                      </div>
                      <h3 className={`text-body-md text-on-surface line-clamp-2 mb-xs ${q.id === selectedId ? 'font-bold' : ''}`}>
                        {q.title}
                      </h3>
                      <div className="flex items-center gap-xs text-on-surface-variant text-label-sm font-label-sm">
                        <span className="material-symbols-outlined text-[16px]">person</span>
                        {q.authorName}
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
                      selected.isAnswered
                        ? 'bg-secondary-container text-on-secondary-container'
                        : 'bg-primary-container text-on-primary-container'
                    }`}>
                      {selected.isAnswered ? '답변 완료' : '답변 대기 중'}
                    </span>
                    {selected.tags.map((tag) => (
                      <span key={tag} className="bg-primary-fixed text-primary text-label-sm font-label-sm px-md py-1 rounded-full">
                        {tag}
                      </span>
                    ))}
                  </div>
                  <h1 className="text-headline-md font-headline-md text-on-surface mb-md">{selected.title}</h1>
                  <div className="flex items-center gap-md text-on-surface-variant text-body-sm font-body-sm">
                    <span className="flex items-center gap-xs">
                      <span className="material-symbols-outlined text-[16px]">person</span>
                      {selected.authorName}
                    </span>
                    <span>·</span>
                    <span>{selected.createdAt}</span>
                  </div>
                </div>

                {/* 질문 본문 + 답변 */}
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
                          {selected.answer.avatarSrc ? (
                            <img
                              src={selected.answer.avatarSrc}
                              alt={selected.answer.authorName}
                              className="w-10 h-10 rounded-full object-cover border border-outline-variant flex-shrink-0"
                            />
                          ) : (
                            <div className="w-10 h-10 rounded-full bg-primary text-on-primary flex items-center justify-center font-bold text-label-md flex-shrink-0">
                              {selected.answer.authorName[0]}
                            </div>
                          )}
                          <div className="flex items-center gap-sm">
                            <span className="text-label-md font-label-md text-on-surface font-bold">{selected.answer.authorName}</span>
                            <span className="bg-primary-container text-on-primary-container text-label-sm font-label-sm px-sm py-0.5 rounded-full">
                              강사
                            </span>
                            <span className="text-label-sm font-label-sm text-on-surface-variant">{selected.answer.createdAt}</span>
                          </div>
                        </div>
                        <p className="text-body-md font-body-md text-on-surface leading-relaxed whitespace-pre-wrap pl-[56px]">
                          {selected.answer.content}
                        </p>
                      </div>
                    </div>
                  )}
                </div>

                {/* 하단 안내 */}
                <div className="p-lg bg-surface-container border-t border-outline-variant flex justify-center items-center flex-shrink-0">
                  <p className="text-on-surface-variant text-label-sm font-label-sm flex items-center gap-xs">
                    <span className="material-symbols-outlined text-tertiary text-[20px]">info</span>
                    답변은 강사만 작성할 수 있습니다.
                  </p>
                </div>
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