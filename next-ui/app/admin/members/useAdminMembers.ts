'use client';

import { useEffect, useMemo, useState } from 'react';
import { adminMemberApi } from './api';
import type { AdminMemberResponse } from './types';

export function useAdminMembers() {
  const [members, setMembers] = useState<AdminMemberResponse[]>([]);
  const [search, setSearch] = useState('');
  const [isLoading, setIsLoading] = useState(true);
  const [errorMessage, setErrorMessage] = useState('');

  useEffect(() => {
    let isMounted = true;

    async function loadMembers() {
      try {
        const data = await adminMemberApi.getMembers();
        if (isMounted) {
          setMembers(data);
        }
      } catch {
        if (isMounted) {
          setErrorMessage('회원 목록을 불러오지 못했습니다.');
        }
      } finally {
        if (isMounted) {
          setIsLoading(false);
        }
      }
    }

    void loadMembers();
    return () => {
      isMounted = false;
    };
  }, []);

  const filteredMembers = useMemo(
    () => members.filter((member) =>
      !search ||
      member.nickname.includes(search) ||
      member.email.includes(search)
    ),
    [members, search]
  );

  const activeCount = members.filter((member) => member.status === 'ACTIVE').length;

  return {
    members,
    filteredMembers,
    activeCount,
    search,
    setSearch,
    isLoading,
    errorMessage,
  };
}
