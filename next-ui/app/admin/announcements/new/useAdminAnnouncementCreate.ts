'use client';

import { useState } from 'react';
import { useRouter } from 'next/navigation';

export function useAdminAnnouncementCreate() {
  const router = useRouter();
  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');
  const [author, setAuthor] = useState('관리자');
  const [isPublic, setIsPublic] = useState(true);

  const cancel = () => router.push('/admin/announcements');
  const save = () => router.push('/admin/announcements');

  return {
    title,
    content,
    author,
    isPublic,
    setTitle,
    setContent,
    setAuthor,
    setIsPublic,
    cancel,
    save,
  };
}
