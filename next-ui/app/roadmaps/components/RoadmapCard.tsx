import Link from 'next/link';
import { useRouter } from 'next/navigation';
import { ProgressBar } from '@/components/ui';
import type { Roadmap } from '../types';

interface RoadmapCardProps {
  data: Roadmap;
  showActions?: boolean; // 내가 만든 로드맵 탭에서만 수정/삭제 표시
}

export function RoadmapCard({ data, showActions = false }: RoadmapCardProps) {
  const router = useRouter();

  return (
    <div className="group relative bg-surface-container-lowest border border-surface-container-high rounded-xl overflow-hidden card-hover-effect transition-all duration-300 flex flex-col">
      <Link href={`/roadmaps/${data.id}`} className="absolute inset-0 z-10" aria-label={data.title} />

      <div className="relative h-48">
        <img className="w-full h-full object-cover" alt={data.title} src={data.thumbnail} />
        {data.category && (
          <div className="absolute top-4 left-4 z-20">
            <span className="bg-secondary-container text-on-secondary-container px-3 py-1 rounded-full font-label-sm text-label-sm shadow-sm">
              {data.category}
            </span>
          </div>
        )}
        {data.isEnrolled && (
          <div className="absolute top-4 right-4 z-20">
            <span className="bg-primary-container text-on-primary-container px-3 py-1 rounded-full font-label-sm text-label-sm shadow-sm">
              참여 중
            </span>
          </div>
        )}
      </div>

      <div className="p-md flex-grow flex flex-col relative z-20 pointer-events-none">
        <h3 className="font-headline-md text-headline-md text-on-surface mb-sm group-hover:text-primary transition-colors line-clamp-1">
          {data.title}
        </h3>

        <div className="flex items-center gap-lg mt-auto pb-md">
          <div className="flex items-center gap-xs text-on-surface-variant">
            <span className="material-symbols-outlined text-lg">menu_book</span>
            <span className="font-label-md text-label-md">{data.courseCount}개 강좌</span>
          </div>
          <div className="flex items-center gap-xs text-on-surface-variant">
            <span className="material-symbols-outlined text-lg">group</span>
            <span className="font-label-md text-label-md">{data.enrollmentCount.toLocaleString()}명 참여</span>
          </div>
        </div>

        {data.isEnrolled && (
          <div className="pt-md border-t border-surface-container">
            <div className="flex justify-between items-center mb-xs">
              <span className="text-label-sm font-label-sm text-on-surface-variant">진행률</span>
              <span className="text-label-sm font-label-sm text-primary font-bold">{data.progress}%</span>
            </div>
            <ProgressBar value={data.progress} />
          </div>
        )}

        {showActions && (
          <div className="flex items-center justify-between pt-md border-t border-surface-container pointer-events-auto">
            <button
              onClick={() => router.push(`/roadmaps/${data.id}/edit`)}
              className="font-label-md text-label-md text-primary hover:underline flex items-center gap-xs"
            >
              콘텐츠 수정
              <span className="material-symbols-outlined text-sm">chevron_right</span>
            </button>
            <button
              className="p-2 text-error hover:bg-error-container rounded-lg transition-colors"
              title="로드맵 삭제"
            >
              <span className="material-symbols-outlined">delete</span>
            </button>
          </div>
        )}
      </div>
    </div>
  );
}
