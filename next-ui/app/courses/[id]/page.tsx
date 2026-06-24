'use client';

import { useState, useEffect } from 'react';
import { TopNavBar } from '@/components/TopNavBar';
import { Footer } from '@/components/Footer';
import { BackLink } from '@/components/ui';
import { useAuth } from '@/lib/AuthContext';
import { courseApi } from '@/app/courses/api';
import { instructorApi } from '@/app/instructors/api';
import { questionApi } from '@/app/questions/api';
import type { CourseDetail } from '@/app/courses/types';
import type { InstructorProfile } from '@/app/instructors/types';
import type { Question } from '@/app/questions/types';
import { CourseHero } from './components/CourseHero';
import { EnrollCard } from './components/EnrollCard';
import { InstructorInfoCard } from './components/InstructorInfoCard';
import { CourseCurriculum } from './components/CourseCurriculum';
import { CourseQnAPreview } from './components/CourseQnAPreview';

export default function CourseDetailPage({ params }: { params: { id: string } }) {
  const { id } = params;
  const { user } = useAuth();

  const [course, setCourse] = useState<CourseDetail | null>(null);
  const [instructorProfile, setInstructorProfile] = useState<InstructorProfile | null>(null);
  const [questions, setQuestions] = useState<Question[]>([]);
  const [isLoading, setIsLoading] = useState(true);
  const [notFound, setNotFound] = useState(false);

  useEffect(() => {
    setIsLoading(true);
    courseApi
      .getById(id)
      .then((data) => {
        setCourse(data);
        // 강사 프로필·Q&A는 강좌 로드 후 병렬 요청
        Promise.allSettled([
          instructorApi.getProfile(data.instructorId),
          questionApi.getByCourse(data.id),
        ]).then(([profileResult, questionsResult]) => {
          if (profileResult.status === 'fulfilled') setInstructorProfile(profileResult.value);
          if (questionsResult.status === 'fulfilled') setQuestions(questionsResult.value);
        });
      })
      .catch(() => setNotFound(true))
      .finally(() => setIsLoading(false));
  }, [id]);

  const handleEnroll = async () => {
    if (!user) return;
    if (!course) return;
    try {
      await fetch(`${process.env.NEXT_PUBLIC_API_URL ?? ''}/api/enrollments`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ memberId: Number(user.id), courseId: course.id }),
      });
      setCourse((prev) => prev ? { ...prev, enrolled: true, enrollmentCount: prev.enrollmentCount + 1 } : prev);
    } catch {
      alert('수강 신청에 실패했습니다. 다시 시도해 주세요.');
    }
  };

  if (isLoading) {
    return (
      <>
        <TopNavBar />
        <main className="pt-16 min-h-screen bg-background">
          <div className="pt-xl mt-16 max-w-container-max mx-auto px-gutter pb-xl">
            <div className="animate-pulse space-y-lg">
              <div className="h-4 w-36 bg-surface-container rounded" />
              <div className="grid grid-cols-1 lg:grid-cols-12 gap-xl">
                <div className="lg:col-span-8 space-y-md">
                  <div className="aspect-video bg-surface-container rounded-xl" />
                  <div className="h-8 w-3/4 bg-surface-container rounded" />
                  <div className="h-24 bg-surface-container rounded-xl" />
                </div>
                <div className="lg:col-span-4">
                  <div className="h-48 bg-surface-container rounded-xl" />
                </div>
              </div>
            </div>
          </div>
        </main>
        <Footer />
      </>
    );
  }

  if (notFound || !course) {
    return (
      <>
        <TopNavBar />
        <main className="pt-16 min-h-screen bg-background flex flex-col items-center justify-center gap-md text-on-surface-variant">
          <span className="material-symbols-outlined text-5xl">search_off</span>
          <p className="text-body-lg font-body-lg">강좌를 찾을 수 없습니다.</p>
          <BackLink href="/courses">강좌 목록으로 돌아가기</BackLink>
        </main>
        <Footer />
      </>
    );
  }

  return (
    <>
      <TopNavBar />
      <main className="pt-16 min-h-screen bg-background">
        <div className="pt-xl mt-16 max-w-container-max mx-auto px-gutter pb-xl">

          <BackLink href="/courses" className="mb-lg inline-flex">강의 목록으로 돌아가기</BackLink>

          <div className="grid grid-cols-1 lg:grid-cols-12 gap-xl mb-xl">
            <div className="lg:col-span-8">
              <CourseHero
                thumbnail={course.thumbnailUrl}
                title={course.title}
                description={course.description}
              />
            </div>
            <div className="lg:col-span-4">
              <div className="sticky top-[80px] space-y-lg">
                <EnrollCard
                  courseId={String(course.id)}
                  enrolled={course.enrolled}
                  enrollmentCount={course.enrollmentCount}
                  onEnroll={handleEnroll}
                />
                <InstructorInfoCard
                  name={course.instructorName}
                  expertise={[]}
                  enrollmentCount={course.enrollmentCount}
                  bio={instructorProfile?.bio}
                />
              </div>
            </div>
          </div>

          <CourseCurriculum curriculum={course.curriculum} />
          <CourseQnAPreview courseId={String(course.id)} questions={questions} />

        </div>
      </main>
      <Footer />
    </>
  );
}
