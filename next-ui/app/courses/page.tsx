'use client';

import { useState, useEffect } from 'react';
import { TopNavBar } from '@/components/TopNavBar';
import { Footer } from '@/components/Footer';
import { PageHeader } from '@/components/ui';
import { CourseCard } from './components/CourseCard';
import { courseApi } from './api';
import type { CourseSummary } from './types';

export default function CoursesPage() {
  const [courses, setCourses] = useState<CourseSummary[]>([]);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    courseApi
      .getAll()
      .then(setCourses)
      .catch(() => {})
      .finally(() => setIsLoading(false));
  }, []);

  return (
    <>
      <TopNavBar />
      <main className="pt-16 min-h-screen bg-background">
        <div className="pt-xl pb-xxl px-gutter max-w-container-max mx-auto mt-16">
          <PageHeader title="성장하세요!" subtitle="성장을 위한 맞춤형 강의 목록입니다." />

          {isLoading ? (
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-xl">
              {Array.from({ length: 6 }).map((_, i) => (
                <div key={i} className="animate-pulse bg-surface-container rounded-xl aspect-video" />
              ))}
            </div>
          ) : courses.length === 0 ? (
            <div className="flex flex-col items-center justify-center py-xxl gap-md text-on-surface-variant">
              <span className="material-symbols-outlined text-5xl">school</span>
              <p className="text-body-lg font-body-lg">강좌가 없습니다.</p>
            </div>
          ) : (
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-xl">
              {courses.map((course) => (
                <CourseCard
                  key={course.id}
                  id={String(course.id)}
                  title={course.title}
                  instructor={course.instructorName}
                  thumbnail={course.thumbnailUrl}
                  enrollmentCount={course.enrollmentCount}
                />
              ))}
            </div>
          )}
        </div>
      </main>
      <Footer />
    </>
  );
}
