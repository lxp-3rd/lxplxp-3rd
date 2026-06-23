'use client';

import Link from 'next/link';
import { MOCK_INSTRUCTORS } from '@/app/instructors/mockData';
import { MOCK_COURSES } from '@/app/courses/mockData';

export default function InstructorDetailPage() {
  const instructor = MOCK_INSTRUCTORS[0];
  const courses = MOCK_COURSES.filter((c) => c.instructorId === instructor.id);

  return (
    <div className="max-w-[1000px] mx-auto px-gutter pt-xl pb-xxl">

          {/* 뒤로가기 */}
          <div className="mb-lg">
            <Link href="/admin/instructors" className="flex items-center gap-xs text-on-surface-variant hover:text-primary transition-colors">
              <span className="material-symbols-outlined text-[20px]">arrow_back</span>
              <span className="text-label-md font-label-md">강사 목록</span>
            </Link>
          </div>

          <div className="grid grid-cols-1 md:grid-cols-3 gap-xl">

            {/* 왼쪽: 프로필 카드 */}
            <div className="space-y-lg">
              <div className="bg-surface-container-lowest border border-outline-variant rounded-xl p-xl">
                <div className="flex flex-col items-center text-center mb-lg">
                  <div className="w-20 h-20 rounded-full bg-primary text-on-primary flex items-center justify-center text-headline-md font-bold mb-md">
                    {instructor.name[0]}
                  </div>
                  <h1 className="text-headline-sm font-headline-md text-on-surface">{instructor.name}</h1>
                  <p className="text-body-sm font-body-md text-on-surface-variant">{instructor.nickname}</p>
                  <span className={`mt-sm px-sm py-1 rounded-full text-label-sm font-label-sm ${
                    instructor.isActive
                      ? 'bg-secondary-container text-on-secondary-container'
                      : 'bg-surface-container-high text-on-surface-variant'
                  }`}>
                    {instructor.isActive ? '활성' : '비활성'}
                  </span>

                  {/* 강사 소개 */}
                  {instructor.bio && (
                    <p className="mt-md text-body-sm font-body-sm text-on-surface-variant leading-relaxed text-left w-full">
                      {instructor.bio}
                    </p>
                  )}
                </div>

                <div className="border-t border-outline-variant pt-lg space-y-sm">
                  <div className="flex justify-between items-center">
                    <span className="text-label-sm font-label-sm text-on-surface-variant">역할</span>
                    <span className="text-label-sm font-label-sm text-on-surface">강사</span>
                  </div>
                  <div className="flex justify-between items-center">
                    <span className="text-label-sm font-label-sm text-on-surface-variant">개설 강좌</span>
                    <span className="text-label-sm font-label-sm text-on-surface">{instructor.courseCount}개</span>
                  </div>
                  <div className="flex justify-between items-center">
                    <span className="text-label-sm font-label-sm text-on-surface-variant">총 수강생</span>
                    <span className="text-label-sm font-label-sm text-on-surface">{instructor.studentCount.toLocaleString()}명</span>
                  </div>
                </div>
              </div>

              {/* 관리 액션 */}
              <div className="bg-surface-container-lowest border border-outline-variant rounded-xl p-lg space-y-sm">
                <h2 className="text-label-md font-label-md text-on-surface-variant uppercase tracking-wider mb-md">계정 관리</h2>
                <button type="button" className="w-full border border-outline-variant text-on-surface-variant py-sm rounded-lg text-label-sm font-label-sm hover:bg-surface-container transition-colors">
                  비밀번호 초기화 이메일 발송
                </button>
                <button type="button" className="w-full border border-error text-error py-sm rounded-lg text-label-sm font-label-sm hover:bg-error-container/30 transition-colors">
                  {instructor.isActive ? '계정 비활성화' : '계정 활성화'}
                </button>
              </div>
            </div>

            {/* 오른쪽: 개설 강좌 내역 */}
            <div className="md:col-span-2">
              <h2 className="text-headline-sm font-headline-md text-on-surface mb-lg">개설 강좌 내역</h2>
              {courses.length === 0 ? (
                <div className="text-center py-xxl text-on-surface-variant">
                  <span className="material-symbols-outlined text-[48px] mb-md block">book</span>
                  <p>아직 등록된 강좌가 없습니다.</p>
                </div>
              ) : (
                <div className="space-y-md">
                  {courses.map((course) => (
                    <div key={course.id} className="bg-surface-container-lowest border border-outline-variant rounded-xl p-lg flex items-center gap-lg">
                      <img src={course.thumbnail} alt={course.title} className="w-20 h-14 object-cover rounded-lg flex-shrink-0" />
                      <div className="flex-1 min-w-0">
                        <p className="text-label-md font-label-md text-on-surface mb-xs truncate">{course.title}</p>
                        <p className="text-label-sm font-label-sm text-on-surface-variant mb-md">
                          {course.enrollmentCount.toLocaleString()}명 수강 중
                        </p>
                      </div>
                      <span className={`flex-shrink-0 px-sm py-1 rounded-full text-label-sm font-label-sm ${
                        course.status === 'PUBLISHED'
                          ? 'bg-secondary-container text-on-secondary-container'
                          : 'bg-surface-container-high text-on-surface-variant'
                      }`}>
                        {course.status === 'PUBLISHED' ? '공개' : '비공개'}
                      </span>
                    </div>
                  ))}
                </div>
              )}
            </div>

          </div>

    </div>
  );
}
