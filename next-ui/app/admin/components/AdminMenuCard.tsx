import Link from 'next/link';
import type { AdminMenuItem } from '../types';

export function AdminMenuCard({ item }: { item: AdminMenuItem }) {
  return (
    <Link
      className="group p-md rounded-xl border border-surface-variant hover:border-primary-container hover:bg-primary-fixed/30 transition-all flex flex-col gap-sm"
      href={item.href}
    >
      <div className="w-10 h-10 rounded-lg bg-surface-container flex items-center justify-center group-hover:bg-primary-container group-hover:text-on-primary transition-colors">
        <span className="material-symbols-outlined">{item.icon}</span>
      </div>
      <span className="font-label-md text-on-surface">{item.title}</span>
      <p className="text-label-sm text-on-surface-variant">{item.description}</p>
    </Link>
  );
}
