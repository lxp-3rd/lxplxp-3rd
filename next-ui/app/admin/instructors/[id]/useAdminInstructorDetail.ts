'use client';

import { useEffect, useState } from 'react';
import { adminInstructorMockApi } from '../../api';
import type { AdminInstructorDetail } from '../../types';
import { toError } from '../../utils';

export function useAdminInstructorDetail(id: string) {
  const [detail, setDetail] = useState<AdminInstructorDetail | null>(null);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState<Error | null>(null);

  useEffect(() => {
    let isMounted = true;

    setIsLoading(true);

    adminInstructorMockApi.getInstructorDetail(id)
      .then((nextDetail) => {
        if (!isMounted) return;
        setDetail(nextDetail);
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
    detail,
    isLoading,
    error,
  };
}
