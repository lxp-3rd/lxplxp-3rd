'use client';

import { useEffect, useState } from 'react';
import { adminDashboardApi } from './api';
import type { AdminDashboardStat, AdminMenuItem } from './types';
import { toError } from './utils';

export function useAdminDashboard() {
  const [stats, setStats] = useState<AdminDashboardStat[]>([]);
  const [menuItems, setMenuItems] = useState<AdminMenuItem[]>([]);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState<Error | null>(null);

  useEffect(() => {
    let isMounted = true;

    Promise.all([
      adminDashboardApi.getStats(),
      adminDashboardApi.getMenuItems(),
    ])
      .then(([nextStats, nextMenuItems]) => {
        if (!isMounted) return;
        setStats(nextStats);
        setMenuItems(nextMenuItems);
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
    stats,
    menuItems,
    isLoading,
    error,
  };
}
