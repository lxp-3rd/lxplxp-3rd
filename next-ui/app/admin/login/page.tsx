'use client';

import { useState } from 'react';
import { useRouter } from 'next/navigation';

export default function AdminLoginPage() {
  const router = useRouter();
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [showPassword, setShowPassword] = useState(false);
  const [error, setError] = useState('');

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    if (!email || !password) {
      setError('이메일과 비밀번호를 입력해주세요.');
      return;
    }
    router.push('/admin');
  };

  return (
    <div className="fixed inset-0 z-50 bg-surface flex items-center justify-center px-gutter">

      <div className="w-full max-w-[420px]">

        {/* 로고 */}
        <div className="flex flex-col items-center mb-xxl">
          <div className="w-12 h-12 rounded-xl bg-primary flex items-center justify-center mb-md shadow-md">
            <span className="material-symbols-outlined text-on-primary text-[28px]" style={{ fontVariationSettings: "'FILL' 1" }}>
              admin_panel_settings
            </span>
          </div>
          <h1 className="font-headline-md text-headline-md text-on-surface">관리자 로그인</h1>
          <p className="font-body-sm text-body-sm text-on-surface-variant mt-xs">LXP 관리자 계정으로 로그인하세요.</p>
        </div>

        {/* 폼 카드 */}
        <div className="bg-surface-container-lowest border border-outline-variant rounded-2xl p-xl shadow-sm">
          <form onSubmit={handleSubmit} className="flex flex-col gap-lg">

            {/* 이메일 */}
            <div className="flex flex-col gap-xs">
              <label className="font-label-md text-label-md text-on-surface-variant">이메일</label>
              <input
                type="email"
                value={email}
                onChange={(e) => { setEmail(e.target.value); setError(''); }}
                placeholder="admin@lxp.com"
                className="w-full px-md py-sm bg-surface-container border border-outline-variant rounded-lg font-body-md text-body-md text-on-surface placeholder:text-on-surface-variant/40 focus:border-primary focus:outline-none transition-colors"
              />
            </div>

            {/* 비밀번호 */}
            <div className="flex flex-col gap-xs">
              <label className="font-label-md text-label-md text-on-surface-variant">비밀번호</label>
              <div className="relative">
                <input
                  type={showPassword ? 'text' : 'password'}
                  value={password}
                  onChange={(e) => { setPassword(e.target.value); setError(''); }}
                  placeholder="비밀번호를 입력하세요"
                  className="w-full px-md py-sm pr-10 bg-surface-container border border-outline-variant rounded-lg font-body-md text-body-md text-on-surface placeholder:text-on-surface-variant/40 focus:border-primary focus:outline-none transition-colors"
                />
                <button
                  type="button"
                  onClick={() => setShowPassword((v) => !v)}
                  className="absolute right-3 top-1/2 -translate-y-1/2 text-on-surface-variant hover:text-on-surface transition-colors"
                >
                  <span className="material-symbols-outlined text-[20px]">
                    {showPassword ? 'visibility_off' : 'visibility'}
                  </span>
                </button>
              </div>
            </div>

            {/* 에러 메시지 */}
            {error && (
              <p className="font-label-sm text-label-sm text-error flex items-center gap-xs">
                <span className="material-symbols-outlined text-[16px]">error</span>
                {error}
              </p>
            )}

            {/* 로그인 버튼 */}
            <button
              type="submit"
              className="w-full py-sm bg-primary text-on-primary font-label-md text-label-md rounded-lg shadow-sm hover:brightness-110 transition-all active:scale-95 mt-xs"
            >
              로그인
            </button>

          </form>
        </div>

        <p className="text-center font-label-sm text-label-sm text-on-surface-variant mt-lg">
          관리자 계정이 없으신가요?{' '}
          <a href="mailto:support@lxp.com" className="text-primary hover:underline">
            문의하기
          </a>
        </p>

      </div>
    </div>
  );
}
