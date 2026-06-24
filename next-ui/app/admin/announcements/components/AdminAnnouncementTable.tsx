import Link from 'next/link';
import type { AdminAnnouncement } from '../../types';

export function AdminAnnouncementTable({ announcements }: { announcements: AdminAnnouncement[] }) {
  return (
    <div className="bg-surface-container-lowest border border-outline-variant rounded-xl overflow-hidden">
      <div className="overflow-x-auto">
        <table className="w-full text-left border-collapse">
          <thead>
            <tr className="bg-surface-container-low border-b border-outline-variant">
              <th className="px-lg py-md font-label-md text-label-md text-on-surface-variant w-16">No.</th>
              <th className="px-lg py-md font-label-md text-label-md text-on-surface-variant">제목</th>
              <th className="px-lg py-md font-label-md text-label-md text-on-surface-variant w-32">작성자</th>
              <th className="px-lg py-md font-label-md text-label-md text-on-surface-variant w-36">작성일</th>
              <th className="px-lg py-md font-label-md text-label-md text-on-surface-variant w-28 text-right">관리</th>
            </tr>
          </thead>
          <tbody className="divide-y divide-outline-variant">
            {announcements.map((announcement, index) => (
              <tr key={announcement.id} className="hover:bg-surface-container-low transition-colors cursor-pointer group">
                <td className="px-lg py-md font-body-md text-body-md text-on-surface-variant">
                  {announcements.length - index}
                </td>
                <td className="px-lg py-md">
                  <Link
                    href={`/admin/announcements/${announcement.id}`}
                    className="font-body-md text-body-md text-on-surface group-hover:text-primary transition-colors"
                  >
                    {announcement.title}
                  </Link>
                </td>
                <td className="px-lg py-md font-body-md text-body-md text-on-surface-variant">{announcement.authorName}</td>
                <td className="px-lg py-md font-body-md text-body-md text-on-surface-variant">{announcement.createdAt}</td>
                <td className="px-lg py-md text-right">
                  <div className="flex items-center justify-end gap-xs">
                    <Link
                      href={`/admin/announcements/${announcement.id}`}
                      className="p-xs rounded hover:bg-surface-container text-on-surface-variant hover:text-primary transition-colors"
                      title="보기"
                    >
                      <span className="material-symbols-outlined text-[18px]">visibility</span>
                    </Link>
                    <Link
                      href={`/admin/announcements/${announcement.id}/edit`}
                      className="p-xs rounded hover:bg-surface-container text-on-surface-variant hover:text-primary transition-colors"
                      title="수정"
                    >
                      <span className="material-symbols-outlined text-[18px]">edit</span>
                    </Link>
                  </div>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}
