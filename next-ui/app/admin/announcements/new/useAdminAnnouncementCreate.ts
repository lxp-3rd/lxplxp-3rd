'use client';

import { useState } from 'react';
import { useRouter } from 'next/navigation';
import { useAuth } from '@/lib/AuthContext';
import { adminAnnouncementApi } from '../../api';

export function useAdminAnnouncementCreate() {
  const router = useRouter();
  const { user } = useAuth();
  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');
  const [author, setAuthor] = useState('관리자');
  const [isPublic, setIsPublic] = useState(true);
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [errorMessage, setErrorMessage] = useState('');

  const cancel = () => router.push('/admin/announcements');

  const save = async () => {
    if (isSubmitting) return;

    const trimmedTitle = title.trim();
    if (trimmedTitle.length < 2 || trimmedTitle.length > 100) {
      setErrorMessage('제목은 2자 이상 100자 이하로 입력해주세요.');
      return;
    }
    if (!content.trim()) {
      setErrorMessage('본문을 입력해주세요.');
      return;
    }
    if (!user) {
      setErrorMessage('로그인이 필요합니다.');
      return;
    }

    setIsSubmitting(true);
    setErrorMessage('');
    try {
      await adminAnnouncementApi.create({
        adminId: Number(user.id),
        title: trimmedTitle,
        content,
        status: isPublic ? 'PUBLISH' : 'HIDDEN',
      });
      router.push('/admin/announcements');
    } catch {
      setErrorMessage('공지사항을 등록하지 못했습니다.');
      setIsSubmitting(false);
    }
  };

  return {
    title,
    content,
    author,
    isPublic,
    isSubmitting,
    errorMessage,
    setTitle,
    setContent,
    setAuthor,
    setIsPublic,
    cancel,
    save,
  };
}
