'use client';

// TODO: API 연동 필요
export default function AdminRoadmapsPage() {
  return (

<div className="flex-grow w-full max-w-[1280px] mx-auto px-margin-desktop py-xl">

<div className="mb-xl">
<div className="flex items-center gap-xs text-on-surface-variant mb-xs">
<span className="font-label-sm text-label-sm">관리자</span>
<span className="material-symbols-outlined text-[14px]">chevron_right</span>
<span className="font-label-sm text-label-sm text-primary">로드맵 관리</span>
</div>
<div className="flex flex-col md:flex-row md:items-center justify-between gap-md">
<h1 className="font-headline-lg text-headline-lg text-on-surface">로드맵 관리</h1>

</div>
</div>

<div className="grid grid-cols-1 md:grid-cols-4 gap-lg mb-xl">
<div className="bg-surface-container-lowest p-lg rounded-xl border border-surface-variant/20 shadow-sm">
<p className="font-label-sm text-label-sm text-on-surface-variant uppercase tracking-wider mb-xs">전체 로드맵</p>
<h3 className="font-headline-lg text-headline-lg">24</h3>
</div>

</div>

<div className="flex flex-col gap-md">

<div className="group bg-surface-container-lowest border border-surface-variant/20 rounded-xl overflow-hidden shadow-sm roadmap-card-hover flex flex-col md:flex-row items-stretch cursor-pointer">
<div className="md:w-64 h-48 md:h-auto overflow-hidden relative">
<img className="w-full h-full object-cover group-hover:scale-105 transition-transform duration-500" alt="Senior UX Specialist Career Track artwork" src="https://lh3.googleusercontent.com/aida-public/AB6AXuCXrE7UCzOiXxsYE8iG-MSN_aODOGtgs2HZpmg6qvkkib14aO6nJ9AkNN5a1GiYZ3kYqnnaDupkIwQl88tnO7MmHCBCwM6-dlELw03WKZJ1MNlGDWxRblGAVmA7s--YSCJrz5LLlkTVBa6bDckR7RkGfVvsjILjhvAo6TBCZYIkpmCvvFVxxESVNkMLOye4MnOkmCL4YyJtf2xK48AlnFPGiSG-Cm94TZhqoH2MoFG2bBe8A3igup8sL4Qir_yxwv3rvXq7mNqXHBIR" />
<div className="absolute inset-0 bg-gradient-to-t from-black/50 to-transparent opacity-0 group-hover:opacity-100 transition-opacity flex items-end p-md">
<span className="text-white font-label-sm text-label-sm">상세 보기</span>
</div>
</div>
<div className="flex-grow p-lg flex flex-col justify-between">
<div>
<div className="flex items-center justify-between mb-sm">
<span className="font-label-sm text-label-sm text-primary uppercase font-bold">기획 및 디자인</span>

</div>
<h2 className="font-headline-md text-headline-md text-on-surface mb-md">시니어 UX 전문가 커리어 트랙</h2>
<div className="flex flex-wrap gap-xl">
<div className="flex items-center gap-xs">
<span className="material-symbols-outlined text-tertiary">menu_book</span>
<span className="font-body-md text-body-md text-on-surface-variant">12개 강좌</span>
</div>
<div className="flex items-center gap-xs">
<span className="material-symbols-outlined text-tertiary">groups</span>
<span className="font-body-md text-body-md text-on-surface-variant">452명 참여</span>
</div>
<div className="flex items-center gap-xs">
<span className="material-symbols-outlined text-tertiary">schedule</span>
<span className="font-body-md text-body-md text-on-surface-variant">24주 과정</span>
</div>
</div>
</div>
<div className="flex items-center justify-end gap-md mt-lg border-t border-surface-variant/10 pt-md">
<button className="font-label-md text-label-md text-on-surface-variant hover:text-primary transition-colors flex items-center gap-xs px-md py-xs rounded-lg hover:bg-surface-container">
<span className="material-symbols-outlined text-[20px]">visibility_off</span>
                            숨김
                        </button>
<button className="font-label-md text-label-md text-error hover:bg-error-container/30 transition-all flex items-center gap-xs px-md py-xs rounded-lg">
<span className="material-symbols-outlined text-[20px]">delete</span>
                            삭제
                        </button>
</div>
</div>
</div>

<div className="group bg-surface-container-lowest border border-surface-variant/20 rounded-xl overflow-hidden shadow-sm roadmap-card-hover flex flex-col md:flex-row items-stretch cursor-pointer">
<div className="md:w-64 h-48 md:h-auto overflow-hidden relative">
<img className="w-full h-full object-cover group-hover:scale-105 transition-transform duration-500" alt="AI Implementation for Software Architects artwork" src="https://lh3.googleusercontent.com/aida-public/AB6AXuB2S0Z_hBaQI5SLggWksYTz74dq87uaQfkgiX5TwcFULW10PWRNmUeg7ydf8rLlGGpD2X0vE01l6FKviOOFtr1jyDx04p1qAjz1aSM7d01-RNq2OBZ8qQmRr1Ve85GuMNSExWIhFhZduyEXF_Rc72pefmO6IF5RAy-j46mmBzG2bME4i9uPNYhI2oRFF45MwZz5C9I0RaSF5c4NCWwGHUNu2Qdt09fzoduw2k1MnPMwBIWbQLZ2uB26mWGbL5dnjh2u9Gn2gQSC4M2H" />
<div className="absolute inset-0 bg-gradient-to-t from-black/50 to-transparent opacity-0 group-hover:opacity-100 transition-opacity flex items-end p-md">
<span className="text-white font-label-sm text-label-sm">상세 보기</span>
</div>
</div>
<div className="flex-grow p-lg flex flex-col justify-between">
<div>
<div className="flex items-center justify-between mb-sm">
<span className="font-label-sm text-label-sm text-primary uppercase font-bold">엔지니어링</span>

</div>
<h2 className="font-headline-md text-headline-md text-on-surface mb-md">소프트웨어 아키텍트를 위한 AI 구축</h2>
<div className="flex flex-wrap gap-xl">
<div className="flex items-center gap-xs">
<span className="material-symbols-outlined text-tertiary">menu_book</span>
<span className="font-body-md text-body-md text-on-surface-variant">8개 강좌</span>
</div>
<div className="flex items-center gap-xs">
<span className="material-symbols-outlined text-tertiary">groups</span>
<span className="font-body-md text-body-md text-on-surface-variant">891명 참여</span>
</div>
<div className="flex items-center gap-xs">
<span className="material-symbols-outlined text-tertiary">schedule</span>
<span className="font-body-md text-body-md text-on-surface-variant">12주 과정</span>
</div>
</div>
</div>
<div className="flex items-center justify-end gap-md mt-lg border-t border-surface-variant/10 pt-md">
<button className="font-label-md text-label-md text-on-surface-variant hover:text-primary transition-colors flex items-center gap-xs px-md py-xs rounded-lg hover:bg-surface-container">
<span className="material-symbols-outlined text-[20px]">visibility_off</span>
                            숨김
                        </button>
<button className="font-label-md text-label-md text-error hover:bg-error-container/30 transition-all flex items-center gap-xs px-md py-xs rounded-lg">
<span className="material-symbols-outlined text-[20px]">delete</span>
                            삭제
                        </button>
</div>
</div>
</div>

<div className="group bg-surface-container-lowest border border-surface-variant/20 rounded-xl overflow-hidden shadow-sm roadmap-card-hover flex flex-col md:flex-row items-stretch cursor-pointer">
<div className="md:w-64 h-48 md:h-auto overflow-hidden relative">
<img className="w-full h-full object-cover group-hover:scale-105 transition-transform duration-500" alt="Executive Leadership Fundamentals artwork" src="https://lh3.googleusercontent.com/aida-public/AB6AXuBfet68HyfjY_ly_2AsrGTW0_Pn9V_uVBCVptj_KhPgq34gahpcJXzUd1tsnE43xSlIJBB58jH9saVL6LDyNhi7GN8mUMANhROPrN2dUqVi9PJ3P75kZbI6pptCCw6yX-B7iXBjgjjwhiO95h0nXe1R1jwnSl2-kyT1EIel_fTkb-IFlURZJRuQ6ta6yW3RZCGW92-AAlu9lrrrsH7IFcZMcWtp2yqxxrVXwvwd-UCJA0u1bc9__2bwmalcbU4tDbulRe65bfc-OnML" />
<div className="absolute inset-0 bg-gradient-to-t from-black/50 to-transparent opacity-0 group-hover:opacity-100 transition-opacity flex items-end p-md">
<span className="text-white font-label-sm text-label-sm">상세 보기</span>
</div>
</div>
<div className="flex-grow p-lg flex flex-col justify-between">
<div>
<div className="flex items-center justify-between mb-sm">
<span className="font-label-sm text-label-sm text-primary uppercase font-bold">리더십</span>

</div>
<h2 className="font-headline-md text-headline-md text-on-surface mb-md">핵심 임원 리더십 기초</h2>
<div className="flex flex-wrap gap-xl">
<div className="flex items-center gap-xs">
<span className="material-symbols-outlined text-tertiary">menu_book</span>
<span className="font-body-md text-body-md text-on-surface-variant">5개 강좌</span>
</div>
<div className="flex items-center gap-xs">
<span className="material-symbols-outlined text-tertiary">groups</span>
<span className="font-body-md text-body-md text-on-surface-variant">124명 참여</span>
</div>
<div className="flex items-center gap-xs">
<span className="material-symbols-outlined text-tertiary">schedule</span>
<span className="font-body-md text-body-md text-on-surface-variant">8주 과정</span>
</div>
</div>
</div>
<div className="flex items-center justify-end gap-md mt-lg border-t border-surface-variant/10 pt-md">
<button className="font-label-md text-label-md text-on-surface-variant hover:text-primary transition-colors flex items-center gap-xs px-md py-xs rounded-lg hover:bg-surface-container">
<span className="material-symbols-outlined text-[20px]">visibility_off</span>
                            숨김
                        </button>
<button className="font-label-md text-label-md text-error hover:bg-error-container/30 transition-all flex items-center gap-xs px-md py-xs rounded-lg">
<span className="material-symbols-outlined text-[20px]">delete</span>
                            삭제
                        </button>
</div>
</div>
</div>

<div className="group bg-surface-container-lowest border border-surface-variant/20 rounded-xl overflow-hidden shadow-sm roadmap-card-hover flex flex-col md:flex-row items-stretch cursor-pointer">
<div className="md:w-64 h-48 md:h-auto overflow-hidden relative">
<img className="w-full h-full object-cover group-hover:scale-105 transition-transform duration-500" alt="Growth Marketing Masterclass artwork" src="https://lh3.googleusercontent.com/aida-public/AB6AXuCdbK0anJbls0CfGOB8g3LLdYsE8D3fyIWT6njcAjTZyH4-AdqUWWEnqjMUo_Ek4g8gPCOawTjW8HBM3gTWMOJgErCAx7rdasTQx-WkTcNBEZNqPNjcypl16u-B8_iv3FEXDxzE-5pZFPN5qXblarUES6K1QG_iyt25Cqxoi384H2FspdfIABun6dLkSRMXU-_woeD1kaU72YoqaIFX63uZHMyYnT7-qdaSWCkbNmL4MyQgUtGsrEsvhGXRulsnOI29I9riBwIzFbKJ" />
<div className="absolute inset-0 bg-gradient-to-t from-black/50 to-transparent opacity-0 group-hover:opacity-100 transition-opacity flex items-end p-md">
<span className="text-white font-label-sm text-label-sm">상세 보기</span>
</div>
</div>
<div className="flex-grow p-lg flex flex-col justify-between">
<div>
<div className="flex items-center justify-between mb-sm">
<span className="font-label-sm text-label-sm text-primary uppercase font-bold">마케팅</span>

</div>
<h2 className="font-headline-md text-headline-md text-on-surface mb-md">그로스 마케팅 마스터클래스</h2>
<div className="flex flex-wrap gap-xl">
<div className="flex items-center gap-xs">
<span className="material-symbols-outlined text-tertiary">menu_book</span>
<span className="font-body-md text-body-md text-on-surface-variant">15개 강좌</span>
</div>
<div className="flex items-center gap-xs">
<span className="material-symbols-outlined text-tertiary">groups</span>
<span className="font-body-md text-body-md text-on-surface-variant">2,105명 참여</span>
</div>
<div className="flex items-center gap-xs">
<span className="material-symbols-outlined text-tertiary">schedule</span>
<span className="font-body-md text-body-md text-on-surface-variant">32주 과정</span>
</div>
</div>
</div>
<div className="flex items-center justify-end gap-md mt-lg border-t border-surface-variant/10 pt-md">
<button className="font-label-md text-label-md text-on-surface-variant hover:text-primary transition-colors flex items-center gap-xs px-md py-xs rounded-lg hover:bg-surface-container">
<span className="material-symbols-outlined text-[20px]">visibility_off</span>
                            숨김
                        </button>
<button className="font-label-md text-label-md text-error hover:bg-error-container/30 transition-all flex items-center gap-xs px-md py-xs rounded-lg">
<span className="material-symbols-outlined text-[20px]">delete</span>
                            삭제
                        </button>
</div>
</div>
</div>

<div className="group bg-surface-container-lowest border border-surface-variant/20 rounded-xl overflow-hidden shadow-sm roadmap-card-hover flex flex-col md:flex-row items-stretch cursor-pointer">
<div className="md:w-64 h-48 md:h-auto overflow-hidden relative">
<img className="w-full h-full object-cover group-hover:scale-105 transition-transform duration-500" alt="Data Analysis with Python artwork" src="https://lh3.googleusercontent.com/aida-public/AB6AXuATYHjJ-2vO0u2IcKd03TDOMgW0YEjFdPpPd-2NzmEBt9pf2iu5DxHcsq0jxXe0dSGaWGaajSvTfCXLKTTIB5hw6jhKGKo_CmeWV-MYJ1Go5XmMvp85N2bSr1vEOICTJXwu2RvPFy9QWwnRLGSlRgZFn29ItMA_ky2fxTxT_7e1KEDN8hrDIWrw7pD69rmBYgMcFoQFHh_Lh4vLNF-rob36qhcg0j5iXakS0hMWxTMCuqwSvBw2s6L6QSA1JK9pcFaWt6u3xa1P1dxz" />
<div className="absolute inset-0 bg-gradient-to-t from-black/50 to-transparent opacity-0 group-hover:opacity-100 transition-opacity flex items-end p-md">
<span className="text-white font-label-sm text-label-sm">상세 보기</span>
</div>
</div>
<div className="flex-grow p-lg flex flex-col justify-between">
<div>
<div className="flex items-center justify-between mb-sm">
<span className="font-label-sm text-label-sm text-primary uppercase font-bold">데이터 사이언스</span>

</div>
<h2 className="font-headline-md text-headline-md text-on-surface mb-md">파이썬을 활용한 데이터 분석</h2>
<div className="flex flex-wrap gap-xl">
<div className="flex items-center gap-xs">
<span className="material-symbols-outlined text-tertiary">menu_book</span>
<span className="font-body-md text-body-md text-on-surface-variant">6개 강좌</span>
</div>
<div className="flex items-center gap-xs">
<span className="material-symbols-outlined text-tertiary">groups</span>
<span className="font-body-md text-body-md text-on-surface-variant">3,420명 참여</span>
</div>
<div className="flex items-center gap-xs">
<span className="material-symbols-outlined text-tertiary">schedule</span>
<span className="font-body-md text-body-md text-on-surface-variant">10주 과정</span>
</div>
</div>
</div>
<div className="flex items-center justify-end gap-md mt-lg border-t border-surface-variant/10 pt-md">
<button className="font-label-md text-label-md text-on-surface-variant hover:text-primary transition-colors flex items-center gap-xs px-md py-xs rounded-lg hover:bg-surface-container">
<span className="material-symbols-outlined text-[20px]">visibility_off</span>
                            숨김
                        </button>
<button className="font-label-md text-label-md text-error hover:bg-error-container/30 transition-all flex items-center gap-xs px-md py-xs rounded-lg">
<span className="material-symbols-outlined text-[20px]">delete</span>
                            삭제
                        </button>
</div>
</div>
</div>

<div className="group bg-surface-container-lowest border border-surface-variant/20 rounded-xl overflow-hidden shadow-sm roadmap-card-hover flex flex-col md:flex-row items-stretch cursor-pointer">
<div className="md:w-64 h-48 md:h-auto overflow-hidden relative">
<img className="w-full h-full object-cover group-hover:scale-105 transition-transform duration-500" alt="Agile &amp; Scrum Professional Path artwork" src="https://lh3.googleusercontent.com/aida-public/AB6AXuB-tiX6BA_HEhToqS_SsY1aIMPVY3DKofoaMkp3SKGZzWYU8AFsxXQWRjAUryNuUog7AH8sB4XTBjyPU8UmKuO9fBtpC9Y0zWjmsYAVXf3wSyzURyD3allN1a8RruoolAlS-JuPmTyPey7tW9DCqEpZm7mc5MbGvZNXF-VXlfCmPD1NaABBXojEvXLDFsI99Gf-Wv0u-eZVxeADDqiokqdTcfwgV5oT1V3qySGcO7xDpNdvcNJUKTCyIzbslzeT92q-fcOexHQzKXp7" />
<div className="absolute inset-0 bg-gradient-to-t from-black/50 to-transparent opacity-0 group-hover:opacity-100 transition-opacity flex items-end p-md">
<span className="text-white font-label-sm text-label-sm">상세 보기</span>
</div>
</div>
<div className="flex-grow p-lg flex flex-col justify-between">
<div>
<div className="flex items-center justify-between mb-sm">
<span className="font-label-sm text-label-sm text-primary uppercase font-bold">프로젝트 관리</span>

</div>
<h2 className="font-headline-md text-headline-md text-on-surface mb-md">애자일 &amp; 스크럼 프로페셔널 패스</h2>
<div className="flex flex-wrap gap-xl">
<div className="flex items-center gap-xs">
<span className="material-symbols-outlined text-tertiary">menu_book</span>
<span className="font-body-md text-body-md text-on-surface-variant">7개 강좌</span>
</div>
<div className="flex items-center gap-xs">
<span className="material-symbols-outlined text-tertiary">groups</span>
<span className="font-body-md text-body-md text-on-surface-variant">567명 참여</span>
</div>
<div className="flex items-center gap-xs">
<span className="material-symbols-outlined text-tertiary">schedule</span>
<span className="font-body-md text-body-md text-on-surface-variant">14주 과정</span>
</div>
</div>
</div>
<div className="flex items-center justify-end gap-md mt-lg border-t border-surface-variant/10 pt-md">
<button className="font-label-md text-label-md text-on-surface-variant hover:text-primary transition-colors flex items-center gap-xs px-md py-xs rounded-lg hover:bg-surface-container">
<span className="material-symbols-outlined text-[20px]">visibility_off</span>
                            숨김
                        </button>
<button className="font-label-md text-label-md text-error hover:bg-error-container/30 transition-all flex items-center gap-xs px-md py-xs rounded-lg">
<span className="material-symbols-outlined text-[20px]">delete</span>
                            삭제
                        </button>
</div>
</div>
</div>
</div>

<div className="mt-xl flex items-center justify-between border-t border-surface-variant/20 pt-lg">
<p className="font-body-md text-body-md text-on-surface-variant">24개 중 6개 표시 중</p>
<div className="flex gap-sm">
<button className="w-10 h-10 flex items-center justify-center rounded-lg border border-outline-variant hover:bg-surface-container transition-all">
<span className="material-symbols-outlined">chevron_left</span>
</button>
<button className="w-10 h-10 flex items-center justify-center rounded-lg bg-primary-container text-on-primary-container font-label-md text-label-md">1</button>
<button className="w-10 h-10 flex items-center justify-center rounded-lg border border-outline-variant hover:bg-surface-container transition-all font-label-md text-label-md">2</button>
<button className="w-10 h-10 flex items-center justify-center rounded-lg border border-outline-variant hover:bg-surface-container transition-all font-label-md text-label-md">3</button>
<button className="w-10 h-10 flex items-center justify-center rounded-lg border border-outline-variant hover:bg-surface-container transition-all">
<span className="material-symbols-outlined">chevron_right</span>
</button>
</div>
</div>
</div>

  );
}
