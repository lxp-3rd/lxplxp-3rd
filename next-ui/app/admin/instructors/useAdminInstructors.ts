'use client';

import { useEffect, useState } from 'react';
import { adminInstructorMockApi } from '../api';
import type { AdminInstructor } from '../types';
import { toError } from '../utils';

export function useAdminInstructors() {
  const [instructors, setInstructors] = useState<AdminInstructor[]>([]);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState<Error | null>(null);

  useEffect(() => {
    let isMounted = true;

    adminInstructorMockApi.getInstructors()
      .then((nextInstructors) => {
        if (!isMounted) return;
        setInstructors(nextInstructors);
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
    instructors,
    isLoading,
    error,
  };
}
