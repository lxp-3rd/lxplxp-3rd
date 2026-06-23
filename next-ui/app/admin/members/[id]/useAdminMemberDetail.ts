'use client';

import { useEffect, useState } from 'react';
import { adminMemberApi } from '../api';
import type { AdminMemberResponse, AdminMemberStatus } from '../types';

export function useAdminMemberDetail(memberId: number | null) {
  const [member, setMember] = useState<AdminMemberResponse | null>(null);
  const [selectedStatus, setSelectedStatus] = useState<AdminMemberStatus | ''>('');
  const [isLoading, setIsLoading] = useState(true);
  const [isSaving, setIsSaving] = useState(false);
  const [message, setMessage] = useState('');
  const [errorMessage, setErrorMessage] = useState('');

  useEffect(() => {
    let isMounted = true;

    async function loadMember() {
      setMember(null);
      setSelectedStatus('');
      setMessage('');
      setErrorMessage('');
      setIsLoading(true);

      if (!memberId) {
        setErrorMessage('회원 ID가 올바르지 않습니다.');
        setIsLoading(false);
        return;
      }

      try {
        const data = await adminMemberApi.getMember(memberId);
        if (!isMounted) return;
        setMember(data);
        setSelectedStatus(data.status);
      } catch {
        if (isMounted) {
          setErrorMessage('회원 정보를 불러오지 못했습니다.');
        }
      } finally {
        if (isMounted) {
          setIsLoading(false);
        }
      }
    }

    void loadMember();
    return () => {
      isMounted = false;
    };
  }, [memberId]);

  const handleStatusChange = async () => {
    if (!memberId || !selectedStatus) return;

    setMessage('');
    setErrorMessage('');
    setIsSaving(true);
    try {
      const updatedMember = await adminMemberApi.changeStatus(memberId, { status: selectedStatus });
      setMember(updatedMember);
      setSelectedStatus(updatedMember.status);
      setMessage('회원 상태가 변경되었습니다.');
    } catch {
      setErrorMessage('회원 상태 변경에 실패했습니다.');
    } finally {
      setIsSaving(false);
    }
  };

  return {
    member,
    selectedStatus,
    setSelectedStatus,
    isLoading,
    isSaving,
    message,
    errorMessage,
    handleStatusChange,
  };
}
