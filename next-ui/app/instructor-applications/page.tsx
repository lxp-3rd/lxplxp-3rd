'use client';

import { TopNavBar } from '@/components/TopNavBar';
import { Footer } from '@/components/Footer';
// TODO: API 연동 필요

export default function InstructorApplicationPage() {
  return (
    <>
      <TopNavBar />
      <main className="pt-16 min-h-screen bg-background">
<div className="pt-24 pb-xxl px-gutter max-w-container-max mx-auto">

<div className="grid grid-cols-1 lg:grid-cols-12 gap-lg">

<section className="lg:col-span-7 flex flex-col gap-lg">
<div className="bg-surface-container-lowest border border-outline-variant rounded-xl p-xl">

<form className="flex flex-col gap-lg">
<div className="grid grid-cols-1 md:grid-cols-2 gap-md">
<div className="flex flex-col gap-base">
<label className="text-label-md font-label-md text-on-surface">강사 활동명</label>
<input className="border border-outline-variant rounded px-md py-sm text-body-md input-focus bg-surface-bright" placeholder="예: 김코딩" type="text" />
</div>
<div className="flex flex-col gap-base">
<label className="text-label-md font-label-md text-on-surface">전문 분야</label>
<select className="border border-outline-variant rounded px-md py-sm text-body-md input-focus bg-surface-bright appearance-none">
<option>IT &amp; 프로그래밍</option>
<option>데이터 사이언스</option>
<option>디자인 &amp; 예술</option>
<option>비즈니스 &amp; 마케팅</option>
</select>
</div>
</div>
<div className="flex flex-col gap-base">
<label className="text-label-md font-label-md text-on-surface">강사 소개글 (Intro)</label>
<textarea className="border border-outline-variant rounded px-md py-sm text-body-md input-focus bg-surface-bright resize-none" placeholder="학습자들에게 보여질 본인의 강점과 경력을 설명해주세요." rows={4}></textarea>
</div>
<div className="flex flex-col gap-base">
<label className="text-label-md font-label-md text-on-surface">포트폴리오 및 증빙 자료</label>
<div className="border-2 border-dashed border-outline-variant rounded-xl p-xl flex flex-col items-center justify-center gap-sm bg-surface-container-low hover:bg-surface-container-high transition-colors cursor-pointer group">
<span className="material-symbols-outlined text-outline group-hover:text-primary transition-colors" style={{fontSize: '32px'}}>cloud_upload</span>
<p className="text-body-sm text-on-surface-variant">파일을 드래그하거나 클릭하여 업로드 (PDF, ZIP, MAX 50MB)</p>
</div>
</div>
<button className="bg-primary text-on-primary font-label-md text-label-md py-md rounded-lg hover:opacity-90 active:scale-[0.98] transition-all flex justify-center items-center gap-xs">
<span className="material-symbols-outlined text-[20px]">send</span>
                            강사 신청서 제출하기
                        </button>
</form>
</div>
</section>

<aside className="lg:col-span-5 flex flex-col gap-lg">

<div className="bg-surface-container-lowest border border-outline-variant rounded-xl p-xl relative overflow-hidden">
<div className="absolute top-0 left-0 w-2 h-full bg-primary"></div>
<div className="flex justify-between items-start mb-lg">
<div>
<h2 className="text-headline-sm font-headline-sm text-on-surface">신청 현황</h2>
<p className="text-body-sm text-on-surface-variant">현재 진행 중인 신청 내역입니다.</p>
</div>
<div className="px-md py-xs bg-primary-container text-on-primary rounded-full text-label-md font-label-md flex items-center gap-xs">
<span className="material-symbols-outlined text-[16px]">sync</span>
                            검토 중
                        </div>
</div>
<div className="flex flex-col gap-md">

<div className="flex gap-md p-md bg-surface-container-low rounded-lg border border-outline-variant">
<div className="w-12 h-12 bg-primary-container rounded-full flex items-center justify-center shrink-0">
<span className="material-symbols-outlined text-on-primary">hourglass_empty</span>
</div>
<div className="flex flex-col gap-base">
<span className="text-label-md font-label-md text-on-surface">LXP 전문가 검토 (Under Review)</span>
<p className="text-body-sm text-on-surface-variant">담당자가 제출하신 서류와 소개글을 확인하고 있습니다. 영업일 기준 3~5일이 소요됩니다.</p>
<span className="text-body-sm text-outline mt-xs">2024년 5월 15일 신청</span>
</div>
</div>

<div className="flex gap-md p-md bg-error-container/30 rounded-lg border border-error/20 opacity-60">
<div className="w-12 h-12 bg-error-container rounded-full flex items-center justify-center shrink-0">
<span className="material-symbols-outlined text-error">close</span>
</div>
<div className="flex flex-col gap-base">
<span className="text-label-md font-label-md text-error">반려 (Rejected)</span>
<p className="text-body-sm text-on-error-container">사유: 포트폴리오 증빙 자료 부족. 실무 경력을 증명할 수 있는 프로젝트 명세서나 링크를 추가해 주세요.</p>
<span className="text-body-sm text-outline mt-xs">2024년 4월 20일 반려</span>
</div>
</div>

<div className="flex gap-md p-md bg-surface-container-low rounded-lg border border-outline-variant opacity-60">
<div className="w-12 h-12 bg-surface-container-high rounded-full flex items-center justify-center shrink-0">
<span className="material-symbols-outlined text-on-surface-variant">check</span>
</div>
<div className="flex flex-col gap-base">
<span className="text-label-md font-label-md text-on-surface">승인 (Approved)</span>
<p className="text-body-sm text-on-surface-variant">축하합니다! 이제 강사 전용 대시보드를 사용할 수 있습니다.</p>
</div>
</div>
</div>
</div>

</aside>
</div>
</div>
</main>
      <Footer />
    </>
  );
}
