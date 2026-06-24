'use client';

import { useEffect, useRef, useState } from 'react';
import { useRouter } from 'next/navigation';
import Link from 'next/link';
import { TopNavBar } from '@/components/TopNavBar';
import { Footer } from '@/components/Footer';
import { courseApi, imageApi, instructorApi } from '../api';

export default function CourseNewPage() {
  const router = useRouter();

  const [instructorId, setInstructorId] = useState<number | null>(null);
  const [title, setTitle] = useState('');
  const [description, setDescription] = useState('');
  const [contents, setContents] = useState<string[]>([]);
  const [showAddInput, setShowAddInput] = useState(false);
  const [newContentTitle, setNewContentTitle] = useState('');
  const [thumbnailFile, setThumbnailFile] = useState<File | null>(null);
  const [thumbnailPreview, setThumbnailPreview] = useState<string | null>(null);
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [errorMessage, setErrorMessage] = useState('');
  const fileInputRef = useRef<HTMLInputElement>(null);

  useEffect(() => {
    instructorApi.getMe()
      .then((res) => setInstructorId(res.id))
      .catch(() => setErrorMessage('강사 정보를 불러올 수 없습니다. 강사 계정으로 로그인해 주세요.'));
  }, []);

  const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0];
    if (!file) return;
    setThumbnailFile(file);
    setThumbnailPreview(URL.createObjectURL(file));
  };

  const handleAddContent = () => {
    const trimmedTitle = newContentTitle.trim();
    if (!trimmedTitle) return;

    setContents((prev) => [...prev, trimmedTitle]);
    setNewContentTitle('');
    setShowAddInput(false);
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!instructorId) {
      setErrorMessage('강사 정보를 불러오는 중입니다. 잠시 후 다시 시도해 주세요.');
      return;
    }

    setIsSubmitting(true);
    setErrorMessage('');

    try {
      let thumbnailUrl: string | undefined;
      if (thumbnailFile) {
        const result = await imageApi.upload(thumbnailFile);
        thumbnailUrl = result.imageUrl; // 상대경로 그대로 저장 (/images/uuid.ext)
      }

      const { courseId } = await courseApi.create({ instructorId, title, description, thumbnailUrl, contents });
      router.push(`/courses/${courseId}`);
    } catch {
      setErrorMessage('강좌 개설에 실패했습니다. 다시 시도해 주세요.');
      setIsSubmitting(false);
    }
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

          {errorMessage && (
            <div className="mb-lg px-md py-sm bg-error-container text-error rounded-lg font-body-md text-body-md">
              {errorMessage}
            </div>
          )}

          <form onSubmit={handleSubmit}>
            <div className="grid grid-cols-1 lg:grid-cols-12 gap-xl">

              {/* 왼쪽 — 기본 정보 */}
              <section className="lg:col-span-7 space-y-lg">
                <div className="bg-surface-container-lowest border border-outline-variant rounded-xl p-lg space-y-md">
                  <h2 className="font-headline-md text-headline-md text-on-surface">강좌 기본 정보</h2>

                  {/* 제목 */}
                  <div className="space-y-xs">
                    <label className="text-label-md font-label-md text-on-surface-variant">
                      강좌 제목 <span className="text-error">*</span>
                    </label>
                    <input
                      required
                      value={title}
                      onChange={(e) => setTitle(e.target.value)}
                      className="w-full px-md py-sm border border-outline-variant rounded-lg focus:ring-2 focus:ring-primary-container focus:border-primary outline-none transition-all font-body-md text-body-md"
                      placeholder="예: 실전 React + TypeScript 완성 강좌"
                    />
                  </div>

                  {/* 상세 설명 */}
                  <div className="space-y-xs">
                    <label className="text-label-md font-label-md text-on-surface-variant">
                      강좌 상세 설명 <span className="text-error">*</span>
                    </label>
                    <textarea
                      required
                      value={description}
                      onChange={(e) => setDescription(e.target.value)}
                      rows={8}
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
                    {contents.map((contentTitle, idx) => (
                      <div key={`${contentTitle}-${idx}`} className="flex items-center gap-md p-md bg-surface-container-low border border-outline-variant rounded-lg group hover:border-primary-container transition-all">
                        <span className="material-symbols-outlined text-outline">drag_indicator</span>
                        <p className="flex-grow text-label-md font-label-md text-on-surface">{idx + 1}. {contentTitle}</p>
                        <button
                          type="button"
                          onClick={() => setContents((prev) => prev.filter((_, i) => i !== idx))}
                          className="material-symbols-outlined text-outline opacity-0 group-hover:opacity-100 transition-opacity"
                        >
                          delete
                        </button>
                      </div>
                    ))}
                    {showAddInput && (
                      <div className="flex items-center gap-sm p-md bg-primary-fixed/10 border border-primary/30 rounded-lg">
                        <input
                          autoFocus
                          type="text"
                          value={newContentTitle}
                          onChange={(e) => setNewContentTitle(e.target.value)}
                          onKeyDown={(e) => {
                            if (e.key === 'Enter') {
                              e.preventDefault();
                              handleAddContent();
                            }
                            if (e.key === 'Escape') {
                              setShowAddInput(false);
                              setNewContentTitle('');
                            }
                          }}
                          placeholder="콘텐츠 제목을 입력하세요"
                          className="flex-1 px-sm py-xs border border-outline-variant rounded-lg text-label-md font-label-md focus:border-primary focus:outline-none"
                        />
                        <button type="button" onClick={handleAddContent} className="px-md py-xs bg-primary text-on-primary rounded-lg text-label-sm font-label-sm hover:opacity-90">입력</button>
                        <button type="button" onClick={() => { setShowAddInput(false); setNewContentTitle(''); }} className="px-md py-xs border border-outline-variant rounded-lg text-label-sm font-label-sm hover:bg-surface-container">취소</button>
                      </div>
                    )}
                  </div>
                </div>
              </section>

              {/* 오른쪽 — 썸네일 + 제출 */}
              <aside className="lg:col-span-5 space-y-lg">

                {/* 썸네일 */}
                <div className="bg-surface-container-lowest border border-outline-variant rounded-xl p-lg space-y-md">
                  <h2 className="font-headline-md text-headline-md text-on-surface">썸네일</h2>

                  <input
                    ref={fileInputRef}
                    type="file"
                    accept="image/jpeg,image/png,image/gif,image/webp"
                    className="hidden"
                    onChange={handleFileChange}
                  />

                  <div
                    onClick={() => fileInputRef.current?.click()}
                    className="aspect-video w-full rounded-xl border-2 border-dashed border-outline-variant bg-surface-container flex flex-col items-center justify-center cursor-pointer hover:border-primary transition-all group overflow-hidden relative"
                  >
                    {thumbnailPreview ? (
                      // eslint-disable-next-line @next/next/no-img-element
                      <img
                        src={thumbnailPreview}
                        alt="썸네일 미리보기"
                        className="w-full h-full object-cover"
                      />
                    ) : (
                      <>
                        <span className="material-symbols-outlined text-[40px] text-outline-variant group-hover:text-primary transition-colors mb-sm">cloud_upload</span>
                        <span className="text-label-md font-label-md text-on-surface-variant">클릭하여 이미지 업로드</span>
                        <span className="text-label-sm font-label-sm text-outline mt-xs">권장: 1280 × 720 (16:9) · JPG, PNG, GIF, WebP</span>
                      </>
                    )}
                  </div>

                  {thumbnailFile && (
                    <div className="flex items-center justify-between text-label-sm font-label-sm text-on-surface-variant">
                      <span className="truncate">{thumbnailFile.name}</span>
                      <button
                        type="button"
                        onClick={() => { setThumbnailFile(null); setThumbnailPreview(null); }}
                        className="ml-sm text-error hover:opacity-70 shrink-0"
                      >
                        삭제
                      </button>
                    </div>
                  )}
                </div>

                {/* 저장 버튼 */}
                <button
                  type="submit"
                  disabled={isSubmitting || !instructorId}
                  className="w-full py-md rounded-xl bg-primary text-on-primary font-label-md text-label-md hover:brightness-110 transition-all disabled:opacity-60 flex items-center justify-center gap-xs"
                >
                  {isSubmitting ? (
                    <>
                      <span className="material-symbols-outlined text-[18px]">progress_activity</span>
                      개설 중...
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
