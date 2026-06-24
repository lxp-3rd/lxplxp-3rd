import type { AdminDashboardStat } from '../types';

const toneClasses: Record<AdminDashboardStat['tone'], { icon: string; bar: string; card: string }> = {
  primary: {
    icon: 'bg-primary-fixed text-primary',
    bar: 'bg-primary-container',
    card: 'glass-card',
  },
  secondary: {
    icon: 'bg-secondary-fixed text-secondary',
    bar: 'bg-secondary-container',
    card: 'glass-card border-l-4 border-l-primary-container',
  },
  tertiary: {
    icon: 'bg-tertiary-fixed text-tertiary',
    bar: 'bg-tertiary-container',
    card: 'glass-card',
  },
  danger: {
    icon: 'bg-white/20 text-white',
    bar: 'bg-white',
    card: 'bg-primary-container text-on-primary shadow-lg',
  },
};

export function AdminStatCard({ stat }: { stat: AdminDashboardStat }) {
  const tone = toneClasses[stat.tone];
  const isDanger = stat.tone === 'danger';

  return (
    <div className={`${tone.card} p-lg rounded-xl stat-card-glow transition-all duration-300`}>
      <div className="flex justify-between items-start mb-md">
        <div className={`p-sm rounded-lg ${tone.icon}`}>
          <span className="material-symbols-outlined">{stat.icon}</span>
        </div>
        <span
          className={[
            'text-label-sm font-label-sm px-2 py-0.5 rounded-full',
            isDanger ? 'bg-white/20 text-white' : 'text-green-600 bg-green-50',
          ].join(' ')}
        >
          {stat.trendLabel}
        </span>
      </div>

      <p className={`text-label-md font-label-md mb-xs ${isDanger ? 'opacity-90' : 'text-on-surface-variant'}`}>
        {stat.label}
      </p>
      <h2 className="text-headline-md font-headline-md">{stat.value}</h2>
      <div className="mt-md h-1 w-full bg-surface-container rounded-full overflow-hidden">
        <div className={`h-full ${tone.bar}`} style={{ width: `${stat.progress}%` }} />
      </div>
    </div>
  );
}
