'use client';

import { useState } from 'react';
import { useRouter } from 'next/navigation';
import Link from 'next/link';
import { TopNavBar } from '@/components/TopNavBar';
import { Footer } from '@/components/Footer';

const CATEGORIES = ['프로그래밍', '데이터 사이언스', 'UI/UX 디자인', '클라우드', '사이버 보안', '그로스 마케팅', '영상/미디어', '기타'];
const LEVELS = ['입문', '초급', '중급', '고급'];

export default function CourseNewPage() {
  const router = useRouter();

  const [title, setTitle] = useState('');
  const [summary, setSummary] = useState('');
  const [description, setDescription] = useState('');
  const [category, setCategory] = useState('');
  const [level, setLevel] = useState('');
  const [price, setPrice] = useState('');
  const [tags, setTags] = useState<string[]>([]);
  const [tagInput, setTagInput] = useState('');
  const [saved, setSaved] = useState(false);
  const [contents, setContents] = useState<string[]>([]);
  const [showAddInput, setShowAddInput] = useState(false);
  const [newContentTitle, setNewContentTitle] = useState('');

  const handleAddContent = () => {
    const t = newContentTitle.trim();
    if (!t) return;
    setContents([...contents, t]);
    setNewContentTitle('');
    setShowAddInput(false);
  };

  const addTag = () => {
    const t = tagInput.trim();
    if (t && !tags.includes(t)) setTags([...tags, t]);
    setTagInput('');
  };

  const removeTag = (t: string) => setTags(tags.filter((x) => x !== t));

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    setSaved(true);
    setTimeout(() => router.push('/courses/manage'), 1200);
  };

  return (
    <>
      <TopNavBar />
      <main className="pt-16 min-h-screen bg-background">
        <div className="mt-16 pb-xl px-gutter max-w-[1280px] mx-auto w-full">

          {/* 상단 네비 */}
          <div className="mb-lg flex items-center justify-between">
            <Link href="/courses/manage" className="flex items-center gap-xs text-primary font-label-md hover:underline">
              <span className="material-symbols-outlined text-[18px]">arrow_back</span>
              강좌 관리로 돌아가기
            </Link>
          </div>

          <div className="mb-xl">
            <h1 className="font-headline-lg text-headline-lg text-on-surface mb-xs">새 강좌 개설하기</h1>
            <p className="font-body-md text-body-md text-on-surface-variant">기본 정보를 입력하고 강좌를 개설하세요. 개설 후 커리큘럼을 추가할 수 있습니다.</p>
          </div>

          <form onSubmit={handleSubmit}>
            <div className="grid grid-cols-1 lg:grid-cols-12 gap-xl">

              {/* 왼쪽 — 기본 정보 */}
              <section className="lg:col-span-7 space-y-lg">

                <div className="bg-surface-container-lowest border border-outline-variant rounded-xl p-lg space-y-md">
                  <h2 className="font-headline-md text-headline-md text-on-surface">강좌 기본 정보</h2>

                  {/* 제목 */}
                  <div className="space-y-xs">
                    <label className="text-label-md font-label-md text-on-surface-variant">강좌 제목 <span className="text-error">*</span></label>
                    <input
                      required
                      value={title}
                      onChange={(e) => setTitle(e.target.value)}
                      className="w-full px-md py-sm border border-outline-variant rounded-lg focus:ring-2 focus:ring-primary-container focus:border-primary outline-none transition-all font-body-md text-body-md"
                      placeholder="예: 실전 React + TypeScript 완성 강좌"
                    />
                  </div>

                  {/* 한 줄 요약 */}
                  <div className="space-y-xs">
                    <label className="text-label-md font-label-md text-on-surface-variant">한 줄 요약 <span className="text-error">*</span></label>
                    <input
                      required
                      value={summary}
                      onChange={(e) => setSummary(e.target.value)}
                      className="w-full px-md py-sm border border-outline-variant rounded-lg focus:ring-2 focus:ring-primary-container focus:border-primary outline-none transition-all font-body-md text-body-md"
                      placeholder="수강생에게 보여지는 핵심 문구"
                    />
                  </div>

                  {/* 상세 설명 */}
                  <div className="space-y-xs">
                    <label className="text-label-md font-label-md text-on-surface-variant">강좌 상세 설명</label>
                    <textarea
                      value={description}
                      onChange={(e) => setDescription(e.target.value)}
                      rows={6}
                      className="w-full px-md py-sm border border-outline-variant rounded-lg focus:ring-2 focus:ring-primary-container focus:border-primary outline-none transition-all font-body-md text-body-md resize-none"
                      placeholder="강좌 목표, 대상, 커리큘럼 개요 등을 자세히 작성해 주세요."
                    />
                  </div>
                </div>

                {/* 커리큘럼 */}
                <div className="bg-surface-container-lowest border border-outline-variant rounded-xl p-lg space-y-md">
                  <div className="flex justify-between items-center">
                    <h2 className="font-headline-md text-headline-md text-on-surface">커리큘럼 구성</h2>
                    <button
                      type="button"
                      onClick={() => setShowAddInput(true)}
                      className="text-primary font-label-md flex items-center gap-xs hover:opacity-80 transition-opacity text-label-md"
                    >
                      <span className="material-symbols-outlined text-[18px]">add_circle</span>
                      콘텐츠 추가
                    </button>
                  </div>
                  <div className="space-y-sm">
                    {contents.length === 0 && !showAddInput && (
                      <p className="text-label-sm font-label-sm text-on-surface-variant text-center py-md">아직 추가된 콘텐츠가 없습니다.</p>
                    )}
                    {contents.map((title, idx) => (
                      <div key={idx} className="flex items-center gap-md p-md bg-surface-container-low border border-outline-variant rounded-lg group hover:border-primary-container transition-all">
                        <span className="material-symbols-outlined text-outline">drag_indicator</span>
                        <p className="flex-grow text-label-md font-label-md text-on-surface">{idx + 1}. {title}</p>
                        <button
                          type="button"
                          onClick={() => setContents(contents.filter((_, i) => i !== idx))}
                          className="material-symbols-outlined text-outline opacity-0 group-hover:opacity-100 transition-opacity"
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

                {/* 태그 */}
                <div className="bg-surface-container-lowest border border-outline-variant rounded-xl p-lg space-y-md">
                  <h2 className="font-headline-md text-headline-md text-on-surface">태그</h2>
                  <div className="flex gap-sm">
                    <input
                      value={tagInput}
                      onChange={(e) => setTagInput(e.target.value)}
                      onKeyDown={(e) => { if (e.key === 'Enter') { e.preventDefault(); addTag(); } }}
                      className="flex-1 px-md py-sm border border-outline-variant rounded-lg focus:ring-2 focus:ring-primary-container focus:border-primary outline-none transition-all font-body-md text-body-md"
                      placeholder="태그 입력 후 Enter"
                    />
                    <button
                      type="button"
                      onClick={addTag}
                      className="px-md py-sm bg-surface-container-high text-on-surface rounded-lg font-label-md text-label-md hover:bg-surface-variant transition-all"
                    >
                      추가
                    </button>
                  </div>
                  {tags.length > 0 && (
                    <div className="flex flex-wrap gap-xs">
                      {tags.map((t) => (
                        <span key={t} className="flex items-center gap-xs px-sm py-xs bg-primary-container text-on-primary-container rounded-full text-label-sm font-label-sm">
                          {t}
                          <button type="button" onClick={() => removeTag(t)} className="hover:opacity-70">
                            <span className="material-symbols-outlined text-[14px]">close</span>
                          </button>
                        </span>
                      ))}
                    </div>
                  )}
                </div>

              </section>

              {/* 오른쪽 — 설정 */}
              <aside className="lg:col-span-5 space-y-lg">

                {/* 썸네일 */}
                <div className="bg-surface-container-lowest border border-outline-variant rounded-xl p-lg space-y-md">
                  <h2 className="font-headline-md text-headline-md text-on-surface">썸네일</h2>
                  <div className="aspect-video w-full rounded-xl border-2 border-dashed border-outline-variant bg-surface-container flex flex-col items-center justify-center cursor-pointer hover:border-primary transition-all group">
                    <span className="material-symbols-outlined text-[40px] text-outline-variant group-hover:text-primary transition-colors mb-sm">cloud_upload</span>
                    <span className="text-label-md font-label-md text-on-surface-variant">클릭하여 이미지 업로드</span>
                    <span className="text-label-sm font-label-sm text-outline mt-xs">권장: 1280 × 720 (16:9)</span>
                  </div>
                </div>

                {/* 분류 설정 */}
                <div className="bg-surface-container-lowest border border-outline-variant rounded-xl p-lg space-y-md">
                  <h2 className="font-headline-md text-headline-md text-on-surface">분류 설정</h2>

                  {/* 카테고리 */}
                  <div className="space-y-xs">
                    <label className="text-label-md font-label-md text-on-surface-variant">카테고리 <span className="text-error">*</span></label>
                    <select
                      required
                      value={category}
                      onChange={(e) => setCategory(e.target.value)}
                      className="w-full px-md py-sm border border-outline-variant rounded-lg focus:ring-2 focus:ring-primary-container focus:border-primary outline-none transition-all font-body-md text-body-md bg-surface-container-lowest"
                    >
                      <option value="">카테고리 선택</option>
                      {CATEGORIES.map((c) => <option key={c} value={c}>{c}</option>)}
                    </select>
                  </div>

                  {/* 난이도 */}
                  <div className="space-y-xs">
                    <label className="text-label-md font-label-md text-on-surface-variant">난이도 <span className="text-error">*</span></label>
                    <div className="grid grid-cols-4 gap-sm">
                      {LEVELS.map((lv) => (
                        <button
                          key={lv}
                          type="button"
                          onClick={() => setLevel(lv)}
                          className={`py-xs rounded-lg text-label-sm font-label-sm border transition-all ${level === lv ? 'bg-primary text-on-primary border-primary' : 'border-outline-variant text-on-surface-variant hover:bg-surface-container'}`}
                        >
                          {lv}
                        </button>
                      ))}
                    </div>
                  </div>

                  {/* 가격 */}
                  <div className="space-y-xs">
                    <label className="text-label-md font-label-md text-on-surface-variant">수강료 (원)</label>
                    <input
                      type="number"
                      value={price}
                      onChange={(e) => setPrice(e.target.value)}
                      min={0}
                      className="w-full px-md py-sm border border-outline-variant rounded-lg focus:ring-2 focus:ring-primary-container focus:border-primary outline-none transition-all font-body-md text-body-md"
                      placeholder="0 입력 시 무료"
                    />
                  </div>
                </div>

                {/* 공개 상태 */}
                <div className="bg-surface-container-lowest border border-outline-variant rounded-xl p-lg space-y-sm">
                  <h2 className="font-headline-md text-headline-md text-on-surface mb-sm">공개 설정</h2>
                  {['비공개 (임시저장)', '공개'].map((opt) => (
                    <label key={opt} className="flex items-center gap-md cursor-pointer">
                      <input type="radio" name="visibility" defaultChecked={opt.startsWith('비공개')} className="accent-primary w-4 h-4" />
                      <span className="font-body-md text-body-md text-on-surface">{opt}</span>
                    </label>
                  ))}
                </div>

                {/* 저장 버튼 */}
                <button
                  type="submit"
                  disabled={saved}
                  className="w-full py-md rounded-xl bg-primary text-on-primary font-label-md text-label-md hover:brightness-110 transition-all disabled:opacity-60 flex items-center justify-center gap-xs"
                >
                  {saved ? (
                    <>
                      <span className="material-symbols-outlined text-[18px]">check_circle</span>
                      개설 완료! 강좌 관리로 이동 중...
                    </>
                  ) : (
                    <>
                      <span className="material-symbols-outlined text-[18px]">add_circle</span>
                      강좌 개설하기
                    </>
                  )}
                </button>

              </aside>
            </div>
          </form>

        </div>
      </main>
      <Footer />
    </>
  );
}
