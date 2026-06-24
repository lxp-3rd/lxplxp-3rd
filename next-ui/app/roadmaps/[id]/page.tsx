'use client';

import { useState, useEffect } from 'react';
import Link from 'next/link';
import { TopNavBar } from '@/components/TopNavBar';
import { Footer } from '@/components/Footer';
import { BackLink } from '@/components/ui';
import { useAuth } from '@/lib/AuthContext';
import { roadmapApi } from '@/app/roadmaps/api';
import { courseApi } from '@/app/courses/api';
import type { RoadmapSummary } from '@/app/roadmaps/types';
import type { CourseDetail } from '@/app/courses/types';

function formatDate(iso: string) {
  return iso.slice(0, 10).replace(/-/g, '.');
}

export default function RoadmapDetailPage({ params }: { params: { id: string } }) {
  const { id } = params;
  const { user } = useAuth();

  const [roadmap, setRoadmap] = useState<RoadmapSummary | null>(null);
  const [courses, setCourses] = useState<CourseDetail[]>([]);
  const [isEnrolled, setIsEnrolled] = useState(false);
  const [isParticipating, setIsParticipating] = useState(false);
  const [isLoading, setIsLoading] = useState(true);
  const [notFound, setNotFound] = useState(false);

  useEffect(() => {
    setIsLoading(true);
    Promise.allSettled([
      roadmapApi.getById(id),
      roadmapApi.getParticipating(),
    ]).then(async ([roadmapResult, participatingResult]) => {
      if (roadmapResult.status === 'rejected') {
        setNotFound(true);
        return;
      }
      const rm = roadmapResult.value;
      setRoadmap(rm);

      if (participatingResult.status === 'fulfilled') {
        setIsEnrolled(participatingResult.value.some((r) => r.id === rm.id));
      }

      const courseResults = await Promise.allSettled(
        rm.courseIds.map((cid) => courseApi.getById(String(cid))),
      );
      setCourses(
        courseResults
          .filter((r): r is PromiseFulfilledResult<CourseDetail> => r.status === 'fulfilled')
          .map((r) => r.value),
      );
    }).finally(() => setIsLoading(false));
  }, [id]);

  const handleParticipate = async () => {
    if (!user) {
      alert('로그인이 필요합니다.');
      return;
    }
    setIsParticipating(true);
    try {
      await roadmapApi.participate(id);
      setIsEnrolled(true);
    } catch {
      alert('로드맵 참여에 실패했습니다. 다시 시도해 주세요.');
    } finally {
      setIsParticipating(false);
    }
  };

  if (isLoading) {
    return (
      <>
        <TopNavBar />
        <main className="pt-16 min-h-screen bg-background">
          <div className="pt-24 pb-xl px-gutter max-w-7xl mx-auto animate-pulse space-y-xl">
            <div className="flex flex-col lg:flex-row gap-xl">
              <div className="w-full lg:w-1/2 aspect-video bg-surface-container rounded-xl" />
              <div className="w-full lg:w-1/2 space-y-md">
                <div className="h-8 w-3/4 bg-surface-container rounded" />
                <div className="h-4 w-full bg-surface-container rounded" />
                <div className="h-4 w-2/3 bg-surface-container rounded" />
                <div className="h-32 bg-surface-container rounded-xl mt-lg" />
              </div>
            </div>
          </div>
        </main>
        <Footer />
      </>
    );
  }

  if (notFound || !roadmap) {
    return (
      <>
        <TopNavBar />
        <main className="pt-16 min-h-screen bg-background flex flex-col items-center justify-center gap-md text-on-surface-variant">
          <span className="material-symbols-outlined text-5xl">search_off</span>
          <p className="text-body-lg font-body-lg">로드맵을 찾을 수 없습니다.</p>
          <BackLink href="/roadmaps">로드맵 목록으로 돌아가기</BackLink>
        </main>
        <Footer />
      </>
    );
  }

  return (
    <>
      <TopNavBar />
      <main className="pt-16 min-h-screen bg-background">
        <div className="pt-24 pb-xl px-gutter max-w-7xl mx-auto">

          <BackLink href="/roadmaps" className="mb-lg inline-flex">로드맵 목록으로</BackLink>

          <div className="flex flex-col lg:flex-row gap-xl mb-xl">
            <div className="w-full lg:w-1/2 aspect-video rounded-xl overflow-hidden shadow-sm border border-outline-variant bg-surface-container-low flex items-center justify-center">
              <span className="material-symbols-outlined text-[80px] text-on-surface-variant">route</span>
            </div>

            <div className="w-full lg:w-1/2 flex flex-col justify-center">
              <div className="flex items-center gap-xs mb-md">
                <span className="flex items-center gap-1 text-on-surface-variant text-label-md font-label-md">
                  <span className="material-symbols-outlined text-[16px]">menu_book</span>
                  {roadmap.courseIds.length}개 강좌
                </span>
              </div>
              <h1 className="text-headline-lg font-headline-lg mb-md leading-tight text-on-surface">
                {roadmap.name}
              </h1>
              <p className="text-body-lg font-body-lg text-on-surface-variant mb-xl leading-relaxed">
                {roadmap.introduction}
              </p>

              <div className="p-lg bg-surface-container-lowest border border-outline-variant rounded-xl shadow-sm">
                {isEnrolled ? (
                  <>
                    <div className="flex items-center justify-center gap-sm mb-md py-sm rounded-lg bg-secondary-container text-on-secondary-container">
                      <span className="material-symbols-outlined text-[20px]">check_circle</span>
                      <span className="text-label-md font-label-md">참여 중인 로드맵입니다</span>
                    </div>
                    {courses[0] && (
                      <Link href={`/courses/${courses[0].id}`}>
                        <button className="w-full py-3 px-xl bg-primary text-on-primary font-label-md text-label-md rounded-lg shadow-sm hover:opacity-90 transition-all duration-300">
                          강의 계속하기
                        </button>
                      </Link>
                    )}
                  </>
                ) : (
                  <>
                    <div className="flex items-center gap-xs mb-md text-on-surface-variant">
                      <span className="material-symbols-outlined text-[18px]">menu_book</span>
                      <span className="text-label-md font-label-md">총 {roadmap.courseIds.length}개 강좌</span>
                    </div>
                    <button
                      className="w-full py-3 px-xl bg-primary text-on-primary font-label-md text-label-md rounded-lg shadow-sm hover:opacity-90 transition-all duration-300 disabled:opacity-50"
                      onClick={handleParticipate}
                      disabled={isParticipating}
                    >
                      {isParticipating ? '참여 중...' : '로드맵 참여하기'}
                    </button>
                  </>
                )}
              </div>
            </div>
          </div>

          <div className="grid grid-cols-1 lg:grid-cols-12 gap-xl">
            <div className="lg:col-span-8">
              <h2 className="text-headline-md font-headline-md mb-lg">로드맵 순서</h2>
              <div className="space-y-0">
                {courses.length === 0 ? (
                  <div className="text-center py-xl text-on-surface-variant">
                    <span className="material-symbols-outlined text-5xl block mb-md">menu_book</span>
                    <p className="text-body-md font-body-md">등록된 강좌가 없습니다.</p>
                  </div>
                ) : (
                  courses.map((course, idx) => {
                    const step = String(idx + 1).padStart(2, '0');
                    const isLast = idx === courses.length - 1;
                    return (
                      <div key={course.id} className="flex gap-md pb-xl">
                        <div className="flex flex-col items-center flex-shrink-0">
                          <div className="w-10 h-10 rounded-full bg-primary text-on-primary flex items-center justify-center shadow-sm flex-shrink-0">
                            <span className="text-label-md font-bold">{step}</span>
                          </div>
                          {!isLast && <div className="flex-1 w-0.5 bg-outline-variant mt-1" />}
                        </div>
                        <div className="flex-1 pb-2">
                          <Link href={`/courses/${course.id}`}>
                            <div className="bg-surface-container-lowest rounded-xl border border-outline-variant overflow-hidden hover:shadow-md hover:border-primary transition-all duration-300">
                              <div className="flex items-center justify-between px-md pt-md pb-sm border-b border-outline-variant">
                                <span className="text-label-sm font-label-sm text-primary font-bold tracking-wider">
                                  STEP {step}
                                </span>
                              </div>
                              <div className="px-md py-sm">
                                <h3 className="text-body-lg font-bold text-on-surface mb-xs">{course.title}</h3>
                                {course.summary && (
                                  <p className="text-body-sm font-body-sm text-on-surface-variant mb-sm">{course.summary}</p>
                                )}
                                <span className="text-label-md font-label-md text-primary flex items-center gap-xs hover:underline">
                                  강의 보러 가기
                                  <span className="material-symbols-outlined text-[16px]">arrow_forward</span>
                                </span>
                              </div>
                            </div>
                          </Link>
                        </div>
                      </div>
                    );
                  })
                )}
              </div>
            </div>

            <aside className="lg:col-span-4 space-y-xl">
              <div className="bg-surface-container-low p-lg rounded-xl border border-outline-variant">
                <h3 className="text-label-md font-label-md mb-md flex items-center gap-2 text-on-surface">
                  <span className="material-symbols-outlined text-primary">info</span>
                  로드맵 정보
                </h3>
                <ul className="space-y-sm text-body-sm font-body-sm text-on-surface-variant">
                  <li className="flex justify-between">
                    <span>총 강좌</span>
                    <span className="font-bold text-on-surface">{roadmap.courseIds.length}개</span>
                  </li>
                  <li className="flex justify-between">
                    <span>업데이트 일자</span>
                    <span className="font-bold text-on-surface">{formatDate(roadmap.updatedAt)}</span>
                  </li>
                </ul>
              </div>
            </aside>
          </div>
        </div>
      </main>
      <Footer />
    </>
  );
}
