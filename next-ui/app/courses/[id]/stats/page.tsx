'use client';

import Link from 'next/link';

import { TopNavBar } from '@/components/TopNavBar';
import { Footer } from '@/components/Footer';
// TODO: API 연동 필요

export default function CourseStatsPage() {
  return (
    <>
      <TopNavBar />
      <main className="pt-16 min-h-screen bg-background">
<div className="pt-24 pb-xl px-gutter max-w-[1280px] mx-auto">

<div className="flex flex-col md:flex-row md:items-end justify-between mb-xl gap-md">
<div>
<nav className="flex items-center gap-xs text-on-surface-variant mb-xs">
<span className="text-label-sm font-label-sm">강사 대시보드</span>
<span className="material-symbols-outlined text-[16px]">chevron_right</span>
<span className="text-label-sm font-label-sm">강의 통계</span>
</nav>
<h1 className="text-headline-lg font-headline-lg text-on-surface tracking-tight">AI 프로그래밍 기초 통계</h1>
</div>
<Link href="/courses/1/edit" className="flex items-center gap-sm px-lg py-md bg-surface border border-primary text-primary font-label-md text-label-md rounded-lg hover:bg-primary-fixed transition-all active:opacity-80">
<span className="material-symbols-outlined">edit</span>
  강의 편집으로 돌아가기
</Link>
</div>

<div className="bento-grid">

<div className="col-span-12 md:col-span-6 lg:col-span-4 bg-surface-container-lowest p-lg rounded-xl border border-surface-container-high stat-card flex flex-col justify-between">
<div className="flex justify-between items-start">
<div className="p-sm bg-primary-fixed rounded-lg text-primary">
<span className="material-symbols-outlined" style={{fontVariationSettings: "'FILL' 1"}}>group</span>
</div>
<span className="text-label-sm font-label-sm text-primary flex items-center bg-primary-fixed/30 px-sm py-xs rounded-full">
<span className="material-symbols-outlined text-[14px] mr-xs">trending_up</span>
                        +12% 이번 달
                    </span>
</div>
<div className="mt-xl">
<p className="text-on-surface-variant font-label-md text-label-md">총 수강 인원</p>
<h2 className="text-display-lg font-display-lg text-on-surface mt-xs">1,284명</h2>
</div>
</div>

<div className="col-span-12 md:col-span-6 lg:col-span-4 bg-surface-container-lowest p-lg rounded-xl border border-surface-container-high stat-card">
<div className="flex justify-between items-start mb-md">
<div className="p-sm bg-secondary-container rounded-lg text-on-secondary-container">
<span className="material-symbols-outlined" style={{fontVariationSettings: "'FILL' 1"}}>insights</span>
</div>
</div>
<p className="text-on-surface-variant font-label-md text-label-md">평균 진행률</p>
<div className="flex items-end gap-sm mt-xs">
<h2 className="text-display-lg font-display-lg text-on-surface">68.5%</h2>
<span className="text-label-md font-label-md text-on-surface-variant mb-sm">목표 대비 높음</span>
</div>
<div className="w-full h-2 bg-surface-container rounded-full mt-md overflow-hidden">
<div className="h-full bg-primary-container" style={{width: '68.5%'}}></div>
</div>
</div>

<div className="col-span-12 md:col-span-6 lg:col-span-4 bg-surface-container-lowest p-lg rounded-xl border border-surface-container-high stat-card">
<div className="flex justify-between items-start mb-md">
<div className="p-sm bg-tertiary-fixed rounded-lg text-on-tertiary-fixed-variant">
<span className="material-symbols-outlined" style={{fontVariationSettings: "'FILL' 1"}}>verified</span>
</div>
</div>
<p className="text-on-surface-variant font-label-md text-label-md">완료 수강생 수</p>
<h2 className="text-display-lg font-display-lg text-on-surface mt-xs">412명</h2>
<p className="text-body-md font-body-md text-on-surface-variant mt-sm">전체 인원의 32% 수료</p>
</div>

<div className="col-span-12 lg:col-span-8 bg-surface-container-lowest p-lg rounded-xl border border-surface-container-high stat-card flex flex-col md:flex-row gap-lg items-center">
<div className="flex-1 w-full">
<div className="flex items-center gap-sm mb-md">
<div className="p-sm bg-primary-fixed rounded-lg text-primary">
<span className="material-symbols-outlined">forum</span>
</div>
<p className="text-on-surface-variant font-label-md text-label-md">질문 통계 개요</p>
</div>
<div className="grid grid-cols-2 gap-lg mt-md">
<div>
<p className="text-label-sm font-label-sm text-on-surface-variant uppercase tracking-wider">총 Q&amp;A 질문 수</p>
<h3 className="text-headline-lg font-headline-lg text-on-surface mt-xs">856건</h3>
</div>
<div>
<p className="text-label-sm font-label-sm text-error uppercase tracking-wider">답변 대기 수</p>
<h3 className="text-headline-lg font-headline-lg text-error mt-xs">24건</h3>
</div>
</div>
</div>
<div className="w-full md:w-48 aspect-square relative flex items-center justify-center">

<svg className="w-full h-full transform -rotate-90">
<circle className="text-surface-container" cx="50%" cy="50%" fill="transparent" r="40%" stroke="currentColor" strokeWidth="8"></circle>
<circle className="text-primary-container transition-all duration-1000" cx="50%" cy="50%" fill="transparent" r="40%" stroke="currentColor" strokeDasharray="251.2" strokeDashoffset="25.12" strokeWidth="8"></circle>
</svg>
<div className="absolute inset-0 flex flex-col items-center justify-center">
<span className="text-headline-md font-headline-md text-on-surface">97%</span>
<span className="text-label-sm font-label-sm text-on-surface-variant">답변율</span>
</div>
</div>
</div>

<div className="col-span-12 lg:col-span-4 bg-primary text-on-primary p-lg rounded-xl shadow-lg flex flex-col justify-between overflow-hidden relative group">

<div className="absolute top-0 right-0 p-lg opacity-10 group-hover:scale-110 transition-transform duration-500">
<span className="material-symbols-outlined text-[120px]">person_add</span>
</div>
<div className="relative z-10">
<p className="text-primary-fixed font-label-md text-label-md">최근 24시간 수강 신청</p>
<h2 className="text-display-lg font-display-lg mt-sm">+42명</h2>
</div>
<div className="relative z-10 mt-xl flex -space-x-3">
<div className="w-10 h-10 rounded-full border-2 border-primary overflow-hidden">
<img className="w-full h-full object-cover" alt="Close-up headshot of a diverse male student in his 20s, wearing casual glasses and a minimalist white t-shirt. The background is a soft, blurred library setting with high-key professional lighting, fitting an energetic and modern learning platform aesthetic." src="https://lh3.googleusercontent.com/aida-public/AB6AXuAvXvm4dji1RRAYjysDkMtvZWJ0o7ucvY97MdkyOinN2eevlx2SpBvUavdzUdhPvQZZx9W6W2s4yrFEqqYUxX-NRgvBxq9KqtrxYcna-1CIbpc4S4eg6UKLbj5DYJMj_KLlFtThtBJuhcrIbKp3wvp6Oq18NQ7QV91Do0cgxWKRNXPcPXKTF0ySd9uE148-MBFt8oppxCf5djN3m18_f2UDierGLC4jiGK0m8f-Abg18Jt4207hfT_6yIV6TxmNF1vTi4aem4YTv07r" />
</div>
<div className="w-10 h-10 rounded-full border-2 border-primary overflow-hidden">
<img className="w-full h-full object-cover" alt="Close-up headshot of a focused female student in her late 20s, with a clean professional bun and minimalist gold jewelry. High-contrast lighting highlights her sharp features against a light gray modern workspace background, matching the clean and crisp LXP visual style." src="https://lh3.googleusercontent.com/aida-public/AB6AXuAVK-VwP5TbwI0hhOSk-sVaOdM2ft7eYEgpA40YqCYIOapGkuST28FZVUDZ1e-vH2o2oyxoSN6HjfvHhdy7InzgIHEJJRAanK5yY5H0ch7EYREaWdLgwjk5wFlIg1C1xp_9-6SWXQUlFucT0U6P9KGYfhcW9Bd5FgFhOvi5OEjBllLkaw6orXG9pwKSgVmM49obXajWjzjZiOpD0ONRL4xeMwq6u3u4eog2XcG4av5KeMvxN17s2RedNLVIzNcZL1EwUJ66fkN-9foE" />
</div>
<div className="w-10 h-10 rounded-full border-2 border-primary overflow-hidden">
<img className="w-full h-full object-cover" alt="Close-up portrait of a mature male professional student in his late 30s, looking at the camera with confidence. He is in a brightly lit modern office environment with a minimalist, high-contrast aesthetic that emphasizes clarity and professional momentum." src="https://lh3.googleusercontent.com/aida-public/AB6AXuDRqqVNnuEr9710Q_jYeEeFtjR1YQU526MT8SynhmQOEzTP17hQsnXG1BLBNOeiK8T9jnU6Zvwxn5RG_CPKEtzCEuHYdHLfBQG8lJJ3qQokyg7Pc4dAzvcrMz0SV_CWAdGBdsksjFjsJ4rjflmptAHcFuezRQiC4kMk64SS7o8wDpTfEEzGLi_nS5Ivcuawvc68kv4mfDka5Ai1apW3LP6KbVwpacZ70NmUiFpXSt3SvQGrrz289BSChvsbLVqfxklYA8CGY1z4N87J" />
</div>
<div className="w-10 h-10 rounded-full bg-primary-container border-2 border-primary flex items-center justify-center text-on-primary-container text-label-sm font-label-sm">
                        +39
                    </div>
</div>
</div>
</div>
</div>
</main>
      <Footer />
    </>
  );
}
