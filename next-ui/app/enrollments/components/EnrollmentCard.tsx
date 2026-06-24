import Link from 'next/link';
import { ProgressBar } from '@/components/ui';
import { getImageUrl } from '@/lib/imageUrl';

interface EnrollmentCardProps {
  enrollmentId: number;
  courseId: string;
  courseTitle: string;
  instructor: string;
  thumbnail: string;
  progress: number;
  lastAccessedAt: string;
  status: 'ACTIVE' | 'COMPLETED';
}

const STATUS_LABEL: Record<string, string> = {
  ACTIVE: '진행 중',
  COMPLETED: '수강 완료',
};

const STATUS_CLASS: Record<string, string> = {
  ACTIVE: 'bg-primary-container text-on-primary-container',
  COMPLETED: 'bg-secondary-container text-on-secondary-container',
};

export function EnrollmentCard({
  enrollmentId,
  courseId,
  courseTitle,
  instructor,
  thumbnail,
  progress,
  lastAccessedAt,
  status,
}: EnrollmentCardProps) {
  return (
    <Link
      href={`/enrollments/${enrollmentId}`}
      className="group block bg-surface-container-lowest border border-outline-variant rounded-xl overflow-hidden transition-all duration-300 hover:shadow-md hover:-translate-y-1"
    >
      <div className="aspect-video relative overflow-hidden bg-surface-container">
        <img
          className="w-full h-full object-cover transition-transform duration-500 group-hover:scale-105"
          alt={courseTitle}
          src={getImageUrl(thumbnail)}
        />
        <span className={[
          'absolute top-md left-md px-sm py-xs rounded text-label-sm font-label-sm shadow-sm',
          STATUS_CLASS[status],
        ].join(' ')}>
          {STATUS_LABEL[status]}
        </span>
      </div>
      <div className="p-lg">
        <h3 className="text-headline-sm font-bold text-on-surface line-clamp-1 group-hover:text-primary transition-colors mb-xs">
          {courseTitle}
        </h3>
        <p className="text-body-sm font-body-sm text-on-surface-variant">{instructor} 강사</p>
        <div className="mt-md">
          <div className="flex justify-between items-center mb-xs">
            <span className="text-label-sm font-label-sm text-on-surface-variant">학습 진도율</span>
            <span className="text-label-sm font-label-sm text-primary font-bold">{progress}%</span>
          </div>
          <ProgressBar value={progress} />
        </div>
        <p className="text-label-sm font-label-sm text-on-surface-variant mt-sm">마지막 접속: {lastAccessedAt}</p>
      </div>
    </Link>
  );
}
