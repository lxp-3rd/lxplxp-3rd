'use client';

import Link from 'next/link';
import { TopNavBar } from '@/components/TopNavBar';
import { Footer } from '@/components/Footer';
import { getCourseById, MOCK_COURSES } from '@/app/courses/mockData';
import { MOCK_QUESTIONS } from '@/app/questions/mockData';

export default function CourseDetailPage({ params }: { params: { id: string } }) {
  const course = getCourseById(params.id) ?? MOCK_COURSES[0];
  const courseQuestions = MOCK_QUESTIONS.filter((q) => q.courseId === course.id);
  return (
    <>
      <TopNavBar />
      <main className="pt-16 min-h-screen bg-background">
<div className="pt-xl mt-16 max-w-container-max mx-auto px-gutter pb-xl">

<a className="inline-flex items-center gap-xs text-label-md font-label-md text-primary mb-lg hover:opacity-80 transition-opacity" href="/courses">
<span className="material-symbols-outlined text-[18px]">arrow_back</span>
            강의 목록으로 돌아가기
        </a>

<div className="grid grid-cols-1 lg:grid-cols-12 gap-xl mb-xl">

<div className="lg:col-span-8">
<div className="rounded-xl overflow-hidden aspect-video mb-lg shadow-sm border border-surface-container-highest relative">
<img className="w-full h-full object-cover" src="https://lh3.googleusercontent.com/aida-public/AB6AXuAwIqs-lINGASXiCJejLKv8vBi5eP92YRhGvwVkrkfRWiPso5J4t5Mi1DEK3ZxYpOt6uUWNeAIWGSjH3ijqeHVeO5dfZLFcjZqNVbcGkgXlyWpOVUrdo2qp4pzrLdW-bpuojHefGMpApAfrR26YdiUfCBmWtYz3cAgiN8MoRmEUtXzpW8DcoPNFLjek7pJcD5I0K9r0Qv6TueMMwdWQUJCCwn1J6jQHtMSqnLusiOm_hUaf1YC_x78NBTMdxnYdD0VRG8KIPxnxZ79r" />
<div className="absolute top-md left-md">
<span className="bg-primary text-on-primary px-md py-xs rounded-full text-label-sm font-label-sm shadow-md">Best Seller</span>
</div>
</div>
<h1 className="font-headline-lg text-headline-lg text-on-surface mb-lg">{course.title}</h1>
<div className="space-y-md">
<div className="bg-surface-container-lowest p-lg rounded-xl border border-surface-container-highest">
<p className="text-headline-md font-headline-md text-primary mb-xs">강의 소개</p>
<p className="text-body-md font-body-md text-on-surface leading-relaxed">
                            단순히 예쁜 디자인을 넘어, 데이터에 기반한 의사결정을 통해 사용자 경험을 혁신하는 방법을 배웁니다. 실제 실무 프로젝트 사례를 통해 디자인 툴 활용법부터 이해관계자 설득까지의 전 과정을 경험해보세요.
                        </p>
</div>
<div className="p-lg">
<p className="text-label-md font-label-md text-on-surface-variant uppercase tracking-wider mb-sm">상세 설명</p>
<div className="text-body-md font-body-md text-on-surface leading-relaxed space-y-md">
                            본 강의는 UX 디자인의 핵심 원칙인 데이터 분석을 중심으로 구성되어 있습니다. 구글 애널리틱스, 믹스패널 등 실제 분석 도구의 기본 사용법부터, 추출된 데이터를 어떻게 디자인 시안으로 변환하는지 단계별로 안내합니다. <br /><br />
                            강의가 끝날 무렵, 여러분은 단순한 디자이너가 아닌 '데이터 드리븐 프로덕트 디자이너'로서의 역량을 갖추게 될 것입니다. 실무에서 바로 적용 가능한 체크리스트와 템플릿도 함께 제공됩니다.
                        </div>
</div>
</div>
</div>

<div className="lg:col-span-4">
<div className="sticky top-[80px] space-y-lg">

<div className="bg-surface-container-lowest border border-surface-container-highest rounded-xl p-lg shadow-sm" id="enroll-card">
<div className="mb-lg">
<div className="flex items-baseline gap-xs">
</div>
</div>
<button className="w-full bg-primary text-on-primary py-md rounded-xl font-label-md text-label-md hover:brightness-110 transition-all mb-sm flex items-center justify-center gap-xs">
<span className="material-symbols-outlined text-[20px]">school</span>
                            수강 신청하기
                        </button>
<p className="text-center text-label-sm font-label-sm text-on-surface-variant">무제한 수강 및 수료증 발급 포함</p>
<hr />
<button className="w-full bg-surface-container-low text-on-surface-variant py-md rounded-xl font-label-md text-label-md hover:bg-surface-container-highest transition-all flex items-center justify-center gap-xs">
<span className="material-symbols-outlined text-[20px]">question_answer</span>
                            강사님께 질문하기
                        </button>
</div>

<div className="hidden bg-surface-container-lowest border border-surface-container-highest rounded-xl p-lg shadow-sm" id="enrolled-card">
<div className="mb-lg">
<div className="flex justify-between items-center mb-xs">
<p className="text-label-md font-label-md text-on-surface">수강 진행률</p>
<p className="text-label-md font-label-md text-primary">60%</p>
</div>
<div className="w-full h-2 bg-surface-container-low rounded-full overflow-hidden">
<div className="bg-primary h-full w-[60%]"></div>
</div>
</div>
<button className="w-full bg-primary text-on-primary py-md rounded-xl font-label-md text-label-md hover:brightness-110 transition-all mb-sm flex items-center justify-center gap-xs">
<span className="material-symbols-outlined text-[20px]">play_circle</span>
                            강의 이어보기
                        </button>
<button className="w-full bg-transparent text-error border border-error/20 py-md rounded-xl font-label-md text-label-md hover:bg-error/5 transition-all mb-lg">
                            수강 취소하기
                        </button>
<button className="w-full bg-surface-container-low text-on-surface-variant py-md rounded-xl font-label-md text-label-md hover:bg-surface-container-highest transition-all flex items-center justify-center gap-xs">
<span className="material-symbols-outlined text-[20px]">question_answer</span>
                            강의 Q&amp;A 바로가기
                        </button>
</div>
<div className="bg-surface-container-lowest border border-surface-container-highest rounded-xl p-lg shadow-sm">
<p className="text-label-md font-label-md text-on-surface-variant uppercase tracking-wider mb-md">강사 정보</p>
<div className="flex items-center gap-md mb-lg">
<div className="w-12 h-12 rounded-full overflow-hidden border border-outline-variant">
<img className="w-full h-full object-cover" src="https://lh3.googleusercontent.com/aida-public/AB6AXuB5ZHz0kUwUvNwbInMtKkHfVWB6Ig_-YH-3TqJV4uGMMrCftPFfiD2afbP4cr8B3dPILzpBKUu0hmDHodLaoDQoVB7TfOPgavXxGew1YtC4jXcMzk9TShaDvbm-halqDurHe2PIPSWQ8_LtqVORIYHw-j0YJdtMOJcZBI_t4t7kqtJ4RuRRe9AX5Y98sYgNIU5qB_GTcwnmceFWXpXjU11eYka9_blVhHlK4GVqEqY_ZknID2hNEqbZEf4vm71JEJu0G6Roj5jJv_7N" />
</div>
<div>
<p className="text-label-md font-label-md text-on-surface">{course.instructor} 강사</p>
<p className="text-label-sm font-label-sm text-on-surface-variant">Senior Product Designer</p>
</div>
</div>
<div className="flex items-center gap-xs text-on-surface-variant mb-lg">
<span className="material-symbols-outlined text-[20px]">group</span>
<span className="text-label-md font-label-md">{course.enrollmentCount.toLocaleString()}명 수강 중</span>
</div>
<hr />
<p className="text-label-md font-label-md text-on-surface-variant uppercase tracking-wider mb-sm">강사 소개</p>
<p className="text-body-md font-body-md text-on-surface leading-relaxed">
        {course.description}
    </p>
</div></div>
</div>
</div>

<section className="max-w-[800px]">
<h2 className="text-headline-md font-headline-md text-on-surface mb-lg">커리큘럼</h2>
<div className="border border-surface-container-highest rounded-xl overflow-hidden bg-surface-container-lowest shadow-sm">
<div className="curriculum-item flex items-center justify-between p-lg border-b border-surface-container-highest transition-colors cursor-pointer group">
<div className="flex items-center gap-lg">
<span className="text-label-md font-label-md text-primary w-6">01</span>
<div>
<p className="text-label-md font-label-md text-on-surface group-hover:text-primary transition-colors">데이터 기반 디자인의 이해</p>
<p className="text-label-sm font-label-sm text-on-surface-variant">왜 지금 데이터가 중요한가?</p>
</div>
</div>
<div className="flex items-center gap-md">
<button className="bg-primary text-on-primary px-md py-xs rounded-xl text-label-sm font-label-md hover:brightness-110 transition-all">수강하기</button>
</div>
</div>
<div className="curriculum-item flex items-center justify-between p-lg border-b border-surface-container-highest transition-colors cursor-pointer group">
<div className="flex items-center gap-lg">
<span className="text-label-md font-label-md text-on-surface-variant w-6">02</span>
<div>
<p className="text-label-md font-label-md text-on-surface group-hover:text-primary transition-colors">정량적 데이터 vs 정성적 데이터</p>
<p className="text-label-sm font-label-sm text-on-surface-variant">데이터의 종류와 수집 방법론</p>
</div>
</div>
<div className="flex items-center gap-md">
<button className="bg-primary text-on-primary px-md py-xs rounded-xl text-label-sm font-label-md hover:brightness-110 transition-all">수강하기</button>
</div>
</div>
<div className="curriculum-item flex items-center justify-between p-lg border-b border-surface-container-highest transition-colors cursor-pointer group">
<div className="flex items-center gap-lg">
<span className="text-label-md font-label-md text-on-surface-variant w-6">03</span>
<div>
<p className="text-label-md font-label-md text-on-surface group-hover:text-primary transition-colors">구글 애널리틱스 대시보드 해석하기</p>
<p className="text-label-sm font-label-sm text-on-surface-variant">핵심 지표(KPI) 설정과 트래킹</p>
</div>
</div>
<div className="flex items-center gap-md">
<button className="bg-primary text-on-primary px-md py-xs rounded-xl text-label-sm font-label-md hover:brightness-110 transition-all">수강하기</button>
</div>
</div>
<div className="curriculum-item flex items-center justify-between p-lg border-b border-surface-container-highest transition-colors cursor-pointer group">
<div className="flex items-center gap-lg">
<span className="text-label-md font-label-md text-on-surface-variant w-6">04</span>
<div>
<p className="text-label-md font-label-md text-on-surface group-hover:text-primary transition-colors">A/B 테스팅 설계와 실행</p>
<p className="text-label-sm font-label-sm text-on-surface-variant">가설 설정 및 유의미한 결과 도출</p>
</div>
</div>
<div className="flex items-center gap-md">
<button className="bg-primary text-on-primary px-md py-xs rounded-xl text-label-sm font-label-md hover:brightness-110 transition-all">수강하기</button>
</div>
</div>
<div className="curriculum-item flex items-center justify-between p-lg transition-colors cursor-pointer group">
<div className="flex items-center gap-lg">
<span className="text-label-md font-label-md text-on-surface-variant w-6">05</span>
<div>
<p className="text-label-md font-label-md text-on-surface group-hover:text-primary transition-colors">데이터를 기반으로 이해관계자 설득하기</p>
<p className="text-label-sm font-label-sm text-on-surface-variant">디자인 결정의 논리적 근거 마련</p>
</div>
</div>
<div className="flex items-center gap-md">
<button className="bg-primary text-on-primary px-md py-xs rounded-xl text-label-sm font-label-md hover:brightness-110 transition-all">수강하기</button>
</div>
</div>
</div>
</section>

{/* Q&A 섹션 */}
<section className="max-w-[800px] mt-xl">
  <div className="flex items-center justify-between mb-lg">
    <div className="flex items-center gap-sm">
      <h2 className="text-headline-md font-headline-md text-on-surface">Q&amp;A</h2>
      <Link
        href={`/courses/${course.id}/questions`}
        className="text-primary text-label-md font-label-md hover:underline flex items-center gap-xs"
      >
        전체 질문 보기
        <span className="material-symbols-outlined text-[16px]">chevron_right</span>
      </Link>
    </div>
    <Link
      href={`/courses/${course.id}/questions/new`}
      className="bg-primary text-on-primary px-lg py-sm rounded-lg text-label-md font-label-md hover:opacity-90 transition-opacity flex items-center gap-xs"
    >
      <span className="material-symbols-outlined text-[18px]">edit</span>
      질문하기
    </Link>
  </div>
  {courseQuestions.length === 0 ? (
    <div className="bg-surface-container-lowest border border-outline-variant rounded-xl p-xl text-center">
      <span className="material-symbols-outlined text-[40px] text-on-surface-variant mb-md block">forum</span>
      <p className="text-body-md font-body-md text-on-surface-variant">아직 등록된 질문이 없습니다.</p>
      <p className="text-label-sm font-label-sm text-on-surface-variant mt-xs">궁금한 점이 있으면 질문해 보세요!</p>
    </div>
  ) : (
    <div className="border border-outline-variant rounded-xl overflow-hidden bg-surface-container-lowest">
      {courseQuestions.map((q, idx) => (
        <Link
          key={q.id}
          href={`/courses/${course.id}/questions/${q.id}`}
          className={`flex items-start gap-md p-lg hover:bg-surface-container transition-colors group ${idx < courseQuestions.length - 1 ? 'border-b border-outline-variant' : ''}`}
        >
          <div className={`mt-1 flex-shrink-0 w-5 h-5 rounded-full flex items-center justify-center ${q.isAnswered ? 'bg-primary' : 'bg-outline-variant'}`}>
            <span className="material-symbols-outlined text-[12px] text-white" style={{ fontVariationSettings: "'FILL' 1" }}>{q.isAnswered ? 'check' : 'help'}</span>
          </div>
          <div className="flex-1 min-w-0">
            <p className="text-body-md font-body-md text-on-surface group-hover:text-primary transition-colors line-clamp-1">{q.title}</p>
            <div className="flex items-center gap-md mt-xs text-label-sm font-label-sm text-on-surface-variant">
              <span>{q.authorName}</span>
              <span>·</span>
              <span>{q.createdAt}</span>
              <span>·</span>
              <span>답변 {q.answerCount}개</span>
            </div>
          </div>
          <span className={`flex-shrink-0 px-sm py-1 rounded-full text-label-sm font-label-sm ${q.isAnswered ? 'bg-secondary-container text-on-secondary-container' : 'bg-primary-fixed text-on-primary-container'}`}>
            {q.isAnswered ? '답변 완료' : '답변 대기'}
          </span>
        </Link>
      ))}
    </div>
  )}
</section>
</div>
</main>
      <Footer />
    </>
  );
}
