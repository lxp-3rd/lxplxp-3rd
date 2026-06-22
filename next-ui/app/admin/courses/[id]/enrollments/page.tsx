'use client';

import Link from 'next/link';

// TODO: API 연동 필요
export default function AdminCourseEnrollmentsPage() {
  return (
    <>
<nav className="sticky top-0 w-full z-50 flex items-center justify-between px-margin-desktop bg-surface-container-lowest dark:bg-inverse-surface h-16 shadow-sm dark:shadow-none border-b border-surface-variant dark:border-outline-variant">
<div className="flex items-center gap-xl">
<span className="text-headline-md font-headline-md font-bold text-primary dark:text-primary-fixed">LXP Admin</span>
<div className="hidden md:flex items-center gap-lg h-full">
<a className="font-label-md text-label-md text-on-surface-variant dark:text-surface-variant pb-1 hover:text-primary dark:hover:text-primary-fixed transition-colors duration-200 cursor-pointer active:opacity-80" href="/admin/courses">Dashboard</a>
<a className="font-label-md text-label-md text-on-surface-variant dark:text-surface-variant pb-1 hover:text-primary dark:hover:text-primary-fixed transition-colors duration-200 cursor-pointer active:opacity-80" href="/admin/members/1">Courses</a>
<a className="font-label-md text-label-md text-primary dark:text-primary-fixed border-b-2 border-primary dark:border-primary-fixed pb-1 hover:text-primary dark:hover:text-primary-fixed transition-colors duration-200 cursor-pointer active:opacity-80" href="/admin/members/2">Enrollments</a>
<a className="font-label-md text-label-md text-on-surface-variant dark:text-surface-variant pb-1 hover:text-primary dark:hover:text-primary-fixed transition-colors duration-200 cursor-pointer active:opacity-80" href="/admin/members/3">Users</a>
<a className="font-label-md text-label-md text-on-surface-variant dark:text-surface-variant pb-1 hover:text-primary dark:hover:text-primary-fixed transition-colors duration-200 cursor-pointer active:opacity-80" href="/admin/members/4">Analytics</a>
</div>
</div>
<div className="flex items-center gap-md">
<button className="p-sm hover:bg-surface-container rounded-full transition-colors">
<span className="material-symbols-outlined text-on-surface-variant">notifications</span>
</button>
<button className="p-sm hover:bg-surface-container rounded-full transition-colors">
<span className="material-symbols-outlined text-on-surface-variant">settings</span>
</button>
<div className="w-8 h-8 rounded-full overflow-hidden border border-outline-variant ml-sm">
<img className="w-full h-full object-cover" alt="A professional headshot of an administrator for a high-tech learning platform. The person has a friendly, confident expression and is dressed in modern business casual attire. The background is a clean, softly blurred office environment with cool blue and grey tones, emphasizing a high-contrast and minimalist aesthetic. Lighting is bright and even, highlighting professional competence." src="https://lh3.googleusercontent.com/aida-public/AB6AXuCErcPy8VuDQHbV2NbF3gyr7hJBxGTNdgts-375GyWpnj3ozxZFzyyNeHV1OQiQZYtmdzsC-2KsjLfn7FM6XapXZ2GwRYalTS5Di56JCDPeogUoGvkiYV7qbLZINJTb7KztvfeRoCwfoRkXXfzSmYLGfYPUjtfpynioqdxH1QwDPh4cx4Ai7Lz0-RyOm-iIgbNJvEfEiqhC4fVIudZH_tL_lb0rl9XbX8e0m0DNTx8pOwAk48oB0vWBFFoSlWMJ_udny2Un65QMNJ_C" />
</div>
</div>
</nav>
<div className="flex-grow w-full max-w-[1280px] mx-auto px-margin-mobile md:px-margin-desktop py-xl">

<div className="mb-lg">
<Link href="/admin/courses" className="group flex items-center gap-xs font-label-md text-label-md text-on-surface-variant hover:text-primary transition-colors"><span className="material-symbols-outlined">arrow_back</span>
  강좌 관리로 돌아가기</Link>
</div>

<section className="bg-surface-container-lowest border border-surface-variant rounded-xl p-lg md:p-xl shadow-sm mb-xl flex flex-col md:flex-row gap-lg items-start md:items-center">
<div className="w-full md:w-48 h-32 rounded-lg overflow-hidden bg-surface-container flex-shrink-0">
<img className="w-full h-full object-cover" src="https://www.gstatic.com/labs-code/stitch/stitch-placeholder-300x300.svg" />
</div>
<div className="flex-grow">
<h1 className="font-headline-md text-headline-md text-on-surface mb-sm">데이터 중심의 UI/UX 디자인 실무 입문</h1>
<div className="flex flex-wrap items-center gap-md text-on-surface-variant">
<div className="flex items-center gap-xs">
<span className="material-symbols-outlined text-md">person</span>
<span className="font-body-md text-body-md">이하연 강사</span>
</div>
<div className="w-1 h-1 bg-outline-variant rounded-full"></div>
<div className="flex items-center gap-xs">
<span className="material-symbols-outlined text-md">group</span>
<span className="font-body-md text-body-md">총 <span className="font-bold text-primary">1,240</span>명 수강 중</span>
</div>
</div>
</div>
<div className="flex flex-col gap-sm w-full md:w-auto">
<span className="bg-secondary-container text-on-secondary-container px-md py-xs rounded-full font-label-md text-label-md text-center">Active Course</span>
</div>
</section>

<div className="bg-surface-container-lowest border border-surface-variant rounded-xl overflow-hidden shadow-sm">
<div className="px-lg py-md border-b border-surface-variant bg-surface-container-low flex justify-between items-center">
<h2 className="font-headline-md text-headline-md text-on-surface">수강생 목록</h2>
<div className="text-on-surface-variant font-label-sm text-label-sm">Total 6 Students</div>
</div>
<div className="overflow-x-auto">
<table className="w-full text-left border-collapse">
<thead>
<tr className="bg-surface-container-low border-b border-surface-variant">
<th className="px-lg py-md font-label-md text-label-md text-on-surface-variant">Nickname</th>
<th className="px-lg py-md font-label-md text-label-md text-on-surface-variant">Email</th>
<th className="px-lg py-md font-label-md text-label-md text-on-surface-variant">Enrollment Date</th>
<th className="px-lg py-md font-label-md text-label-md text-on-surface-variant">Progress</th>
<th className="px-lg py-md font-label-md text-label-md text-on-surface-variant text-right">Action</th>
</tr>
</thead>
<tbody className="divide-y divide-surface-variant">

<tr className="hover:bg-surface-bright transition-colors group">
<td className="px-lg py-md font-body-md text-body-md text-on-surface font-medium">김하늘</td>
<td className="px-lg py-md font-body-md text-body-md text-on-surface-variant">sky.kim@example.com</td>
<td className="px-lg py-md font-body-md text-body-md text-on-surface-variant">2024.03.15</td>
<td className="px-lg py-md">
<div className="flex items-center gap-sm">
<div className="w-24 h-2 bg-surface-container rounded-full overflow-hidden">
<div className="h-full bg-primary-container" style={{width: '85%'}}></div>
</div>
<span className="font-label-sm text-label-sm text-on-surface font-bold">85%</span>
</div>
</td>
<td className="px-lg py-md text-right">
<button className="px-md py-1.5 rounded border border-error text-error font-label-md text-label-md hover:bg-error-container/10 transition-all active:scale-95">수강 취소</button>
</td>
</tr>

<tr className="hover:bg-surface-bright transition-colors group">
<td className="px-lg py-md font-body-md text-body-md text-on-surface font-medium">이준호</td>
<td className="px-lg py-md font-body-md text-body-md text-on-surface-variant">junho.lee@example.com</td>
<td className="px-lg py-md font-body-md text-body-md text-on-surface-variant">2024.03.12</td>
<td className="px-lg py-md">
<div className="flex items-center gap-sm">
<div className="w-24 h-2 bg-surface-container rounded-full overflow-hidden">
<div className="h-full bg-primary-container" style={{width: '42%'}}></div>
</div>
<span className="font-label-sm text-label-sm text-on-surface font-bold">42%</span>
</div>
</td>
<td className="px-lg py-md text-right">
<button className="px-md py-1.5 rounded border border-error text-error font-label-md text-label-md hover:bg-error-container/10 transition-all active:scale-95">수강 취소</button>
</td>
</tr>

<tr className="hover:bg-surface-bright transition-colors group">
<td className="px-lg py-md font-body-md text-body-md text-on-surface font-medium">박지민</td>
<td className="px-lg py-md font-body-md text-body-md text-on-surface-variant">jimin.park@example.com</td>
<td className="px-lg py-md font-body-md text-body-md text-on-surface-variant">2024.03.10</td>
<td className="px-lg py-md">
<div className="flex items-center gap-sm">
<div className="w-24 h-2 bg-surface-container rounded-full overflow-hidden">
<div className="h-full bg-primary-container" style={{width: '100%'}}></div>
</div>
<span className="font-label-sm text-label-sm text-on-surface font-bold">100%</span>
</div>
</td>
<td className="px-lg py-md text-right">
<button className="px-md py-1.5 rounded border border-error text-error font-label-md text-label-md hover:bg-error-container/10 transition-all active:scale-95">수강 취소</button>
</td>
</tr>

<tr className="hover:bg-surface-bright transition-colors group">
<td className="px-lg py-md font-body-md text-body-md text-on-surface font-medium">최서연</td>
<td className="px-lg py-md font-body-md text-body-md text-on-surface-variant">sy.choi@example.com</td>
<td className="px-lg py-md font-body-md text-body-md text-on-surface-variant">2024.03.05</td>
<td className="px-lg py-md">
<div className="flex items-center gap-sm">
<div className="w-24 h-2 bg-surface-container rounded-full overflow-hidden">
<div className="h-full bg-primary-container" style={{width: '15%'}}></div>
</div>
<span className="font-label-sm text-label-sm text-on-surface font-bold">15%</span>
</div>
</td>
<td className="px-lg py-md text-right">
<button className="px-md py-1.5 rounded border border-error text-error font-label-md text-label-md hover:bg-error-container/10 transition-all active:scale-95">수강 취소</button>
</td>
</tr>

<tr className="hover:bg-surface-bright transition-colors group">
<td className="px-lg py-md font-body-md text-body-md text-on-surface font-medium">정우진</td>
<td className="px-lg py-md font-body-md text-body-md text-on-surface-variant">wj.jung@example.com</td>
<td className="px-lg py-md font-body-md text-body-md text-on-surface-variant">2024.03.01</td>
<td className="px-lg py-md">
<div className="flex items-center gap-sm">
<div className="w-24 h-2 bg-surface-container rounded-full overflow-hidden">
<div className="h-full bg-primary-container" style={{width: '60%'}}></div>
</div>
<span className="font-label-sm text-label-sm text-on-surface font-bold">60%</span>
</div>
</td>
<td className="px-lg py-md text-right">
<button className="px-md py-1.5 rounded border border-error text-error font-label-md text-label-md hover:bg-error-container/10 transition-all active:scale-95">수강 취소</button>
</td>
</tr>

<tr className="hover:bg-surface-bright transition-colors group">
<td className="px-lg py-md font-body-md text-body-md text-on-surface font-medium">윤도현</td>
<td className="px-lg py-md font-body-md text-body-md text-on-surface-variant">dh.yoon@example.com</td>
<td className="px-lg py-md font-body-md text-body-md text-on-surface-variant">2024.02.28</td>
<td className="px-lg py-md">
<div className="flex items-center gap-sm">
<div className="w-24 h-2 bg-surface-container rounded-full overflow-hidden">
<div className="h-full bg-primary-container" style={{width: '0%'}}></div>
</div>
<span className="font-label-sm text-label-sm text-on-surface font-bold">0%</span>
</div>
</td>
<td className="px-lg py-md text-right">
<button className="px-md py-1.5 rounded border border-error text-error font-label-md text-label-md hover:bg-error-container/10 transition-all active:scale-95">수강 취소</button>
</td>
</tr>
</tbody>
</table>
</div>
<div className="px-lg py-md bg-surface-container-low border-t border-surface-variant flex justify-between items-center">
<div className="flex items-center gap-sm">
<button className="p-xs rounded hover:bg-surface-container text-on-surface-variant transition-colors disabled:opacity-30" disabled>
<span className="material-symbols-outlined">chevron_left</span>
</button>
<span className="font-label-md text-label-md text-on-surface font-bold">1</span>
<button className="p-xs rounded hover:bg-surface-container text-on-surface-variant transition-colors">
<span className="material-symbols-outlined">chevron_right</span>
</button>
</div>
</div>
</div>

<div className="hidden mt-xl flex flex-col items-center justify-center py-20 px-lg bg-surface-container-lowest border border-dashed border-outline-variant rounded-xl text-center" id="empty-state">
<div className="w-16 h-16 bg-surface-container rounded-full flex items-center justify-center mb-md">
<span className="material-symbols-outlined text-4xl text-outline">group_off</span>
</div>
<p className="font-headline-md text-headline-md text-on-surface-variant mb-xs">현재 수강 중인 회원이 없습니다.</p>
<p className="font-body-md text-body-md text-outline">회원 등록이 완료되면 이곳에 목록이 표시됩니다.</p>
</div>
</div>
    </>
  );
}
