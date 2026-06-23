'use client';

import type { FormEvent } from 'react';
import { useState } from 'react';
import { useRouter } from 'next/navigation';
import { authApi } from './api';

export function useRegisterForm() {
  const router = useRouter();
  const [email, setEmail] = useState('');
  const [nickname, setNickname] = useState('');
  const [password, setPassword] = useState('');
  const [passwordConfirm, setPasswordConfirm] = useState('');
  const [termsAgreed, setTermsAgreed] = useState(false);
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [errorMessage, setErrorMessage] = useState('');

  const handleSubmit = async (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    setErrorMessage('');

    if (password !== passwordConfirm) {
      setErrorMessage('비밀번호와 비밀번호 확인이 일치하지 않습니다.');
      return;
    }

    setIsSubmitting(true);
    try {
      await authApi.signUp({
        email,
        nickname,
        password,
        passwordConfirm,
      });
      router.push('/login');
    } catch {
      setErrorMessage('회원가입 정보를 확인해 주세요.');
    } finally {
      setIsSubmitting(false);
    }
  };

  return {
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
  };
}
