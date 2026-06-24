import Link from 'next/link';
import { BackLink } from '@/components/ui';
import type { AdminInstructorDetail } from '../../../types';
import { formatAdminHandle } from '../../../utils';

export function AdminInstructorDetailView({ detail }: { detail: AdminInstructorDetail }) {
  const { instructor, courses } = detail;

  return (
    <div className="max-w-[1000px] mx-auto px-gutter pt-xl pb-xxl">
      <div className="mb-lg">
        <BackLink href="/admin/instructors">강사 목록</BackLink>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-3 gap-xl">
        <div className="space-y-lg">
          <div className="bg-surface-container-lowest border border-outline-variant rounded-xl p-lg">
            <div className="w-20 h-20 rounded-full bg-primary-fixed text-primary flex items-center justify-center mb-md">
              <span className="material-symbols-outlined text-[40px]">school</span>
            </div>
            <h1 className="font-headline-md text-headline-md text-on-surface">{instructor.name}</h1>
            <p className="text-body-md text-on-surface-variant">@{formatAdminHandle(instructor.nickname)}</p>
            <p className="mt-md text-body-md text-on-surface-variant">{instructor.bio}</p>
          </div>

          <div className="bg-surface-container-lowest border border-outline-variant rounded-xl p-lg">
            <h2 className="text-title-md font-title-md mb-md">전문 분야</h2>
            <div className="flex flex-wrap gap-xs">
              {instructor.expertise.map((item) => (
                <span key={item} className="px-sm py-xs rounded-full bg-secondary-container text-on-secondary-container text-label-sm">
                  {item}
                </span>
              ))}
            </div>
          </div>
        </div>

        <div className="md:col-span-2 space-y-lg">
          <div className="grid grid-cols-1 sm:grid-cols-3 gap-md">
            <SummaryCard label="강좌 수" value={`${instructor.courseCount}개`} />
            <SummaryCard label="누적 수강생" value={`${instructor.studentCount.toLocaleString()}명`} />
            <SummaryCard label="계정 상태" value={instructor.isActive ? '활성' : '비활성'} />
          </div>

          <div className="bg-surface-container-lowest border border-outline-variant rounded-xl overflow-hidden">
            <div className="px-lg py-md border-b border-outline-variant">
              <h2 className="text-title-md font-title-md">담당 강좌</h2>
            </div>
            <div className="divide-y divide-outline-variant">
              {courses.map((course) => (
                <Link
                  key={course.id}
                  href={`/courses/${course.id}`}
                  className="flex items-center justify-between px-lg py-md hover:bg-surface-container-low transition-colors"
                >
                  <div>
                    <p className="text-body-md text-on-surface">{course.title}</p>
                    <p className="text-label-sm text-on-surface-variant">{course.category}</p>
                  </div>
                  <span className="material-symbols-outlined text-on-surface-variant">chevron_right</span>
                </Link>
              ))}
              {courses.length === 0 && (
                <p className="px-lg py-xl text-center text-on-surface-variant">담당 강좌가 없습니다.</p>
              )}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

function SummaryCard({ label, value }: { label: string; value: string }) {
  return (
    <div className="bg-surface-container-lowest border border-outline-variant rounded-xl p-lg">
      <p className="text-label-md text-on-surface-variant mb-xs">{label}</p>
      <p className="text-title-lg font-title-lg text-on-surface">{value}</p>
    </div>
  );
}
