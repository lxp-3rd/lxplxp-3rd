'use client';

import { useEffect, useState } from 'react';
import Link from 'next/link';
import { TopNavBar } from '@/components/TopNavBar';
import { Footer } from '@/components/Footer';
import { contentApi, courseApi } from '@/app/courses/api';
import type { CourseContent, CourseDetail } from '@/app/courses/types';
import { enrollmentApi } from '@/app/enrollments/api';
import type { Enrollment } from '@/app/enrollments/types';
import { getImageUrl } from '@/lib/imageUrl';

export default function EnrollmentDetailPage({ params }: { params: { id: string } }) {
  const [enrollment, setEnrollment] = useState<Enrollment | null>(null);
  const [course, setCourse] = useState<CourseDetail | null>(null);
  const [contents, setContents] = useState<CourseContent[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const controller = new AbortController();
    setLoading(true);
    setError(null);

    enrollmentApi.getById(Number(params.id))
      .then(async (loadedEnrollment) => {
        const [loadedCourse, loadedContents] = await Promise.all([
          courseApi.getById(String(loadedEnrollment.courseId)),
          contentApi.getByCourse(loadedEnrollment.courseId),
        ]);
        if (controller.signal.aborted) return;
        setEnrollment(loadedEnrollment);
        setCourse(loadedCourse);
        setContents(loadedContents);
      })
      .catch(() => {
        if (!controller.signal.aborted) setError('수강 정보를 불러올 수 없습니다.');
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
          <p className="text-on-surface-variant text-body-md font-body-md">불러오는 중...</p>
        </main>
        <Footer />
      </>
    );
  }

  if (error || !enrollment || !course) {
    return (
      <>
        <TopNavBar />
        <main className="pt-16 min-h-screen flex items-center justify-center">
          <p className="text-error text-body-md font-body-md">{error ?? '수강 정보를 찾을 수 없습니다.'}</p>
        </main>
        <Footer />
      </>
    );
  }

  const isCompleted = enrollment.status === 'COMPLETED';
  const doneCount = isCompleted ? contents.length : 0;
  const current = contents[0];
  const progress = contents.length === 0 ? 0 : Math.round((doneCount / contents.length) * 100);

  return (
    <>
      <TopNavBar />
      <main className="pt-16 min-h-screen bg-background">
        <div className="max-w-[1100px] mx-auto px-gutter pt-xl pb-xxl">

          {/* 뒤로가기 */}
          <div className="mb-lg">
            <Link href="/enrollments" className="flex items-center gap-xs text-on-surface-variant hover:text-primary transition-colors">
              <span className="material-symbols-outlined text-[20px]">arrow_back</span>
              <span className="text-label-md font-label-md">내 강의로</span>
            </Link>
          </div>

          {/* 강좌 헤더 */}
          <div className="bg-surface-container-lowest border border-outline-variant rounded-xl p-xl mb-xl">
            <div className="flex flex-col md:flex-row gap-lg items-start">
              <img
                src={getImageUrl(course.thumbnailUrl)}
                alt={course.title}
                className="w-full md:w-48 h-32 object-cover rounded-lg flex-shrink-0"
              />
              <div className="flex-1">
                <p className="text-label-sm font-label-sm text-primary mb-xs">강좌 #{course.id}</p>
                <h1 className="text-headline-md font-headline-md text-on-surface mb-md">{course.title}</h1>
                <div className="flex items-center gap-md mb-md">
                  <span className={`px-sm py-1 rounded-full text-label-sm font-label-sm ${
                    isCompleted
                      ? 'bg-secondary-container text-on-secondary-container'
                      : 'bg-primary-container text-on-primary-container'
                  }`}>
                    {isCompleted ? '수강 완료' : '수강 중'}
                  </span>
                  <span className="text-label-sm font-label-sm text-on-surface-variant">수강 시작: {enrollment.createdAt.slice(0, 10)}</span>
                </div>
                <div className="flex items-center gap-md">
                  <div className="flex-1 h-2 bg-surface-container-high rounded-full overflow-hidden">
                    <div
                      className="h-full bg-primary rounded-full transition-all"
                      style={{ width: `${progress}%` }}
                    />
                  </div>
                  <span className="text-label-md font-label-md text-primary whitespace-nowrap">{progress}%</span>
                </div>
                <p className="text-label-sm font-label-sm text-on-surface-variant mt-xs">{doneCount} / {contents.length}개 완료</p>
              </div>
              {current && (
                <div className="flex-shrink-0">
                  <Link
                    href={`/courses/${course.id}/contents/${current.id}`}
                    className="bg-primary text-on-primary px-xl py-sm rounded-lg text-label-md font-label-md hover:opacity-90 transition-opacity flex items-center gap-xs whitespace-nowrap"
                  >
                    <span className="material-symbols-outlined text-[18px]">play_circle</span>
                    {isCompleted ? '다시 학습' : '이어서 학습'}
                  </Link>
                </div>
              )}
            </div>
          </div>

          {/* 커리큘럼 */}
          <div>
            <h2 className="text-headline-sm font-headline-md text-on-surface mb-md">커리큘럼</h2>
            <div className="bg-surface-container-lowest border border-outline-variant rounded-xl overflow-hidden">
              <div className="divide-y divide-outline-variant">
                {contents.map((item) => {
                  const done = isCompleted;
                  return (
                    <div key={item.id} className="px-lg py-md flex items-center gap-md group">
                      <div className={`w-6 h-6 rounded-full flex items-center justify-center flex-shrink-0 ${done ? 'bg-primary' : 'bg-surface-container-high'}`}>
                        {done
                          ? <span className="material-symbols-outlined text-on-primary text-[14px]" style={{ fontVariationSettings: "'FILL' 1" }}>check</span>
                          : <span className="material-symbols-outlined text-on-surface-variant text-[14px]">play_arrow</span>
                        }
                      </div>
                      <div className="flex-1 min-w-0">
                        <p className={`text-body-md font-body-md truncate ${done ? 'text-on-surface-variant line-through' : 'text-on-surface'}`}>
                          {item.order}. {item.title}
                        </p>
                      </div>
                      <Link
                        href={`/courses/${course.id}/contents/${item.id}`}
                        className="text-label-sm font-label-sm text-primary opacity-0 group-hover:opacity-100 transition-opacity"
                      >
                        {done ? '다시 보기' : '학습하기'}
                      </Link>
                    </div>
                  );
                })}
              </div>
            </div>
          </div>

        </div>
      </main>
      <Footer />
    </>
  );
}
