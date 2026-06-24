'use client';

import { useEffect, useState } from 'react';
import { adminAnnouncementMockApi } from '../../api';
import type { AdminAnnouncement } from '../../types';

export function useAdminAnnouncementDetail(id: string) {
  const [announcement, setAnnouncement] = useState<AdminAnnouncement | null>(null);

  useEffect(() => {
    let isMounted = true;

    adminAnnouncementMockApi.getAnnouncement(id).then((nextAnnouncement) => {
      if (isMounted) setAnnouncement(nextAnnouncement);
    });

    return () => {
      isMounted = false;
    };
  }, [id]);

  return {
    announcement,
  };
}
