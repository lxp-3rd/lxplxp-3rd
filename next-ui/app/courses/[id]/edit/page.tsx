'use client';

import { useState } from 'react';
import { TopNavBar } from '@/components/TopNavBar';
import { Footer } from '@/components/Footer';
import { getCourseById, MOCK_COURSES } from '@/app/courses/mockData';

export default function CourseEditPage({ params }: { params: { id: string } }) {
  const course = getCourseById(params.id) ?? MOCK_COURSES[0];
  return (
    <>
      <TopNavBar />
      <main className="pt-16 min-h-screen bg-background">
<div className="mt-16 flex-grow px-gutter py-xl max-w-[1280px] mx-auto w-full">

<div className="mb-lg flex items-center justify-between">
<a className="flex items-center gap-xs text-primary font-label-md hover:underline transition-all" href="/courses/manage">
<span className="material-symbols-outlined text-[18px]">arrow_back</span>
                강의 목록으로 돌아가기
            </a>
<div className="flex gap-sm">
<button className="flex items-center gap-sm px-md py-sm bg-surface border border-outline-variant rounded-lg text-label-md font-label-md hover:bg-surface-container-low transition-all">
<span className="material-symbols-outlined text-[20px]">analytics</span>
                    통계 보기
                </button>
<button className="flex items-center gap-sm px-md py-sm bg-surface border border-outline-variant rounded-lg text-label-md font-label-md hover:bg-surface-container-low transition-all">
<span className="material-symbols-outlined text-[20px]">forum</span>
                    Q&amp;A 바로가기
                </button>
</div>
</div>
<div className="grid grid-cols-1 lg:grid-cols-12 gap-xl">

<section className="lg:col-span-7 space-y-lg">
<div className="glass-panel p-lg rounded-xl shadow-sm">
<h2 className="text-headline-md font-headline-md mb-lg">강의 기본 정보 편집</h2>
<form className="space-y-md">

<div className="space-y-xs">
<label className="text-label-md font-label-md text-on-surface-variant">강의 제목</label>
<input className="w-full px-md py-sm border border-outline-variant rounded-lg focus:ring-2 focus:ring-primary-container focus:border-primary-container outline-none transition-all text-body-md font-body-md" type="text" defaultValue={course.title} />
</div>

<div className="space-y-xs">
<label className="text-label-md font-label-md text-on-surface-variant">강의 썸네일</label>
<div className="group relative aspect-video w-full rounded-xl overflow-hidden border-2 border-dashed border-outline-variant bg-surface-container-lowest flex flex-col items-center justify-center cursor-pointer hover:border-primary-container transition-all">
<div className="absolute inset-0 z-0">
<img className="w-full h-full object-cover opacity-30 group-hover:opacity-10 transition-opacity" alt="A high-quality educational course thumbnail representing data science and Python programming. The design is sleek and modern, featuring vibrant orange abstract data visualizations and geometric patterns over a clean white background. High contrast, sharp lines, and a professional academic aesthetic suitable for a premium learning platform." src="https://lh3.googleusercontent.com/aida-public/AB6AXuCLiG9MKp4Qp5a6Fw7eLj7PDoKfcZDkfTR50qFMIHnM2L8FX5dxqHnoI-DtJVniBWp8O3-TxSfTPWLdkce2MTjmNahTi21n5HsQy1HtQdxOwHNDXY74Wb01gqrbBxGTfLLtbpgxw56LtuWMlgdWCLk6WUOYXPGIWsZDJE9IBDdsUcI1_yXtwrNW-ByMk8OMFOKqUDcAMANRef0vK2l_WxvvjL1Zwcqscmjw63auYrmVDncjuitRYAFKqXbgN4GklznF6-Z-t2JAaIZY" />
</div>
<div className="z-10 flex flex-col items-center">
<span className="material-symbols-outlined text-primary-container text-[48px] mb-sm">cloud_upload</span>
<span className="text-label-md font-label-md text-on-surface-variant">이미지를 드래그하거나 클릭하여 업로드</span>
<span className="text-label-sm font-label-sm text-tertiary mt-xs">추천 사이즈: 1280 x 720 (16:9)</span>
</div>
</div>
</div>

<div className="space-y-xs">
<label className="text-label-md font-label-md text-on-surface-variant">강의 요약 (단문)</label>
<input className="w-full px-md py-sm border border-outline-variant rounded-lg focus:ring-2 focus:ring-primary-container focus:border-primary-container outline-none text-body-md" placeholder="핵심 내용 한 줄 요약" type="text" />
</div>

<div className="space-y-xs">
<label className="text-label-md font-label-md text-on-surface-variant">상세 설명</label>
<textarea className="w-full px-md py-sm border border-outline-variant rounded-lg focus:ring-2 focus:ring-primary-container focus:border-primary-container outline-none text-body-md custom-scrollbar resize-none" rows={6}>이 강의는 데이터 과학의 핵심 원리를 기초부터 다룹니다. Python 라이브러리인 Pandas, NumPy, Matplotlib을 활용하여 실제 데이터를 분석하고 시각화하는 방법을 학습합니다. 입문자도 쉽게 따라올 수 있도록 실습 위주로 구성되어 있습니다.</textarea>
</div>
</form>
</div>
</section>

<section className="lg:col-span-5 space-y-lg">

<div className="glass-panel p-lg rounded-xl shadow-sm">
<div className="flex justify-between items-center mb-md">
<h2 className="text-headline-md font-headline-md text-[20px]">커리큘럼 구성</h2>
<button className="text-primary font-label-md flex items-center gap-xs hover:opacity-80 transition-opacity">
<span className="material-symbols-outlined text-[18px]">add_circle</span>
                            콘텐츠 추가
                        </button>
</div>
<div className="space-y-sm">

<div className="flex items-center gap-md p-md bg-white border border-outline-variant rounded-lg group hover:border-primary-container transition-all cursor-move">
<span className="material-symbols-outlined text-outline">drag_indicator</span>
<div className="flex-grow">
<p className="text-label-md font-label-md text-on-surface">1. 오리엔테이션 및 환경 설정</p>
<p className="text-label-sm font-label-sm text-tertiary">비디오 • 12:40</p>
</div>
<button className="material-symbols-outlined text-outline opacity-0 group-hover:opacity-100 transition-opacity" style={{opacity: '1'}}>edit</button>
</div>

<div className="flex items-center gap-md p-md bg-white border border-outline-variant rounded-lg group hover:border-primary-container transition-all cursor-move">
<span className="material-symbols-outlined text-outline">drag_indicator</span>
<div className="flex-grow">
<p className="text-label-md font-label-md text-on-surface">2. Python 기본 문법 복습</p>
<p className="text-label-sm font-label-sm text-tertiary">비디오 • 25:15</p>
</div>
<button className="material-symbols-outlined text-outline opacity-0 group-hover:opacity-100 transition-opacity">edit</button>
</div>

<div className="flex items-center gap-md p-md bg-white border border-outline-variant rounded-lg group hover:border-primary-container transition-all cursor-move">
<span className="material-symbols-outlined text-outline">drag_indicator</span>
<div className="flex-grow">
<p className="text-label-md font-label-md text-on-surface">3. Pandas를 활용한 데이터 핸들링</p>
<p className="text-label-sm font-label-sm text-tertiary">실습 파일 • 2.4 MB</p>
</div>
<button className="material-symbols-outlined text-outline opacity-0 group-hover:opacity-100 transition-opacity">edit</button>
</div>

<div className="flex items-center gap-md p-md bg-white border border-outline-variant rounded-lg group hover:border-primary-container transition-all cursor-move">
<span className="material-symbols-outlined text-outline">drag_indicator</span>
<div className="flex-grow">
<p className="text-label-md font-label-md text-on-surface">4. 데이터 시각화의 정석</p>
<p className="text-label-sm font-label-sm text-tertiary">비디오 • 18:00</p>
</div>
<button className="material-symbols-outlined text-outline opacity-0 group-hover:opacity-100 transition-opacity">edit</button>
</div>

<div className="flex items-center gap-md p-md bg-white border border-outline-variant rounded-lg group hover:border-primary-container transition-all cursor-move border-l-4 border-l-primary-container">
<span className="material-symbols-outlined text-outline">drag_indicator</span>
<div className="flex-grow">
<p className="text-label-md font-label-md text-on-surface">5. 중급 분석 실습 프로젝트</p>
<p className="text-label-sm font-label-sm text-tertiary">퀴즈 • 10 문항</p>
</div>
<button className="material-symbols-outlined text-outline opacity-0 group-hover:opacity-100 transition-opacity">edit</button>
</div>
</div>
</div>

<div className="glass-panel p-lg rounded-xl shadow-sm space-y-md">
<h2 className="text-headline-md font-headline-md text-[20px]">강의 상태 설정</h2>
<div className="flex items-center justify-between p-md bg-surface-container-low rounded-lg">
<div className="flex items-center gap-sm">
<span className="material-symbols-outlined text-primary">visibility</span>
<span className="text-label-md font-label-md">강의 공개 여부</span>
</div>
<label className="relative inline-flex items-center cursor-pointer">
<input checked className="sr-only peer" type="checkbox" />
<div className="w-11 h-6 bg-surface-variant peer-focus:outline-none rounded-full peer peer-defaultChecked={true}:after:translate-x-full peer-defaultChecked={true}:after:border-white after:content-[''] after:absolute after:top-[2px] after:left-[2px] after:bg-white after:border-gray-300 after:border after:rounded-full after:h-5 after:w-5 after:transition-all peer-defaultChecked={true}:bg-primary-container"></div>
<span className="ml-3 text-label-md font-label-md">공개</span>
</label>
</div>
<div className="p-md bg-surface-container-low rounded-lg space-y-sm">
<div className="flex items-center gap-sm">
<span className="material-symbols-outlined text-primary">info</span>
<span className="text-label-md font-label-md">저장 안내</span>
</div>
<p className="text-label-sm font-label-sm text-on-surface-variant">
                            변경사항은 즉시 반영되지 않으며, 하단의 '변경사항 저장' 버튼을 눌러야 최종 저장됩니다.
                        </p>
</div>
<button className="w-full py-md bg-primary-container text-white font-headline-md text-[18px] rounded-lg hover:shadow-lg hover:scale-[1.02] active:scale-100 transition-all duration-300">
                        변경사항 저장
                    </button>
</div>
</section>
</div>
</div>
</main>
      <Footer />
    </>
  );
}
