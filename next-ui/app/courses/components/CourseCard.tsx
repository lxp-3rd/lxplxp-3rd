import Link from 'next/link';

interface CourseCardProps {
  id: string;
  title: string;
  instructor: string;
  thumbnail: string;
  enrollmentCount: number;
}

export function CourseCard({ id, title, instructor, thumbnail, enrollmentCount }: CourseCardProps) {
  return (
    <Link href={`/courses/${id}`} className="block">
      <div className="course-card group bg-surface-container-lowest border border-outline-variant rounded-xl overflow-hidden cursor-pointer">
        <div className="aspect-video overflow-hidden">
          <img
            className="w-full h-full object-cover transition-transform duration-500 group-hover:scale-105"
            alt={`${title} 썸네일`}
            src={thumbnail}
          />
        </div>
        <div className="p-md">
          <h3 className="text-headline-sm font-headline-sm text-on-surface mb-xs line-clamp-2">{title}</h3>
          <p className="text-body-sm font-body-sm text-on-surface-variant mb-md">강사: {instructor}</p>
          <div className="flex items-center gap-xs">
            <span className="material-symbols-outlined text-primary text-[16px]">groups</span>
            <span className="text-label-sm font-label-sm text-on-surface-variant">
              {enrollmentCount.toLocaleString()}명 수강
            </span>
          </div>
        </div>
      </div>
    </Link>
  );
}
