'use client';

import { useEffect, useState } from 'react';
import { TopNavBar } from '@/components/TopNavBar';
import { Footer } from '@/components/Footer';
import { Button, PageHeader } from '@/components/ui';
import { courseApi } from '@/app/courses/api';
import { myPageApi } from '@/app/my-page/api';
import { EnrollmentCard } from './components/EnrollmentCard';
import { enrollmentApi } from './api';
import type { Enrollment } from './types';

type EnrollmentView = {
  enrollment: Enrollment;
  courseTitle: string;
  instructor: string;
  thumbnail: string;
  progress: number;
  lastAccessedAt: string;
};

export default function EnrollmentsPage() {
  const [items, setItems] = useState<EnrollmentView[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const controller = new AbortController();
    setLoading(true);
    setError(null);

    myPageApi.getProfile()
      .then((profile) => enrollmentApi.getMine(profile.memberId))
      .then(async (enrollments) => {
        const activeEnrollments = enrollments.filter((enrollment) => enrollment.status !== 'CANCELED');
        const views = await Promise.all(activeEnrollments.map(async (enrollment) => {
          const course = await courseApi.getById(String(enrollment.courseId));
          return {
            enrollment,
            courseTitle: course.title,
            instructor: course.instructorName,
            thumbnail: course.thumbnailUrl,
            progress: enrollment.status === 'COMPLETED' ? 100 : 0,
            lastAccessedAt: enrollment.createdAt.slice(0, 10),
          };
        }));
        if (!controller.signal.aborted) setItems(views);
      })
      .catch(() => {
        if (!controller.signal.aborted) setError('수강 목록을 불러올 수 없습니다.');
      })
      .finally(() => {
        if (!controller.signal.aborted) setLoading(false);
      });

    return () => controller.abort();
  }, []);

  const inProgress = items.filter((item) => item.enrollment.status === 'ACTIVE').length;

  return (
    <>
      <TopNavBar />
      <main className="pt-16 min-h-screen bg-background">
        <div className="pt-xxl pb-xxl max-w-[1280px] mx-auto px-gutter mt-16">
          <PageHeader
            title="학습 중인 강의"
            subtitle={`총 ${items.length}개의 강의 중 ${inProgress}개 수강 중입니다. 오늘도 열공하세요!`}
            actions={
              <div className="hidden md:flex gap-sm">
                <Button variant="outline" size="sm">최근 수강순</Button>
                <Button variant="outline" size="sm">진도율순</Button>
              </div>
            }
          />

          {loading ? (
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-lg">
              {Array.from({ length: 3 }).map((_, index) => (
                <div key={index} className="h-72 bg-surface-container rounded-xl animate-pulse" />
              ))}
            </div>
          ) : error ? (
            <div className="flex items-center justify-center py-xxl text-error text-body-md font-body-md">
              {error}
            </div>
          ) : items.length === 0 ? (
            <div className="flex flex-col items-center justify-center py-xxl gap-md text-on-surface-variant">
              <span className="material-symbols-outlined text-5xl">school</span>
              <p className="text-body-lg font-body-lg">수강 중인 강의가 없습니다.</p>
            </div>
          ) : (
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-lg">
              {items.map((item) => (
                <EnrollmentCard
                  key={item.enrollment.id}
                  enrollmentId={item.enrollment.id}
                  courseId={String(item.enrollment.courseId)}
                  courseTitle={item.courseTitle}
                  instructor={item.instructor}
                  thumbnail={item.thumbnail}
                  progress={item.progress}
                  lastAccessedAt={item.lastAccessedAt}
                  status={item.enrollment.status === 'COMPLETED' ? 'COMPLETED' : 'ACTIVE'}
                />
              ))}
            </div>
          )}

          <div className="flex justify-center mt-xl">
            <Button variant="outline">
              <span className="material-symbols-outlined text-[18px]">expand_more</span>
              더 많은 강의 보기
            </Button>
          </div>
        </div>
      </main>
      <Footer />
    </>
  );
}
