'use client';

import { TopNavBar } from '@/components/TopNavBar';
import { Footer } from '@/components/Footer';
// TODO: API 연동 필요

export default function MyPage() {
  return (
    <>
      <TopNavBar />
      <main className="pt-16 min-h-screen bg-background">

<div className="pt-xxl mt-16 pb-xxl px-gutter max-w-container-max mx-auto">
<div className="grid grid-cols-12 gap-gutter">

<aside className="col-span-12 md:col-span-3">
<div className="bg-surface-container-lowest border border-outline-variant rounded-xl p-lg space-y-md">
<div className="flex items-center gap-md pb-md border-b border-outline-variant">
<div className="w-12 h-12 rounded-full bg-primary-container/10 flex items-center justify-center text-primary">
<span className="material-symbols-outlined text-headline-md" style={{fontVariationSettings: "'FILL' 1"}}>person</span>
</div>
<div>
<h2 className="text-label-md font-label-md text-on-surface">김지혜 님</h2>
<p className="text-body-sm font-body-sm text-on-surface-variant">학습자 프로필</p>
</div>
</div>
<nav className="flex flex-col gap-xs">
<a className="flex items-center gap-sm p-sm rounded-lg bg-primary text-white font-bold" href="/courses">
<span className="material-symbols-outlined text-md">account_circle</span>
<span className="text-label-md font-label-md">프로필 정보</span>
</a>

<a className="flex items-center gap-sm p-sm rounded-lg text-on-surface-variant hover:bg-surface-container-low transition-all" href="/roadmaps">
<span className="material-symbols-outlined text-md">settings</span>
<span className="text-label-md font-label-md">회원 탈퇴&nbsp;</span>
</a>
</nav>
</div>
</aside>

<section className="col-span-12 md:col-span-9">
<div className="bg-surface-container-lowest border border-outline-variant rounded-xl overflow-hidden">

<div className="h-32 bg-primary relative">
<div className="absolute inset-0 opacity-10" style={{backgroundImage: 'radial-gradient(#ffffff 1px, transparent 1px)', backgroundSize: '20px 20px'}}></div>
</div>
<div className="px-xl pb-xl">

<div className="relative -mt-12 mb-lg flex items-end gap-lg">
<div className="w-24 h-24 rounded-xl bg-surface-container-lowest p-1 shadow-sm">
<div className="w-full h-full rounded-lg bg-surface-container-high overflow-hidden border border-outline-variant">
<img className="w-full h-full object-cover" alt="User profile icon" src="https://lh3.googleusercontent.com/aida-public/AB6AXuAG-u_sFDL95p0fwB45WsfOzLLSHZz-k6Z7cOyV2ebkTxQ3McuKFO1WPnpooMFnPB68cBATl-5mMw_bdrG_0EDYJKv7-WklZ17J-NEKOZRcLGHkvcgnf_-4TyP-5mpYmNLCjeyUYkLrH4eaAfQCn8xLE51yekCfDQNT9nkywhFlu2UJmKdd66A5LQRv0F-AQ-uvTuH2bZ9QwXQxRU0eeaI41_JbtBk_rfTb6v8ftf8-nOY3drUXHkrkVf8syx1_UZBHZtqzk2IPHkDC" />
</div>
</div>
<div className="pb-base">
<h1 className="text-headline-md font-headline-md text-on-surface">프로필 정보</h1>
<p className="text-body-sm font-body-sm text-on-surface-variant">LXP 학습자님의 소중한 정보를 안전하게 관리합니다.</p>
</div>
</div>

<div className="grid grid-cols-1 md:grid-cols-2 gap-xl border-t border-outline-variant pt-xl">

<div className="space-y-lg">
<div>
<label className="block text-label-md font-label-md text-on-surface-variant mb-xs">이메일 주소</label>
<div className="bg-surface-container-lowest border border-outline-variant rounded-lg p-md flex items-center justify-between">
<span className="text-body-md font-body-md text-on-surface">jihye.kim@example.com</span>
<span className="material-symbols-outlined text-on-surface-variant scale-75">lock</span>
</div>
<p className="mt-xs text-body-sm font-body-sm text-on-surface-variant opacity-70">로그인 계정으로 사용되는 이메일입니다.</p>
</div>
<div>
<label className="block text-label-md font-label-md text-on-surface-variant mb-xs">가입일</label>
<div className="bg-surface-container-lowest border border-outline-variant rounded-lg p-md flex items-center justify-between">
<span className="text-body-md font-body-md text-on-surface">2024년 03월 15일</span>
<span className="material-symbols-outlined text-on-surface-variant scale-75">calendar_today</span>
</div>
</div>
</div>

<div className="bg-surface-container-low rounded-xl p-lg border border-outline-variant">
<h3 className="text-label-md font-label-md text-primary mb-md flex items-center gap-xs">
<span className="material-symbols-outlined text-lg">edit</span>
                                    정보 수정
                                </h3>
<div className="space-y-lg">
<div className="group">
<label className="block text-label-md font-label-md text-on-surface-variant mb-xs" htmlFor="nickname">닉네임</label>
<div className="relative">
<input className="w-full bg-surface-container-lowest border border-outline-variant rounded-lg p-md text-body-md font-body-md custom-input-focus transition-all" id="nickname" type="text" defaultValue="김지혜" />
<div className="absolute right-md top-1/2 -translate-y-1/2 text-label-sm font-label-sm text-on-surface-variant">3 / 10</div>
</div>
<p className="mt-xs text-body-sm font-body-sm text-on-surface-variant">커뮤니티 및 대시보드에 표시되는 이름입니다.</p>
</div>
<div className="flex items-center justify-end gap-md pt-md">
<button className="px-lg py-xs text-label-md font-label-md text-on-surface-variant hover:bg-surface-container-highest rounded-lg transition-all">
                                            취소
                                        </button>
<button className="bg-primary hover:bg-on-primary-container text-white px-xl py-xs rounded-lg text-label-md font-label-md transition-all shadow-sm active:scale-[0.98]" id="saveBtn">
                                            변경사항 저장
                                        </button>
</div>
</div>
</div>
</div>

<div className="mt-xl grid grid-cols-1 md:grid-cols-3 gap-lg">
<div className="p-lg bg-surface-container-lowest border border-outline-variant rounded-xl text-center">
<p className="text-label-sm font-label-sm text-on-surface-variant mb-xs">수강 중인 강의</p>
<p className="text-headline-md font-headline-md text-primary">12개</p>
</div>
<div className="p-lg bg-surface-container-lowest border border-outline-variant rounded-xl text-center">
<p className="text-label-sm font-label-sm text-on-surface-variant mb-xs">완료한 로드맵</p>
<p className="text-headline-md font-headline-md text-primary">2개</p>
</div>

</div>
</div>
</div>
</section>
</div>
</div>

<div className="fixed bottom-lg left-1/2 -translate-x-1/2 bg-inverse-surface text-inverse-on-surface px-xl py-sm rounded-full text-label-md shadow-xl transition-all opacity-0 translate-y-10 pointer-events-none z-[100]" id="toast">
        성공적으로 저장되었습니다.
    </div>
      </main>
      <Footer />
    </>
  );
}
