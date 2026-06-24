'use client';

import { useEffect, useState } from 'react';
import { adminInstructorMockApi } from '../api';
import type { AdminInstructor } from '../types';

export function useAdminInstructors() {
  const [instructors, setInstructors] = useState<AdminInstructor[]>([]);

  useEffect(() => {
    let isMounted = true;

    adminInstructorMockApi.getInstructors().then((nextInstructors) => {
      if (isMounted) setInstructors(nextInstructors);
    });

    return () => {
      isMounted = false;
    };
  }, []);

  return {
    instructors,
  };
}
