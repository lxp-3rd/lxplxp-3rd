interface AdminIconButtonProps {
  icon: string;
  label: string;
  onClick?: () => void;
  danger?: boolean;
}

export function AdminIconButton({ icon, label, onClick, danger = false }: AdminIconButtonProps) {
  return (
    <button
      type="button"
      onClick={onClick}
      className={[
        'p-xs rounded-full hover:bg-surface-container transition-colors text-on-surface-variant',
        danger ? 'hover:text-error hover:bg-error-container' : 'hover:text-primary',
      ].join(' ')}
      title={label}
      aria-label={label}
    >
      <span className="material-symbols-outlined text-[20px]">{icon}</span>
    </button>
  );
}
