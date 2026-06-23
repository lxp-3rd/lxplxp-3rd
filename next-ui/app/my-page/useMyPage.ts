'use client';

import type { FormEvent } from 'react';
import { useEffect, useState } from 'react';
import { useAuth } from '@/lib/AuthContext';
import { ApiError } from '@/lib/fetcher';
import { myPageApi } from './api';
import type { MyProfileResponse } from './types';

export function useMyPage() {
  const { logout, updateProfileUser } = useAuth();
  const [profile, setProfile] = useState<MyProfileResponse | null>(null);
  const [nickname, setNickname] = useState('');
  const [currentPassword, setCurrentPassword] = useState('');
  const [newPassword, setNewPassword] = useState('');
  const [newPasswordConfirm, setNewPasswordConfirm] = useState('');
  const [withdrawPassword, setWithdrawPassword] = useState('');
  const [isLoading, setIsLoading] = useState(true);
  const [isSavingProfile, setIsSavingProfile] = useState(false);
  const [isChangingPassword, setIsChangingPassword] = useState(false);
  const [isWithdrawing, setIsWithdrawing] = useState(false);
  const [message, setMessage] = useState('');
  const [errorMessage, setErrorMessage] = useState('');

  const handleUnauthorized = async (error: unknown) => {
    if (error instanceof ApiError && error.status === 401) {
      await logout('/login');
      return true;
    }
    return false;
  };

  useEffect(() => {
    let isMounted = true;

    async function loadProfile() {
      try {
        const data = await myPageApi.getProfile();
        if (!isMounted) return;
        setProfile(data);
        setNickname(data.nickname);
        updateProfileUser(data);
      } catch (error) {
        if (error instanceof ApiError && error.status === 401) {
          await logout('/login');
          return;
        }
        if (isMounted) {
          setErrorMessage('내 정보를 불러오지 못했습니다.');
        }
      } finally {
        if (isMounted) {
          setIsLoading(false);
        }
      }
    }

    void loadProfile();
    return () => {
      isMounted = false;
    };
  }, [logout, updateProfileUser]);

  const handleProfileSubmit = async (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    setMessage('');
    setErrorMessage('');
    setIsSavingProfile(true);

    try {
      const updatedProfile = await myPageApi.updateProfile({ nickname });
      setProfile(updatedProfile);
      setNickname(updatedProfile.nickname);
      updateProfileUser(updatedProfile);
      setMessage('프로필 정보가 수정되었습니다.');
    } catch (error) {
      if (await handleUnauthorized(error)) return;
      setErrorMessage('닉네임을 확인해 주세요.');
    } finally {
      setIsSavingProfile(false);
    }
  };

  const handlePasswordSubmit = async (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    setMessage('');
    setErrorMessage('');

    if (newPassword !== newPasswordConfirm) {
      setErrorMessage('새 비밀번호와 확인 값이 일치하지 않습니다.');
      return;
    }

    setIsChangingPassword(true);
    try {
      await myPageApi.changePassword({
        currentPassword,
        newPassword,
        newPasswordConfirm,
      });
      setCurrentPassword('');
      setNewPassword('');
      setNewPasswordConfirm('');
      setMessage('비밀번호가 변경되었습니다.');
    } catch (error) {
      if (await handleUnauthorized(error)) return;
      setErrorMessage('현재 비밀번호 또는 새 비밀번호를 확인해 주세요.');
    } finally {
      setIsChangingPassword(false);
    }
  };

  const handleWithdrawSubmit = async (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    setMessage('');
    setErrorMessage('');

    if (!window.confirm('정말 회원 탈퇴를 진행하시겠습니까?')) {
      return;
    }

    setIsWithdrawing(true);
    try {
      await myPageApi.withdraw({ password: withdrawPassword });
      await logout('/login');
    } catch (error) {
      if (await handleUnauthorized(error)) return;
      setErrorMessage('비밀번호를 확인해 주세요.');
      setIsWithdrawing(false);
    }
  };

  return {
    profile,
    nickname,
    setNickname,
    currentPassword,
    setCurrentPassword,
    newPassword,
    setNewPassword,
    newPasswordConfirm,
    setNewPasswordConfirm,
    withdrawPassword,
    setWithdrawPassword,
    isLoading,
    isSavingProfile,
    isChangingPassword,
    isWithdrawing,
    message,
    errorMessage,
    handleProfileSubmit,
    handlePasswordSubmit,
    handleWithdrawSubmit,
  };
}
