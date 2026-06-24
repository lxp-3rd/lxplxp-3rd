import Link from 'next/link';
import { AdminStatusDot } from '../../components/AdminStatusDot';
import { AdminTableFooter } from '../../components/AdminTableFooter';
import type { AdminInstructor } from '../../types';

export function AdminInstructorTable({ instructors }: { instructors: AdminInstructor[] }) {
  return (
    <div className="bg-surface-container-lowest border border-outline-variant rounded-xl overflow-hidden">
      <div className="overflow-x-auto">
        <table className="w-full text-left border-collapse">
          <thead className="bg-surface-container-low border-b border-outline-variant">
            <tr>
              <th className="px-xl py-lg text-label-md font-label-md text-on-surface-variant uppercase tracking-wider">닉네임</th>
              <th className="px-xl py-lg text-label-md font-label-md text-on-surface-variant uppercase tracking-wider">표시 이름</th>
              <th className="px-xl py-lg text-label-md font-label-md text-on-surface-variant uppercase tracking-wider">전문 분야</th>
              <th className="px-xl py-lg text-label-md font-label-md text-on-surface-variant uppercase tracking-wider text-center">강좌 수</th>
              <th className="px-xl py-lg text-label-md font-label-md text-on-surface-variant uppercase tracking-wider text-center">상태</th>
              <th className="px-xl py-lg text-label-md font-label-md text-on-surface-variant uppercase tracking-wider" />
            </tr>
          </thead>
          <tbody className="divide-y divide-outline-variant">
            {instructors.map((instructor) => (
              <tr key={instructor.id} className="hover:bg-surface-container/50 transition-colors group">
                <td className="px-xl py-lg">
                  <span className="text-body-md font-body-md text-on-surface-variant">@{instructor.nickname.split('@')[0]}</span>
                </td>
                <td className="px-xl py-lg">
                  <span className="text-body-md font-body-md text-on-surface">{instructor.name}</span>
                </td>
                <td className="px-xl py-lg">
                  <span className="px-sm py-xs rounded-full bg-secondary-container text-on-secondary-container text-label-sm font-label-sm whitespace-nowrap">
                    {instructor.expertise[0] ?? '미지정'}
                  </span>
                </td>
                <td className="px-xl py-lg text-body-md font-body-md text-on-surface text-center">{instructor.courseCount}</td>
                <td className="px-xl py-lg text-center">
                  <AdminStatusDot active={instructor.isActive} />
                </td>
                <td className="px-xl py-lg">
                  <Link
                    href={`/admin/instructors/${instructor.id}`}
                    className="text-primary text-label-sm font-label-sm hover:underline"
                  >
                    프로필 보기
                  </Link>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
      <AdminTableFooter total={instructors.length} visible={instructors.length} unit="명" />
    </div>
  );
}
