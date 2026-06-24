'use client';

import { useEffect, useState } from 'react';
import { adminRoadmapMockApi } from '../api';
import type { AdminRoadmap } from '../types';
import { toError } from '../utils';

export function useAdminRoadmaps() {
  const [roadmaps, setRoadmaps] = useState<AdminRoadmap[]>([]);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState<Error | null>(null);

  useEffect(() => {
    let isMounted = true;

    adminRoadmapMockApi.getRoadmaps()
      .then((nextRoadmaps) => {
        if (!isMounted) return;
        setRoadmaps(nextRoadmaps);
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
    setRoadmaps((prev) =>
      prev.map((roadmap) => (roadmap.id === id ? { ...roadmap, hidden: !roadmap.hidden } : roadmap)),
    );
  };

  const removeRoadmap = (id: string) => {
    setRoadmaps((prev) => prev.filter((roadmap) => roadmap.id !== id));
  };

  return {
    roadmaps,
    isLoading,
    error,
    toggleHidden,
    removeRoadmap,
  };
}
