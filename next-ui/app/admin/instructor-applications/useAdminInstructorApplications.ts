'use client';

import { useEffect, useMemo, useState } from 'react';
import { adminInstructorApplicationMockApi } from '../api';
import type { AdminInstructorApplication } from '../types';
import { toError } from '../utils';

export function useAdminInstructorApplications() {
  const [applications, setApplications] = useState<AdminInstructorApplication[]>([]);
  const [selected, setSelected] = useState<AdminInstructorApplication | null>(null);
  const [rejectTarget, setRejectTarget] = useState<AdminInstructorApplication | null>(null);
  const [rejectInput, setRejectInput] = useState('');
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState<Error | null>(null);

  useEffect(() => {
    let isMounted = true;

    adminInstructorApplicationMockApi.getApplications()
      .then((nextApplications) => {
        if (!isMounted) return;
        setApplications(nextApplications);
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

  const pendingCount = useMemo(
    () => applications.filter((application) => application.status === 'PENDING').length,
    [applications],
  );

  const approve = (id: string) => {
    setApplications((prev) =>
      prev.map((application) =>
        application.id === id ? { ...application, status: 'APPROVED', rejectReason: '' } : application,
      ),
    );
    setSelected(null);
  };

  const reject = (id: string, reason: string) => {
    setApplications((prev) =>
      prev.map((application) =>
        application.id === id ? { ...application, status: 'REJECTED', rejectReason: reason } : application,
      ),
    );
    setRejectTarget(null);
    setRejectInput('');
    setSelected(null);
  };

  const openRejectModal = (application: AdminInstructorApplication) => {
    setRejectTarget(application);
    setRejectInput('');
  };

  return {
    applications,
    selected,
    rejectTarget,
    rejectInput,
    pendingCount,
    isLoading,
    error,
    setSelected,
    setRejectInput,
    setRejectTarget,
    approve,
    reject,
    openRejectModal,
  };
}
