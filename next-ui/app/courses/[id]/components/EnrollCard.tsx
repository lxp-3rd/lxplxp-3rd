import Link from 'next/link';
import { Button } from '@/components/ui';

interface EnrollCardProps {
  courseId: string;
  enrolled: boolean;
  enrollmentCount: number;
}

export function EnrollCard({ courseId, enrolled, enrollmentCount }: EnrollCardProps) {
  return (
    <div className="bg-surface-container-lowest border border-surface-container-highest rounded-xl p-lg shadow-sm">
      {enrolled ? (
        <div className="flex items-center justify-center gap-sm mb-sm py-sm rounded-lg bg-secondary-container text-on-secondary-container">
          <span className="material-symbols-outlined text-[20px]">check_circle</span>
          <span className="text-label-md font-label-md">수강 중인 강좌입니다</span>
        </div>
      ) : (
        <Button fullWidth className="mb-sm">
          <span className="material-symbols-outlined text-[20px]">school</span>
          수강 신청하기
        </Button>
      )}
      <div className="flex items-center justify-center gap-xs text-on-surface-variant mb-md">
        <span className="material-symbols-outlined text-[16px]">groups</span>
        <span className="text-label-sm font-label-sm">
          {enrollmentCount.toLocaleString()}명 수강 중
        </span>
      </div>
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
