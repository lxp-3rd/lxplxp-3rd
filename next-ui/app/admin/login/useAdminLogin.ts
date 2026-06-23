'use client';

import type { ChangeEvent, FormEvent } from 'react';
import { useState } from 'react';
import { authApi } from '@/app/(auth)/login/api';
import { useAuth } from '@/lib/AuthContext';

export function useAdminLogin() {
  const { login } = useAuth();
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [showPassword, setShowPassword] = useState(false);
  const [error, setError] = useState('');
  const [isSubmitting, setIsSubmitting] = useState(false);

  const handleEmailChange = (event: ChangeEvent<HTMLInputElement>) => {
    setEmail(event.target.value);
    setError('');
  };

  const handlePasswordChange = (event: ChangeEvent<HTMLInputElement>) => {
    setPassword(event.target.value);
    setError('');
  };

  const toggleShowPassword = () => {
    setShowPassword((value) => !value);
  };

  const handleSubmit = async (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    if (isSubmitting) {
      return;
    }

    if (!email || !password) {
      setError('이메일과 비밀번호를 입력해주세요.');
      return;
    }

    setIsSubmitting(true);
    try {
      const tokenPair = await authApi.login({ email, password });
      login({ ...tokenPair, email });
    } catch {
      setError('관리자 계정 정보를 확인해주세요.');
    } finally {
      setIsSubmitting(false);
    }
  };

  return {
    email,
    password,
    showPassword,
    error,
    isSubmitting,
    handleEmailChange,
    handlePasswordChange,
    toggleShowPassword,
    handleSubmit,
  };
}
