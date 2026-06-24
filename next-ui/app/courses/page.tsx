import { TopNavBar } from '@/components/TopNavBar';
import { Footer } from '@/components/Footer';
import { PageHeader } from '@/components/ui';
import { CourseCard } from './components/CourseCard';
import { MOCK_COURSES } from './mockData';
// TODO: API 연동 필요

export default function CoursesPage() {
  return (
    <>
      <TopNavBar />
      <main className="pt-16 min-h-screen bg-background">
        <div className="pt-xl pb-xxl px-gutter max-w-container-max mx-auto mt-16">
          <PageHeader
            title="성장하세요!"
            subtitle="성장을 위한 맞춤형 강의 목록입니다."
          />
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-xl">
            {MOCK_COURSES.map((course) => (
              <CourseCard
                key={course.id}
                id={course.id}
                title={course.title}
                instructor={course.instructor}
                thumbnail={course.thumbnail}
                enrollmentCount={course.enrollmentCount}
              />
            ))}
          </div>
        </div>
      </main>
      <Footer />
    </>
  );
}
