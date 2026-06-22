'use client';

import { useState } from 'react';
import Link from 'next/link';
import { useParams } from 'next/navigation';
import { TopNavBar } from '@/components/TopNavBar';
import { Footer } from '@/components/Footer';
import { getRoadmapById } from '@/app/roadmaps/mockData';
import { MOCK_COURSES } from '@/app/courses/mockData';

type Course = typeof MOCK_COURSES[number];

export default function RoadmapEditPage() {
  const { id } = useParams<{ id: string }>();
  const roadmap = getRoadmapById(id);

  const initialIds = MOCK_COURSES
    .filter((c) => c.category === roadmap?.category)
    .map((c) => c.id);

  const [search, setSearch] = useState('');
  const [selectedIds, setSelectedIds] = useState<string[]>(initialIds);

  const filtered = MOCK_COURSES.filter((c) =>
    !search ||
    c.title.includes(search) ||
    c.instructor.includes(search),
  );

  const selectedCourses = selectedIds
    .map((sid) => MOCK_COURSES.find((c) => c.id === sid))
    .filter(Boolean) as Course[];

  const toggleCourse = (courseId: string) => {
    setSelectedIds((prev) =>
      prev.includes(courseId) ? prev.filter((i) => i !== courseId) : [...prev, courseId],
    );
  };

  const moveUp = (index: number) => {
    if (index === 0) return;
    setSelectedIds((prev) => {
      const next = [...prev];
      [next[index - 1], next[index]] = [next[index], next[index - 1]];
      return next;
    });
  };

  const moveDown = (index: number) => {
    setSelectedIds((prev) => {
      if (index === prev.length - 1) return prev;
      const next = [...prev];
      [next[index], next[index + 1]] = [next[index + 1], next[index]];
      return next;
    });
  };

  return (
    <>
      <TopNavBar />
      <main className="pt-16 min-h-screen bg-background">
        <div className="flex-grow w-full max-w-screen-2xl mx-auto px-lg py-xl">

          <section className="mb-xl flex flex-col md:flex-row md:items-end justify-between gap-md">
            <div>
              <Link href={`/roadmaps/${id}`} className="flex items-center gap-xs text-primary font-label-md text-label-md mb-sm hover:underline active:scale-95 transition-all">
                <span className="material-symbols-outlined text-[18px]">arrow_back</span>
                로드맵으로 돌아가기
              </Link>
              <h1 className="font-headline-lg text-headline-lg text-on-surface mb-xs">로드맵 수정</h1>
              <p className="font-body-md text-body-md text-on-surface-variant">학생들의 학습 경로를 체계적으로 설계하고 가이드하세요.</p>
            </div>
            <button
              type="button"
              className="flex items-center gap-xs px-xl py-md bg-primary text-on-primary font-label-md text-label-md rounded-xl shadow-sm hover:brightness-105 active:scale-95 transition-all"
            >
              <span className="material-symbols-outlined text-[20px]">check</span>
              수정하기
            </button>
          </section>

          <div className="grid grid-cols-1 lg:grid-cols-12 gap-gutter items-start">

            {/* 왼쪽 */}
            <div className="lg:col-span-8 flex flex-col gap-xl">

              {/* 기본 정보 */}
              <div className="bg-surface-container-lowest p-lg rounded-xl border border-surface-container-highest shadow-sm">
                <h2 className="font-headline-md text-headline-md text-on-surface mb-lg">기본 정보</h2>
                <div className="flex flex-col md:flex-row gap-lg">
                  <div className="flex-shrink-0 w-full md:w-64">
                    <label className="block font-label-md text-label-md text-on-surface-variant mb-sm">로드맵 썸네일</label>
                    <div className="aspect-video w-full rounded-lg border-2 border-dashed border-outline-variant bg-surface-container flex flex-col items-center justify-center gap-sm cursor-pointer hover:bg-surface-container-high transition-colors group">
                      <span className="material-symbols-outlined text-outline group-hover:text-primary text-[32px]">add_photo_alternate</span>
                      <span className="font-label-sm text-label-sm text-on-surface-variant">이미지 업로드</span>
                    </div>
                  </div>
                  <div className="flex-grow flex flex-col gap-md">
                    <div>
                      <label className="block font-label-md text-label-md text-on-surface-variant mb-sm">로드맵 명칭</label>
                      <input
                        className="w-full px-md py-sm rounded-lg border border-outline-variant focus:border-primary focus:ring-2 focus:ring-primary/20 transition-all font-body-md text-body-md"
                        defaultValue={roadmap?.title ?? ''}
                        placeholder="예: 시니어 프론트엔드 개발자 마스터 코스"
                        type="text"
                      />
                    </div>
                    <div>
                      <label className="block font-label-md text-label-md text-on-surface-variant mb-sm">로드맵 소개</label>
                      <textarea
                        className="w-full px-md py-sm rounded-lg border border-outline-variant focus:border-primary focus:ring-2 focus:ring-primary/20 transition-all font-body-md text-body-md resize-none"
                        defaultValue={roadmap?.description ?? ''}
                        placeholder="로드맵의 목표와 대상 독자를 간략하게 설명해주세요."
                        rows={4}
                      />
                    </div>
                  </div>
                </div>
              </div>

              {/* 강좌 선택 */}
              <div className="bg-surface-container-lowest p-lg rounded-xl border border-surface-container-highest shadow-sm">
                <div className="flex flex-col md:flex-row justify-between md:items-center mb-lg gap-md">
                  <h2 className="font-headline-md text-headline-md text-on-surface">강좌 선택</h2>
                  <div className="relative flex-grow max-w-sm">
                    <span className="absolute left-3 top-1/2 -translate-y-1/2 material-symbols-outlined text-outline text-[20px]">search</span>
                    <input
                      className="w-full pl-10 pr-4 py-sm bg-surface rounded-lg border border-outline-variant focus:border-primary focus:ring-2 focus:ring-primary/20 transition-all font-body-md text-body-md"
                      placeholder="강좌명 또는 강사명으로 검색"
                      type="text"
                      value={search}
                      onChange={(e) => setSearch(e.target.value)}
                    />
                  </div>
                </div>
                <div className="grid grid-cols-1 md:grid-cols-2 gap-md overflow-y-auto max-h-[500px] pr-sm custom-scrollbar">
                  {filtered.map((course) => {
                    const selected = selectedIds.includes(course.id);
                    return (
                      <div
                        key={course.id}
                        onClick={() => toggleCourse(course.id)}
                        className={`flex items-center gap-md p-sm rounded-lg border transition-all cursor-pointer ${
                          selected
                            ? 'border-primary bg-primary/5'
                            : 'border-surface-container-high hover:border-primary bg-surface-bright'
                        }`}
                      >
                        <div className="w-20 h-14 rounded overflow-hidden flex-shrink-0">
                          <img className="w-full h-full object-cover" src={course.thumbnail} alt={course.title} />
                        </div>
                        <div className="flex-grow min-w-0">
                          <h4 className="font-label-md text-label-md text-on-surface truncate">{course.title}</h4>
                          <p className="font-label-sm text-label-sm text-on-surface-variant">{course.instructor} 강사</p>
                        </div>
                        <span
                          className="material-symbols-outlined transition-colors"
                          style={selected ? { fontVariationSettings: "'FILL' 1", color: 'var(--color-primary)' } : {}}
                        >
                          {selected ? 'check_circle' : 'add_circle'}
                        </span>
                      </div>
                    );
                  })}
                </div>
              </div>
            </div>

            {/* 오른쪽 */}
            <div className="lg:col-span-4 flex flex-col gap-lg sticky top-24">

              {/* 선택된 강좌 */}
              <div className="bg-surface-container-lowest rounded-xl border border-surface-container-highest shadow-sm overflow-hidden">
                <div className="bg-primary px-lg py-md flex justify-between items-center">
                  <h3 className="font-label-md text-label-md text-on-primary">선택된 강좌</h3>
                  <span className="bg-white/20 text-white font-label-sm text-label-sm px-2 py-0.5 rounded-full">
                    {selectedCourses.length}개
                  </span>
                </div>
                <div className="p-md flex flex-col gap-sm max-h-[300px] overflow-y-auto custom-scrollbar">
                  {selectedCourses.length === 0 ? (
                    <p className="text-center py-md font-label-sm text-label-sm text-on-surface-variant">강좌를 선택해주세요.</p>
                  ) : (
                    selectedCourses.map((course) => (
                      <div key={course.id} className="flex items-center justify-between p-sm rounded bg-surface-container-low group">
                        <Link
                          href={`/courses/${course.id}`}
                          className="font-label-sm text-label-sm text-on-surface truncate hover:text-primary transition-colors flex-1 min-w-0 mr-sm"
                          onClick={(e) => e.stopPropagation()}
                        >
                          {course.title}
                        </Link>
                        <button
                          type="button"
                          onClick={() => toggleCourse(course.id)}
                          className="material-symbols-outlined text-outline-variant hover:text-error transition-colors text-[18px] flex-shrink-0"
                        >
                          close
                        </button>
                      </div>
                    ))
                  )}
                </div>
              </div>

              {/* 학습 경로 순서 */}
              {selectedCourses.length > 0 && (
                <div className="bg-surface-container-lowest p-lg rounded-xl border border-surface-container-highest shadow-sm">
                  <h2 className="font-headline-md text-headline-md text-on-surface mb-lg">학습 경로 순서</h2>
                  <div className="flex flex-col gap-md">
                    {selectedCourses.map((course, index) => (
                      <div key={course.id} className="flex items-center gap-md">
                        <div className="flex flex-col items-center gap-1">
                          <span className="w-6 h-6 rounded-full bg-primary-container text-on-primary-container font-label-sm text-label-sm flex items-center justify-center">
                            {index + 1}
                          </span>
                          {index < selectedCourses.length - 1 && (
                            <div className="w-0.5 h-8 bg-surface-container-highest" />
                          )}
                        </div>
                        <div className="flex-grow bg-surface p-sm rounded-lg border border-surface-container-high flex items-center gap-md">
                          <div className="w-12 h-10 rounded bg-surface-container-highest flex-shrink-0 overflow-hidden">
                            <img className="w-full h-full object-cover" src={course.thumbnail} alt={course.title} />
                          </div>
                          <Link
                            href={`/courses/${course.id}`}
                            className="flex-grow min-w-0 hover:text-primary transition-colors"
                          >
                            <h4 className="font-label-sm text-label-sm text-on-surface truncate">{course.title}</h4>
                          </Link>
                          <div className="flex flex-col gap-0.5">
                            <button type="button" onClick={() => moveUp(index)}>
                              <span className="material-symbols-outlined text-outline cursor-pointer hover:text-primary text-[16px]">keyboard_arrow_up</span>
                            </button>
                            <button type="button" onClick={() => moveDown(index)}>
                              <span className="material-symbols-outlined text-outline cursor-pointer hover:text-primary text-[16px]">keyboard_arrow_down</span>
                            </button>
                          </div>
                        </div>
                      </div>
                    ))}
                  </div>
                  <p className="font-label-sm text-label-sm text-on-surface-variant mt-lg italic flex items-center gap-xs">
                    <span className="material-symbols-outlined text-[14px]">info</span>
                    화살표를 눌러 학습 순서를 변경할 수 있습니다.
                  </p>
                </div>
              )}

            </div>
          </div>
        </div>
      </main>
      <Footer />
    </>
  );
}
