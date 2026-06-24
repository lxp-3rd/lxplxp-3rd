'use client';

import { useEffect, useState } from 'react';
import Link from 'next/link';
import { useRouter } from 'next/navigation';
import { contentApi, courseApi } from '@/app/courses/api';
import type { CourseContent, CourseDetail } from '@/app/courses/types';
import { enrollmentApi } from '@/app/enrollments/api';
import { myPageApi } from '@/app/my-page/api';

const TYPE_ICON: Record<string, string> = {
  VIDEO: 'play_circle',
  DOCUMENT: 'description',
  QUIZ: 'quiz',
};

function inferType(contentUrl: string) {
  if (contentUrl.includes('/quiz/')) return 'QUIZ';
  if (contentUrl.endsWith('.pdf') || contentUrl.includes('/docs/')) return 'DOCUMENT';
  return 'VIDEO';
}

export default function ContentViewerPage({ params }: { params: { id: string; contentId: string } }) {
  const router = useRouter();
  const [noteOpen, setNoteOpen] = useState(false);
  const [note, setNote] = useState('');
  const [course, setCourse] = useState<CourseDetail | null>(null);
  const [content, setContent] = useState<CourseContent | null>(null);
  const [contents, setContents] = useState<CourseContent[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [completing, setCompleting] = useState(false);

  useEffect(() => {
    const controller = new AbortController();
    setLoading(true);
    setError(null);

    Promise.all([
      courseApi.getById(params.id),
      contentApi.getById(Number(params.id), Number(params.contentId)),
      contentApi.getByCourse(Number(params.id)),
    ])
      .then(([loadedCourse, loadedContent, loadedContents]) => {
        if (controller.signal.aborted) return;
        setCourse(loadedCourse);
        setContent(loadedContent);
        setContents(loadedContents);
      })
      .catch(() => {
        if (!controller.signal.aborted) setError('콘텐츠를 불러올 수 없습니다.');
      })
      .finally(() => {
        if (!controller.signal.aborted) setLoading(false);
      });

    return () => controller.abort();
  }, [params.id, params.contentId]);

  const handleComplete = async () => {
    if (!course || completing) return;
    setCompleting(true);
    setError(null);
    try {
      const profile = await myPageApi.getProfile();
      const enrollments = await enrollmentApi.getMine(profile.memberId);
      const enrollment = enrollments.find((item) => item.courseId === course.id && item.status !== 'CANCELED');
      const currentIndex = contents.findIndex((item) => item.id === Number(params.contentId));
      const next = currentIndex >= 0 ? contents[currentIndex + 1] : null;
      if (next) {
        router.push(`/courses/${course.id}/contents/${next.id}`);
      } else {
        if (!enrollment) throw new Error('Enrollment not found');
        await enrollmentApi.updateProgress(enrollment.id);
        router.push(`/enrollments/${enrollment.id}`);
      }
    } catch {
      setError('학습 완료 처리 중 오류가 발생했습니다.');
    } finally {
      setCompleting(false);
    }
  };

  if (loading) {
    return (
      <div className="min-h-screen bg-background flex items-center justify-center">
        <p className="text-on-surface-variant text-body-md font-body-md">불러오는 중...</p>
      </div>
    );
  }

  if (error && (!course || !content)) {
    return (
      <div className="min-h-screen bg-background flex items-center justify-center">
        <p className="text-error text-body-md font-body-md">{error}</p>
      </div>
    );
  }

  if (!course || !content) return null;

  const type = inferType(content.contentUrl);
  const isVideo = type === 'VIDEO';
  const isDocument = type === 'DOCUMENT';
  const currentId = Number(params.contentId);

  return (
    <div className="min-h-screen bg-background flex flex-col">
      {/* 상단 바 (간소화) */}
      <header className="fixed top-0 left-0 right-0 z-50 h-16 bg-surface-container-low border-b border-outline-variant flex items-center px-gutter gap-lg">
        <Link href={`/courses/${course.id}`} className="flex items-center gap-xs text-on-surface-variant hover:text-primary transition-colors">
          <span className="material-symbols-outlined text-[20px]">arrow_back</span>
          <span className="text-label-md font-label-md hidden sm:block">강좌로</span>
        </Link>
        <div className="flex-1 min-w-0">
          <p className="text-label-md font-label-md text-on-surface truncate">{course.title}</p>
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
            {contents.map((item) => {
              const itemType = inferType(item.contentUrl);
              const current = item.id === currentId;
              return (
                <Link
                  key={item.id}
                  href={`/courses/${course.id}/contents/${item.id}`}
                  className={`w-full text-left px-lg py-md flex items-center gap-md border-b border-outline-variant/50 transition-colors ${
                    current ? 'bg-primary-container/30' : 'hover:bg-surface-container'
                  }`}
                >
                  <div className={`w-6 h-6 rounded-full flex-shrink-0 flex items-center justify-center ${
                    current ? 'bg-primary/20 border-2 border-primary' : 'bg-surface-container-high'
                  }`}>
                    <span className={`material-symbols-outlined text-[13px] ${current ? 'text-primary' : 'text-on-surface-variant'}`}>{TYPE_ICON[itemType] ?? 'circle'}</span>
                  </div>
                  <div className="flex-1 min-w-0">
                    <p className={`text-body-sm font-body-md line-clamp-2 leading-tight ${current ? 'text-primary font-medium' : 'text-on-surface'}`}>
                      {item.title}
                    </p>
                  </div>
                </Link>
              );
            })}
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
                  <p className="text-label-md font-label-md opacity-80">{content.title}</p>
                  <a href={content.contentUrl} target="_blank" rel="noreferrer" className="text-label-sm font-label-sm opacity-60 mt-xs inline-block underline">
                    콘텐츠 URL 열기
                  </a>
                </div>
              </div>
            )}

            {/* 문서 뷰어 */}
            {isDocument && (
              <div className="bg-surface-container-lowest border border-outline-variant rounded-xl p-xl mb-xl min-h-[400px] flex flex-col items-center justify-center">
                <span className="material-symbols-outlined text-[48px] text-primary mb-md">description</span>
                <p className="text-label-md font-label-md text-on-surface mb-sm">학습 자료</p>
                <p className="text-body-sm font-body-md text-on-surface-variant mb-xl">{content.title}</p>
                <a href={content.contentUrl} target="_blank" rel="noreferrer" className="bg-primary text-on-primary px-xl py-sm rounded-lg text-label-md font-label-md hover:opacity-90 transition-opacity flex items-center gap-xs">
                  <span className="material-symbols-outlined text-[18px]">download</span>
                  자료 다운로드
                </a>
              </div>
            )}

            {type === 'QUIZ' && (
              <div className="bg-surface-container-lowest border border-outline-variant rounded-xl p-xl mb-xl min-h-[300px] flex flex-col items-center justify-center">
                <span className="material-symbols-outlined text-[48px] text-primary mb-md">quiz</span>
                <p className="text-label-md font-label-md text-on-surface mb-sm">{content.title}</p>
                <a href={content.contentUrl} target="_blank" rel="noreferrer" className="text-primary text-label-md font-label-md underline">
                  퀴즈 열기
                </a>
              </div>
            )}

            {/* 콘텐츠 정보 */}
            <div className="mb-xl">
              <h1 className="text-headline-md font-headline-md text-on-surface mb-md">{content.title}</h1>
              <div className="flex items-center gap-md text-label-md font-label-md text-on-surface-variant">
                <span className="flex items-center gap-xs">
                  <span className="material-symbols-outlined text-[16px]">{TYPE_ICON[type]}</span>
                  {type === 'VIDEO' ? '동영상' : type === 'DOCUMENT' ? '학습 자료' : '퀴즈'}
                </span>
                <span>·</span>
                <span>{content.order}강</span>
              </div>
              {error && <p className="text-error text-label-md font-label-md mt-md">{error}</p>}
            </div>

            {/* 이전 / 다음 버튼 */}
            <div className="flex items-center justify-between border-t border-outline-variant pt-lg">
              <Link href={`/enrollments`} className="flex items-center gap-xs text-on-surface-variant hover:text-primary transition-colors">
                <span className="material-symbols-outlined">chevron_left</span>
                <span className="text-label-md font-label-md">내 강의</span>
              </Link>
              <button
                type="button"
                onClick={handleComplete}
                disabled={completing}
                className="bg-primary text-on-primary px-xl py-sm rounded-lg text-label-md font-label-md hover:opacity-90 transition-opacity flex items-center gap-xs disabled:opacity-40"
              >
                {completing ? '처리 중...' : '완료 및 다음 강의'}
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
