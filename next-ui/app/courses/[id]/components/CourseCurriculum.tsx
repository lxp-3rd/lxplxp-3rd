import type { Content } from '@/app/courses/types';
import { ContentItem } from '@/app/courses/components/ContentItem';

interface CourseCurriculumProps {
  contents: Content[];
}

export function CourseCurriculum({ contents }: CourseCurriculumProps) {
  return (
    <section className="max-w-[800px]">
      <h2 className="text-headline-md font-headline-md text-on-surface mb-lg">커리큘럼</h2>
      <div className="border border-surface-container-highest rounded-xl overflow-hidden bg-surface-container-lowest shadow-sm">
        {contents.map((content, idx) => (
          <ContentItem
            key={content.id}
            data={content}
            index={idx}
            isLast={idx === contents.length - 1}
          />
        ))}
        {contents.length === 0 && (
          <div className="p-xl text-center text-on-surface-variant text-body-md font-body-md">
            커리큘럼 준비 중입니다.
          </div>
        )}
      </div>
    </section>
  );
}
