'use client';

import { useEffect, useState } from 'react';
import { useRouter } from 'next/navigation';
import { ApiError } from '@/lib/fetcher';
import { adminAnnouncementApi } from '../../../api';
import { toError } from '../../../utils';

export function useAdminAnnouncementEdit(id: string) {
  const router = useRouter();
  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');
  const [author, setAuthor] = useState('관리자');
  const [isPublic, setIsPublic] = useState(true);
  const [isFound, setIsFound] = useState(true);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState<Error | null>(null);
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [errorMessage, setErrorMessage] = useState('');

  useEffect(() => {
    let isMounted = true;

    setIsLoading(true);

    adminAnnouncementApi.getById(id)
      .then((announcement) => {
        if (!isMounted) return;
        setTitle(announcement.title);
        setContent(announcement.content);
        setIsPublic(announcement.status === 'PUBLISH');
        setIsFound(true);
        setError(null);
      })
      .catch((loadError) => {
        if (!isMounted) return;
        if (loadError instanceof ApiError && loadError.status === 404) {
          setIsFound(false);
          return;
        }
        setError(toError(loadError));
      })
      .finally(() => {
        if (isMounted) setIsLoading(false);
      });

    return () => {
      isMounted = false;
    };
  }, [id]);

  const cancel = () => router.back();

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

    setIsSubmitting(true);
    setErrorMessage('');
    try {
      await adminAnnouncementApi.update(id, {
        title: trimmedTitle,
        content,
        status: isPublic ? 'PUBLISH' : 'HIDDEN',
      });
      router.push(`/admin/announcements/${id}`);
    } catch {
      setErrorMessage('공지사항을 수정하지 못했습니다.');
      setIsSubmitting(false);
    }
  };

  return {
    title,
    content,
    author,
    isPublic,
    isFound,
    isLoading,
    error,
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