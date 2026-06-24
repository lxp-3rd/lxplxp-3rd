'use client';

import { TopNavBar } from '@/components/TopNavBar';
import { Footer } from '@/components/Footer';
import { BackLink } from '@/components/ui';
import { getCourseById, MOCK_COURSES } from '@/app/courses/mockData';
import { getInstructorById } from '@/app/instructors/mockData';
import { MOCK_QUESTIONS } from '@/app/questions/mockData';
import { CourseHero } from './components/CourseHero';
import { EnrollCard } from './components/EnrollCard';
import { InstructorInfoCard } from './components/InstructorInfoCard';
import { CourseCurriculum } from './components/CourseCurriculum';
import { CourseQnAPreview } from './components/CourseQnAPreview';

export default function CourseDetailPage({ params }: { params: { id: string } }) {
  const course = getCourseById(params.id) ?? MOCK_COURSES[0];
  const instructor = getInstructorById(course.instructorId);
  const courseQuestions = MOCK_QUESTIONS.filter((q) => q.courseId === course.id);

  return (
    <>
      <TopNavBar />
      <main className="pt-16 min-h-screen bg-background">
        <div className="pt-xl mt-16 max-w-container-max mx-auto px-gutter pb-xl">

          <BackLink href="/courses" className="mb-lg inline-flex">강의 목록으로 돌아가기</BackLink>

          <div className="grid grid-cols-1 lg:grid-cols-12 gap-xl mb-xl">
            <div className="lg:col-span-8">
              <CourseHero
                thumbnail={course.thumbnail}
                title={course.title}
                description={course.description}
                badge={course.badge}
              />
            </div>
            <div className="lg:col-span-4">
              <div className="sticky top-[80px] space-y-lg">
                <EnrollCard courseId={course.id} />
                <InstructorInfoCard
                  name={instructor?.name ?? course.instructor}
                  expertise={instructor?.expertise ?? []}
                  enrollmentCount={course.enrollmentCount}
                  bio={instructor?.bio}
                />
              </div>
            </div>
          </div>

          <CourseCurriculum contents={course.contents} />
          <CourseQnAPreview courseId={course.id} questions={courseQuestions} />

        </div>
      </main>
      <Footer />
    </>
  );
}
