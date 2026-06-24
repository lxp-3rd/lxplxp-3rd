import Link from 'next/link';
import { Button } from '@/components/ui';
import type { LegacyQuestion } from '@/app/questions/types';

interface CourseQnAPreviewProps {
  courseId: string;
  questions: LegacyQuestion[];
}

export function CourseQnAPreview({ courseId, questions }: CourseQnAPreviewProps) {
  return (
    <section className="max-w-[800px] mt-xl">
      <div className="flex items-center justify-between mb-lg">
        <div className="flex items-center gap-sm">
          <h2 className="text-headline-md font-headline-md text-on-surface">Q&amp;A</h2>
          <Link
            href={`/courses/${courseId}/questions`}
            className="text-primary text-label-md font-label-md hover:underline flex items-center gap-xs"
          >
            전체 질문 보기
            <span className="material-symbols-outlined text-[16px]">chevron_right</span>
          </Link>
        </div>
        <Link href={`/courses/${courseId}/questions/new`}>
          <Button size="sm">
            <span className="material-symbols-outlined text-[18px]">edit</span>
            질문하기
          </Button>
        </Link>
      </div>
      {questions.length === 0 ? (
        <div className="bg-surface-container-lowest border border-outline-variant rounded-xl p-xl text-center">
          <span className="material-symbols-outlined text-[40px] text-on-surface-variant mb-md block">forum</span>
          <p className="text-body-md font-body-md text-on-surface-variant">아직 등록된 질문이 없습니다.</p>
          <p className="text-label-sm font-label-sm text-on-surface-variant mt-xs">궁금한 점이 있으면 질문해 보세요!</p>
        </div>
      ) : (
        <div className="border border-outline-variant rounded-xl overflow-hidden bg-surface-container-lowest">
          {questions.map((q, idx) => (
            <Link
              key={q.id}
              href={`/courses/${courseId}/questions/${q.id}`}
              className={[
                'flex items-start gap-md p-lg hover:bg-surface-container transition-colors group',
                idx < questions.length - 1 ? 'border-b border-outline-variant' : '',
              ].join(' ')}
            >
              <div className={[
                'mt-1 flex-shrink-0 w-5 h-5 rounded-full flex items-center justify-center',
                q.isAnswered ? 'bg-primary' : 'bg-outline-variant',
              ].join(' ')}>
                <span className="material-symbols-outlined text-[12px] text-white" style={{ fontVariationSettings: "'FILL' 1" }}>
                  {q.isAnswered ? 'check' : 'help'}
                </span>
              </div>
              <div className="flex-1 min-w-0">
                <p className="text-body-md font-body-md text-on-surface group-hover:text-primary transition-colors line-clamp-1">
                  {q.title}
                </p>
                <div className="flex items-center gap-md mt-xs text-label-sm font-label-sm text-on-surface-variant">
                  <span>{q.authorName}</span>
                  <span>·</span>
                  <span>{q.createdAt}</span>
                </div>
              </div>
              <span className={[
                'flex-shrink-0 px-sm py-1 rounded-full text-label-sm font-label-sm',
                q.isAnswered
                  ? 'bg-secondary-container text-on-secondary-container'
                  : 'bg-primary-fixed text-on-primary-container',
              ].join(' ')}>
                {q.isAnswered ? '답변 완료' : '답변 대기'}
              </span>
            </Link>
          ))}
        </div>
      )}
    </section>
  );
}
