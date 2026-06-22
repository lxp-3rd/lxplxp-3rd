'use client';

import Link from 'next/link';

import { TopNavBar } from '@/components/TopNavBar';
import { Footer } from '@/components/Footer';
// TODO: API 연동 필요

export default function CoursesPage() {
  return (
    <>
      <TopNavBar />
      <main className="pt-16 min-h-screen bg-background">
        <div className="pt-xl pb-xxl px-gutter max-w-container-max mx-auto mt-16">
          <div className="flex flex-col md:flex-row justify-between items-end mb-xl gap-md">
            <div>
              <h1 className="text-headline-lg font-headline-lg text-on-surface mb-xs">성장하세요!</h1>
              <p className="text-body-md font-body-md text-on-surface-variant">성장을 위한 맞춤형 강의 목록입니다.</p>
            </div>
            <div className="flex gap-sm"></div>
          </div>

          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-xl">

            <Link href="/courses/1" className="block">
              <div className="course-card group bg-surface-container-lowest border border-outline-variant rounded-xl overflow-hidden cursor-pointer">
                <div className="relative aspect-video overflow-hidden">
                  <img className="w-full h-full object-cover transition-transform duration-500 group-hover:scale-105" alt="Course thumbnail" src="https://lh3.googleusercontent.com/aida-public/AB6AXuAqEj2frJnRNOb62f_lQOVhixPGSRrzv3CHZEDuEYsbK310mVsYae6Nwav9rL24q802hweEm1SJYQGZxBZpUjvFsdPPZh-mwJwHWWApht1_CfWTBUJdXNcROfUN4cprdlsbIRtgUBJvgSuKiXPDNCmWDh-dcNOBPimy1jwtsjYyhA1MLuWIq_8bwEF-yQZZNP782PzMxOnN_K6TvWx6lCRFKJGAiGq3ICMr7PQ11WnPfiNfyvfWlZMGnHwTXRWtxW2VF8Sr9FQ9MrI5" />
                  <div className="absolute top-sm left-sm">
                    <span className="bg-primary text-on-primary px-xs py-[2px] rounded text-label-sm font-label-sm">BEST</span>
                  </div>
                </div>
                <div className="p-md">
                  <h3 className="text-headline-sm font-headline-sm text-on-surface mb-xs line-clamp-2">실전 React Query &amp; Recoil을 활용한 상태 관리 마스터</h3>
                  <p className="text-body-sm font-body-sm text-on-surface-variant mb-md">강사: 김태희 시니어 엔지니어</p>
                  <div className="flex items-center gap-xs">
                    <span className="material-symbols-outlined text-primary text-[16px]">groups</span>
                    <span className="text-label-sm font-label-sm text-on-surface-variant">1,240명 수강</span>
                  </div>
                </div>
              </div>
            </Link>

            <Link href="/courses/2" className="block">
              <div className="course-card group bg-surface-container-lowest border border-outline-variant rounded-xl overflow-hidden cursor-pointer">
                <div className="relative aspect-video overflow-hidden">
                  <img className="w-full h-full object-cover transition-transform duration-500 group-hover:scale-105" alt="Course thumbnail" src="https://lh3.googleusercontent.com/aida-public/AB6AXuAUVaKE_pH9JWjF8Kz_DC6IDKOveG3uv718QW0tJSmmTKIEjxiFv172i17Y4Ke4qLy8F2z_v3WctLP_i8jqTf0J-Jjyea73UEFVjRxGayqWnxXq4sL3QrwlIyNH7AzyiDLhrsXJ-Iqv-QGCu92Ker6BrmhaO4uCDN2JitZS93Ktk5r3MoiMmzVHqQgPYx_4Kw1Hw8kgKaEXK6fwS2CBZz1_Y3ivBjExybMeDEgJU9Yn679NCyUD6JOlru9_En7HE96qUoTg6t_v5z5r" />
                  <div className="absolute top-sm left-sm">
                    <span className="bg-secondary-container text-on-secondary-container px-xs py-[2px] rounded text-label-sm font-label-sm">NEW</span>
                  </div>
                </div>
                <div className="p-md">
                  <h3 className="text-headline-sm font-headline-sm text-on-surface mb-xs line-clamp-2">Figma를 활용한 고퀄리티 UI/UX 디자인 시스템 구축하기</h3>
                  <p className="text-body-sm font-body-sm text-on-surface-variant mb-md">강사: 이준호 리드 디자이너</p>
                  <div className="flex items-center gap-xs">
                    <span className="material-symbols-outlined text-primary text-[16px]">groups</span>
                    <span className="text-label-sm font-label-sm text-on-surface-variant">856명 수강</span>
                  </div>
                </div>
              </div>
            </Link>

            <Link href="/courses/3" className="block">
              <div className="course-card group bg-surface-container-lowest border border-outline-variant rounded-xl overflow-hidden cursor-pointer">
                <div className="relative aspect-video overflow-hidden">
                  <img className="w-full h-full object-cover transition-transform duration-500 group-hover:scale-105" alt="Course thumbnail" src="https://lh3.googleusercontent.com/aida-public/AB6AXuDzlL9RR1hhvptmCTbKcIM0BPBDbHoZVRz7UnTAeXbFz76Xb894prmnkFC96LE_oOP7NCPZe2ISm4jFX2xbi33mlQCYUE9CC_dGpqSkZQ-hmIQqI2S6jBGUuuhru1ZaUqMOVBvL2QsqpRNRCqSRsGNjWo8JMRzxXvO-mXgzw0cb6Lo-jIuXoF-yJ_RmdFrdHfhHbjhBxk3dl0jzm6bTO0pI3zoQoM-0FlmasBr7GBcuniMpcHnUUnXMUrH12OhFpzR-4jFDu6pPQcqU" />
                </div>
                <div className="p-md">
                  <h3 className="text-headline-sm font-headline-sm text-on-surface mb-xs line-clamp-2">파이썬을 활용한 머신러닝 실전 데이터 분석 입문</h3>
                  <p className="text-body-sm font-body-sm text-on-surface-variant mb-md">강사: 박서연 데이터 사이언티스트</p>
                  <div className="flex items-center gap-xs">
                    <span className="material-symbols-outlined text-primary text-[16px]">groups</span>
                    <span className="text-label-sm font-label-sm text-on-surface-variant">2,105명 수강</span>
                  </div>
                </div>
              </div>
            </Link>

            <Link href="/courses/4" className="block">
              <div className="course-card group bg-surface-container-lowest border border-outline-variant rounded-xl overflow-hidden cursor-pointer">
                <div className="relative aspect-video overflow-hidden">
                  <img className="w-full h-full object-cover transition-transform duration-500 group-hover:scale-105" alt="Course thumbnail" src="https://lh3.googleusercontent.com/aida-public/AB6AXuA6qlAmzwPu8Pr1bQZ_-z4BrMRshX_NqpMW5JMFdyqHSuMhhVvEM6EXDm2HL98T2QlB9DAVnaJ9BTtjPzfvI-Bu91PNKU1bpCYtqNZIP1iTI9ytCka9VaeikLAP19lq3MfxVZZgYsfiEVHxGHDDUWuw4H8FwTR7-a_LjF3WohDplG37uGkxMXbzzUjqZV9JmZXTnKGssX_svp7YxUvvSnsTsjIMoJqSrJQdnOiqNz7iJiDdnwjlSbI6_U6FXkpqZZcTJaGoBlHJbZPc" />
                </div>
                <div className="p-md">
                  <h3 className="text-headline-sm font-headline-sm text-on-surface mb-xs line-clamp-2">백엔드 개발의 정석: Spring Boot &amp; MSA 아키텍처</h3>
                  <p className="text-body-sm font-body-sm text-on-surface-variant mb-md">강사: 정민규 시니어 개발자</p>
                  <div className="flex items-center gap-xs">
                    <span className="material-symbols-outlined text-primary text-[16px]">groups</span>
                    <span className="text-label-sm font-label-sm text-on-surface-variant">1,890명 수강</span>
                  </div>
                </div>
              </div>
            </Link>

            <Link href="/courses/5" className="block">
              <div className="course-card group bg-surface-container-lowest border border-outline-variant rounded-xl overflow-hidden cursor-pointer">
                <div className="relative aspect-video overflow-hidden">
                  <img className="w-full h-full object-cover transition-transform duration-500 group-hover:scale-105" alt="Course thumbnail" src="https://lh3.googleusercontent.com/aida-public/AB6AXuAmKVL_KFYk0yAjG1tWTtrXrb1HbYncRVkRyJYZQZA-s8meRt7pwIG8J8pyds4_QUpSso-JX0FANYgGj_sjhlI2ooGDnKTaeHeQt_A9Tco2fhbI8OIvFBPq-c3UiKfncD2XwLs1jKxUr3oGX05rcbfa4D7KoeMdxAIAtYBHy_AtxHQIHBp4zTDe2hYCUDXV3TPZY5FR4Fy2V_THOWOZZ4QhQcMtcCkCe2q4ot1z-Q-Z4qMhW_i0Bi-k3_onpHc9jz9TbSBX-G" />
                  <div className="absolute top-sm left-sm">
                    <span className="bg-primary text-on-primary px-xs py-[2px] rounded text-label-sm font-label-sm">BEST</span>
                  </div>
                </div>
                <div className="p-md">
                  <h3 className="text-headline-sm font-headline-sm text-on-surface mb-xs line-clamp-2">2024년 최신 퍼포먼스 마케팅 실무 로드맵</h3>
                  <p className="text-body-sm font-body-sm text-on-surface-variant mb-md">강사: 최아름 마케팅 디렉터</p>
                  <div className="flex items-center gap-xs">
                    <span className="material-symbols-outlined text-primary text-[16px]">groups</span>
                    <span className="text-label-sm font-label-sm text-on-surface-variant">3,420명 수강</span>
                  </div>
                </div>
              </div>
            </Link>

            <Link href="/courses/6" className="block">
              <div className="course-card group bg-surface-container-lowest border border-outline-variant rounded-xl overflow-hidden cursor-pointer">
                <div className="relative aspect-video overflow-hidden">
                  <img className="w-full h-full object-cover transition-transform duration-500 group-hover:scale-105" alt="Course thumbnail" src="https://lh3.googleusercontent.com/aida-public/AB6AXuBu6wZmdRFEsjfmGGMI6GFGYGYERUQdroPHuP0lZNVXNZG_6ZDn7P9ZO3BefFSV7oHblg_JlUU9A6dGDoqW_pTDy6FM-WB5IDrKnkjj1KY2hlyHy7nZllLAwHVxPvM_Q7FdvpMxh3mw6NZCliaQ_mLy0eGq4F26PWBqrnyY0h8j_cbE3TGdu1ZncBaDwKz-f-vEG4GrDebDPPfYJAwJ6Jcs0sW6mJT_EbIkyxX5ozLrp39RsTEkqX-xghK9f69IDYwrZO8MJJaXwrmg" />
                </div>
                <div className="p-md">
                  <h3 className="text-headline-sm font-headline-sm text-on-surface mb-xs line-clamp-2">AWS 클라우드 인프라 구축 및 데브옵스 실무 마스터</h3>
                  <p className="text-body-sm font-body-sm text-on-surface-variant mb-md">강사: 한도윤 인프라 엔지니어</p>
                  <div className="flex items-center gap-xs">
                    <span className="material-symbols-outlined text-primary text-[16px]">groups</span>
                    <span className="text-label-sm font-label-sm text-on-surface-variant">920명 수강</span>
                  </div>
                </div>
              </div>
            </Link>

          </div>
        </div>
      </main>
      <Footer />
    </>
  );
}
