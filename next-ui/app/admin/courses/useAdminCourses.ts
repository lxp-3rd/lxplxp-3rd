'use client';

import { useEffect, useState } from 'react';
import { adminCourseMockApi } from '../api';
import type { AdminCourse } from '../types';

export function useAdminCourses() {
  const [courses, setCourses] = useState<AdminCourse[]>([]);

  useEffect(() => {
    let isMounted = true;

    adminCourseMockApi.getCourses().then((nextCourses) => {
      if (isMounted) setCourses(nextCourses);
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
    toggleHidden,
    removeCourse,
  };
}
