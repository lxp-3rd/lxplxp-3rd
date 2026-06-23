'use client';

import Link from 'next/link';
import { TopNavBar } from '@/components/TopNavBar';
import { Footer } from '@/components/Footer';
import { getMyCourses } from '@/app/courses/mockData';
import { MOCK_QUESTIONS } from '@/app/questions/mockData';
import { useAuth } from '@/lib/AuthContext';

export default function InstructorStatsPage() {
  const { user } = useAuth();
  const courses = getMyCourses(user?.id ?? 'u2');

  const totalStudents = courses.reduce((s, c) => s + c.enrollmentCount, 0);
  const totalCourses = courses.length;
  const publishedCount = courses.filter((c) => c.status === 'PUBLISHED').length;

  const totalQuestions = MOCK_QUESTIONS.length;
  const totalUnanswered = MOCK_QUESTIONS.filter((q) => !q.isAnswered).length;

  const questionCountByCourse = (courseId: string) =>
    MOCK_QUESTIONS.filter((q) => q.courseId === courseId).length;
  const unansweredCountByCourse = (courseId: string) =>
    MOCK_QUESTIONS.filter((q) => q.courseId === courseId && !q.isAnswered).length;

  return (
    <>
      <TopNavBar />
      <main className="pt-16 min-h-screen bg-background">
        <div className="max-w-[1280px] mx-auto px-gutter pt-xl pb-xxl mt-8">

          <div className="mb-xl">
            <h1 className="text-headline-lg font-headline-lg text-on-surface mb-xs">내 강의 통계</h1>
            <p className="text-body-md font-body-md text-on-surface-variant">운영 중인 강좌의 전체 성과를 확인하세요.</p>
          </div>

          {/* 요약 카드 */}
          <div className="grid grid-cols-2 lg:grid-cols-4 gap-lg mb-xl">
            {[
              { icon: 'book', label: '전체 강좌', value: `${totalCourses}개`, sub: `공개 ${publishedCount}개` },
              { icon: 'group', label: '전체 수강생', value: totalStudents.toLocaleString(), sub: '누적' },
              { icon: 'forum', label: '전체 질문 수', value: `${totalQuestions}건`, sub: '누적 등록 질문' },
              { icon: 'help', label: '미답변 질문', value: `${totalUnanswered}건`, sub: '빠른 답변이 필요해요' },
            ].map((stat) => (
              <div key={stat.label} className="bg-surface-container-lowest border border-outline-variant rounded-xl p-lg">
                <div className="flex items-center gap-sm mb-md">
                  <div className="p-sm bg-primary-fixed rounded-lg">
                    <span className="material-symbols-outlined text-primary text-[20px]">{stat.icon}</span>
                  </div>
                </div>
                <p className="text-label-md font-label-md text-on-surface-variant mb-xs">{stat.label}</p>
                <p className="text-headline-md font-headline-md text-on-surface">{stat.value}</p>
                <p className="text-label-sm font-label-sm text-on-surface-variant mt-xs">{stat.sub}</p>
              </div>
            ))}
          </div>

          {/* 강좌별 통계 테이블 */}
          <div className="bg-surface-container-lowest border border-outline-variant rounded-xl overflow-hidden">
            <div className="px-xl py-lg border-b border-outline-variant">
              <h2 className="text-headline-sm font-headline-md text-on-surface">강좌별 현황</h2>
            </div>
            <div className="overflow-x-auto">
              <table className="w-full text-left border-collapse">
                <thead className="bg-surface-container-low border-b border-outline-variant">
                  <tr>
                    <th className="px-xl py-lg text-label-md font-label-md text-on-surface-variant uppercase tracking-wider">강좌명</th>
                    <th className="px-xl py-lg text-label-md font-label-md text-on-surface-variant uppercase tracking-wider text-center">수강생</th>
                    <th className="px-xl py-lg text-label-md font-label-md text-on-surface-variant uppercase tracking-wider text-center">질문 수</th>
                    <th className="px-xl py-lg text-label-md font-label-md text-on-surface-variant uppercase tracking-wider text-center">미답변</th>
                    <th className="px-xl py-lg text-label-md font-label-md text-on-surface-variant uppercase tracking-wider text-center">상태</th>
                    <th className="px-xl py-lg text-label-md font-label-md text-on-surface-variant uppercase tracking-wider"></th>
                  </tr>
                </thead>
                <tbody className="divide-y divide-outline-variant">
                  {courses.map((course) => (
                    <tr key={course.id} className="hover:bg-surface-container/50 transition-colors group">
                      <td className="px-xl py-lg">
                        <div className="flex items-center gap-md">
                          <img src={course.thumbnail} alt={course.title} className="w-14 h-10 object-cover rounded-lg flex-shrink-0" />
                          <p className="text-body-md font-body-md text-on-surface line-clamp-1">{course.title}</p>
                        </div>
                      </td>
                      <td className="px-xl py-lg text-body-md font-body-md text-on-surface text-center">{course.enrollmentCount.toLocaleString()}</td>
                      <td className="px-xl py-lg text-body-md font-body-md text-on-surface text-center">
                        {questionCountByCourse(course.id)}건
                      </td>
                      <td className="px-xl py-lg text-center">
                        {unansweredCountByCourse(course.id) > 0 ? (
                          <span className="px-sm py-1 rounded-full text-label-sm font-label-sm bg-error-container text-on-error-container">
                            {unansweredCountByCourse(course.id)}건
                          </span>
                        ) : (
                          <span className="text-label-sm font-label-sm text-on-surface-variant">-</span>
                        )}
                      </td>
                      <td className="px-xl py-lg text-center">
                        <span className={`px-sm py-1 rounded-full text-label-sm font-label-sm ${
                          course.status === 'PUBLISHED'
                            ? 'bg-secondary-container text-on-secondary-container'
                            : 'bg-surface-container-high text-on-surface-variant'
                        }`}>
                          {course.status === 'PUBLISHED' ? '공개' : '비공개'}
                        </span>
                      </td>
                      <td className="px-xl py-lg">
                        <Link
                          href={`/courses/${course.id}/stats`}
                          className="text-primary text-label-sm font-label-sm opacity-0 group-hover:opacity-100 transition-opacity hover:underline"
                        >
                          상세 통계
                        </Link>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          </div>

        </div>
      </main>
      <Footer />
    </>
  );
}
