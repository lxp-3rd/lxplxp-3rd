import { ButtonHTMLAttributes } from 'react';

type ButtonVariant = 'primary' | 'secondary' | 'danger' | 'outline' | 'ghost';
type ButtonSize    = 'sm' | 'md' | 'lg';

interface ButtonProps extends ButtonHTMLAttributes<HTMLButtonElement> {
  variant?: ButtonVariant;
  size?:    ButtonSize;
  fullWidth?: boolean;
}

const variantClasses: Record<ButtonVariant, string> = {
  primary:   'bg-primary text-on-primary hover:brightness-110 active:brightness-95',
  secondary: 'bg-secondary-container text-on-secondary-container hover:brightness-105',
  danger:    'bg-transparent text-error border border-error/20 hover:bg-error/5',
  outline:   'bg-transparent text-primary border border-primary hover:bg-primary-fixed',
  ghost:     'bg-transparent text-on-surface-variant hover:bg-surface-container',
};

const sizeClasses: Record<ButtonSize, string> = {
  sm: 'px-md py-xs text-label-sm rounded-lg',
  md: 'px-md py-sm text-label-md rounded-xl',
  lg: 'px-xl py-md text-label-md rounded-xl',
};

export function Button({
  variant   = 'primary',
  size      = 'md',
  fullWidth = false,
  className = '',
  children,
  ...props
}: ButtonProps) {
  return (
    <button
      className={[
        'inline-flex items-center justify-center gap-xs font-semibold transition-all',
        'disabled:opacity-50 disabled:cursor-not-allowed',
        variantClasses[variant],
        sizeClasses[size],
        fullWidth ? 'w-full' : '',
        className,
      ].join(' ')}
      {...props}
    >
      {children}
    </button>
  );
}
