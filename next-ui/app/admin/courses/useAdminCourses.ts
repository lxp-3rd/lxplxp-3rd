'use client';

import { useEffect, useState } from 'react';
import { adminCourseMockApi } from '../api';
import type { AdminCourse } from '../types';
import { toError } from '../utils';

export function useAdminCourses() {
  const [courses, setCourses] = useState<AdminCourse[]>([]);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState<Error | null>(null);

  useEffect(() => {
    let isMounted = true;

    adminCourseMockApi.getCourses()
      .then((nextCourses) => {
        if (!isMounted) return;
        setCourses(nextCourses);
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

  const toggleHidden = (id: string) => {
    setCourses((prev) =>
      prev.map((course) => (course.id === id ? { ...course, hidden: !course.hidden } : course)),
    );
  };

  const removeCourse = (id: string) => {
    setCourses((prev) => prev.filter((course) => course.id !== id));
  };

  return {
    courses,
    totalCount: courses.length,
    isLoading,
    error,
    toggleHidden,
    removeCourse,
  };
}
