'use client';

import Link from 'next/link';
import { Button, Input } from '@/components/ui';
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
            <Input
              label="이메일 주소"
              id="email"
              icon="mail"
              type="email"
              placeholder="example@lxp.com"
              required
              value={email}
              onChange={(event) => setEmail(event.target.value)}
            />

            <Input
              label="닉네임"
              id="nickname"
              icon="person"
              type="text"
              placeholder="학습 공간에서 사용할 이름"
              required
              value={nickname}
              onChange={(event) => setNickname(event.target.value)}
            />

            <Input
              label="비밀번호"
              id="password"
              icon="lock"
              type="password"
              placeholder="8자 이상 입력하세요"
              required
              value={password}
              onChange={(event) => setPassword(event.target.value)}
            />

            <Input
              label="비밀번호 확인"
              id="passwordConfirm"
              icon="key"
              type="password"
              placeholder="비밀번호를 다시 입력하세요"
              required
              value={passwordConfirm}
              onChange={(event) => setPasswordConfirm(event.target.value)}
            />

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

            <Button type="submit" disabled={isSubmitting} fullWidth size="lg">
              {isSubmitting ? '회원가입 중...' : '회원가입 완료'}
              <span className="material-symbols-outlined">arrow_forward</span>
            </Button>
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