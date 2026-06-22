'use client';

import { useState } from 'react';
import Link from 'next/link';
import { TopNavBar } from '@/components/TopNavBar';
import { Footer } from '@/components/Footer';
import { getCourseById, MOCK_COURSES } from '@/app/courses/mockData';

export default function CourseEditPage({ params }: { params: { id: string } }) {
  const course = getCourseById(params.id) ?? MOCK_COURSES[0];

  const [isPublic, setIsPublic] = useState(true);

  const [contents, setContents] = useState([
    '오리엔테이션 및 환경 설정',
    'Python 기본 문법 복습',
    'Pandas를 활용한 데이터 핸들링',
    '데이터 시각화의 정석',
    '중급 분석 실습 프로젝트',
  ]);
  const [showAddInput, setShowAddInput] = useState(false);
  const [newContentTitle, setNewContentTitle] = useState('');

  const handleAddContent = () => {
    const title = newContentTitle.trim();
    if (!title) return;
    setContents([...contents, title]);
    setNewContentTitle('');
    setShowAddInput(false);
  };

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
<Link href={`/courses/${course.id}/stats`} className="flex items-center gap-sm px-md py-sm bg-surface border border-outline-variant rounded-lg text-label-md font-label-md hover:bg-surface-container-low transition-all">
<span className="material-symbols-outlined text-[20px]">analytics</span>
                    통계 보기
                </Link>
<Link href={`/courses/${course.id}/questions/instructor`} className="flex items-center gap-sm px-md py-sm bg-surface border border-outline-variant rounded-lg text-label-md font-label-md hover:bg-surface-container-low transition-all">
<span className="material-symbols-outlined text-[20px]">forum</span>
                    Q&amp;A 바로가기
                </Link>
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
<button
  type="button"
  onClick={() => setShowAddInput(true)}
  className="text-primary font-label-md flex items-center gap-xs hover:opacity-80 transition-opacity"
>
<span className="material-symbols-outlined text-[18px]">add_circle</span>
  콘텐츠 추가
</button>
</div>
<div className="space-y-sm">
{contents.map((title, idx) => (
  <div key={idx} className="flex items-center gap-md p-md bg-white border border-outline-variant rounded-lg group hover:border-primary-container transition-all cursor-move">
    <span className="material-symbols-outlined text-outline">drag_indicator</span>
    <p className="flex-grow text-label-md font-label-md text-on-surface">{idx + 1}. {title}</p>
    <button
      type="button"
      onClick={() => setContents(contents.filter((_, i) => i !== idx))}
      className="material-symbols-outlined text-outline opacity-0 group-hover:opacity-100 transition-opacity text-error hover:text-error"
    >delete</button>
  </div>
))}
{showAddInput && (
  <div className="flex items-center gap-sm p-md bg-primary-fixed/10 border border-primary/30 rounded-lg">
    <input
      autoFocus
      type="text"
      value={newContentTitle}
      onChange={(e) => setNewContentTitle(e.target.value)}
      onKeyDown={(e) => { if (e.key === 'Enter') { e.preventDefault(); handleAddContent(); } if (e.key === 'Escape') { setShowAddInput(false); setNewContentTitle(''); } }}
      placeholder="콘텐츠 제목을 입력하세요"
      className="flex-1 px-sm py-xs border border-outline-variant rounded-lg text-label-md font-label-md focus:border-primary focus:outline-none"
    />
    <button type="button" onClick={handleAddContent} className="px-md py-xs bg-primary text-on-primary rounded-lg text-label-sm font-label-sm hover:opacity-90">입력</button>
    <button type="button" onClick={() => { setShowAddInput(false); setNewContentTitle(''); }} className="px-md py-xs border border-outline-variant rounded-lg text-label-sm font-label-sm hover:bg-surface-container">취소</button>
  </div>
)}
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
<input
  type="checkbox"
  checked={isPublic}
  onChange={(e) => setIsPublic(e.target.checked)}
  className="sr-only peer"
/>
<div className="w-11 h-6 bg-surface-variant rounded-full peer peer-checked:bg-primary after:content-[''] after:absolute after:top-[2px] after:left-[2px] after:bg-white after:border after:border-gray-300 after:rounded-full after:h-5 after:w-5 after:transition-all peer-checked:after:translate-x-full peer-checked:after:border-white"></div>
<span className="ml-3 text-label-md font-label-md">{isPublic ? '공개' : '비공개'}</span>
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
