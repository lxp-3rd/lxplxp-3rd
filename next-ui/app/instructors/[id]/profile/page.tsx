'use client';

import { useState } from 'react';
import Link from 'next/link';
import { TopNavBar } from '@/components/TopNavBar';
import { Footer } from '@/components/Footer';
import { MOCK_INSTRUCTORS } from '@/app/instructors/mockData';

export default function InstructorProfileEditPage() {
  const instructor = MOCK_INSTRUCTORS[0];

  const [form, setForm] = useState({
    name: instructor.name,
    email: instructor.email,
    phone: '010-0000-0000',
    bio: instructor.bio,
    expertise: instructor.expertise.join(', '),
    portfolioUrl: 'https://github.com',
    youtubeUrl: '',
  });
  const [saved, setSaved] = useState(false);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
    setForm({ ...form, [e.target.name]: e.target.value });
    setSaved(false);
  };

  const handleSave = () => {
    setSaved(true);
    setTimeout(() => setSaved(false), 3000);
  };

  return (
    <>
      <TopNavBar />
      <main className="pt-16 min-h-screen bg-background">
        <div className="max-w-[760px] mx-auto px-gutter pt-xl pb-xxl">

          {/* 뒤로가기 */}
          <div className="mb-lg">
            <Link href={`/instructors/${instructor.id}`} className="flex items-center gap-xs text-on-surface-variant hover:text-primary transition-colors">
              <span className="material-symbols-outlined text-[20px]">arrow_back</span>
              <span className="text-label-md font-label-md">강사 프로필로</span>
            </Link>
          </div>

          <h1 className="text-headline-md font-headline-md text-on-surface mb-xl">프로필 수정</h1>

          {/* 아바타 */}
          <div className="bg-surface-container-lowest border border-outline-variant rounded-xl p-xl mb-lg flex items-center gap-lg">
            <div className="w-20 h-20 rounded-full bg-primary text-on-primary flex items-center justify-center text-headline-md font-bold flex-shrink-0">
              {instructor.name[0]}
            </div>
            <div>
              <p className="text-label-md font-label-md text-on-surface mb-sm">프로필 사진</p>
              <button type="button" className="bg-surface-container-high text-on-surface px-lg py-sm rounded-lg text-label-sm font-label-sm hover:bg-surface-container transition-colors">
                사진 변경
              </button>
            </div>
          </div>

          {/* 기본 정보 */}
          <div className="bg-surface-container-lowest border border-outline-variant rounded-xl p-xl mb-lg">
            <h2 className="text-label-md font-label-md text-on-surface-variant uppercase tracking-wider mb-lg">기본 정보</h2>
            <div className="space-y-md">
              <div>
                <label htmlFor="name" className="text-label-sm font-label-sm text-on-surface-variant block mb-xs">이름</label>
                <input
                  id="name"
                  name="name"
                  type="text"
                  value={form.name}
                  onChange={handleChange}
                  className="w-full bg-surface-container-low border border-outline-variant rounded-lg px-md py-sm text-body-md font-body-md text-on-surface focus:border-primary focus:outline-none transition-all"
                />
              </div>
              <div>
                <label htmlFor="email" className="text-label-sm font-label-sm text-on-surface-variant block mb-xs">이메일</label>
                <input
                  id="email"
                  name="email"
                  type="email"
                  value={form.email}
                  onChange={handleChange}
                  className="w-full bg-surface-container-low border border-outline-variant rounded-lg px-md py-sm text-body-md font-body-md text-on-surface focus:border-primary focus:outline-none transition-all"
                />
              </div>
              <div>
                <label htmlFor="phone" className="text-label-sm font-label-sm text-on-surface-variant block mb-xs">연락처</label>
                <input
                  id="phone"
                  name="phone"
                  type="tel"
                  value={form.phone}
                  onChange={handleChange}
                  className="w-full bg-surface-container-low border border-outline-variant rounded-lg px-md py-sm text-body-md font-body-md text-on-surface focus:border-primary focus:outline-none transition-all"
                />
              </div>
            </div>
          </div>

          {/* 강사 소개 */}
          <div className="bg-surface-container-lowest border border-outline-variant rounded-xl p-xl mb-lg">
            <h2 className="text-label-md font-label-md text-on-surface-variant uppercase tracking-wider mb-lg">강사 소개</h2>
            <div className="space-y-md">
              <div>
                <label htmlFor="bio" className="text-label-sm font-label-sm text-on-surface-variant block mb-xs">한줄 소개</label>
                <textarea
                  id="bio"
                  name="bio"
                  rows={3}
                  value={form.bio}
                  onChange={handleChange}
                  className="w-full bg-surface-container-low border border-outline-variant rounded-lg px-md py-sm text-body-md font-body-md text-on-surface resize-none focus:border-primary focus:outline-none transition-all"
                />
              </div>
              <div>
                <label htmlFor="expertise" className="text-label-sm font-label-sm text-on-surface-variant block mb-xs">전문 분야 (쉼표로 구분)</label>
                <input
                  id="expertise"
                  name="expertise"
                  type="text"
                  value={form.expertise}
                  onChange={handleChange}
                  placeholder="예: React, TypeScript, Node.js"
                  className="w-full bg-surface-container-low border border-outline-variant rounded-lg px-md py-sm text-body-md font-body-md text-on-surface focus:border-primary focus:outline-none transition-all"
                />
              </div>
            </div>
          </div>

          {/* 링크 */}
          <div className="bg-surface-container-lowest border border-outline-variant rounded-xl p-xl mb-xl">
            <h2 className="text-label-md font-label-md text-on-surface-variant uppercase tracking-wider mb-lg">외부 링크</h2>
            <div className="space-y-md">
              <div>
                <label htmlFor="portfolioUrl" className="text-label-sm font-label-sm text-on-surface-variant block mb-xs">포트폴리오 / GitHub</label>
                <input
                  id="portfolioUrl"
                  name="portfolioUrl"
                  type="url"
                  value={form.portfolioUrl}
                  onChange={handleChange}
                  placeholder="https://"
                  className="w-full bg-surface-container-low border border-outline-variant rounded-lg px-md py-sm text-body-md font-body-md text-on-surface focus:border-primary focus:outline-none transition-all"
                />
              </div>
              <div>
                <label htmlFor="youtubeUrl" className="text-label-sm font-label-sm text-on-surface-variant block mb-xs">YouTube 채널 (선택)</label>
                <input
                  id="youtubeUrl"
                  name="youtubeUrl"
                  type="url"
                  value={form.youtubeUrl}
                  onChange={handleChange}
                  placeholder="https://youtube.com/@"
                  className="w-full bg-surface-container-low border border-outline-variant rounded-lg px-md py-sm text-body-md font-body-md text-on-surface focus:border-primary focus:outline-none transition-all"
                />
              </div>
            </div>
          </div>

          {/* 저장 버튼 */}
          <div className="flex items-center justify-end gap-md">
            {saved && (
              <span className="flex items-center gap-xs text-secondary text-label-md font-label-md">
                <span className="material-symbols-outlined text-[18px]" style={{ fontVariationSettings: "'FILL' 1" }}>check_circle</span>
                저장되었습니다
              </span>
            )}
            <Link
              href={`/instructors/${instructor.id}`}
              className="border border-outline-variant text-on-surface px-xl py-sm rounded-lg text-label-md font-label-md hover:bg-surface-container transition-colors"
            >
              취소
            </Link>
            <button
              type="button"
              onClick={handleSave}
              className="bg-primary text-on-primary px-xl py-sm rounded-lg text-label-md font-label-md hover:opacity-90 transition-opacity active:scale-[0.98]"
            >
              저장
            </button>
          </div>

        </div>
      </main>
      <Footer />
    </>
  );
}
