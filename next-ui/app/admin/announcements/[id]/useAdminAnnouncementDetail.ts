'use client';

import { useEffect, useState } from 'react';
import { adminAnnouncementApi } from '../../api';
import type { AdminAnnouncement } from '../../types';
import { toError } from '../../utils';

export function useAdminAnnouncementDetail(id: string) {
  const [announcement, setAnnouncement] = useState<AdminAnnouncement | null>(null);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState<Error | null>(null);

  useEffect(() => {
    let isMounted = true;

    setIsLoading(true);

    adminAnnouncementApi.getById(id)
      .then((nextAnnouncement) => {
        if (!isMounted) return;
        setAnnouncement(nextAnnouncement);
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

  return {
    announcement,
    isLoading,
    error,
  };
}
