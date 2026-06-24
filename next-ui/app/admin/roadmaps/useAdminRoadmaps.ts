'use client';

import { useEffect, useState } from 'react';
import { adminRoadmapMockApi } from '../api';
import type { AdminRoadmap } from '../types';

export function useAdminRoadmaps() {
  const [roadmaps, setRoadmaps] = useState<AdminRoadmap[]>([]);

  useEffect(() => {
    let isMounted = true;

    adminRoadmapMockApi.getRoadmaps().then((nextRoadmaps) => {
      if (isMounted) setRoadmaps(nextRoadmaps);
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
    toggleHidden,
    removeRoadmap,
  };
}
