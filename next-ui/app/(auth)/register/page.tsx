'use client';

import Link from 'next/link';
import { useRegisterForm } from './useRegisterForm';

export default function RegisterPage() {
  const {
    email,
    setEmail,
    nickname,
    setNickname,
    password,
    setPassword,
    passwordConfirm,
    setPasswordConfirm,
    termsAgreed,
    setTermsAgreed,
    isSubmitting,
    errorMessage,
    handleSubmit,
  } = useRegisterForm();

  return (
    <>
      <main className="flex-grow flex items-center justify-center pt-24 pb-xl px-margin-mobile md:px-gutter">
        <div className="signup-card bg-surface-container-lowest w-full max-w-[480px] p-xl rounded-xl border border-surface-variant">
          <div className="text-center mb-xl">
            <h1 className="text-headline-lg text-on-surface mb-xs">회원가입</h1>
            <p className="text-body-md text-on-surface-variant">
              LXP 학습 플랫폼의 계정을 시작하세요.
            </p>
          </div>

          <form className="space-y-lg" id="signupForm" onSubmit={handleSubmit}>
            <div className="space-y-xs">
              <label className="text-label-md text-on-surface-variant" htmlFor="email">
                이메일 주소
              </label>
              <div className="relative">
                <span className="material-symbols-outlined absolute left-md top-1/2 -translate-y-1/2 text-outline">
                  mail
                </span>
                <input
                  className="w-full h-12 pl-[48px] pr-md bg-white border border-outline-variant rounded-lg text-body-md focus:border-primary-container focus:ring-2 focus:ring-primary-container/20 transition-all outline-none"
                  id="email"
                  placeholder="example@lxp.com"
                  required
                  type="email"
                  value={email}
                  onChange={(event) => setEmail(event.target.value)}
                />
              </div>
            </div>

            <div className="space-y-xs">
              <label className="text-label-md text-on-surface-variant" htmlFor="nickname">
                닉네임
              </label>
              <div className="relative">
                <span className="material-symbols-outlined absolute left-md top-1/2 -translate-y-1/2 text-outline">
                  person
                </span>
                <input
                  className="w-full h-12 pl-[48px] pr-md bg-white border border-outline-variant rounded-lg text-body-md focus:border-primary-container focus:ring-2 focus:ring-primary-container/20 transition-all outline-none"
                  id="nickname"
                  placeholder="학습 공간에서 사용할 이름"
                  required
                  type="text"
                  value={nickname}
                  onChange={(event) => setNickname(event.target.value)}
                />
              </div>
            </div>

            <div className="space-y-xs">
              <label className="text-label-md text-on-surface-variant" htmlFor="password">
                비밀번호
              </label>
              <div className="relative">
                <span className="material-symbols-outlined absolute left-md top-1/2 -translate-y-1/2 text-outline">
                  lock
                </span>
                <input
                  className="w-full h-12 pl-[48px] pr-md bg-white border border-outline-variant rounded-lg text-body-md focus:border-primary-container focus:ring-2 focus:ring-primary-container/20 transition-all outline-none"
                  id="password"
                  placeholder="8자 이상 입력하세요"
                  required
                  type="password"
                  value={password}
                  onChange={(event) => setPassword(event.target.value)}
                />
              </div>
            </div>

            <div className="space-y-xs">
              <label className="text-label-md text-on-surface-variant" htmlFor="passwordConfirm">
                비밀번호 확인
              </label>
              <div className="relative">
                <span className="material-symbols-outlined absolute left-md top-1/2 -translate-y-1/2 text-outline">
                  key
                </span>
                <input
                  className="w-full h-12 pl-[48px] pr-md bg-white border border-outline-variant rounded-lg text-body-md focus:border-primary-container focus:ring-2 focus:ring-primary-container/20 transition-all outline-none"
                  id="passwordConfirm"
                  placeholder="비밀번호를 다시 입력하세요"
                  required
                  type="password"
                  value={passwordConfirm}
                  onChange={(event) => setPasswordConfirm(event.target.value)}
                />
              </div>
            </div>

            <div className="flex items-start gap-xs py-xs">
              <input
                className="mt-1 rounded-sm border-outline text-primary-container focus:ring-primary-container"
                id="terms"
                required
                type="checkbox"
                checked={termsAgreed}
                onChange={(event) => setTermsAgreed(event.target.checked)}
              />
              <label className="text-label-sm text-on-surface-variant" htmlFor="terms">
                <Link className="text-primary-container hover:underline" href="/terms">
                  이용약관
                </Link>
                에 동의합니다.
              </label>
            </div>

            {errorMessage && (
              <p className="text-label-sm font-label-sm text-error">
                {errorMessage}
              </p>
            )}

            <button
              className="btn-hover-effect w-full h-12 bg-primary-container text-on-primary font-label-md rounded-lg shadow-sm hover:brightness-105 disabled:opacity-60 transition-all flex items-center justify-center gap-xs"
              type="submit"
              disabled={isSubmitting}
            >
              {isSubmitting ? '회원가입 중...' : '회원가입 완료'}
              <span className="material-symbols-outlined">arrow_forward</span>
            </button>
          </form>

          <div className="mt-xl text-center">
            <p className="text-label-md text-on-surface-variant">
              이미 계정이 있으신가요?
              <Link className="text-primary-container font-bold ml-xs hover:underline transition-all" href="/login">
                로그인하기
              </Link>
            </p>
          </div>
        </div>

        <div className="fixed -bottom-32 -left-32 w-96 h-96 bg-primary-fixed opacity-20 rounded-full blur-3xl -z-10" />
        <div className="fixed top-24 -right-32 w-64 h-64 bg-secondary-fixed opacity-20 rounded-full blur-2xl -z-10" />
      </main>
    </>
  );
}
