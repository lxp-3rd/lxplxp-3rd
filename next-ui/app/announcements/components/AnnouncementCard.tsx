import Link from 'next/link';
import type { Announcement } from '../types';

interface AnnouncementCardProps {
  data: Announcement;
  isNew?: boolean;
}

export function AnnouncementCard({ data, isNew }: AnnouncementCardProps) {
  return (
    <div className="bg-surface-container-lowest border border-outline-variant rounded-xl p-xl flex flex-col justify-between relative overflow-hidden group hover:shadow-lg transition-all duration-300">
      <div className="relative z-10">
        <span className="bg-primary-container text-on-primary-container px-sm py-1 rounded-full text-label-sm font-label-sm mb-md inline-block">
          {data.category}
        </span>
        <h2 className="text-headline-md font-headline-md mb-md group-hover:text-primary transition-colors">
          {data.title}
        </h2>
        <p className="text-on-surface-variant font-body-md text-body-md max-w-2xl line-clamp-2">
          {data.content}
        </p>
      </div>
      <div className="mt-xl flex items-center justify-between relative z-10">
        <div className="flex items-center space-x-md">
          <span className="text-on-surface-variant font-label-md text-label-md">{data.authorName}</span>
          <span className="w-1 h-1 bg-outline-variant rounded-full" />
          <span className="text-on-surface-variant font-label-md text-label-md">{data.createdAt}</span>
          {isNew && (
            <span className="bg-primary-container/20 text-primary text-[10px] px-1.5 py-0.5 rounded font-bold">
              NEW
            </span>
          )}
        </div>
        <Link
          href={`/announcements/${data.id}`}
          className="bg-primary text-on-primary px-lg py-sm rounded-lg font-label-md text-label-md hover:opacity-90 transition-opacity"
        >
          자세히 보기
        </Link>
      </div>
      <div className="absolute -right-16 -bottom-16 opacity-5 group-hover:opacity-10 transition-opacity">
        <span className="material-symbols-outlined text-primary" style={{ fontSize: '240px' }}>campaign</span>
      </div>
    </div>
  );
}
