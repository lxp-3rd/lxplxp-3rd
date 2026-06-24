import Link from 'next/link';
import { Button } from '@/components/ui';

interface EnrollCardProps {
  courseId: string;
}

export function EnrollCard({ courseId }: EnrollCardProps) {
  return (
    <div className="bg-surface-container-lowest border border-surface-container-highest rounded-xl p-lg shadow-sm">
      <Button fullWidth className="mb-sm">
        <span className="material-symbols-outlined text-[20px]">school</span>
        수강 신청하기
      </Button>
      <p className="text-center text-label-sm font-label-sm text-on-surface-variant mb-md">
        무제한 수강 및 수료증 발급 포함
      </p>
      <hr className="mb-md" />
      <Link href={`/courses/${courseId}/questions/new`}>
        <Button fullWidth variant="ghost">
          <span className="material-symbols-outlined text-[20px]">question_answer</span>
          강사님께 질문하기
        </Button>
      </Link>
    </div>
  );
}
