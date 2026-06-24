'use client';

import { useEffect, useState } from 'react';
import { adminCourseApi } from './api';
import type { AdminCourseResponse, AdminCourseStatus } from './types';

export function useAdminCourses() {
  const [courses, setCourses] = useState<AdminCourseResponse[]>([]);
  const [isLoading, setIsLoading] = useState(true);
  const [errorMessage, setErrorMessage] = useState('');

  useEffect(() => {
    let isMounted = true;

    async function loadCourses() {
      try {
        const data = await adminCourseApi.getCourses();
        if (isMounted) setCourses(data);
      } catch {
        if (isMounted) setErrorMessage('강좌 목록을 불러오지 못했습니다.');
      } finally {
        if (isMounted) setIsLoading(false);
      }
    }

    void loadCourses();
    return () => {
      isMounted = false;
    };
  }, []);

  const toggleVisibility = async (id: number) => {
    const target = courses.find((course) => course.id === id);
    if (!target) return;

    const nextStatus: AdminCourseStatus = target.status === 'PUBLIC' ? 'HIDDEN' : 'PUBLIC';
    try {
      await adminCourseApi.changeStatus(id, { status: nextStatus, changedBy: 'ADMIN' });
      setCourses((prev) =>
        prev.map((course) =>
          course.id === id
            ? { ...course, status: nextStatus, hiddenBy: nextStatus === 'HIDDEN' ? 'ADMIN' : null }
            : course,
        ),
      );
    } catch {
      setErrorMessage('강좌 공개 상태를 변경하지 못했습니다.');
    }
  };

  const removeCourse = async (id: number) => {
    try {
      await adminCourseApi.remove(id);
      setCourses((prev) => prev.filter((course) => course.id !== id));
    } catch {
      setErrorMessage('강좌를 삭제하지 못했습니다.');
    }
  };

  return {
    courses,
    totalCount: courses.length,
    isLoading,
    errorMessage,
    toggleVisibility,
    removeCourse,
  };
}