interface AdminStatusDotProps {
  active: boolean;
  activeLabel?: string;
  inactiveLabel?: string;
}

export function AdminStatusDot({
  active,
  activeLabel = '활성',
  inactiveLabel = '비활성',
}: AdminStatusDotProps) {
  return (
    <span
      className={[
        'flex items-center justify-center gap-xs text-label-sm font-label-sm',
        active ? 'text-green-600' : 'text-on-surface-variant',
      ].join(' ')}
    >
      <span
        className={[
          'w-2 h-2 rounded-full inline-block',
          active ? 'bg-green-600' : 'bg-outline-variant',
        ].join(' ')}
      />
      {active ? activeLabel : inactiveLabel}
    </span>
  );
}
