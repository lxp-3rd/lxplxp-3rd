'use client';

import { useState } from 'react';
import { useParams, useRouter } from 'next/navigation';
import { getAnnouncementById } from '@/app/announcements/mockData';

export default function AdminAnnouncementEditPage() {
  const params = useParams();
  const router = useRouter();
  const id = Array.isArray(params.id) ? params.id[0] : (params.id as string);
  const announcement = getAnnouncementById(id);

  const [title, setTitle] = useState(announcement?.title ?? '');
  const [content, setContent] = useState(announcement?.content ?? '');
  const [author, setAuthor] = useState(announcement?.author ?? '');
  const [isPublic, setIsPublic] = useState(true);

  if (!announcement) {
    return (
      <div className="max-w-[1280px] mx-auto px-gutter py-xl flex items-center justify-center">
        <div className="text-center">
          <h1 className="text-headline-lg font-headline-lg text-on-surface mb-md">공지를 찾을 수 없습니다</h1>
          <a href="/admin/announcements" className="text-primary hover:underline text-label-md font-label-md">목록으로 돌아가기</a>
        </div>
      </div>
    );
  }

  return (
    <div className="flex-grow pt-xl pb-xxl px-gutter max-w-[1280px] mx-auto w-full">

      <div className="flex flex-col md:flex-row md:items-end justify-between mb-xl gap-md">
        <div>
          <a
            className="flex items-center gap-xs text-primary font-label-md mb-xs hover:gap-sm transition-all"
            href="/admin/announcements"
          >
            <span className="material-symbols-outlined text-sm">arrow_back</span>
            공지사항 목록으로 돌아가기
          </a>
          <h1 className="font-headline-lg text-headline-lg text-on-surface">공지사항 수정</h1>
          <p className="font-body-md text-on-surface-variant mt-sm">공지사항 내용을 수정합니다.</p>
        </div>
        <div className="flex items-center gap-md">
          <button
            type="button"
            onClick={() => router.back()}
            className="px-lg py-sm font-label-md text-on-surface-variant border border-outline-variant rounded-lg hover:bg-surface-container-low transition-colors"
          >
            취소
          </button>
          <button
            type="button"
            className="px-lg py-sm font-label-md bg-primary text-on-primary rounded-lg shadow-sm hover:brightness-110 transition-all active:scale-95"
          >
            저장하기
          </button>
        </div>
      </div>

      <div className="bento-grid">

        <div className="col-span-12 lg:col-span-8 flex flex-col gap-lg">

          <div className="bg-surface-container-lowest p-lg rounded-xl border border-outline-variant shadow-sm">
            <label className="block font-label-md text-on-surface-variant mb-sm">제목 입력</label>
            <input
              className="w-full text-headline-md font-headline-md bg-transparent border-none p-0 placeholder:text-on-surface-variant/40 focus:ring-0 focus:outline-none"
              placeholder="공지사항 제목을 입력하세요"
              type="text"
              value={title}
              onChange={(e) => setTitle(e.target.value)}
            />
          </div>

          <div className="bg-surface-container-lowest p-lg rounded-xl border border-outline-variant shadow-sm flex flex-col gap-md">
            <div className="flex items-center justify-between border-b border-outline-variant pb-md">
              <label className="font-label-md text-on-surface-variant">본문 입력</label>
              <div className="flex items-center gap-sm text-on-surface-variant">
                <button type="button" className="material-symbols-outlined hover:text-primary">format_bold</button>
                <button type="button" className="material-symbols-outlined hover:text-primary">format_italic</button>
                <button type="button" className="material-symbols-outlined hover:text-primary">format_list_bulleted</button>
                <button type="button" className="material-symbols-outlined hover:text-primary">link</button>
                <button type="button" className="material-symbols-outlined hover:text-primary">image</button>
              </div>
            </div>
            <textarea
              className="w-full min-h-[400px] bg-transparent border-none p-0 resize-none font-body-lg text-body-lg placeholder:text-on-surface-variant/40 focus:ring-0 focus:outline-none"
              placeholder="학습자들에게 전달할 내용을 상세히 작성해 주세요..."
              value={content}
              onChange={(e) => setContent(e.target.value)}
            />
          </div>
        </div>

        <div className="col-span-12 lg:col-span-4 flex flex-col gap-lg">

          <div className="bg-surface-container-lowest p-lg rounded-xl border border-outline-variant shadow-sm">
            <h3 className="font-label-md text-on-surface-variant uppercase tracking-wider mb-lg">설정 및 공개</h3>
            <div className="flex flex-col gap-xl">
              <div className="flex items-center justify-between">
                <div className="flex flex-col">
                  <span className="font-label-md text-on-surface">공개 여부 선택</span>
                  <span className="text-label-sm text-on-surface-variant">활성화 시 즉시 게시됩니다.</span>
                </div>
                <label className="toggle-switch">
                  <input
                    type="checkbox"
                    checked={isPublic}
                    onChange={(e) => setIsPublic(e.target.checked)}
                  />
                  <span className="slider" />
                </label>
              </div>
            </div>
          </div>

          <div className="bg-surface-container-lowest p-lg rounded-xl border border-outline-variant shadow-sm">
            <h3 className="font-label-md text-on-surface-variant uppercase tracking-wider mb-lg">작성 정보</h3>
            <div className="flex items-center gap-md mb-lg">
              <div className="w-10 h-10 rounded-full bg-primary/20 text-primary flex items-center justify-center font-bold text-label-md flex-shrink-0">
                {author[0] ?? 'A'}
              </div>
              <div className="flex flex-col min-w-0">
                <label className="font-label-sm text-on-surface-variant">작성자</label>
                <input
                  className="font-label-md text-on-surface bg-transparent border-none p-0 focus:ring-0 focus:outline-none"
                  type="text"
                  value={author}
                  onChange={(e) => setAuthor(e.target.value)}
                />
              </div>
            </div>
            <div className="flex flex-col gap-sm text-label-sm font-label-sm text-on-surface-variant">
              <div className="flex justify-between">
                <span>작성일</span>
                <span>{announcement.createdAt}</span>
              </div>
              <div className="flex justify-between">
                <span>조회수</span>
                <span>{announcement.views.toLocaleString()}회</span>
              </div>
            </div>
          </div>

        </div>
      </div>

    </div>
  );
}
