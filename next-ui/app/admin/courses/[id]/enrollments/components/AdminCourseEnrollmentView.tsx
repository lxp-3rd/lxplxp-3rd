import { BackLink } from '@/components/ui';
import type { AdminCourseEnrollmentDetail } from '../../../../types';

export function AdminCourseEnrollmentView({ detail }: { detail: AdminCourseEnrollmentDetail }) {
  const { course, enrollments } = detail;

  return (
    <div className="flex-grow w-full max-w-[1280px] mx-auto px-margin-mobile md:px-margin-desktop py-xl">
      <div className="mb-lg">
        <BackLink href="/admin/courses">강좌 관리로 돌아가기</BackLink>
      </div>

      <section className="bg-surface-container-lowest border border-surface-variant rounded-xl p-lg md:p-xl shadow-sm mb-xl flex flex-col md:flex-row gap-lg items-start md:items-center">
        <div className="w-full md:w-48 h-32 rounded-lg overflow-hidden bg-surface-container flex-shrink-0">
          <img className="w-full h-full object-cover" src={course.thumbnail} alt={course.title} />
        </div>
        <div className="flex-grow">
          <h1 className="font-headline-md text-headline-md text-on-surface mb-sm">{course.title}</h1>
          <div className="flex flex-wrap items-center gap-md text-on-surface-variant">
            <div className="flex items-center gap-xs">
              <span className="material-symbols-outlined text-md">person</span>
              <span className="font-body-md text-body-md">{course.instructor} 강사</span>
            </div>
            <div className="w-1 h-1 bg-outline-variant rounded-full" />
            <div className="flex items-center gap-xs">
              <span className="material-symbols-outlined text-md">group</span>
              <span className="font-body-md text-body-md">
                총 <span className="font-bold text-primary">{course.enrollmentCount.toLocaleString()}</span>명 수강 중
              </span>
            </div>
          </div>
        </div>
        <span className="bg-secondary-container text-on-secondary-container px-md py-xs rounded-full font-label-md text-label-md text-center">
          Active Course
        </span>
      </section>

      <div className="bg-surface-container-lowest border border-surface-variant rounded-xl overflow-hidden shadow-sm">
        <div className="px-lg py-md border-b border-surface-variant bg-surface-container-low flex justify-between items-center">
          <h2 className="font-headline-md text-headline-md text-on-surface">수강생 목록</h2>
          <div className="text-on-surface-variant font-label-sm text-label-sm">Total {enrollments.length} Students</div>
        </div>
        <div className="overflow-x-auto">
          <table className="w-full text-left border-collapse">
            <thead>
              <tr className="bg-surface-container-low border-b border-surface-variant">
                <th className="px-lg py-md font-label-md text-label-md text-on-surface-variant">Nickname</th>
                <th className="px-lg py-md font-label-md text-label-md text-on-surface-variant">Email</th>
                <th className="px-lg py-md font-label-md text-label-md text-on-surface-variant">Enrollment Date</th>
                <th className="px-lg py-md font-label-md text-label-md text-on-surface-variant">Progress</th>
                <th className="px-lg py-md font-label-md text-label-md text-on-surface-variant text-right">Action</th>
              </tr>
            </thead>
            <tbody className="divide-y divide-surface-variant">
              {enrollments.map((enrollment) => (
                <tr key={enrollment.id} className="hover:bg-surface-bright transition-colors group">
                  <td className="px-lg py-md font-body-md text-body-md text-on-surface font-medium">{enrollment.nickname}</td>
                  <td className="px-lg py-md font-body-md text-body-md text-on-surface-variant">{enrollment.email}</td>
                  <td className="px-lg py-md font-body-md text-body-md text-on-surface-variant">{enrollment.enrolledAt}</td>
                  <td className="px-lg py-md">
                    <div className="flex items-center gap-sm">
                      <div className="w-24 h-2 bg-surface-container rounded-full overflow-hidden">
                        <div className="h-full bg-primary-container" style={{ width: `${enrollment.progress}%` }} />
                      </div>
                      <span className="font-label-sm text-label-sm text-on-surface font-bold">{enrollment.progress}%</span>
                    </div>
                  </td>
                  <td className="px-lg py-md text-right">
                    <button className="px-md py-1.5 rounded border border-error text-error font-label-md text-label-md hover:bg-error-container/10 transition-all active:scale-95">
                      수강 취소
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
}
