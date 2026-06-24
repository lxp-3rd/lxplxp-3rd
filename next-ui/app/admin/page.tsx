'use client';

import { PageHeader } from '@/components/ui';
import { AdminMenuCard } from './components/AdminMenuCard';
import { AdminPageContainer } from './components/AdminPageContainer';
import { AdminStatCard } from './components/AdminStatCard';
import { useAdminDashboard } from './useAdminDashboard';

export default function AdminDashboardPage() {
  const { stats, menuItems, isLoading, error } = useAdminDashboard();

  if (isLoading) {
    return <AdminPageContainer className="mt-16">Loading...</AdminPageContainer>;
  }

  if (error) {
    return <AdminPageContainer className="mt-16">Failed to load admin dashboard.</AdminPageContainer>;
  }

  return (
    <AdminPageContainer className="mt-16">
      <PageHeader
        title="관리자 개요"
        subtitle="플랫폼 전반의 최신 활동 내역입니다."
      />

      <section className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-lg mb-xl">
        {stats.map((stat) => (
          <AdminStatCard key={stat.label} stat={stat} />
        ))}
      </section>

      <section className="grid grid-cols-1 lg:grid-cols-3 gap-lg">
        <div className="lg:col-span-2 glass-card rounded-xl p-lg">
          <h3 className="text-headline-md font-headline-md mb-lg flex items-center gap-2">
            <span className="material-symbols-outlined text-primary">rocket_launch</span>
            빠른 메뉴
          </h3>
          <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-md">
            {menuItems.map((item) => (
              <AdminMenuCard key={item.href} item={item} />
            ))}
          </div>
        </div>
      </section>
    </AdminPageContainer>
  );
}
