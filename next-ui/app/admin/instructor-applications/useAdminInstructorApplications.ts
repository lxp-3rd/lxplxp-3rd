'use client';

import { useEffect, useMemo, useState } from 'react';
import { adminInstructorApplicationMockApi } from '../api';
import type { AdminInstructorApplication } from '../types';

export function useAdminInstructorApplications() {
  const [applications, setApplications] = useState<AdminInstructorApplication[]>([]);
  const [selected, setSelected] = useState<AdminInstructorApplication | null>(null);
  const [rejectTarget, setRejectTarget] = useState<AdminInstructorApplication | null>(null);
  const [rejectInput, setRejectInput] = useState('');

  useEffect(() => {
    let isMounted = true;

    adminInstructorApplicationMockApi.getApplications().then((nextApplications) => {
      if (isMounted) setApplications(nextApplications);
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
    setSelected,
    setRejectInput,
    setRejectTarget,
    approve,
    reject,
    openRejectModal,
  };
}
