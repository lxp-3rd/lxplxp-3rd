'use client';

import Link from 'next/link';
import { TopNavBar } from '@/components/TopNavBar';
import { Footer } from '@/components/Footer';
import { MOCK_INSTRUCTORS } from '@/app/instructors/mockData';
import { MOCK_COURSES } from '@/app/courses/mockData';
import { useAuth } from '@/lib/AuthContext';

export default function InstructorDetailPage() {
  const { user } = useAuth();
  const instructor = MOCK_INSTRUCTORS[0];
  const courses = MOCK_COURSES.filter((c) => c.instructorId === instructor.id);
  const isSelf = user?.id === instructor.id;

  return (
    <>
      <TopNavBar />
      <main className="pt-16 min-h-screen bg-background">
        <div className="max-w-[1000px] mx-auto px-gutter pt-xl pb-xxl">

          {/* 뒤로가기 */}
          <div className="mb-lg">
            <Link href="/instructors" className="flex items-center gap-xs text-on-surface-variant hover:text-primary transition-colors">
              <span className="material-symbols-outlined text-[20px]">arrow_back</span>
              <span className="text-label-md font-label-md">강사 목록</span>
            </Link>
          </div>

          {/* 프로필 헤더 */}
          <div className="bg-surface-container-lowest border border-outline-variant rounded-xl p-xl mb-xl">
            <div className="flex flex-col md:flex-row gap-xl items-start">
              <div className="w-24 h-24 rounded-full bg-primary text-on-primary flex items-center justify-center text-display-sm font-bold flex-shrink-0">
                {instructor.name[0]}
              </div>
              <div className="flex-1">
                <div className="flex items-start justify-between flex-wrap gap-md">
                  <div>
                    <h1 className="text-headline-md font-headline-md text-on-surface mb-xs">{instructor.name}</h1>
                    <p className="text-body-md font-body-md text-on-surface-variant mb-md">{instructor.email}</p>
                  </div>
                  {isSelf && (
                    <Link
                      href={`/instructors/${instructor.id}/profile`}
                      className="bg-surface-container-high text-on-surface px-lg py-sm rounded-lg text-label-md font-label-md hover:bg-surface-container transition-colors flex items-center gap-xs"
                    >
                      <span className="material-symbols-outlined text-[18px]">edit</span>
                      프로필 수정
                    </Link>
                  )}
                </div>
                <div className="flex flex-wrap gap-xs mb-lg">
                  {instructor.expertise.map((tag) => (
                    <span key={tag} className="bg-primary-fixed text-primary px-sm py-1 rounded-full text-label-sm font-label-sm">{tag}</span>
                  ))}
                </div>
                <p className="text-body-md font-body-md text-on-surface-variant leading-relaxed mb-lg">{instructor.bio}</p>
                {/* 통계 */}
                <div className="flex flex-wrap gap-xl">
                  <div className="text-center">
                    <p className="text-headline-md font-headline-md text-primary">{instructor.courseCount}</p>
                    <p className="text-label-sm font-label-sm text-on-surface-variant">강좌</p>
                  </div>
                  <div className="text-center">
                    <p className="text-headline-md font-headline-md text-primary">{instructor.studentCount.toLocaleString()}</p>
                    <p className="text-label-sm font-label-sm text-on-surface-variant">수강생</p>
                  </div>
                  <div className="text-center">
                    <p className="text-headline-md font-headline-md text-tertiary">{instructor.rating}</p>
                    <p className="text-label-sm font-label-sm text-on-surface-variant">평점</p>
                  </div>
                </div>
              </div>
            </div>
          </div>

          {/* 강좌 목록 */}
          <div>
            <h2 className="text-headline-sm font-headline-md text-on-surface mb-lg">{instructor.name} 강사의 강좌</h2>
            {courses.length === 0 ? (
              <div className="text-center py-xxl text-on-surface-variant">
                <span className="material-symbols-outlined text-[48px] mb-md block">book</span>
                <p>아직 등록된 강좌가 없습니다.</p>
              </div>
            ) : (
              <div className="grid grid-cols-1 sm:grid-cols-2 gap-lg">
                {courses.map((course) => (
                  <Link
                    key={course.id}
                    href={`/courses/${course.id}`}
                    className="bg-surface-container-lowest border border-outline-variant rounded-xl overflow-hidden hover:shadow-md hover:border-primary/30 transition-all group"
                  >
                    <img src={course.thumbnail} alt={course.title} className="w-full h-40 object-cover" />
                    <div className="p-lg">
                      <div className="flex items-center gap-xs mb-sm">
                        <span className="bg-primary-fixed text-primary px-sm py-0.5 rounded-full text-label-sm font-label-sm">{course.category}</span>
                        <span className="bg-surface-container text-on-surface-variant px-sm py-0.5 rounded-full text-label-sm font-label-sm">{course.level}</span>
                      </div>
                      <h3 className="text-label-md font-label-md text-on-surface group-hover:text-primary transition-colors line-clamp-2 mb-md">{course.title}</h3>
                      <div className="flex items-center gap-md text-label-sm font-label-sm text-on-surface-variant">
                        <span className="flex items-center gap-xs">
                          <span className="material-symbols-outlined text-[14px]">group</span>
                          {course.enrollmentCount.toLocaleString()}명
                        </span>
                        <span className="flex items-center gap-xs text-tertiary">
                          <span className="material-symbols-outlined text-[14px]" style={{ fontVariationSettings: "'FILL' 1" }}>star</span>
                          {course.rating}
                        </span>
                      </div>
                    </div>
                  </Link>
                ))}
              </div>
            )}
          </div>

        </div>
      </main>
      <Footer />
    </>
  );
}
