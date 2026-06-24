'use client';

import { useEffect, useState } from 'react';
import { adminDashboardApi } from './api';
import type { AdminDashboardStat, AdminMenuItem } from './types';

export function useAdminDashboard() {
  const [stats, setStats] = useState<AdminDashboardStat[]>([]);
  const [menuItems, setMenuItems] = useState<AdminMenuItem[]>([]);

  useEffect(() => {
    let isMounted = true;

    Promise.all([
      adminDashboardApi.getStats(),
      adminDashboardApi.getMenuItems(),
    ]).then(([nextStats, nextMenuItems]) => {
      if (!isMounted) return;
      setStats(nextStats);
      setMenuItems(nextMenuItems);
    });

    return () => {
      isMounted = false;
    };
  }, []);

  return {
    stats,
    menuItems,
  };
}
