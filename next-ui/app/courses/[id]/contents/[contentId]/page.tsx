'use client';

import { useState } from 'react';
import Link from 'next/link';
import { TopNavBar } from '@/components/TopNavBar';
import { MOCK_COURSES } from '@/app/courses/mockData';

const COURSE = MOCK_COURSES[0];
const CONTENT = COURSE.contents[1]; // VIDEO 예시

const SIDEBAR_ITEMS = COURSE.contents.map((c, i) => ({
  ...c,
  done: i === 0,
  current: i === 1,
}));

const TYPE_ICON: Record<string, string> = {
  VIDEO: 'play_circle',
  DOCUMENT: 'description',
  QUIZ: 'quiz',
};

export default function ContentViewerPage() {
  const [noteOpen, setNoteOpen] = useState(false);
  const [note, setNote] = useState('');

  const isVideo = CONTENT.type === 'VIDEO';
  const isDocument = CONTENT.type === 'DOCUMENT';

  return (
    <div className="min-h-screen bg-background flex flex-col">
      {/* 상단 바 (간소화) */}
      <header className="fixed top-0 left-0 right-0 z-50 h-16 bg-surface-container-low border-b border-outline-variant flex items-center px-gutter gap-lg">
        <Link href={`/courses/${COURSE.id}`} className="flex items-center gap-xs text-on-surface-variant hover:text-primary transition-colors">
          <span className="material-symbols-outlined text-[20px]">arrow_back</span>
          <span className="text-label-md font-label-md hidden sm:block">강좌로</span>
        </Link>
        <div className="flex-1 min-w-0">
          <p className="text-label-md font-label-md text-on-surface truncate">{COURSE.title}</p>
        </div>
        <button
          type="button"
          onClick={() => setNoteOpen(!noteOpen)}
          className={`flex items-center gap-xs px-md py-sm rounded-lg text-label-sm font-label-sm transition-colors ${noteOpen ? 'bg-primary text-on-primary' : 'bg-surface-container-high text-on-surface hover:bg-surface-container'}`}
        >
          <span className="material-symbols-outlined text-[18px]">edit_note</span>
          노트
        </button>
      </header>

      <div className="pt-16 flex flex-1 overflow-hidden">

        {/* 사이드바: 커리큘럼 */}
        <aside className="w-72 flex-shrink-0 border-r border-outline-variant bg-surface-container-lowest overflow-y-auto hidden lg:flex flex-col">
          <div className="px-lg pt-lg pb-md border-b border-outline-variant">
            <p className="text-label-sm font-label-sm text-on-surface-variant">커리큘럼</p>
          </div>
          <div className="flex-1 overflow-y-auto">
            {SIDEBAR_ITEMS.map((item, idx) => (
              <button
                key={item.id}
                type="button"
                className={`w-full text-left px-lg py-md flex items-center gap-md border-b border-outline-variant/50 transition-colors ${
                  item.current ? 'bg-primary-container/30' : 'hover:bg-surface-container'
                }`}
              >
                <div className={`w-6 h-6 rounded-full flex-shrink-0 flex items-center justify-center ${
                  item.done ? 'bg-primary' : item.current ? 'bg-primary/20 border-2 border-primary' : 'bg-surface-container-high'
                }`}>
                  {item.done
                    ? <span className="material-symbols-outlined text-on-primary text-[13px]" style={{ fontVariationSettings: "'FILL' 1" }}>check</span>
                    : <span className={`material-symbols-outlined text-[13px] ${item.current ? 'text-primary' : 'text-on-surface-variant'}`}>{TYPE_ICON[item.type] ?? 'circle'}</span>
                  }
                </div>
                <div className="flex-1 min-w-0">
                  <p className={`text-body-sm font-body-md line-clamp-2 leading-tight ${item.current ? 'text-primary font-medium' : item.done ? 'text-on-surface-variant line-through' : 'text-on-surface'}`}>
                    {item.title}
                  </p>
                  {item.durationSeconds > 0 && (
                    <p className="text-label-sm font-label-sm text-on-surface-variant mt-0.5">
                      {Math.floor(item.durationSeconds / 60)}분
                    </p>
                  )}
                </div>
              </button>
            ))}
          </div>
        </aside>

        {/* 메인 콘텐츠 영역 */}
        <main className="flex-1 overflow-y-auto">
          <div className="max-w-[900px] mx-auto px-gutter py-xl">

            {/* 비디오 플레이어 */}
            {isVideo && (
              <div className="bg-black rounded-xl overflow-hidden aspect-video mb-xl flex items-center justify-center">
                <div className="text-center text-white">
                  <span className="material-symbols-outlined text-[64px] mb-md block opacity-60" style={{ fontVariationSettings: "'FILL' 1" }}>play_circle</span>
                  <p className="text-label-md font-label-md opacity-60">영상 플레이어 영역</p>
                  <p className="text-label-sm font-label-sm opacity-40 mt-xs">BE 연동 후 실제 영상 표시</p>
                </div>
              </div>
            )}

            {/* 문서 뷰어 */}
            {isDocument && (
              <div className="bg-surface-container-lowest border border-outline-variant rounded-xl p-xl mb-xl min-h-[400px] flex flex-col items-center justify-center">
                <span className="material-symbols-outlined text-[48px] text-primary mb-md">description</span>
                <p className="text-label-md font-label-md text-on-surface mb-sm">학습 자료</p>
                <p className="text-body-sm font-body-md text-on-surface-variant mb-xl">BE 연동 후 문서 파일이 표시됩니다.</p>
                <button type="button" className="bg-primary text-on-primary px-xl py-sm rounded-lg text-label-md font-label-md hover:opacity-90 transition-opacity flex items-center gap-xs">
                  <span className="material-symbols-outlined text-[18px]">download</span>
                  자료 다운로드
                </button>
              </div>
            )}

            {/* 콘텐츠 정보 */}
            <div className="mb-xl">
              <h1 className="text-headline-md font-headline-md text-on-surface mb-md">{CONTENT.title}</h1>
              <div className="flex items-center gap-md text-label-md font-label-md text-on-surface-variant">
                <span className="flex items-center gap-xs">
                  <span className="material-symbols-outlined text-[16px]">{TYPE_ICON[CONTENT.type]}</span>
                  {CONTENT.type === 'VIDEO' ? '동영상' : CONTENT.type === 'DOCUMENT' ? '학습 자료' : '퀴즈'}
                </span>
                {CONTENT.durationSeconds > 0 && (
                  <>
                    <span>·</span>
                    <span>{Math.floor(CONTENT.durationSeconds / 60)}분 {CONTENT.durationSeconds % 60}초</span>
                  </>
                )}
              </div>
            </div>

            {/* 이전 / 다음 버튼 */}
            <div className="flex items-center justify-between border-t border-outline-variant pt-lg">
              <button type="button" disabled className="flex items-center gap-xs text-on-surface-variant disabled:opacity-40 hover:text-primary transition-colors">
                <span className="material-symbols-outlined">chevron_left</span>
                <span className="text-label-md font-label-md">이전 강의</span>
              </button>
              <button
                type="button"
                className="bg-primary text-on-primary px-xl py-sm rounded-lg text-label-md font-label-md hover:opacity-90 transition-opacity flex items-center gap-xs"
              >
                완료 및 다음 강의
                <span className="material-symbols-outlined">chevron_right</span>
              </button>
            </div>

          </div>
        </main>

        {/* 노트 패널 */}
        {noteOpen && (
          <aside className="w-72 flex-shrink-0 border-l border-outline-variant bg-surface-container-lowest flex flex-col">
            <div className="px-lg pt-lg pb-md border-b border-outline-variant flex items-center justify-between">
              <p className="text-label-md font-label-md text-on-surface">학습 노트</p>
              <button type="button" onClick={() => setNoteOpen(false)} className="text-on-surface-variant hover:text-on-surface transition-colors">
                <span className="material-symbols-outlined text-[20px]">close</span>
              </button>
            </div>
            <div className="flex-1 p-md">
              <textarea
                value={note}
                onChange={(e) => setNote(e.target.value)}
                className="w-full h-full bg-surface-container-low border border-outline-variant rounded-lg p-md text-body-sm font-body-md text-on-surface resize-none focus:border-primary focus:outline-none"
                placeholder="이 강의에서 중요한 내용을 기록해 두세요..."
              />
            </div>
          </aside>
        )}

      </div>
    </div>
  );
}
