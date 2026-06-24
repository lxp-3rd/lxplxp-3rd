'use client';

import { useEffect, useState } from 'react';
import { adminInstructorMockApi } from '../../api';
import type { AdminInstructorDetail } from '../../types';

export function useAdminInstructorDetail(id: string) {
  const [detail, setDetail] = useState<AdminInstructorDetail | null>(null);

  useEffect(() => {
    let isMounted = true;

    adminInstructorMockApi.getInstructorDetail(id).then((nextDetail) => {
      if (isMounted) setDetail(nextDetail);
    });

    return () => {
      isMounted = false;
    };
  }, [id]);

  return {
    detail,
  };
}
