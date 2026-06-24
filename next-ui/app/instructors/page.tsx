import { TopNavBar } from '@/components/TopNavBar';
import { Footer } from '@/components/Footer';
import { PageHeader } from '@/components/ui';
import { InstructorCard } from './components/InstructorCard';
import { MOCK_INSTRUCTORS } from './mockData';

export default function InstructorsPage() {
  return (
    <>
      <TopNavBar />
      <main className="pt-16 min-h-screen bg-background">
        <div className="max-w-container-max mx-auto px-gutter pt-xl pb-xxl mt-16">
          <PageHeader
            title="강사 소개"
            subtitle="LXP의 전문 강사진을 만나보세요."
          />
          <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-lg">
            {MOCK_INSTRUCTORS.map((instructor) => (
              <InstructorCard key={instructor.id} data={instructor} />
            ))}
          </div>
        </div>
      </main>
      <Footer />
    </>
  );
}
