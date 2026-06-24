import { AdminIconButton } from '../../components/AdminIconButton';
import { AdminTableFooter } from '../../components/AdminTableFooter';
import type { AdminRoadmap } from '../../types';

interface AdminRoadmapTableProps {
  roadmaps: AdminRoadmap[];
  onToggleHidden: (id: string) => void;
  onRemove: (id: string) => void;
}

export function AdminRoadmapTable({ roadmaps, onToggleHidden, onRemove }: AdminRoadmapTableProps) {
  return (
    <div className="bg-surface-container-lowest border border-outline-variant rounded-xl overflow-hidden">
      <div className="overflow-x-auto">
        <table className="w-full text-left border-collapse">
          <colgroup>
            <col className="w-[40%]" />
            <col className="w-[120px]" />
            <col className="w-[80px]" />
            <col className="w-[100px]" />
            <col className="w-[120px]" />
            <col className="w-[80px]" />
          </colgroup>
          <thead className="bg-surface-container-low border-b border-outline-variant">
            <tr>
              <th className="px-xl py-lg text-label-md font-label-md text-on-surface-variant uppercase tracking-wider">로드맵명</th>
              <th className="px-xl py-lg text-label-md font-label-md text-on-surface-variant uppercase tracking-wider">카테고리</th>
              <th className="px-xl py-lg text-label-md font-label-md text-on-surface-variant uppercase tracking-wider text-center">강좌 수</th>
              <th className="px-xl py-lg text-label-md font-label-md text-on-surface-variant uppercase tracking-wider text-center">참여자</th>
              <th className="px-xl py-lg text-label-md font-label-md text-on-surface-variant uppercase tracking-wider">제작자</th>
              <th className="px-xl py-lg text-label-md font-label-md text-on-surface-variant uppercase tracking-wider text-right">관리</th>
            </tr>
          </thead>
          <tbody className="divide-y divide-outline-variant">
            {roadmaps.map((roadmap) => (
              <tr key={roadmap.id} className={`hover:bg-surface-container-low transition-colors ${roadmap.hidden ? 'opacity-50' : ''}`}>
                <td className="px-xl py-lg">
                  <p className="text-body-md font-body-md text-on-surface line-clamp-1">{roadmap.title}</p>
                  {roadmap.hidden && (
                    <span className="text-label-sm font-label-sm text-on-surface-variant">(숨김)</span>
                  )}
                </td>
                <td className="px-xl py-lg">
                  <span className="px-sm py-xs rounded-full bg-secondary-container text-on-secondary-container text-label-sm font-label-sm whitespace-nowrap">
                    {roadmap.category}
                  </span>
                </td>
                <td className="px-xl py-lg text-body-md font-body-md text-on-surface text-center">{roadmap.courseCount}개</td>
                <td className="px-xl py-lg text-body-md font-body-md text-on-surface text-center">{roadmap.enrollmentCount.toLocaleString()}명</td>
                <td className="px-xl py-lg text-body-md font-body-md text-on-surface-variant">{roadmap.creatorName}</td>
                <td className="px-xl py-lg">
                  <div className="flex items-center justify-end gap-sm">
                    <AdminIconButton
                      icon={roadmap.hidden ? 'visibility_off' : 'visibility'}
                      label={roadmap.hidden ? '공개' : '숨김'}
                      onClick={() => onToggleHidden(roadmap.id)}
                    />
                    <AdminIconButton
                      icon="delete"
                      label="삭제"
                      danger
                      onClick={() => onRemove(roadmap.id)}
                    />
                  </div>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
      <AdminTableFooter total={roadmaps.length} visible={roadmaps.length} />
    </div>
  );
}
