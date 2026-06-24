'use client';

import { useEffect, useState } from 'react';
import { useRouter } from 'next/navigation';
import { adminAnnouncementMockApi } from '../../../api';
import { toError } from '../../../utils';

export function useAdminAnnouncementEdit(id: string) {
  const router = useRouter();
  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');
  const [author, setAuthor] = useState('');
  const [isPublic, setIsPublic] = useState(true);
  const [isFound, setIsFound] = useState(true);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState<Error | null>(null);

  useEffect(() => {
    let isMounted = true;

    setIsLoading(true);

    adminAnnouncementMockApi.getAnnouncement(id)
      .then((announcement) => {
        if (!isMounted) return;
        if (!announcement) {
          setIsFound(false);
          return;
        }

        setTitle(announcement.title);
        setContent(announcement.content);
        setAuthor(announcement.authorName);
        setIsPublic(true);
        setIsFound(true);
        setError(null);
      })
      .catch((loadError) => {
        if (!isMounted) return;
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
  const save = () => router.push(`/admin/announcements/${id}`);

  return {
    title,
    content,
    author,
    isPublic,
    isFound,
    isLoading,
    error,
    setTitle,
    setContent,
    setAuthor,
    setIsPublic,
    cancel,
    save,
  };
}
