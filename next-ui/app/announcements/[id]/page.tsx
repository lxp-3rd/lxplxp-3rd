'use client';

import Link from 'next/link';

import { TopNavBar } from '@/components/TopNavBar';
import { Footer } from '@/components/Footer';
// TODO: API 연동 필요

export default function AnnouncementDetailPage() {
  return (
    <>
      <TopNavBar />
      <main className="pt-16 min-h-screen bg-background">
<div className="pt-xl pb-xl mt-16 px-gutter max-w-[1200px] mx-auto">

<div className="mb-lg flex items-center gap-xs">
<Link href="/announcements" className="flex items-center gap-xs text-on-surface-variant hover:text-primary transition-all group">
<span className="material-symbols-outlined text-[20px]">arrow_back</span>
<span className="text-label-md">목록으로 돌아가기</span>
</Link>
</div>
<div className="grid grid-cols-12 gap-gutter">

<article className="col-span-12 lg:col-span-9">
<div className="bg-surface-container-lowest border border-outline-variant p-lg lg:p-xl rounded-xl">

<div className="text-body-lg text-on-surface-variant leading-relaxed space-y-md">
<p>안녕하세요, LXP 학습 플랫폼입니다. 더 나은 학습 환경과 시스템 안정성 확보를 위해 정기 점검을 실시할 예정입니다.</p>
<div className="bg-surface-container p-lg rounded-xl my-lg border-l-4 border-primary-container">
<h3 className="text-headline-md text-on-surface mb-sm">정기 점검 상세 안내</h3>
<ul className="space-y-xs text-body-md">
<li className="flex gap-sm"><span className="font-bold text-primary shrink-0">점검 일시:</span> 2024년 5월 25일 (토) 00:00 ~ 06:00 (약 6시간)</li>
<li className="flex gap-sm"><span className="font-bold text-primary shrink-0">점검 영향:</span> 점검 시간 내 플랫폼 모든 서비스 접속 불가</li>
<li className="flex gap-sm"><span className="font-bold text-primary shrink-0">주요 내용:</span> 서버 보안 패치, 데이터베이스 최적화, 모바일 앱 연동 개선</li>
</ul>
</div>
<p>점검 시간 동안은 강의 시청 및 과제 제출이 불가능하오니, 학습 일정에 참고하시어 차질 없으시길 바랍니다. 점검 시간은 작업 진행 상황에 따라 약간의 변동이 있을 수 있으며, 조기 완료 시 즉시 서비스를 재개하도록 하겠습니다.</p>
<div className="w-full h-80 my-lg rounded-xl overflow-hidden relative border border-outline-variant">
<img alt="Server Room" className="w-full h-full object-cover" src="https://lh3.googleusercontent.com/aida-public/AB6AXuAJzhtbnWrkzVN3dBcrz_hFvNMO1xpLO-6qrHLrKisqvmjksgVArSKCkS6ALE17DFP9c7e-qIjIXsVkDXt7l3jDtfo2iQb3Q09XOk8HHj9Aw2Fq36PetH2CzP4pRyGEgg6pWWEc6KmpQDeZQ1GPskqAYU-1DOQvGVfaRItfkfT1d4JkVev5cT1FT2ZKypSOgqPe2dGRivZPfREW4Hrj2zgmXuKR_JhSDtpiOtXWc3iGM1dN4Kdl7F85PbK7KzCzo0kUTTQ6KppeV10U" />
<div className="absolute inset-0 bg-gradient-to-t from-black/20 to-transparent"></div>
</div>
<p>항상 LXP를 이용해 주시는 학습자 여러분께 감사의 말씀을 드립니다. 더욱 원활한 학습 경험을 제공하기 위해 최선을 다하겠습니다.</p>
<p className="pt-lg border-t border-outline-variant mt-xl text-[14px] italic">본 공지는 모든 학습자에게 발송되는 공통 안내입니다. 문의사항은 고객센터를 이용해 주세요.</p>
</div>

<div className="mt-xl flex justify-between items-center">

<div className="flex gap-md">

</div>
</div>
</div>

</article>

<aside className="col-span-12 lg:col-span-3 space-y-lg">
<div className="bg-surface-container-lowest border border-outline-variant p-lg rounded-xl">
<h4 className="text-label-md text-on-surface-variant uppercase tracking-wider mb-md">최근 공지사항</h4>
<ul className="space-y-md">
<li>
<a className="group block" href="/announcements">
<p className="text-[14px] font-semibold text-on-surface line-clamp-2 group-hover:text-primary mb-1">앱 버전 2.1 업데이트 주요 기능 소개</p>
<span className="text-label-sm text-on-surface-variant">2024.05.15</span>
</a>
</li>
<li className="border-t border-outline-variant pt-md">
<a className="group block" href="/announcements">
<p className="text-[14px] font-semibold text-on-surface line-clamp-2 group-hover:text-primary mb-1">개인정보 처리방침 일부 개정 안내</p>
<span className="text-label-sm text-on-surface-variant">2024.05.12</span>
</a>
</li>
<li className="border-t border-outline-variant pt-md">
<a className="group block" href="/announcements">
<p className="text-[14px] font-semibold text-on-surface line-clamp-2 group-hover:text-primary mb-1">5월 학습 챌린지 당첨자 발표</p>
<span className="text-label-sm text-on-surface-variant">2024.05.08</span>
</a>
</li>
</ul>
<button className="w-full mt-lg text-primary text-label-md hover:underline font-bold">전체 보기</button>
</div>

</aside>
</div>
</div>
</main>
      <Footer />
    </>
  );
}
