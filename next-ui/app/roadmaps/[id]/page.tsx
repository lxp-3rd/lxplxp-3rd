'use client';

import { TopNavBar } from '@/components/TopNavBar';
import { Footer } from '@/components/Footer';
import { getRoadmapById, MOCK_ROADMAPS } from '@/app/roadmaps/mockData';

export default function RoadmapDetailPage({ params }: { params: { id: string } }) {
  const roadmap = getRoadmapById(params.id) ?? MOCK_ROADMAPS[0];
  return (
    <>
      <TopNavBar />
      <main className="pt-16 min-h-screen bg-background">
<div className="pt-24 pb-xl px-gutter max-w-7xl mx-auto">

<div className="flex flex-col lg:flex-row gap-xl mb-xl">

<div className="w-full lg:w-1/2 aspect-video rounded-xl overflow-hidden shadow-sm border border-outline-variant">
<img className="w-full h-full object-cover" alt="A visually sophisticated 16:9 aspect ratio illustration representing a full-stack web development roadmap." src="https://lh3.googleusercontent.com/aida-public/AB6AXuBqRyP8lSrTX35EcjFOoHHi3NT42KIPttHitsIfAyAegZKs73Eu1g8Sx6GB6wyCtEu3tWIDKyl4_wprAGlIHhbsC3xmSAijtldgKaEbD-O6g32uXAhHiMXvLk2vGx8cCLrY6bYLi73ZFklVm47bkEMiAlXHqJQKnrXL3cIE9zkIN1myzwO2ydul-F4xz1oqRlsm6HiOD_5gWPqmb-RpzlprS7dBkgfcEA7MePojLBJTYrLhD6dCS6QEZR4H2Gz8mGtmdJMBZCYERgTT" />
</div>

<div className="w-full lg:w-1/2 flex flex-col justify-center">
<div className="flex items-center gap-xs mb-md">

<span className="flex items-center gap-1 text-on-surface-variant text-label-md font-label-md">
<span className="material-symbols-outlined text-[16px]">group</span>
                    {roadmap.enrollmentCount.toLocaleString()}명 참여 중
                </span>
</div>
<h1 className="text-headline-lg font-headline-lg mb-md leading-tight text-on-surface">{roadmap.title}</h1>
<p className="text-body-lg font-body-lg text-on-surface-variant mb-xl leading-relaxed">
                {roadmap.description}
            </p>

<div className="p-lg bg-surface-container-lowest border border-outline-variant rounded-xl shadow-sm" id="roadmap-action-area">
<div className="mb-sm flex justify-between items-end">
<span className="text-label-md font-label-md text-primary">진행률 35%</span>
<span className="text-label-md font-label-md text-on-surface-variant">2 / 6 강의 완료</span>
</div>

<div className="w-full h-2 bg-surface-container-high rounded-full overflow-hidden mb-lg">
<div className="h-full bg-primary w-[35%] transition-all duration-1000 ease-out"></div>
</div>
<div className="flex gap-md">
<button className="flex-1 py-3 px-xl bg-primary text-on-primary font-label-md text-label-md rounded-lg shadow-sm hover:opacity-90 transition-all duration-300">강의 계속하기</button>
<button className="py-3 px-xl bg-surface-container-low text-on-surface-variant font-label-md text-label-md rounded-lg hover:bg-surface-container-high transition-all duration-300">로드맵 취소</button>
</div>
</div>
</div>
</div>

<div className="grid grid-cols-1 lg:grid-cols-12 gap-xl">

<div className="lg:col-span-8">
<h2 className="text-headline-md font-headline-md mb-lg">로드맵 순서</h2>
<div className="space-y-0 roadmap-container">

  {/* STEP 01 — 완료 */}
  <div className="flex gap-md pb-xl">
    <div className="flex flex-col items-center flex-shrink-0">
      <div className="w-10 h-10 rounded-full bg-primary text-on-primary flex items-center justify-center shadow-sm flex-shrink-0">
        <span className="material-symbols-outlined text-[20px]">check</span>
      </div>
      <div className="flex-1 w-0.5 bg-outline-variant mt-1" />
    </div>
    <div className="flex-1 pb-2">
      <div className="bg-surface-container-lowest rounded-xl border border-outline-variant overflow-hidden hover:shadow-md transition-all duration-300">
        <div className="flex items-center justify-between px-md pt-md pb-sm border-b border-outline-variant">
          <span className="text-label-sm font-label-sm text-primary font-bold tracking-wider">STEP 01</span>
          <span className="bg-secondary-container text-on-secondary-container text-label-sm font-label-sm px-sm py-0.5 rounded-full">완료</span>
        </div>
        <div className="px-md py-sm">
          <h3 className="text-body-lg font-bold text-on-surface mb-xs">웹 프로그래밍 기초 (HTML/CSS)</h3>
          <p className="text-body-sm font-body-sm text-on-surface-variant">시맨틱 웹과 모던 CSS 레이아웃 시스템을 완벽하게 이해합니다.</p>
        </div>
      </div>
    </div>
  </div>

  {/* STEP 02 — 수강 중 */}
  <div className="flex gap-md pb-xl">
    <div className="flex flex-col items-center flex-shrink-0">
      <div className="w-10 h-10 rounded-full bg-primary text-on-primary flex items-center justify-center shadow-md ring-4 ring-primary/20 flex-shrink-0">
        <span className="text-label-md font-bold">02</span>
      </div>
      <div className="flex-1 w-0.5 bg-outline-variant mt-1" />
    </div>
    <div className="flex-1 pb-2">
      <div className="bg-surface-container-lowest rounded-xl border-2 border-primary overflow-hidden shadow-md">
        <div className="flex items-center justify-between px-md pt-md pb-sm border-b border-primary/20">
          <span className="text-label-sm font-label-sm text-primary font-bold tracking-wider">STEP 02</span>
          <span className="bg-primary text-on-primary text-label-sm font-label-sm px-sm py-0.5 rounded-full">수강 중</span>
        </div>
        <div className="px-md py-sm">
          <h3 className="text-body-lg font-bold text-primary mb-xs">자바스크립트 딥다이브</h3>
          <p className="text-body-sm font-body-sm text-on-surface-variant mb-md">비동기 프로그래밍부터 클로저까지, 핵심 원리를 파고듭니다.</p>
          <button className="text-label-md font-label-md text-primary flex items-center gap-xs hover:underline">
            강의실로 이동하기 <span className="material-symbols-outlined text-[16px]">arrow_forward</span>
          </button>
        </div>
      </div>
    </div>
  </div>

  {/* STEP 03 — 대기 중 */}
  <div className="flex gap-md pb-xl opacity-60">
    <div className="flex flex-col items-center flex-shrink-0">
      <div className="w-10 h-10 rounded-full bg-surface-container text-on-surface-variant flex items-center justify-center border border-outline-variant flex-shrink-0">
        <span className="material-symbols-outlined text-[20px]">lock</span>
      </div>
      <div className="flex-1 w-0.5 bg-outline-variant mt-1" />
    </div>
    <div className="flex-1 pb-2">
      <div className="bg-surface-container rounded-xl border border-outline-variant overflow-hidden">
        <div className="flex items-center justify-between px-md pt-md pb-sm border-b border-outline-variant">
          <span className="text-label-sm font-label-sm text-on-surface-variant font-bold tracking-wider">STEP 03</span>
          <span className="bg-surface-container-high text-on-surface-variant text-label-sm font-label-sm px-sm py-0.5 rounded-full">대기 중</span>
        </div>
        <div className="px-md py-sm">
          <h3 className="text-body-lg font-bold text-on-surface mb-xs">React 및 상태 관리 (Redux/Zustand)</h3>
          <p className="text-body-sm font-body-sm text-on-surface-variant">컴포넌트 기반 아키텍처와 효율적인 전역 상태 관리를 배웁니다.</p>
        </div>
      </div>
    </div>
  </div>

  {/* STEP 04 — 대기 중 (마지막, 연결선 없음) */}
  <div className="flex gap-md opacity-60">
    <div className="flex flex-col items-center flex-shrink-0">
      <div className="w-10 h-10 rounded-full bg-surface-container text-on-surface-variant flex items-center justify-center border border-outline-variant flex-shrink-0">
        <span className="material-symbols-outlined text-[20px]">lock</span>
      </div>
    </div>
    <div className="flex-1">
      <div className="bg-surface-container rounded-xl border border-outline-variant overflow-hidden">
        <div className="flex items-center justify-between px-md pt-md pb-sm border-b border-outline-variant">
          <span className="text-label-sm font-label-sm text-on-surface-variant font-bold tracking-wider">STEP 04</span>
          <span className="bg-surface-container-high text-on-surface-variant text-label-sm font-label-sm px-sm py-0.5 rounded-full">대기 중</span>
        </div>
        <div className="px-md py-sm">
          <h3 className="text-body-lg font-bold text-on-surface mb-xs">실전 프로젝트: 이커머스 웹 앱</h3>
          <p className="text-body-sm font-body-sm text-on-surface-variant">지금까지 배운 모든 기술을 활용하여 상용 수준의 프로젝트를 제작합니다.</p>
        </div>
      </div>
    </div>
  </div>

</div>
</div>

<aside className="lg:col-span-4 space-y-xl">
<div>
<h2 className="text-headline-md font-headline-md mb-lg">포함된 강의 (6)</h2>
<div className="flex flex-col gap-md">

<div className="flex gap-md group cursor-pointer">
<div className="w-24 h-16 rounded overflow-hidden flex-shrink-0 border border-outline-variant">
<img className="w-full h-full object-cover" src="https://lh3.googleusercontent.com/aida-public/AB6AXuCmqw8ZHb7gDEFtexB9SoAkF_RPrH6gzJDLsyFJeuGJEbldxxwWUZlOMZxr5HZv9RblSWHYdggzb9JqGtANMwn5iLS2bmDw-rtlmV4tYvytfzssQtaceuGkz4LcyUuguMscURMZDxYPJigFK8jQHuAj8EO1pqzTUrsooAU0ll2HJV4SOlvljJfpXD69yiMm6v3eX78TtM0Nw3PV1YwnJN3c1DQsxexQpt7bsJui7SUjGfGqmXSt2Fx8wkJMNH5Xyp7bUTiTnqP1uCFl" />
</div>
<div>
<h4 className="text-label-md font-label-md group-hover:text-primary transition-colors line-clamp-1">HTML5/CSS3 기초 완성</h4>
<p className="text-body-sm font-body-sm text-on-surface-variant">김철수 강사</p>
<div className="flex items-center gap-2 mt-1">
<span className="text-[12px] text-primary font-bold">100% 완료</span>
</div>
</div>
</div>

<div className="flex gap-md group cursor-pointer">
<div className="w-24 h-16 rounded overflow-hidden flex-shrink-0 border border-outline-variant">
<img className="w-full h-full object-cover" src="https://lh3.googleusercontent.com/aida-public/AB6AXuDp8XML3ZOnMcTTAlbeD0HMI1nX4KD7m2DAhwkxAHSDtHTMD0txqNtUYoEK2CmmBeuP3o9d5QD_Mktu9y7ChXe75u5-ivO6tT-0SqU8YcJrqAR-QYeHb4jdrNHXbiTZRpftCJnF6ih7S-DlNvXL17nsiuUgaFtEzXVnV7BfOFF8g2OSOUnuSf3BePIB-z24fB00gNKGxzqfhiydbOkMVBevZc5c6iUnybs_fJzHzmZz3aIKXDKSA87_Ywygoe1QQYF90CKxS_FOlpdb" />
</div>
<div>
<h4 className="text-label-md font-label-md group-hover:text-primary transition-colors line-clamp-1">자바스크립트 비동기 프로그래밍</h4>
<p className="text-body-sm font-body-sm text-on-surface-variant">이영희 강사</p>
<div className="flex items-center gap-2 mt-1">
<span className="text-[12px] text-on-surface-variant">24% 진행 중</span>
</div>
</div>
</div>

<div className="flex gap-md group cursor-pointer">
<div className="w-24 h-16 rounded overflow-hidden flex-shrink-0 border border-outline-variant">
<img className="w-full h-full object-cover" src="https://lh3.googleusercontent.com/aida-public/AB6AXuBol9rvCo0EfaOSv5b0r6kj_k99DPckwjATnRzWAHfWLpLfdu8sz9sgIW4srSW6hR--7imDDOG7hfhk15kjlqCEDo7Cc1Wy-JODjIgvVF2ajCvjRHslGHkJ25muH3y1Q-8JMrLp8KGF03LM89B_ZWtYbj66Y3vkptZu0uuTtTd3F5_0_L86bjlZzfUpyCHnYAsamvR4S6z9mBqKwrHHZq9Pb5J7l_4cJkpoiaStBRknNlpF66TZVPIDDDmWM5qZqTgTJqVdjJisICzM" />
</div>
<div>
<h4 className="text-label-md font-label-md group-hover:text-primary transition-colors line-clamp-1">React 시작하기: 기본 개념</h4>
<p className="text-body-sm font-body-sm text-on-surface-variant">박지성 강사</p>
<div className="flex items-center gap-2 mt-1">
<span className="text-[12px] text-on-surface-variant">시작 전</span>
</div>
</div>
</div>
</div>
<button className="w-full mt-lg py-2 border border-outline-variant rounded-lg text-label-md font-label-md text-on-surface-variant hover:bg-surface-container-low transition-colors">
                    강의 목록 더보기
                </button>
</div>

<div className="bg-surface-container-low p-lg rounded-xl border border-outline-variant">
<h3 className="text-label-md font-label-md mb-md flex items-center gap-2 text-on-surface">
<span className="material-symbols-outlined text-primary">info</span> 로드맵 정보
                </h3>
<ul className="space-y-sm text-body-sm font-body-sm text-on-surface-variant">
<li className="flex justify-between">
<span className="">만든 사람</span>
<span className="font-bold text-on-surface">LXP 운영팀</span>
</li>
<li className="flex justify-between">
<span className="">업데이트 일자</span>
<span className="font-bold text-on-surface">2024.05.20</span>
</li>
</ul>
</div>
</aside>
</div>
</div>
</main>
      <Footer />
    </>
  );
}
