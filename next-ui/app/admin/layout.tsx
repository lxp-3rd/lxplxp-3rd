import type { Metadata } from 'next';
import { AdminNavBar } from '@/components/admin/AdminNavBar';

export const metadata: Metadata = {
  title: { default: 'LXP 관리자', template: '%s | LXP 관리자' },
};

export default function AdminLayout({ children }: { children: React.ReactNode }) {
  return (
    <>
      <AdminNavBar />
      <main className="pt-16 min-h-screen bg-surface">
        {children}
      </main>
    </>
  );
}
