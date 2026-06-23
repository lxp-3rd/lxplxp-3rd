'use client';

import { useState, useEffect } from 'react';
import Link from 'next/link';
import { useRouter } from 'next/navigation';
import { TopNavBar } from '@/components/TopNavBar';
import { Footer } from '@/components/Footer';
import { questionApi } from '../api';
import type { QuestionResponse } from '../types';
import { formatDate } from '@/lib/formatDate';

export default function CourseQuestionDetailPage({ params }: { params: { id: string; qid: string } }) {
  const router = useRouter();
  const [question, setQuestion] = useState<QuestionResponse | null>(null);
  const [loading, setLoading] = useState(true);
  const [isEditing, setIsEditing] = useState(false);
  const [editTitle, setEditTitle] = useState('');
  const [editContent, setEditContent] = useState('');
  const [saving, setSaving] = useState(false);
  const [deleting, setDeleting] = useState(false);
  const [error, setError] = useState<string | null>(null);

  // TODO: 인증 구현 후 JWT에서 memberId 추출로 교체
  const memberId = 1;

  useEffect(() => {
    questionApi.getById(Number(params.qid))
      .then((data) => {
        setQuestion(data);
        setEditTitle(data.title);
        setEditContent(data.content);
      })
      .catch(() => setError('질문을 불러올 수 없습니다.'))
      .finally(() => setLoading(false));
  }, [params.qid]);

  const handleUpdate = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!question) return;
    setSaving(true);
    setError(null);
    try {
      const updated = await questionApi.update(question.id, { memberId, title: editTitle, content: editContent });
      setQuestion(updated);
      setEditTitle(updated.title);
      setEditContent(updated.content);
      setIsEditing(false);
    } catch {
      setError('수정 중 오류가 발생했습니다.');
    } finally {
      setSaving(false);
    }
  };

  const handleDelete = async () => {
    if (!question || deleting || !confirm('질문을 삭제하시겠습니까?')) return;
    setDeleting(true);
    try {
      await questionApi.remove(question.id, memberId);
      router.push(`/courses/${params.id}/questions`);
    } catch {
      setError('삭제 중 오류가 발생했습니다.');
      setDeleting(false);
    }
  };

  const isOwner = question?.memberId === memberId;

  if (loading) {
    return (
      <>
        <TopNavBar />
        <main className="pt-16 min-h-screen flex items-center justify-center">
          <p className="text-on-surface-variant text-body-md font-body-md">불러오는 중...</p>
        </main>
        <Footer />
      </>
    );
  }

  if (error && !question) {
    return (
      <>
        <TopNavBar />
        <main className="pt-16 min-h-screen flex items-center justify-center">
          <p className="text-error text-body-md font-body-md">{error}</p>
        </main>
        <Footer />
      </>
    );
  }

  return (
    <>
      <TopNavBar />
      <main className="pt-16 min-h-screen bg-background">
        <div className="max-w-[900px] mx-auto px-gutter pt-xl pb-xxl">

          {/* 뒤로가기 */}
          <div className="mb-lg">
            <Link href={`/courses/${params.id}/questions`} className="flex items-center gap-xs text-on-surface-variant hover:text-primary transition-colors">
              <span className="material-symbols-outlined text-[20px]">arrow_back</span>
              <span className="text-label-md font-label-md">Q&A 목록</span>
            </Link>
          </div>

          {/* 질문 */}
          <div className="bg-surface-container-lowest border border-outline-variant rounded-xl p-xl mb-xl">
            {isEditing ? (
              <form onSubmit={handleUpdate} className="space-y-md">
                <input
                  type="text"
                  value={editTitle}
                  onChange={(e) => setEditTitle(e.target.value)}
                  required
                  minLength={2}
                  maxLength={200}
                  className="w-full bg-surface-container-low border border-outline-variant rounded-lg px-md py-sm text-headline-md font-headline-md text-on-surface focus:border-primary focus:outline-none"
                />
                <textarea
                  value={editContent}
                  onChange={(e) => setEditContent(e.target.value)}
                  required
                  maxLength={500}
                  rows={6}
                  className="w-full bg-surface-container-low border border-outline-variant rounded-lg px-md py-sm text-body-md font-body-md text-on-surface resize-none focus:border-primary focus:outline-none"
                />
                {error && <p className="text-error text-label-md font-label-md">{error}</p>}
                <div className="flex gap-sm justify-end">
                  <button
                    type="button"
                    onClick={() => { setIsEditing(false); setEditTitle(question?.title ?? ''); setEditContent(question?.content ?? ''); setError(null); }}
                    className="border border-outline-variant text-on-surface px-lg py-sm rounded-lg text-label-md font-label-md hover:bg-surface-container transition-colors"
                  >
                    취소
                  </button>
                  <button
                    type="submit"
                    disabled={saving}
                    className="bg-primary text-on-primary px-lg py-sm rounded-lg text-label-md font-label-md hover:opacity-90 transition-opacity disabled:opacity-40"
                  >
                    {saving ? '저장 중...' : '저장'}
                  </button>
                </div>
              </form>
            ) : (
              <>
                <div className="flex items-start justify-between mb-md gap-md">
                  <h1 className="text-headline-md font-headline-md text-on-surface">{question?.title}</h1>
                  {isOwner && (
                    <div className="flex gap-sm flex-shrink-0">
                      <button
                        type="button"
                        onClick={() => setIsEditing(true)}
                        className="flex items-center gap-xs text-label-md font-label-md text-on-surface-variant hover:text-primary transition-colors"
                      >
                        <span className="material-symbols-outlined text-[18px]">edit</span>
                        수정
                      </button>
                      <button
                        type="button"
                        onClick={handleDelete}
                        disabled={deleting}
                        className="flex items-center gap-xs text-label-md font-label-md text-on-surface-variant hover:text-error transition-colors disabled:opacity-40"
                      >
                        <span className="material-symbols-outlined text-[18px]">delete</span>
                        {deleting ? '삭제 중...' : '삭제'}
                      </button>
                    </div>
                  )}
                </div>
                <p className="text-body-lg font-body-md text-on-surface leading-relaxed mb-xl whitespace-pre-wrap">
                  {question?.content}
                </p>
                <div className="flex items-center gap-md text-label-md font-label-md text-on-surface-variant border-t border-outline-variant pt-md">
                  <span className="flex items-center gap-xs">
                    <span className="material-symbols-outlined text-[16px]">person</span>
                    회원 #{question?.memberId}
                  </span>
                  <span>·</span>
                  <span>{question && formatDate(question.createdAt)}</span>
                </div>
              </>
            )}
          </div>

          {/* 강사 답변 영역 (추후 구현) */}
          <div className="bg-surface-container-lowest border border-outline-variant rounded-xl p-lg flex items-center gap-md text-on-surface-variant">
            <span className="material-symbols-outlined text-[24px] text-tertiary">pending</span>
            <p className="text-body-md font-body-md">강사 답변 기능이 곧 추가될 예정입니다.</p>
          </div>

        </div>
      </main>
      <Footer />
    </>
  );
}
