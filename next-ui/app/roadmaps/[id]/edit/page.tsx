'use client';

import Link from 'next/link';
import { useParams } from 'next/navigation';

import { TopNavBar } from '@/components/TopNavBar';
import { Footer } from '@/components/Footer';
// TODO: API 연동 필요

export default function RoadmapEditPage() {
  const { id } = useParams<{ id: string }>();

  return (
    <>
      <TopNavBar />
      <main className="pt-16 min-h-screen bg-background">
<div className="flex-grow w-full max-w-screen-2xl mx-auto px-lg py-xl">

<section className="mb-xl flex flex-col md:flex-row md:items-end justify-between gap-md">
<div>
<Link href={`/roadmaps/${id}`} className="flex items-center gap-xs text-primary font-label-md text-label-md mb-sm hover:underline active:scale-95 transition-all">
<span className="material-symbols-outlined text-[18px]">arrow_back</span>
  로드맵으로 돌아가기
</Link>
<h1 className="font-headline-lg text-headline-lg text-on-surface mb-xs">로드맵 수정</h1>
<p className="font-body-md text-body-md text-on-surface-variant">학생들의 학습 경로를 체계적으로 설계하고 가이드하세요.</p>
</div>
<button
  type="button"
  className="flex items-center gap-xs px-xl py-md bg-primary text-on-primary font-label-md text-label-md rounded-xl shadow-sm hover:brightness-105 active:scale-95 transition-all"
>
  <span className="material-symbols-outlined text-[20px]">check</span>
  수정하기
</button>
</section>
<div className="grid grid-cols-1 lg:grid-cols-12 gap-gutter items-start">

<div className="lg:col-span-8 flex flex-col gap-xl">

<div className="bg-surface-container-lowest p-lg rounded-xl border border-surface-container-highest shadow-sm">
<h2 className="font-headline-md text-headline-md text-on-surface mb-lg">기본 정보</h2>
<div className="flex flex-col md:flex-row gap-lg">
<div className="flex-shrink-0 w-full md:w-64">
<label className="block font-label-md text-label-md text-on-surface-variant mb-sm">로드맵 썸네일</label>
<div className="aspect-video w-full rounded-lg border-2 border-dashed border-outline-variant bg-surface-container flex flex-col items-center justify-center gap-sm cursor-pointer hover:bg-surface-container-high transition-colors group">
<span className="material-symbols-outlined text-outline group-hover:text-primary text-[32px]">add_photo_alternate</span>
<span className="font-label-sm text-label-sm text-on-surface-variant">이미지 업로드</span>
</div>
</div>
<div className="flex-grow flex flex-col gap-md">
<div>
<label className="block font-label-md text-label-md text-on-surface-variant mb-sm">로드맵 명칭</label>
<input className="w-full px-md py-sm rounded-lg border border-outline-variant focus:border-primary focus:ring-2 focus:ring-primary/20 transition-all font-body-md text-body-md" placeholder="예: 시니어 프론트엔드 개발자 마스터 코스" type="text" />
</div>
<div>
<label className="block font-label-md text-label-md text-on-surface-variant mb-sm">로드맵 소개</label>
<textarea className="w-full px-md py-sm rounded-lg border border-outline-variant focus:border-primary focus:ring-2 focus:ring-primary/20 transition-all font-body-md text-body-md resize-none" placeholder="로드맵의 목표와 대상 독자를 간략하게 설명해주세요." rows={4}></textarea>
</div>
</div>
</div>
</div>

<div className="bg-surface-container-lowest p-lg rounded-xl border border-surface-container-highest shadow-sm">
<div className="flex flex-col md:flex-row justify-between md:items-center mb-lg gap-md">
<h2 className="font-headline-md text-headline-md text-on-surface">강좌 선택</h2>
<div className="relative flex-grow max-w-sm">
<span className="absolute left-3 top-1/2 -translate-y-1/2 material-symbols-outlined text-outline text-[20px]">search</span>
<input className="w-full pl-10 pr-4 py-sm bg-surface rounded-lg border border-outline-variant focus:border-primary focus:ring-2 focus:ring-primary/20 transition-all font-body-md text-body-md" placeholder="강좌명 또는 강사명으로 검색" type="text" />
</div>
</div>
<div className="grid grid-cols-1 md:grid-cols-2 gap-md overflow-y-auto max-h-[500px] pr-sm custom-scrollbar">

<div className="flex items-center gap-md p-sm rounded-lg border border-surface-container-high hover:border-primary transition-all group cursor-pointer bg-surface-bright">
<div className="w-20 h-14 rounded overflow-hidden flex-shrink-0">
<img className="w-full h-full object-cover" alt="React 마스터 클래스" src="https://lh3.googleusercontent.com/aida-public/AB6AXuBTJjrKdPbV0PoK-nOt6pDibrOYft8LEYlFHLw9TK-n8KBycW3-Usfd1NzqjBJY0Sv7xvxoWcs2G0GgDgA5a1OFNR617ZcTGxGpqEzzlAPFouNPE3cx7y8e7oRHGgClRIiV6fwDIYAbmVpQJtK5FhxewL3PqDvAOjRPk8nYnHl7DPmbZaSuKrwyO7AEpQ8Exnd1KQOC806vnVDpy2-tc7yfhFJSO2q8tRxXTEsTJR9ky_5WOfu1F1qfxkIxFt6Rrdhg2AR1NirPcEEn" />
</div>
<div className="flex-grow min-w-0">
<h4 className="font-label-md text-label-md text-on-surface truncate">React 마스터 클래스</h4>
<p className="font-label-sm text-label-sm text-on-surface-variant">김철수 강사</p>
</div>
<button className="material-symbols-outlined text-outline group-hover:text-primary transition-colors">add_circle</button>
</div>
<div className="flex items-center gap-md p-sm rounded-lg border border-primary bg-primary/5 transition-all group cursor-pointer">
<div className="w-20 h-14 rounded overflow-hidden flex-shrink-0">
<img className="w-full h-full object-cover" alt="TypeScript 실무 가이드" src="https://lh3.googleusercontent.com/aida-public/AB6AXuBTFsrMyRJ8G90le92gp3WK7tG_QP3PUY_O0skuYDzrXdyn_LH8iA10E0nDjdXUaHZ-JLuzinRBxuO_JnE_31F8HnoEgAzMhXaBd5PYQcVeOp_yPcy_HMwCTJbNZeFH4lpTqOMveFyobcXmxpf6fRS9KSOrJb_BOMRqpWea9P2F72iZuTVuuZGh7NxrI8T-nwXRJgQZCwruSVRcFi3M5PSaSRaGSHWhsAhijl6Zdl9pL0XfkYFaSiR9FOa234rF2CSAaS1FTshW__dz" />
</div>
<div className="flex-grow min-w-0">
<h4 className="font-label-md text-label-md text-on-surface truncate">TypeScript 실무 가이드</h4>
<p className="font-label-sm text-label-sm text-on-surface-variant">이영희 강사</p>
</div>
<button className="material-symbols-outlined text-primary" style={{fontVariationSettings: "'FILL' 1"}}>check_circle</button>
</div>
<div className="flex items-center gap-md p-sm rounded-lg border border-surface-container-high hover:border-primary transition-all group cursor-pointer bg-surface-bright">
<div className="w-20 h-14 rounded overflow-hidden flex-shrink-0">
<img className="w-full h-full object-cover" alt="고급 CSS 애니메이션" src="https://lh3.googleusercontent.com/aida-public/AB6AXuDtdxqajN_uTuLPGyQ4AQ1tzt8ubzYz7NNEGoW95QCiz_qEhq_1leReFu-jJX7h98d8WyrXj4c4JbLhCTJVqGJhAKQD4yID7AadPawAvLJ0964aVPZ6BdkUYDExp1e9ejhgKlzXnAMd_36KfS_e1i08nJuOxQ21_5tdVl5qa0dHXiHzw02fgFsV12d84jiskk0RYwVo7Csp75_WrwrK0hWhrtUYhm2tCzHfwcTBgY95K1G_0k1mfAmY9aGTmTkt5KycehagjlVa2sPt" />
</div>
<div className="flex-grow min-w-0">
<h4 className="font-label-md text-label-md text-on-surface truncate">고급 CSS 애니메이션</h4>
<p className="font-label-sm text-label-sm text-on-surface-variant">박지성 강사</p>
</div>
<button className="material-symbols-outlined text-outline group-hover:text-primary transition-colors">add_circle</button>
</div>
<div className="flex items-center gap-md p-sm rounded-lg border border-surface-container-high hover:border-primary transition-all group cursor-pointer bg-surface-bright">
<div className="w-20 h-14 rounded overflow-hidden flex-shrink-0">
<img className="w-full h-full object-cover" alt="UI/UX 디자인 입문" src="https://lh3.googleusercontent.com/aida-public/AB6AXuCsaBXHf4mwDUbWI-LuZnS05X-A6EC4IkvwcgEDCEsVjRs0wdzbAVEBI9Uj7v3x_VDoRNqIOtFwy0u2cncPgH-nmqZ5VltYWeChzV2otDTDJ_MK0ixN0hXoeH1OTLgYykpLAU1rFmBwhVZJQjVtO7WPvAJ_6hGhdrWyBt2SIXOk7-G4CUs_Z2Ml66fA0HhChWofoipPEwDrvCU8pzOWjTXkPxD3CRc_dOfCPbB6A6poznoMl4sADH8eh3Un3ZSMKyKJEnHOgpm9l9v2" />
</div>
<div className="flex-grow min-w-0">
<h4 className="font-label-md text-label-md text-on-surface truncate">UI/UX 디자인 입문</h4>
<p className="font-label-sm text-label-sm text-on-surface-variant">최수현 강사</p>
</div>
<button className="material-symbols-outlined text-outline group-hover:text-primary transition-colors">add_circle</button>
</div>
</div>
</div>
</div>

<div className="lg:col-span-4 flex flex-col gap-lg sticky top-24">

<div className="bg-surface-container-lowest rounded-xl border border-surface-container-highest shadow-sm overflow-hidden">
<div className="bg-primary px-lg py-md flex justify-between items-center">
<h3 className="font-label-md text-label-md text-on-primary">선택된 강좌</h3>
<span className="bg-white/20 text-white font-label-sm text-label-sm px-2 py-0.5 rounded-full">3개</span>
</div>
<div className="p-md flex flex-col gap-sm max-h-[300px] overflow-y-auto custom-scrollbar">
<div className="flex items-center justify-between p-sm rounded bg-surface-container-low group">
<span className="font-label-sm text-label-sm text-on-surface truncate">TypeScript 실무 가이드</span>
<button className="material-symbols-outlined text-outline-variant hover:text-error transition-colors text-[18px]">close</button>
</div>
<div className="flex items-center justify-between p-sm rounded bg-surface-container-low group">
<span className="font-label-sm text-label-sm text-on-surface truncate">React 마스터 클래스</span>
<button className="material-symbols-outlined text-outline-variant hover:text-error transition-colors text-[18px]">close</button>
</div>
<div className="flex items-center justify-between p-sm rounded bg-surface-container-low group">
<span className="font-label-sm text-label-sm text-on-surface truncate">고급 CSS 애니메이션</span>
<button className="material-symbols-outlined text-outline-variant hover:text-error transition-colors text-[18px]">close</button>
</div>
</div>
</div>

<div className="bg-surface-container-lowest p-lg rounded-xl border border-surface-container-highest shadow-sm">
<h2 className="font-headline-md text-headline-md text-on-surface mb-lg">학습 경로 순서</h2>
<div className="flex flex-col gap-md">
<div className="flex items-center gap-md">
<div className="flex flex-col items-center gap-1">
<span className="w-6 h-6 rounded-full bg-primary-container text-on-primary-container font-label-sm text-label-sm flex items-center justify-center">1</span>
<div className="w-0.5 h-8 bg-surface-container-highest"></div>
</div>
<div className="flex-grow bg-surface p-sm rounded-lg border border-surface-container-high flex items-center gap-md">
<div className="w-12 h-10 rounded bg-surface-container-highest flex-shrink-0 overflow-hidden">
<img className="w-full h-full object-cover" alt="TypeScript" src="https://lh3.googleusercontent.com/aida-public/AB6AXuA8nVecRw2YzNemzxspBQtao7TGLaNiCjIy16dyen_DkV4FCOMDEQI9I-or5TM67S24NHlFdjvPvRIoPFmUssgNhEOXYlnV5vl2rv5j9nfznm30hRyzqVRJpZa_QhoIN5Tk2ojIDLkuTtsrsadUAFgRB2_io8NajNg5B7ezeIJDbUPyrOCoVioC9-58WH0hCcRj9O4HriLOKrcveCYKaoqUTS8wNMmp8ym08Xp5Jsv4UR4m8F52l5_AmH58400fmTvaZ1iQosZduvEN" />
</div>
<div className="flex-grow min-w-0">
<h4 className="font-label-sm text-label-sm text-on-surface truncate">TypeScript 실무 가이드</h4>
</div>
<div className="flex flex-col gap-0.5">
<span className="material-symbols-outlined text-outline cursor-pointer hover:text-primary text-[16px]">keyboard_arrow_up</span>
<span className="material-symbols-outlined text-outline cursor-pointer hover:text-primary text-[16px]">keyboard_arrow_down</span>
</div>
</div>
</div>
<div className="flex items-center gap-md">
<div className="flex flex-col items-center gap-1">
<span className="w-6 h-6 rounded-full bg-primary-container text-on-primary-container font-label-sm text-label-sm flex items-center justify-center">2</span>
<div className="w-0.5 h-8 bg-surface-container-highest"></div>
</div>
<div className="flex-grow bg-surface p-sm rounded-lg border border-surface-container-high flex items-center gap-md">
<div className="w-12 h-10 rounded bg-surface-container-highest flex-shrink-0 overflow-hidden">
<img className="w-full h-full object-cover" alt="React" src="https://lh3.googleusercontent.com/aida-public/AB6AXuCwCYAB1d9X480Q0B0HfnWylQ5O0-BW-FZp37yiVyKZoIh4dB2ixh96BRr_oEcpAroj3xNW9JzKDaSn7L5muoMox1KPy7ji3mACwFDqam6DjV2eVYVO_CxTj-3M1ogsDvJb9jPEBNcepqRqyREXAynUOe8VZNG7QIJWxC57QDylNfpwZ2az8o5zSMFmx710ArZw-UyZ2f-Y8lAByiQ4H0P_PPvwetgoRFLyk5kBBweui-MkFjngLrlaZrDbsyTrKKMS83oCQr9ktL68" />
</div>
<div className="flex-grow min-w-0">
<h4 className="font-label-sm text-label-sm text-on-surface truncate">React 마스터 클래스</h4>
</div>
<div className="flex flex-col gap-0.5">
<span className="material-symbols-outlined text-outline cursor-pointer hover:text-primary text-[16px]">keyboard_arrow_up</span>
<span className="material-symbols-outlined text-outline cursor-pointer hover:text-primary text-[16px]">keyboard_arrow_down</span>
</div>
</div>
</div>
<div className="flex items-center gap-md">
<div className="flex flex-col items-center gap-1">
<span className="w-6 h-6 rounded-full bg-primary-container text-on-primary-container font-label-sm text-label-sm flex items-center justify-center">3</span>
</div>
<div className="flex-grow bg-surface p-sm rounded-lg border border-surface-container-high flex items-center gap-md">
<div className="w-12 h-10 rounded bg-surface-container-highest flex-shrink-0 overflow-hidden">
<img className="w-full h-full object-cover" alt="CSS" src="https://lh3.googleusercontent.com/aida-public/AB6AXuAQYHkx71tzvkkClR20WXalX2umPBTRWAy_GKesWS1K29t9CIdxOwmaG_iQNSIWape9LOML51IK6bZ1f4wxsqImrDn7y4JQ_hhIdiIH4enQwL-UInH2bedktWG0G2d6ltg-t7hpZjICW5CL46SU4CeT1jYO7hEWBJpin0YtqlN9Nl5rH49ru5oq9fDe3bOsdbQFR_3tdF2tnU10Sfee2tKAtmDsgUrlBkAn4VslPjolqaJK_miIEBwXdl2oOoU21QTTzVEeoPGuSydR" />
</div>
<div className="flex-grow min-w-0">
<h4 className="font-label-sm text-label-sm text-on-surface truncate">고급 CSS 애니메이션</h4>
</div>
<div className="flex flex-col gap-0.5">
<span className="material-symbols-outlined text-outline cursor-pointer hover:text-primary text-[16px]">keyboard_arrow_up</span>
<span className="material-symbols-outlined text-outline cursor-pointer hover:text-primary text-[16px]">keyboard_arrow_down</span>
</div>
</div>
</div>
</div>
<p className="font-label-sm text-label-sm text-on-surface-variant mt-lg italic flex items-center gap-xs">
<span className="material-symbols-outlined text-[14px]">info</span>
                        화살표를 눌러 학습 순서를 변경할 수 있습니다.
                    </p>
</div>
</div>
</div>
</div>
</main>
      <Footer />
    </>
  );
}
