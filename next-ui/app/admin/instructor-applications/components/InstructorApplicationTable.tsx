import type { AdminInstructorApplication } from '../../types';
import { InstructorApplicationStatus } from './InstructorApplicationStatus';

interface InstructorApplicationTableProps {
  applications: AdminInstructorApplication[];
  onSelect: (application: AdminInstructorApplication) => void;
}

export function InstructorApplicationTable({ applications, onSelect }: InstructorApplicationTableProps) {
  return (
    <div className="bg-surface-container-lowest border border-outline-variant rounded-xl overflow-hidden">
      <div className="overflow-x-auto">
        <table className="w-full text-left border-collapse">
          <thead className="bg-surface-container-low border-b border-outline-variant">
            <tr>
              <th className="px-xl py-lg text-label-md font-label-md text-on-surface-variant uppercase tracking-wider">닉네임</th>
              <th className="px-xl py-lg text-label-md font-label-md text-on-surface-variant uppercase tracking-wider">이름</th>
              <th className="px-xl py-lg text-label-md font-label-md text-on-surface-variant uppercase tracking-wider">전문 분야</th>
              <th className="px-xl py-lg text-label-md font-label-md text-on-surface-variant uppercase tracking-wider">신청일</th>
              <th className="px-xl py-lg text-label-md font-label-md text-on-surface-variant uppercase tracking-wider text-center">상태</th>
              <th className="px-xl py-lg text-label-md font-label-md text-on-surface-variant uppercase tracking-wider text-right">관리</th>
            </tr>
          </thead>
          <tbody className="divide-y divide-outline-variant">
            {applications.map((application) => (
              <tr key={application.id} className="hover:bg-surface-container-low transition-colors">
                <td className="px-xl py-lg text-body-md text-on-surface-variant">@{application.nickname}</td>
                <td className="px-xl py-lg text-body-md text-on-surface">{application.displayName}</td>
                <td className="px-xl py-lg">
                  <span className="px-sm py-xs rounded-full bg-secondary-container text-on-secondary-container text-label-sm">
                    {application.expertise}
                  </span>
                </td>
                <td className="px-xl py-lg text-body-md text-on-surface-variant">{application.appliedAt}</td>
                <td className="px-xl py-lg">
                  <div className="flex justify-center">
                    <InstructorApplicationStatus status={application.status} />
                  </div>
                </td>
                <td className="px-xl py-lg text-right">
                  <button
                    type="button"
                    onClick={() => onSelect(application)}
                    className="text-primary text-label-sm font-label-sm hover:underline"
                  >
                    검토
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}
