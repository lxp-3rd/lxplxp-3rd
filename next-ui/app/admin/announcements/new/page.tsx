'use client';

// TODO: API 연동 필요
export default function AdminAnnouncementNewPage() {
  return (

<div className="flex-grow pt-24 pb-12 px-gutter max-w-[1280px] mx-auto w-full">

<div className="flex flex-col md:flex-row md:items-end justify-between mb-xl gap-md">
<div>
<a className="flex items-center gap-xs text-primary font-label-md mb-xs hover:gap-sm transition-all" href="/admin/announcements">
<span className="material-symbols-outlined text-sm">arrow_back</span>
                    공지사항 목록으로 돌아가기
                </a>
<h1 className="font-headline-lg text-headline-lg text-on-background">새 공지사항 작성</h1>
<p className="font-body-md text-on-surface-variant mt-sm">LXP 플랫폼 사용자들에게 전달할 새로운 소식을 작성하세요.</p>
</div>
<div className="flex items-center gap-md">
<button className="px-lg py-sm font-label-md text-on-surface-variant border border-outline-variant rounded-lg hover:bg-surface-container-low transition-colors">취소</button>
<button className="px-lg py-sm font-label-md bg-primary-container text-white rounded-lg shadow-sm hover:shadow-md transition-all active:scale-95">저장하기</button>
</div>
</div>

<div className="bento-grid">

<div className="col-span-12 lg:col-span-8 flex flex-col gap-lg">

<div className="bg-surface-container-lowest p-lg rounded-xl border border-surface-variant shadow-sm">
<label className="block font-label-md text-on-surface-variant mb-sm">제목 입력</label>
<input className="w-full text-headline-md font-headline-md bg-transparent border-none p-0 placeholder:text-surface-variant focus:ring-0" placeholder="공지사항 제목을 입력하세요 (예: 2024 하반기 학습 과정 안내)" type="text" />
</div>

<div className="bg-surface-container-lowest p-lg rounded-xl border border-surface-variant shadow-sm flex flex-col gap-md">
<div className="flex items-center justify-between border-b border-surface-variant pb-md">
<label className="font-label-md text-on-surface-variant">본문 입력</label>
<div className="flex items-center gap-sm text-on-surface-variant">
<button className="material-symbols-outlined hover:text-primary">format_bold</button>
<button className="material-symbols-outlined hover:text-primary">format_italic</button>
<button className="material-symbols-outlined hover:text-primary">format_list_bulleted</button>
<button className="material-symbols-outlined hover:text-primary">link</button>
<button className="material-symbols-outlined hover:text-primary">image</button>
</div>
</div>
<textarea className="w-full min-h-[400px] bg-transparent border-none p-0 resize-none font-body-lg text-body-lg placeholder:text-surface-variant focus:ring-0" placeholder="학습자들에게 전달할 내용을 상세히 작성해 주세요..."></textarea>
</div>
</div>

<div className="col-span-12 lg:col-span-4 flex flex-col gap-lg">

<div className="bg-surface-container-lowest p-lg rounded-xl border border-surface-variant shadow-sm">
<h3 className="font-label-md text-on-surface-variant uppercase tracking-wider mb-lg">설정 및 공개</h3>
<div className="flex flex-col gap-xl">

<div className="flex items-center justify-between">
<div className="flex flex-col">
<span className="font-label-md text-on-surface">공개 여부 선택</span>
<span className="text-label-sm text-on-surface-variant">활성화 시 즉시 게시됩니다.</span>
</div>
<label className="toggle-switch">
<input checked type="checkbox" />
<span className="slider"></span>
</label>
</div>

</div>
</div>

<div className="bg-surface-container-lowest p-lg rounded-xl border border-surface-variant shadow-sm">
<h3 className="font-label-md text-on-surface-variant uppercase tracking-wider mb-lg">작성 정보</h3>
<div className="flex items-center gap-md mb-lg">
<div className="w-12 h-12 rounded-full overflow-hidden">
<img className="w-full h-full object-cover" alt="Close-up of a diverse professional educator at a desk, with warm orange and neutral grey tones. The visual is sharp, high-key, and maintains the modern professional LXP brand aesthetic." src="https://lh3.googleusercontent.com/aida-public/AB6AXuAhP5G2KaJ3UpHAStyd6W-dgc0dB1sQkidQeIEs3kqJhy9srlxcuRnrzLwzQAO50qoiW3U5CcHGGJfktF4NH853UtUC3g_fR7Az8HDzTdszCfYUzxXCoFnU2yPdo83lju5gc5yAU8Sa7eeehKh6JYlyzgJvPJ19vYucy2IWJqPcSw_8YY_oCOxCK19xKHYoMuJLnJvOfbfxWFmkx8wTgadCiiZIILoalTgwTRzkyAfFfAdST1kBMCjNsuWwdA205A83RB7WtIngPgKo" />
</div>
<div className="flex flex-col">
<label className="font-label-sm text-on-surface-variant">작성자 표시/입력</label>
<input className="font-label-md text-on-surface bg-transparent border-none p-0 focus:ring-0" type="text" defaultValue="관리자 (홍길동)" />
</div>
</div>
<div className="flex flex-col gap-sm">

</div>
</div>

</div>
</div>
</div>

  );
}
