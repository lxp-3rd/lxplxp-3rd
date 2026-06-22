'use client';

import Link from 'next/link';

import { TopNavBar } from '@/components/TopNavBar';
import { Footer } from '@/components/Footer';
// TODO: API 연동 필요

export default function AnnouncementsPage() {
  return (
    <>
      <TopNavBar />
      <main className="pt-16 min-h-screen bg-background">
<div className="pt-xxl pb-xxl min-h-screen">
<div className="max-w-container-max mx-auto px-gutter mt-16">

<div className="mb-xl flex flex-col md:flex-row md:items-end justify-between gap-md">
<div>
<h1 className="font-headline-lg text-headline-lg text-on-surface mb-xs">공지사항</h1>
<p className="text-on-surface-variant font-body-md text-body-md">LXP의 새로운 소식과 학습 안내를 확인하세요.</p>
</div>
<div className="flex items-center space-x-xs">

</div>
</div>

<div className="grid grid-cols-1 md:grid-cols-12 gap-lg mb-xxl">
<div className="md:col-span-8 bg-surface-container-lowest border border-outline-variant rounded-xl p-xl flex flex-col justify-between relative overflow-hidden group hover:shadow-lg transition-all duration-300">
<div className="relative z-10">
<span className="bg-primary-container text-on-primary-container px-sm py-1 rounded-full text-label-sm font-label-sm mb-md inline-block">최신 공지</span>
<h2 className="text-headline-md font-headline-md mb-md group-hover:text-primary transition-colors">2024 하반기 신규 로드맵 공개 및 플랫폼 고도화 안내</h2>
<p className="text-on-surface-variant font-body-md text-body-md max-w-2xl">더욱 효율적인 학습 경험을 제공하기 위해 LXP가 대규모 업데이트를 진행했습니다. 새롭게 추가된 AI 학습 비서와 30여 개의 전공별 로드맵을 지금 확인해 보세요.</p>
</div>
<div className="mt-xl flex items-center justify-between relative z-10">
<div className="flex items-center space-x-md">
<span className="text-on-surface-variant font-label-md text-label-md">운영지원팀</span>
<span className="w-1 h-1 bg-outline-variant rounded-full"></span>
<span className="text-on-surface-variant font-label-md text-label-md">2024.05.20</span>
</div>
<Link href="/announcements/1" className="bg-primary text-on-primary px-lg py-sm rounded-lg font-label-md text-label-md hover:opacity-90 transition-opacity">자세히 보기</Link>
</div>

<div className="absolute -right-16 -bottom-16 opacity-5 group-hover:opacity-10 transition-opacity">
<span className="material-symbols-outlined text-primary" style={{fontSize: '240px'}}>campaign</span>
</div>
</div>

</div>

<div className="bg-surface-container-lowest border border-outline-variant rounded-xl overflow-hidden mb-xl">
<div className="overflow-x-auto">
<table className="w-full text-left border-collapse">
<thead className="bg-surface-container-low border-b border-outline-variant">
<tr>
<th className="px-xl py-lg font-label-md text-label-md text-on-surface-variant uppercase tracking-wider w-[10%] text-center">번호</th>
<th className="px-xl py-lg font-label-md text-label-md text-on-surface-variant uppercase tracking-wider">제목</th>
<th className="px-xl py-lg font-label-md text-label-md text-on-surface-variant uppercase tracking-wider w-[15%]">작성자</th>
<th className="px-xl py-lg font-label-md text-label-md text-on-surface-variant uppercase tracking-wider w-[15%]">날짜</th>
</tr>
</thead>
<tbody className="divide-y divide-outline-variant">

<tr 
  className="hover:bg-surface-container-lowest transition-colors cursor-pointer group" 
  onClick={() => window.location.href='/announcements/1'}>
<td className="px-xl py-lg text-center font-body-md text-body-md text-on-surface-variant">6</td>
<td className="px-xl py-lg">
<div className="flex items-center gap-sm">
<span className="font-label-md text-label-md text-on-surface group-hover:text-primary transition-colors">5월 정기 시스템 점검 안내 (05/25 02:00~06:00)</span>
<span className="bg-primary-container/20 text-primary text-[10px] px-1.5 py-0.5 rounded font-bold">NEW</span>
</div>
</td>
<td className="px-xl py-lg font-body-md text-body-md text-on-surface-variant">시스템관리팀</td>
<td className="px-xl py-lg font-body-md text-body-md text-on-surface-variant">2024.05.21</td>
</tr>

<tr className="hover:bg-surface-container-lowest transition-colors cursor-pointer group" onClick={() => window.location.href='/announcements/2'}>
<td className="px-xl py-lg text-center font-body-md text-body-md text-on-surface-variant">5</td>
<td className="px-xl py-lg">
<span className="font-label-md text-label-md text-on-surface group-hover:text-primary transition-colors">모바일 앱 안정화 버전 업데이트 안내</span>
</td>
<td className="px-xl py-lg font-body-md text-body-md text-on-surface-variant">기술지원팀</td>
<td className="px-xl py-lg font-body-md text-body-md text-on-surface-variant">2024.05.18</td>
</tr>

<tr className="hover:bg-surface-container-lowest transition-colors cursor-pointer group" onClick={() => window.location.href='/announcements/3'}>
<td className="px-xl py-lg text-center font-body-md text-body-md text-on-surface-variant">4</td>
<td className="px-xl py-lg">
<span className="font-label-md text-label-md text-on-surface group-hover:text-primary transition-colors">커뮤니티 이용 가이드라인 개정 알림</span>
</td>
<td className="px-xl py-lg font-body-md text-body-md text-on-surface-variant">운영지원팀</td>
<td className="px-xl py-lg font-body-md text-body-md text-on-surface-variant">2024.05.15</td>
</tr>

<tr className="hover:bg-surface-container-lowest transition-colors cursor-pointer group" onClick={() => window.location.href='/announcements/4'}>
<td className="px-xl py-lg text-center font-body-md text-body-md text-on-surface-variant">3</td>
<td className="px-xl py-lg">
<span className="font-label-md text-label-md text-on-surface group-hover:text-primary transition-colors">신규 자격증 과정 오픈 이벤트 당첨자 발표</span>
</td>
<td className="px-xl py-lg font-body-md text-body-md text-on-surface-variant">마케팅팀</td>
<td className="px-xl py-lg font-body-md text-body-md text-on-surface-variant">2024.05.12</td>
</tr>

<tr className="hover:bg-surface-container-lowest transition-colors cursor-pointer group" onClick={() => window.location.href='/announcements/5'}>
<td className="px-xl py-lg text-center font-body-md text-body-md text-on-surface-variant">2</td>
<td className="px-xl py-lg">
<span className="font-label-md text-label-md text-on-surface group-hover:text-primary transition-colors">학습 포인트 제도 변경에 따른 적립률 조정 안내</span>
</td>
<td className="px-xl py-lg font-body-md text-body-md text-on-surface-variant">운영지원팀</td>
<td className="px-xl py-lg font-body-md text-body-md text-on-surface-variant">2024.05.08</td>
</tr>

<tr className="hover:bg-surface-container-lowest transition-colors cursor-pointer group" onClick={() => window.location.href='/announcements/6'}>
<td className="px-xl py-lg text-center font-body-md text-body-md text-on-surface-variant">1</td>
<td className="px-xl py-lg">
<span className="font-label-md text-label-md text-on-surface group-hover:text-primary transition-colors">개인정보 처리방침 변경 안내</span>
</td>
<td className="px-xl py-lg font-body-md text-body-md text-on-surface-variant">법무지원팀</td>
<td className="px-xl py-lg font-body-md text-body-md text-on-surface-variant">2024.05.01</td>
</tr>
</tbody>
</table>
</div>
</div>

<div className="flex justify-center items-center gap-xs">
<button className="w-10 h-10 flex items-center justify-center rounded-lg border border-outline-variant hover:bg-surface-container transition-colors disabled:opacity-30" disabled>
<span className="material-symbols-outlined">chevron_left</span>
</button>
<button className="w-10 h-10 flex items-center justify-center rounded-lg bg-primary text-on-primary font-label-md text-label-md">1</button>
<button className="w-10 h-10 flex items-center justify-center rounded-lg border border-outline-variant hover:bg-surface-container font-label-md text-label-md transition-colors">2</button>
<button className="w-10 h-10 flex items-center justify-center rounded-lg border border-outline-variant hover:bg-surface-container font-label-md text-label-md transition-colors">3</button>
<button className="w-10 h-10 flex items-center justify-center rounded-lg border border-outline-variant hover:bg-surface-container transition-colors">
<span className="material-symbols-outlined">chevron_right</span>
</button>
</div>
</div>
</div>
</main>
      <Footer />
    </>
  );
}
