import type { CurriculumItem } from '@/app/courses/types';

interface CourseCurriculumProps {
  curriculum: CurriculumItem[];
}

export function CourseCurriculum({ curriculum }: CourseCurriculumProps) {
  return (
    <section className="max-w-[800px] mb-xl">
      <h2 className="text-headline-md font-headline-md text-on-surface mb-lg">커리큘럼</h2>
      <div className="border border-surface-container-highest rounded-xl overflow-hidden bg-surface-container-lowest shadow-sm">
        {curriculum.length === 0 ? (
          <div className="p-xl text-center text-on-surface-variant text-body-md font-body-md">
            커리큘럼 준비 중입니다.
          </div>
        ) : (
          curriculum.map((item, idx) => (
            <div
              key={item.id}
              className={`flex items-center gap-md px-lg py-md${idx < curriculum.length - 1 ? ' border-b border-surface-container-highest' : ''}`}
            >
              <span className="text-label-sm font-label-sm text-on-surface-variant w-6 text-right flex-shrink-0">
                {String(item.order).padStart(2, '0')}
              </span>
              <span className="material-symbols-outlined text-on-surface-variant text-[20px]">play_circle</span>
              <span className="text-body-md font-body-md text-on-surface flex-1">{item.title}</span>
            </div>
          ))
        )}
      </div>
    </section>
  );
}
