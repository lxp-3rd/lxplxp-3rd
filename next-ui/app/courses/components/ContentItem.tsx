import type { Content } from '../types';
import { Button } from '@/components/ui';

const CONTENT_TYPE_ICON: Record<string, string> = {
  VIDEO:    'play_circle',
  DOCUMENT: 'description',
  QUIZ:     'quiz',
};

interface ContentItemProps {
  data: Content;
  index: number;
  isLast: boolean;
}

export function ContentItem({ data, index, isLast }: ContentItemProps) {
  return (
    <div className={[
      'flex items-center justify-between p-lg transition-colors cursor-pointer group',
      !isLast ? 'border-b border-surface-container-highest' : '',
    ].join(' ')}>
      <div className="flex items-center gap-lg">
        <span className={[
          'text-label-md font-label-md w-6',
          index === 0 ? 'text-primary' : 'text-on-surface-variant',
        ].join(' ')}>
          {String(index + 1).padStart(2, '0')}
        </span>
        <div>
          <p className="text-label-md font-label-md text-on-surface group-hover:text-primary transition-colors">
            {data.title}
          </p>
          <p className="text-label-sm font-label-sm text-on-surface-variant flex items-center gap-xs mt-0.5">
            <span className="material-symbols-outlined text-[14px]">
              {CONTENT_TYPE_ICON[data.type] ?? 'play_circle'}
            </span>
            {data.type === 'VIDEO' && data.durationSeconds > 0
              ? `${Math.floor(data.durationSeconds / 60)}분`
              : data.type}
          </p>
        </div>
      </div>
      <Button size="sm">수강하기</Button>
    </div>
  );
}
