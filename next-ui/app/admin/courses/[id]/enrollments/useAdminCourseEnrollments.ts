'use client';

import { useEffect, useState } from 'react';
import { adminCourseMockApi } from '../../../api';
import type { AdminCourseEnrollmentDetail } from '../../../types';
import { toError } from '../../../utils';

export function useAdminCourseEnrollments(courseId: string) {
  const [detail, setDetail] = useState<AdminCourseEnrollmentDetail | null>(null);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState<Error | null>(null);

  useEffect(() => {
    let isMounted = true;

    setIsLoading(true);

    adminCourseMockApi.getCourseEnrollments(courseId)
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
  }, [courseId]);

  return {
    detail,
    isLoading,
    error,
  };
}
