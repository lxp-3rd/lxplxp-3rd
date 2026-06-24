'use client';

import { useEffect, useState } from 'react';
import { adminCourseMockApi } from '../../../api';
import type { AdminCourseEnrollmentDetail } from '../../../types';

export function useAdminCourseEnrollments(courseId: string) {
  const [detail, setDetail] = useState<AdminCourseEnrollmentDetail | null>(null);

  useEffect(() => {
    let isMounted = true;

    adminCourseMockApi.getCourseEnrollments(courseId).then((nextDetail) => {
      if (isMounted) setDetail(nextDetail);
    });

    return () => {
      isMounted = false;
    };
  }, [courseId]);

  return {
    detail,
  };
}
