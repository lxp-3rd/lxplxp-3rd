import { getImageUrl } from '@/lib/imageUrl';

interface CourseHeroProps {
  thumbnail: string;
  title: string;
  description: string;
}

export function CourseHero({ thumbnail, title, description }: CourseHeroProps) {
  return (
    <>
      <div className="rounded-xl overflow-hidden aspect-video mb-lg shadow-sm border border-surface-container-highest">
        <img className="w-full h-full object-cover" src={getImageUrl(thumbnail)} alt={title} />
      </div>
      <h1 className="font-headline-lg text-headline-lg text-on-surface mb-lg">{title}</h1>
      <div className="bg-surface-container-lowest p-lg rounded-xl border border-surface-container-highest">
        <p className="text-headline-md font-headline-md text-primary mb-xs">강의 소개</p>
        <p className="text-body-md font-body-md text-on-surface leading-relaxed">{description}</p>
      </div>
    </>
  );
}
