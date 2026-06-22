'use client';

import Link from 'next/link';

import { TopNavBar } from '@/components/TopNavBar';
import { Footer } from '@/components/Footer';
// TODO: API 연동 필요

export default function HomePage() {
  return (
    <>
      <TopNavBar />
      <main className="pt-16 min-h-screen bg-background">
        <div className="pt-xxl pb-xxl px-gutter max-w-container-max mx-auto mt-16">

        <section className="mb-xl">
          <div className="flex flex-col md:flex-row md:items-end justify-between gap-md">
            <div>

            <h1 className="text-headline-lg font-headline-lg text-on-surface">참여 중인 로드맵</h1>
            <p className="text-body-md font-body-md text-on-surface-variant mt-xs">참여 중인 6개의 로드맵이 있습니다. 계속해서 학습을 이어가세요.</p>
            </div>
            <div className="flex gap-sm">

            </div>
          </div>
        </section>

        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-lg">

        <Link href="/roadmaps/1">
          <div className="roadmap-card flex flex-col border border-outline-variant rounded-xl overflow-hidden cursor-pointer group shadow-sm">
            <div className="relative aspect-video overflow-hidden">
              <img className="w-full h-full object-cover group-hover:scale-105 transition-transform duration-500" alt="3D visualization of digital architecture" src="https://lh3.googleusercontent.com/aida-public/AB6AXuDTQFKb_hEU9HlhXAfR3z8DBRKEbCNRaN-hD_AY7KmDoToAUw1kn-TAy8Yr85-zC_rdC9BHpaL7uNsWx2KHK__nVIer28-3u-s68dgTZHMyUTiQACFXb74xghPW_A7mDhQI6gUN6CR682L-fHMOmRAVX8qkzFpiERrIrJ6KGSCPzarJduU8hXX4Pvgw_9EuW1zr5GR_9K6WFQxrVW8qhOGpgTFx8n3txPa0-ikvk4x4YnfiIRIgwHLF2ntuU6buW8sJbbssS0ZuBQAk" />
              <div className="absolute top-3 left-3 bg-white/95 backdrop-blur-sm px-sm py-1 rounded-full shadow-sm">
                <span className="text-label-sm font-label-sm text-primary">진행 중</span>
              </div>
            </div>
            <div className="p-lg flex flex-col flex-grow">
              <h3 className="text-headline-sm font-headline-sm text-on-surface line-clamp-1">백엔드 마스터: Node.js &amp; Architecture</h3>
              <p className="text-body-sm font-body-sm text-on-surface-variant mt-xs mb-md">전문가 수준의 서버 사이드 아키텍처 설계와 최적화 전략</p>
              <div className="mt-auto">
                <div className="flex justify-between items-center mb-xs">
                  <span className="text-label-sm font-label-sm text-on-surface-variant">진행률 65%</span>
                  <span className="text-label-sm font-label-sm text-primary">12/18 강의</span>
                </div>
                <div className="w-full h-1.5 progress-bar-bg rounded-full overflow-hidden">
                  <div className="h-full progress-bar-fill" style={{width: '65%'}}></div>
                </div>
              </div>
            </div>
          </div>
        </Link>

        <Link href="/roadmaps/2">
        <div className="roadmap-card flex flex-col border border-outline-variant rounded-xl overflow-hidden cursor-pointer group shadow-sm">
          <div className="relative aspect-video overflow-hidden">
            <img className="w-full h-full object-cover group-hover:scale-105 transition-transform duration-500" alt="UI design workspace" src="https://lh3.googleusercontent.com/aida-public/AB6AXuAfDf4qrYq1K-z2pijoFWScllGM4tGojjXss-g5tgnfP9CDMH7xGoR-ST_IDIEA28laPiy-rqJsSCpxKGe3yREi2kvTsfQ6Nvu42hsYDssVtXXJ2t9xaWJcG4qAKf3eAz3JLXtwLbp4hnP1m7OLTH9LShlzTgNoHBzMtCuSn2vx6JEeXJjXC98Za3IWnhXiIaEiESGNCASHyg27PtNVPYB69VUU4CKaKssWOb4BusZVPmyhvP42RTHUMmENEqJsp9fLq9EhOC-JjZyX" />
            <div className="absolute top-3 left-3 bg-white/95 backdrop-blur-sm px-sm py-1 rounded-full shadow-sm">
              <span className="text-label-sm font-label-sm text-primary">진행 중</span>
            </div>
          </div>
          <div className="p-lg flex flex-col flex-grow">
            <h3 className="text-headline-sm font-headline-sm text-on-surface line-clamp-1">UI/UX 디자인 시스템 구축 가이드</h3>
            <p className="text-body-sm font-body-sm text-on-surface-variant mt-xs mb-md">일관된 사용자 경험을 위한 전사 디자인 시스템 설계 방법론</p>
            <div className="mt-auto">
              <div className="flex justify-between items-center mb-xs">
                <span className="text-label-sm font-label-sm text-on-surface-variant">진행률 28%</span>
                <span className="text-label-sm font-label-sm text-primary">4/14 강의</span>
              </div>
              <div className="w-full h-1.5 progress-bar-bg rounded-full overflow-hidden">
                <div className="h-full progress-bar-fill" style={{width: '28%'}}></div>
              </div>
            </div>
          </div>
        </div>
        </Link>

        <Link href="/roadmaps/3"><div className="roadmap-card flex flex-col border border-outline-variant rounded-xl overflow-hidden cursor-pointer group shadow-sm">
          <div className="relative aspect-video overflow-hidden">
          <img className="w-full h-full object-cover group-hover:scale-105 transition-transform duration-500" alt="Modern data visualization" src="https://lh3.googleusercontent.com/aida-public/AB6AXuDL0fg60EEPiHm13Jw-Xb7LgWrjtqKC3kTv5uZVufttohtMoV38oluY7O1WrBBSlL6W-4VAtvxD_hZ2KejMX_l_RkdcfMtbJyZeZJZyS9WP8-6YTGv4ogYLeJoG0yvEjKnxWf1LRStqe3ysX5-Lz3HrfX887IQaW8LpDsquEWcCUwEOcvMJeXZLJmPJ2yL1hbWlmxK5FA6I2GFPLpF12-AAqSq5yH0LVJ_ZWIij_4_1dA3CYfsDku7HovQw__Pwhls28zhLi5hL4R_5" />
          <div className="absolute top-3 left-3 bg-primary-fixed px-sm py-1 rounded-full shadow-sm">
          <span className="text-label-sm font-label-sm text-on-primary-fixed">New</span>
          </div>
          </div>
          <div className="p-lg flex flex-col flex-grow">
          <h3 className="text-headline-sm font-headline-sm text-on-surface line-clamp-1">데이터 분석과 비즈니스 인텔리전스</h3>
          <p className="text-body-sm font-body-sm text-on-surface-variant mt-xs mb-md">데이터 기반 의사결정을 위한 분석 기법과 시각화 전략</p>
          <div className="mt-auto">
          <div className="flex justify-between items-center mb-xs">
          <span className="text-label-sm font-label-sm text-on-surface-variant">진행률 5%</span>
          <span className="text-label-sm font-label-sm text-primary">1/20 강의</span>
          </div>
          <div className="w-full h-1.5 progress-bar-bg rounded-full overflow-hidden">
          <div className="h-full progress-bar-fill" style={{width: '5%'}}></div>
          </div>
          </div>
          </div>
          </div>
        </Link>

        <Link href="/roadmaps/4"><div className="roadmap-card flex flex-col border border-outline-variant rounded-xl overflow-hidden cursor-pointer group shadow-sm">
        <div className="relative aspect-video overflow-hidden">
        <img className="w-full h-full object-cover group-hover:scale-105 transition-transform duration-500" alt="Minimalist digital workplace" src="https://lh3.googleusercontent.com/aida-public/AB6AXuDXZQR-M6LHCtT6DNJXbQiA65KfcbIPh29VcxTo43OeLPamUYkFdonIQrD02OQptV3gkZJOZKFHrx-9xXlAjujLhO5C7nLW-93QyzcdWb_YkIbV1fx1V_BypTBgXiRuPaK5d5ybBvJaboiKNzdNnnC4Gnt1mDd7AJOvdmNC0go09JpEOxPoaMsomiKm5Ms65GD7Px7eVIdormoL1vWXMrwfoUlJm9aGlJuv8Usi76a3ToNmqACiaUVxsAiDbX6bsPerh_5CCj-IwOVS" />
        <div className="absolute top-3 left-3 bg-white/95 backdrop-blur-sm px-sm py-1 rounded-full shadow-sm">
        <span className="text-label-sm font-label-sm text-primary">거의 완료</span>
        </div>
        </div>
        <div className="p-lg flex flex-col flex-grow">
        <h3 className="text-headline-sm font-headline-sm text-on-surface line-clamp-1">프로덕트 매니지먼트 에센셜</h3>
        <p className="text-body-sm font-body-sm text-on-surface-variant mt-xs mb-md">시장 정의부터 제품 출시까지, 성공하는 PM의 로드맵</p>
        <div className="mt-auto">
        <div className="flex justify-between items-center mb-xs">
        <span className="text-label-sm font-label-sm text-on-surface-variant">진행률 92%</span>
        <span className="text-label-sm font-label-sm text-primary">11/12 강의</span>
        </div>
        <div className="w-full h-1.5 progress-bar-bg rounded-full overflow-hidden">
        <div className="h-full progress-bar-fill" style={{width: '92%'}}></div>
        </div>
        </div>
        </div>
        </div>
        </Link>

        <Link href="/roadmaps/5"><div className="roadmap-card flex flex-col border border-outline-variant rounded-xl overflow-hidden cursor-pointer group shadow-sm">
        <div className="relative aspect-video overflow-hidden">
        <img className="w-full h-full object-cover group-hover:scale-105 transition-transform duration-500" alt="Cybersecurity abstraction" src="https://lh3.googleusercontent.com/aida-public/AB6AXuA8qopuHzxkfovzBWHhm4-rv-C7QTrsXjCpyI_nsH4olOUN1q1zXATJlGb_f1On__oQMXtlmdzdn_Xf1FZhETBpnJ40sg_Ndf9m3NtwcZx5e798kCHQOzIeEvVAl8SloUsm5Tg-tEYibegAcN1OLNvwtpzWcK4PFCilDH6c2lIX6CXJqP6bVnTo96QEkmGBwtnrZKtqErWRElFsjeGbicdtb_CzFRF3yzmwVhUFR8xO60t6iEQP8A03yr8nU0FDMql-l2WXmRPu6l_i" />
        <div className="absolute top-3 left-3 bg-white/95 backdrop-blur-sm px-sm py-1 rounded-full shadow-sm">
        <span className="text-label-sm font-label-sm text-primary">진행 중</span>
        </div>
        </div>
        <div className="p-lg flex flex-col flex-grow">
        <h3 className="text-headline-sm font-headline-sm text-on-surface line-clamp-1">엔터프라이즈 보안 기초</h3>
        <p className="text-body-sm font-body-sm text-on-surface-variant mt-xs mb-md">기업 환경에서의 정보 보호 프레임워크와 거버넌스 이해</p>
        <div className="mt-auto">
        <div className="flex justify-between items-center mb-xs">
        <span className="text-label-sm font-label-sm text-on-surface-variant">진행률 40%</span>
        <span className="text-label-sm font-label-sm text-primary">4/10 강의</span>
        </div>
        <div className="w-full h-1.5 progress-bar-bg rounded-full overflow-hidden">
        <div className="h-full progress-bar-fill" style={{width: '40%'}}></div>
        </div>
        </div>
        </div>
        </div>
        </Link>

        <Link href="/roadmaps/6"><div className="roadmap-card flex flex-col border border-outline-variant rounded-xl overflow-hidden cursor-pointer group shadow-sm bg-surface-container-low">
        <div className="relative aspect-video overflow-hidden grayscale opacity-80">
        <img className="w-full h-full object-cover" alt="Cloud infrastructure" src="https://lh3.googleusercontent.com/aida-public/AB6AXuAgzlUvpM2zU_igtlkyBKasd8FRdnad7KWjp5aoj1rfx2OigDlkbUIiqatqH_KgMuwuu4-_iGuT11wKi_eBfLqCjUjqFXa-CQr5J3S9ShuZZZ0xay1r9iADuEtYt7cjDMuAdgKrEj-PpBqLkY_rPuvZbD91IQcR2MjQOfRhf-jX6VwJiumPzqBxdcE_ECQ2py7NdNhMcOGGNScRyh3zUIDs-ShtEc0zio5F2fnHHuZSQ1taTsauisYHZ8VyKqrEKmvQnYvPBKqEZaw_" />
        <div className="absolute inset-0 bg-primary/20 flex items-center justify-center">
        <span className="material-symbols-outlined text-primary text-4xl">check_circle</span>
        </div>
        </div>
        <div className="p-lg flex flex-col flex-grow">
        <h3 className="text-headline-sm font-headline-sm text-on-surface line-clamp-1">클라우드 네이티브 입문</h3>
        <p className="text-body-sm font-body-sm text-on-surface-variant mt-xs mb-md">Docker, Kubernetes를 활용한 마이크로서비스 배포 실습</p>
        <div className="mt-auto">
        <div className="flex justify-between items-center mb-xs">
        <span className="text-label-sm font-label-sm text-on-surface-variant">완료됨</span>
        <span className="text-label-sm font-label-sm text-primary">로드맵 달성</span>
        </div>
        <div className="w-full h-1.5 bg-primary/10 rounded-full overflow-hidden">
        <div className="h-full bg-primary" style={{width: '100%'}}></div>
        </div>
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
