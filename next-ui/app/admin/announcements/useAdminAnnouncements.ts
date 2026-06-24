'use client';

import { useEffect, useState } from 'react';
import { adminAnnouncementMockApi } from '../api';
import type { AdminAnnouncement } from '../types';

export function useAdminAnnouncements() {
  const [announcements, setAnnouncements] = useState<AdminAnnouncement[]>([]);

  useEffect(() => {
    let isMounted = true;

    adminAnnouncementMockApi.getAnnouncements().then((nextAnnouncements) => {
      if (isMounted) setAnnouncements(nextAnnouncements);
    });

    return () => {
      isMounted = false;
    };
  }, []);

  return {
    announcements,
  };
}
