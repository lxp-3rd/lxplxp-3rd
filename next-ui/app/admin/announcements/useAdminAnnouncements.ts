'use client';

import { useEffect, useState } from 'react';
import { adminAnnouncementApi } from '../api';
import type { AdminAnnouncement } from '../types';
import { toError } from '../utils';

export function useAdminAnnouncements() {
  const [announcements, setAnnouncements] = useState<AdminAnnouncement[]>([]);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState<Error | null>(null);

  useEffect(() => {
    let isMounted = true;

    adminAnnouncementApi.getAll()
      .then((nextAnnouncements) => {
        if (!isMounted) return;
        setAnnouncements(nextAnnouncements);
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
  }, []);

  return {
    announcements,
    isLoading,
    error,
  };
}
