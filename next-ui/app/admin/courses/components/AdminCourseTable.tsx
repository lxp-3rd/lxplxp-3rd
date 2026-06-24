import Link from 'next/link';
import { AdminIconButton } from '../../components/AdminIconButton';
import { AdminTableFooter } from '../../components/AdminTableFooter';
import { getImageUrl } from '@/lib/imageUrl';
import type { AdminCourseResponse } from '../types';
import { getAdminCourseStatusBadge } from '../viewModel';

interface AdminCourseTableProps {
  courses: AdminCourseResponse[];
  onToggleVisibility: (id: number) => void;
  onRemove: (id: number) => void;
}

export function AdminCourseTable({ courses, onToggleVisibility, onRemove }: AdminCourseTableProps) {
  return (
    <div className="bg-surface-container-lowest border border-outline-variant rounded-xl overflow-hidden">
      <div className="overflow-x-auto">
        <table className="w-full text-left border-collapse">
          <colgroup>
            <col className="w-[44%]" />
            <col className="w-[140px]" />
            <col className="w-[110px]" />
            <col className="w-[100px]" />
            <col className="w-[130px]" />
          </colgroup>
          <thead className="bg-surface-container-low border-b border-outline-variant">
            <tr>
              <th className="px-xl py-lg text-label-md font-label-md text-on-surface-variant uppercase tracking-wider">강좌명</th>
              <th className="px-xl py-lg text-label-md font-label-md text-on-surface-variant uppercase tracking-wider">강사</th>
              <th className="px-xl py-lg text-label-md font-label-md text-on-surface-variant uppercase tracking-wider text-center">수강생</th>
              <th className="px-xl py-lg text-label-md font-label-md text-on-surface-variant uppercase tracking-wider text-center">상태</th>
              <th className="px-xl py-lg text-label-md font-label-md text-on-surface-variant uppercase tracking-wider text-right">관리</th>
            </tr>
          </thead>
          <tbody className="divide-y divide-outline-variant">
            {courses.map((course) => {
              const statusInfo = getAdminCourseStatusBadge(course.status);
              const isHidden = course.status !== 'PUBLIC';
              return (
                <tr key={course.id} className={`hover:bg-surface-container-low transition-colors ${isHidden ? 'opacity-50' : ''}`}>
                  <td className="px-xl py-lg">
                    <div className="flex items-center gap-md">
                      {course.thumbnailUrl ? (
                        <img src={getImageUrl(course.thumbnailUrl)} alt={course.title} className="w-16 h-11 object-cover rounded-lg flex-shrink-0" />
                      ) : (
                        <div className="w-16 h-11 rounded-lg flex-shrink-0 bg-surface-container-high flex items-center justify-center">
                          <span className="material-symbols-outlined text-[20px] text-on-surface-variant">image</span>
                        </div>
                      )}
                      <div className="min-w-0">
                        <p className="text-body-md font-body-md text-on-surface line-clamp-1">{course.title}</p>
                      </div>
                    </div>
                  </td>
                  <td className="px-xl py-lg text-body-md font-body-md text-on-surface-variant whitespace-nowrap">{course.instructorName ?? '-'}</td>
                  <td className="px-xl py-lg text-body-md font-body-md text-on-surface text-center">{course.enrollmentCount.toLocaleString()}명</td>
                  <td className="px-xl py-lg text-center">
                    <span className={`px-sm py-1 rounded-full text-label-sm font-label-sm whitespace-nowrap ${statusInfo.cls}`}>
                      {statusInfo.label}
                    </span>
                  </td>
                  <td className="px-xl py-lg">
                    <div className="flex items-center justify-end gap-xs">
                      <Link
                        href={`/courses/${course.id}/edit`}
                        className="p-xs rounded-full hover:bg-surface-container transition-colors text-on-surface-variant hover:text-primary"
                        title="수정"
                      >
                        <span className="material-symbols-outlined text-[20px]">edit</span>
                      </Link>
                      <Link
                        href={`/courses/${course.id}/stats`}
                        className="p-xs rounded-full hover:bg-surface-container transition-colors text-on-surface-variant hover:text-primary"
                        title="통계"
                      >
                        <span className="material-symbols-outlined text-[20px]">bar_chart</span>
                      </Link>
                      <AdminIconButton
                        icon={isHidden ? 'visibility_off' : 'visibility'}
                        label={isHidden ? '공개' : '숨김'}
                        onClick={() => onToggleVisibility(course.id)}
                      />
                      <AdminIconButton
                        icon="delete"
                        label="삭제"
                        danger
                        onClick={() => onRemove(course.id)}
                      />
                    </div>
                  </td>
                </tr>
              );
            })}
          </tbody>
        </table>
      </div>
      <AdminTableFooter total={courses.length} visible={courses.length} />
    </div>
  );
}
