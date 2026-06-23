'use client';

import Link from 'next/link';
import { useLoginForm } from './useLoginForm';

export default function LoginPage() {
  const {
    email,
    setEmail,
    password,
    setPassword,
    showPassword,
    setShowPassword,
    isSubmitting,
    errorMessage,
    handleSubmit,
  } = useLoginForm();

  return (
    <>
      <div className="fixed inset-0 pointer-events-none overflow-hidden z-0">
        <div className="absolute -top-24 -left-24 w-96 h-96 rounded-full bg-primary-container/10 blur-3xl" />
        <div className="absolute -bottom-24 -right-24 w-96 h-96 rounded-full bg-secondary-container/20 blur-3xl" />
      </div>

      <main className="flex-grow flex items-center justify-center px-gutter relative z-10 py-xl">
        <div className="w-full max-w-[440px]">
          <div className="text-center mb-xl">
            <h1 className="text-headline-lg font-headline-lg text-primary tracking-tight">LXP</h1>
            <p className="text-body-md font-body-md text-on-surface-variant mt-xs">
              미래를 위한 학습 플랫폼
            </p>
          </div>

          <div className="bg-surface-container-lowest border border-outline-variant rounded-xl p-xl shadow-sm">
            <h2 className="text-headline-md font-headline-md text-on-surface mb-lg">로그인</h2>
            <form className="space-y-md" onSubmit={handleSubmit}>
              <div className="space-y-xs">
                <label className="text-label-md font-label-md text-on-surface-variant" htmlFor="email">
                  이메일 주소
                </label>
                <div className="relative flex items-center border border-outline-variant rounded-lg bg-surface px-sm h-12">
                  <span className="material-symbols-outlined text-on-surface-variant mr-xs">mail</span>
                  <input
                    className="w-full bg-transparent border-none focus:ring-0 p-0 text-body-md font-body-md text-on-surface placeholder:text-outline"
                    id="email"
                    name="email"
                    placeholder="example@lxp.com"
                    type="email"
                    value={email}
                    onChange={(event) => setEmail(event.target.value)}
                    required
                  />
                </div>
              </div>

              <div className="space-y-xs">
                <label className="text-label-md font-label-md text-on-surface-variant" htmlFor="password">
                  비밀번호
                </label>
                <div className="relative flex items-center border border-outline-variant rounded-lg bg-surface px-sm h-12">
                  <span className="material-symbols-outlined text-on-surface-variant mr-xs">lock</span>
                  <input
                    className="w-full bg-transparent border-none focus:ring-0 p-0 text-body-md font-body-md text-on-surface placeholder:text-outline"
                    id="password"
                    name="password"
                    placeholder="비밀번호를 입력하세요"
                    type={showPassword ? 'text' : 'password'}
                    value={password}
                    onChange={(event) => setPassword(event.target.value)}
                    required
                  />
                  <button
                    className="text-on-surface-variant hover:text-on-surface transition-colors"
                    type="button"
                    onClick={() => setShowPassword((value) => !value)}
                    aria-label="비밀번호 표시 전환"
                  >
                    <span className="material-symbols-outlined">
                      {showPassword ? 'visibility_off' : 'visibility'}
                    </span>
                  </button>
                </div>
              </div>

              {errorMessage && (
                <p role="alert" className="text-label-sm font-label-sm text-error">
                  {errorMessage}
                </p>
              )}

              <button
                type="submit"
                disabled={isSubmitting}
                className="w-full bg-primary hover:opacity-90 disabled:opacity-60 text-on-primary font-label-md text-label-md py-md rounded-lg shadow-sm transition-all active:scale-[0.98] mt-lg"
              >
                {isSubmitting ? '로그인 중...' : '로그인하기'}
              </button>
            </form>

            <div className="mt-lg pt-lg border-t border-outline-variant text-center">
              <p className="text-body-md font-body-md text-on-surface-variant">
                아직 계정이 없으신가요?{' '}
                <Link className="text-primary font-bold hover:underline" href="/register">
                  지금 가입하기
                </Link>
              </p>
            </div>
          </div>
        </div>
      </main>
    </>
  );
}
