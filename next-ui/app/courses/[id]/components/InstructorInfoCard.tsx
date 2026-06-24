interface InstructorInfoCardProps {
  name: string;
  expertise: string[];
  enrollmentCount: number;
  bio?: string;
}

export function InstructorInfoCard({ name, expertise, enrollmentCount, bio }: InstructorInfoCardProps) {
  return (
    <div className="bg-surface-container-lowest border border-surface-container-highest rounded-xl p-lg shadow-sm">
      <p className="text-label-md font-label-md text-on-surface-variant uppercase tracking-wider mb-md">강사 정보</p>
      <div className="flex items-center gap-md mb-lg">
        <div className="w-12 h-12 rounded-full bg-primary text-on-primary flex items-center justify-center font-bold text-label-md flex-shrink-0">
          {name[0]}
        </div>
        <div>
          <p className="text-label-md font-label-md text-on-surface">{name} 강사</p>
          <p className="text-label-sm font-label-sm text-on-surface-variant">
            {expertise.join(' · ')}
          </p>
        </div>
      </div>
      <div className="flex items-center gap-xs text-on-surface-variant mb-lg">
        <span className="material-symbols-outlined text-[20px]">group</span>
        <span className="text-label-md font-label-md">
          {enrollmentCount.toLocaleString()}명 수강 중
        </span>
      </div>
      {bio && (
        <>
          <hr className="mb-sm" />
          <p className="text-label-md font-label-md text-on-surface-variant uppercase tracking-wider mb-sm">강사 소개</p>
          <p className="text-body-md font-body-md text-on-surface leading-relaxed">{bio}</p>
        </>
      )}
    </div>
  );
}
