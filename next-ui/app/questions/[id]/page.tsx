'use client';

import Link from 'next/link';
import { TopNavBar } from '@/components/TopNavBar';
import { Footer } from '@/components/Footer';
import { MOCK_QUESTIONS } from '@/app/questions/mockData';

const MOCK_ANSWERS = [
  { id: 'a1', questionId: 'q1', author: '이하연 강사', role: '강사', content: '정량 조사는 설문, A/B 테스트처럼 수치로 측정 가능한 방법이고, 정성 조사는 인터뷰나 사용성 테스트처럼 이유와 맥락을 파악하는 방법입니다. 실무에서는 정성으로 문제를 발견하고, 정량으로 규모를 확인하는 순서로 함께 사용하는 경우가 많습니다.', createdAt: '2026-06-21', isAccepted: true },
  { id: 'a2', questionId: 'q1', author: '김지혜', role: '수강생', content: '저도 같은 고민을 했는데, 강의 3주차 자료에 실무 예시가 잘 정리되어 있어서 참고해 보시면 좋을 것 같아요!', createdAt: '2026-06-22', isAccepted: false },
];

export default function QuestionDetailPage() {
  const question = MOCK_QUESTIONS[0];

  if (!question) return (
    <>
      <TopNavBar />
      <main className="pt-16 min-h-screen flex items-center justify-center">
        <p className="text-on-surface-variant">질문을 찾을 수 없습니다.</p>
      </main>
      <Footer />
    </>
  );

  return (
    <>
      <TopNavBar />
      <main className="pt-16 min-h-screen bg-background">
        <div className="max-w-[1000px] mx-auto px-gutter pt-xl pb-xxl">

          {/* 뒤로가기 */}
          <div className="mb-lg">
            <Link href="/questions" className="flex items-center gap-xs text-on-surface-variant hover:text-primary transition-colors">
              <span className="material-symbols-outlined text-[20px]">arrow_back</span>
              <span className="text-label-md font-label-md">질문 목록으로</span>
            </Link>
          </div>

          {/* 질문 카드 */}
          <div className="bg-surface-container-lowest border border-outline-variant rounded-xl p-xl mb-xl">
            <div className="flex items-center gap-sm mb-md flex-wrap">
              {question.tags.map((tag) => (
                <span key={tag} className="bg-primary-fixed text-primary px-sm py-1 rounded-full text-label-sm font-label-sm">{tag}</span>
              ))}
              <span className={`px-sm py-1 rounded-full text-label-sm font-label-sm ml-auto ${question.isAnswered ? 'bg-secondary-container text-on-secondary-container' : 'bg-error-container text-on-error-container'}`}>
                {question.isAnswered ? '답변 완료' : '답변 대기'}
              </span>
            </div>
            <h1 className="text-headline-md font-headline-md text-on-surface mb-lg">{question.title}</h1>
            <p className="text-body-lg font-body-md text-on-surface-variant leading-relaxed mb-xl">{question.content}</p>
            <div className="flex items-center gap-md text-label-md font-label-md text-on-surface-variant border-t border-outline-variant pt-md">
              <span className="flex items-center gap-xs">
                <span className="material-symbols-outlined text-[16px]">person</span>
                {question.authorName}
              </span>
              <span>·</span>
              <span className="flex items-center gap-xs">
                <span className="material-symbols-outlined text-[16px]">schedule</span>
                {question.createdAt}
              </span>
              <span>·</span>
              <span className="flex items-center gap-xs">
                <span className="material-symbols-outlined text-[16px]">book</span>
                {question.courseTitle}
              </span>
            </div>
          </div>

          {/* 답변 목록 */}
          <div className="mb-xl">
            <h2 className="text-headline-sm font-headline-md text-on-surface mb-md flex items-center gap-sm">
              <span className="material-symbols-outlined text-primary">forum</span>
              답변 {MOCK_ANSWERS.length}개
            </h2>
            <div className="space-y-md">
              {MOCK_ANSWERS.map((answer) => (
                <div key={answer.id} className={`bg-surface-container-lowest border rounded-xl p-lg ${answer.isAccepted ? 'border-primary bg-primary-fixed/10' : 'border-outline-variant'}`}>
                  {answer.isAccepted && (
                    <div className="flex items-center gap-xs mb-sm text-primary">
                      <span className="material-symbols-outlined text-[18px]" style={{ fontVariationSettings: "'FILL' 1" }}>check_circle</span>
                      <span className="text-label-sm font-label-sm">채택된 답변</span>
                    </div>
                  )}
                  <div className="flex items-center gap-sm mb-md">
                    <div className="w-8 h-8 rounded-full bg-primary text-on-primary flex items-center justify-center text-label-sm font-bold">
                      {answer.author[0]}
                    </div>
                    <div>
                      <p className="text-label-md font-label-md text-on-surface">{answer.author}</p>
                      <p className="text-label-sm font-label-sm text-on-surface-variant">{answer.role} · {answer.createdAt}</p>
                    </div>
                  </div>
                  <p className="text-body-md font-body-md text-on-surface-variant leading-relaxed">{answer.content}</p>
                </div>
              ))}
            </div>
          </div>

          {/* 답변 작성 */}
          <div className="bg-surface-container-lowest border border-outline-variant rounded-xl p-lg">
            <h3 className="text-label-md font-label-md text-on-surface mb-md flex items-center gap-xs">
              <span className="material-symbols-outlined text-[18px] text-primary">edit</span>
              답변 작성
            </h3>
            <textarea
              className="w-full bg-surface-container-low border border-outline-variant rounded-lg p-md text-body-md font-body-md text-on-surface resize-none focus:border-primary focus:outline-none transition-all"
              rows={4}
              placeholder="도움이 될 답변을 작성해 주세요..."
            />
            <div className="flex justify-end mt-md">
              <button type="button" className="bg-primary text-on-primary px-xl py-sm rounded-lg text-label-md font-label-md hover:opacity-90 transition-opacity active:scale-[0.98]">
                답변 등록
              </button>
            </div>
          </div>

        </div>
      </main>
      <Footer />
    </>
  );
}
