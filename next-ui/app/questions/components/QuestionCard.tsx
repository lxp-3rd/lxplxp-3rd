import type { Question } from '../types';

interface QuestionCardProps {
  data: Question;
  isSelected: boolean;
  onClick: () => void;
}

export function QuestionCard({ data, isSelected, onClick }: QuestionCardProps) {
  return (
    <button
      type="button"
      onClick={onClick}
      className={[
        'text-left p-md rounded-xl border transition-all',
        isSelected
          ? 'border-primary bg-surface-container-lowest shadow-md'
          : 'border-outline-variant bg-surface-container-lowest hover:border-primary/50 hover:shadow-sm',
      ].join(' ')}
    >
      <div className="flex justify-between items-start mb-xs">
        <span className={[
          'text-label-sm font-label-sm px-sm py-0.5 rounded-full',
          data.answer
          ? 'bg-surface-container-high text-on-surface-variant'
          : 'bg-primary-fixed text-on-primary-container',
        ].join(' ')}>
          {data.answer ? '답변 완료' : '답변 대기'}
        </span>
        <span className="text-label-sm font-label-sm text-on-surface-variant">{data.createdAt}</span>
      </div>
      <h3 className={`text-body-md text-on-surface line-clamp-2 mb-xs ${isSelected ? 'font-bold' : ''}`}>
        {data.title}
      </h3>
      <div className="flex items-center gap-xs text-on-surface-variant text-label-sm font-label-sm">
        <span className="material-symbols-outlined text-[16px]">person</span>
        회원 #{data.memberId}
      </div>
    </button>
  );
}
