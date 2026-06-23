'use client';

// TODO: API 연동 필요
export default function AdminDashboardPage() {
  return (

<div className="mt-16 flex-grow px-gutter py-xl max-w-[1280px] mx-auto w-full">

<section className="mb-xl flex flex-col md:flex-row md:items-end justify-between gap-4">
<div>
<h1 className="font-headline-lg text-headline-lg text-on-background">관리자 개요</h1>
<p className="font-body-md text-body-md text-on-surface-variant">다시 오신 것을 환영합니다. 플랫폼 전반의 최신 활동 내역입니다.</p>
</div>
<div className="flex gap-sm">

</div>
</section>

<section className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-lg mb-xl">

<div className="glass-card p-lg rounded-xl stat-card-glow transition-all duration-300">
<div className="flex justify-between items-start mb-md">
<div className="p-sm bg-primary-fixed rounded-lg text-primary">
<span className="material-symbols-outlined" style={{transform: 'scale(1) rotate(0deg)'}}>group</span>
</div>
<span className="text-label-sm font-label-sm text-green-600 bg-green-50 px-2 py-0.5 rounded-full">+12.5%</span>
</div>
<p className="text-label-md font-label-md text-on-surface-variant mb-xs">전체 회원 수</p>
<h2 className="text-headline-md font-headline-md text-on-surface">24,512</h2>
<div className="mt-md h-1 w-full bg-surface-container rounded-full overflow-hidden">
<div className="h-full bg-primary-container" style={{width: '75%'}}></div>
</div>
</div>

<div className="glass-card p-lg rounded-xl stat-card-glow transition-all duration-300 border-l-4 border-l-primary-container">
<div className="flex justify-between items-start mb-md">
<div className="p-sm bg-secondary-fixed rounded-lg text-secondary">
<span className="material-symbols-outlined" style={{transform: 'scale(1) rotate(0deg)'}}>school</span>
</div>
<span className="text-label-sm font-label-sm text-green-600 bg-green-50 px-2 py-0.5 rounded-full">+4.2%</span>
</div>
<p className="text-label-md font-label-md text-on-surface-variant mb-xs">전체 강사 수</p>
<h2 className="text-headline-md font-headline-md text-on-surface">843</h2>
<div className="mt-md h-1 w-full bg-surface-container rounded-full overflow-hidden">
<div className="h-full bg-secondary-container" style={{width: '40%'}}></div>
</div>
</div>

<div className="glass-card p-lg rounded-xl stat-card-glow transition-all duration-300">
<div className="flex justify-between items-start mb-md">
<div className="p-sm bg-tertiary-fixed rounded-lg text-tertiary">
<span className="material-symbols-outlined" style={{transform: 'scale(1) rotate(0deg)'}}>auto_stories</span>
</div>
<span className="text-label-sm font-label-sm text-on-surface-variant bg-surface-container px-2 py-0.5 rounded-full">안정적</span>
</div>
<p className="text-label-md font-label-md text-on-surface-variant mb-xs">전체 강좌 수</p>
<h2 className="text-headline-md font-headline-md text-on-surface">1,204</h2>
<div className="mt-md h-1 w-full bg-surface-container rounded-full overflow-hidden">
<div className="h-full bg-tertiary-container" style={{width: '60%'}}></div>
</div>
</div>

<div className="bg-primary-container p-lg rounded-xl shadow-lg transform hover:scale-[1.02] transition-all duration-300 text-on-primary">
<div className="flex justify-between items-start mb-md">
<div className="p-sm bg-white/20 rounded-lg">
<span className="material-symbols-outlined text-white">pending_actions</span>
</div>
<span className="text-label-sm font-label-sm bg-white/20 text-white px-2 py-0.5 rounded-full">검토 필요</span>
</div>
<p className="text-label-md font-label-md opacity-90 mb-xs">대기 중인 강사 신청 수</p>
<h2 className="text-headline-md font-headline-md">18</h2>
<p className="text-label-sm mt-md font-label-sm flex items-center gap-1">
<span className="material-symbols-outlined text-[14px]">warning</span>
                    24시간 이내 검토 권장
                </p>
</div>
</section>

<section className="grid grid-cols-1 lg:grid-cols-3 gap-lg">

<div className="lg:col-span-2 glass-card rounded-xl p-lg">
<h3 className="text-headline-md font-headline-md mb-lg flex items-center gap-2">
<span className="material-symbols-outlined text-primary">rocket_launch</span>
                    빠른 메뉴
                </h3>
<div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-md">
<a className="group p-md rounded-xl border border-surface-variant hover:border-primary-container hover:bg-primary-fixed/30 transition-all flex flex-col gap-sm" href="/admin/instructor-applications">
<div className="w-10 h-10 rounded-lg bg-surface-container flex items-center justify-center group-hover:bg-primary-container group-hover:text-on-primary transition-colors">
<span className="material-symbols-outlined">assignment_ind</span>
</div>
<span className="font-label-md text-on-surface">강사 신청 목록</span>
<p className="text-label-sm text-on-surface-variant">새로운 강사 프로필 및 자격 서류를 검토합니다.</p>
</a>
<a className="group p-md rounded-xl border border-surface-variant hover:border-primary-container hover:bg-primary-fixed/30 transition-all flex flex-col gap-sm" href="/admin/instructors">
<div className="w-10 h-10 rounded-lg bg-surface-container flex items-center justify-center group-hover:bg-primary-container group-hover:text-on-primary transition-colors">
<span className="material-symbols-outlined">manage_accounts</span>
</div>
<span className="font-label-md text-on-surface">강사 관리</span>
<p className="text-label-sm text-on-surface-variant">강사 권한 및 세부 정보를 수정합니다.</p>
</a>
<a className="group p-md rounded-xl border border-surface-variant hover:border-primary-container hover:bg-primary-fixed/30 transition-all flex flex-col gap-sm" href="/admin/members">
<div className="w-10 h-10 rounded-lg bg-surface-container flex items-center justify-center group-hover:bg-primary-container group-hover:text-on-primary transition-colors">
<span className="material-symbols-outlined">person_search</span>
</div>
<span className="font-label-md text-on-surface">회원 관리</span>
<p className="text-label-sm text-on-surface-variant">모든 학습자 계정을 검색하고 관리합니다.</p>
</a>
<a className="group p-md rounded-xl border border-surface-variant hover:border-primary-container hover:bg-primary-fixed/30 transition-all flex flex-col gap-sm" href="/admin/courses">
<div className="w-10 h-10 rounded-lg bg-surface-container flex items-center justify-center group-hover:bg-primary-container group-hover:text-on-primary transition-colors">
<span className="material-symbols-outlined">book</span>
</div>
<span className="font-label-md text-on-surface">강좌 관리</span>
<p className="text-label-sm text-on-surface-variant">활성 강좌를 감사하거나 승인 또는 숨김 처리합니다.</p>
</a>
<a className="group p-md rounded-xl border border-surface-variant hover:border-primary-container hover:bg-primary-fixed/30 transition-all flex flex-col gap-sm" href="/admin/announcements">
<div className="w-10 h-10 rounded-lg bg-surface-container flex items-center justify-center group-hover:bg-primary-container group-hover:text-on-primary transition-colors">
<span className="material-symbols-outlined">campaign</span>
</div>
<span className="font-label-md text-on-surface">공지사항 관리</span>
<p className="text-label-sm text-on-surface-variant">학습자와 강사에게 새로운 소식을 알립니다.</p>
</a>
<a className="group p-md rounded-xl border border-surface-variant hover:border-primary-container hover:bg-primary-fixed/30 transition-all flex flex-col gap-sm" href="/admin/roadmaps">
<div className="w-10 h-10 rounded-lg bg-surface-container flex items-center justify-center group-hover:bg-primary-container group-hover:text-on-primary transition-colors">
<span className="material-symbols-outlined">map</span>
</div>
<span className="font-label-md text-on-surface">로드맵 관리</span>
<p className="text-label-sm text-on-surface-variant">플랫폼 학습 경로와 로드맵을 구성하고 관리합니다.</p>
</a>
</div>
</div>

</section>
</div>

  );
}
