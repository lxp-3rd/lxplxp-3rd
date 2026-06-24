'use client';

import { useEffect, useState } from 'react';
import { useRouter } from 'next/navigation';
import { adminAnnouncementMockApi } from '../../../api';

export function useAdminAnnouncementEdit(id: string) {
  const router = useRouter();
  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');
  const [author, setAuthor] = useState('');
  const [isPublic, setIsPublic] = useState(true);
  const [isFound, setIsFound] = useState(true);

  useEffect(() => {
    let isMounted = true;

    adminAnnouncementMockApi.getAnnouncement(id).then((announcement) => {
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
    setTitle,
    setContent,
    setAuthor,
    setIsPublic,
    cancel,
    save,
  };
}
