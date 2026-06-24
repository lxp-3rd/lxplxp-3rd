import Link from 'next/link';
import type { Instructor } from '../types';

interface InstructorCardProps {
  data: Instructor;
}

export function InstructorCard({ data }: InstructorCardProps) {
  return (
    <Link
      href={`/instructors/${data.id}`}
      className="bg-surface-container-lowest border border-outline-variant rounded-xl p-xl hover:shadow-md hover:border-primary/30 transition-all group"
    >
      <div className="flex items-center gap-lg mb-lg">
        <div className="w-16 h-16 rounded-full bg-primary text-on-primary flex items-center justify-center text-headline-sm font-bold flex-shrink-0">
          {data.name[0]}
        </div>
        <div>
          <h2 className="text-label-md font-label-md text-on-surface group-hover:text-primary transition-colors">{data.name}</h2>
          <p className="text-label-sm font-label-sm text-on-surface-variant">{data.nickname}</p>
        </div>
      </div>

      <div className="flex flex-wrap gap-xs mb-lg">
        {data.expertise.map((tag) => (
          <span key={tag} className="bg-primary-fixed text-primary px-sm py-0.5 rounded-full text-label-sm font-label-sm">
            {tag}
          </span>
        ))}
      </div>

      <p className="text-body-sm font-body-md text-on-surface-variant line-clamp-2 mb-lg">{data.bio}</p>

      <div className="flex items-center gap-lg border-t border-outline-variant pt-md">
        <div className="flex items-center gap-xs text-on-surface-variant">
          <span className="material-symbols-outlined text-[16px]">book</span>
          <span className="text-label-sm font-label-sm">{data.courseCount}개 강좌</span>
        </div>
        <div className="flex items-center gap-xs text-on-surface-variant">
          <span className="material-symbols-outlined text-[16px]">group</span>
          <span className="text-label-sm font-label-sm">{data.studentCount.toLocaleString()}명</span>
        </div>
        <div className="flex items-center gap-xs text-tertiary ml-auto">
          <span className="material-symbols-outlined text-[16px]" style={{ fontVariationSettings: "'FILL' 1" }}>star</span>
          <span className="text-label-sm font-label-sm font-bold">{data.rating}</span>
        </div>
      </div>
    </Link>
  );
}
