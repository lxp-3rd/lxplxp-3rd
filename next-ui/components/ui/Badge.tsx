type BadgeVariant = 'default' | 'primary' | 'secondary' | 'success' | 'warning' | 'error';

interface BadgeProps {
  children:  React.ReactNode;
  variant?:  BadgeVariant;
  className?: string;
}

const variantClasses: Record<BadgeVariant, string> = {
  default:   'bg-surface-container text-on-surface-variant',
  primary:   'bg-primary text-on-primary',
  secondary: 'bg-secondary-container text-on-secondary-container',
  success:   'bg-surface-container-high text-on-surface',
  warning:   'bg-primary-fixed text-on-primary-fixed',
  error:     'bg-error-container text-on-error-container',
};

export function Badge({ children, variant = 'default', className = '' }: BadgeProps) {
  return (
    <span
      className={[
        'inline-flex items-center px-sm py-[2px] rounded-full text-label-sm font-medium',
        variantClasses[variant],
        className,
      ].join(' ')}
    >
      {children}
    </span>
  );
}
