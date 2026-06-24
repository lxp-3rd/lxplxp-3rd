'use client';

import type { FormEvent } from 'react';
import { useState } from 'react';
import { useAuth } from '@/lib/AuthContext';
import { authApi } from './api';

export function useLoginForm() {
  const { login } = useAuth();
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [showPassword, setShowPassword] = useState(false);
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [errorMessage, setErrorMessage] = useState('');

  const handleSubmit = async (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    setErrorMessage('');
    setIsSubmitting(true);

    try {
      const tokenPair = await authApi.login({ email, password });
      await login({ ...tokenPair, email });
    } catch {
      setErrorMessage('이메일 또는 비밀번호를 확인해 주세요.');
    } finally {
      setIsSubmitting(false);
    }
  };

  const quickLogin = async (presetEmail: string) => {
    setErrorMessage('');
    setIsSubmitting(true);
    try {
      const tokenPair = await authApi.login({ email: presetEmail, password: 'testtest' });
      await login({ ...tokenPair, email: presetEmail });
    } catch {
      setErrorMessage('임시 계정 로그인에 실패했습니다.');
    } finally {
      setIsSubmitting(false);
    }
  };

  return {
    email,
    setEmail,
    password,
    setPassword,
    showPassword,
    setShowPassword,
    isSubmitting,
    errorMessage,
    handleSubmit,
    quickLogin,
  };
}
