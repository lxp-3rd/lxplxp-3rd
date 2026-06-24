'use client';

import { TopNavBar } from '@/components/TopNavBar';
import { Footer } from '@/components/Footer';
import { Button, PageHeader } from '@/components/ui';
import { EnrollmentCard } from './components/EnrollmentCard';
import { MOCK_ENROLLMENTS } from './mockData';

export default function EnrollmentsPage() {
  const enrollments = MOCK_ENROLLMENTS;
  const inProgress = enrollments.filter((e) => e.status === 'IN_PROGRESS').length;

  return (
    <>
      <TopNavBar />
      <main className="pt-16 min-h-screen bg-background">
        <div className="pt-xxl pb-xxl max-w-[1280px] mx-auto px-gutter mt-16">
          <PageHeader
            title="학습 중인 강의"
            subtitle={`총 ${enrollments.length}개의 강의 중 ${inProgress}개 수강 중입니다. 오늘도 열공하세요!`}
            actions={
              <div className="hidden md:flex gap-sm">
                <Button variant="outline" size="sm">최근 수강순</Button>
                <Button variant="outline" size="sm">진도율순</Button>
              </div>
            }
          />

          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-lg">
            {enrollments.map((e) => (
              <EnrollmentCard
                key={e.id}
                courseId={e.courseId}
                courseTitle={e.courseTitle}
                instructor={e.instructor}
                thumbnail={e.thumbnail}
                progress={e.progress}
                lastAccessedAt={e.lastAccessedAt}
                status={e.status}
              />
            ))}
          </div>

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