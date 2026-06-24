interface CourseHeroProps {
  thumbnail: string;
  title: string;
  description: string;
  badge?: string;
}

export function CourseHero({ thumbnail, title, description, badge }: CourseHeroProps) {
  return (
    <>
      <div className="rounded-xl overflow-hidden aspect-video mb-lg shadow-sm border border-surface-container-highest relative">
        <img className="w-full h-full object-cover" src={thumbnail} alt={title} />
        {badge && (
          <div className="absolute top-md left-md">
            <span className="bg-primary text-on-primary px-md py-xs rounded-full text-label-sm font-label-sm shadow-md">
              {badge}
            </span>
          </div>
        )}
      </div>
      <h1 className="font-headline-lg text-headline-lg text-on-surface mb-lg">{title}</h1>
      <div className="bg-surface-container-lowest p-lg rounded-xl border border-surface-container-highest">
        <p className="text-headline-md font-headline-md text-primary mb-xs">강의 소개</p>
        <p className="text-body-md font-body-md text-on-surface leading-relaxed">{description}</p>
      </div>
    </>
  );
}
