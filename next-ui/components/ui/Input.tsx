'use client';

import { InputHTMLAttributes, useId } from 'react';

interface InputProps extends InputHTMLAttributes<HTMLInputElement> {
  label?:       string;
  error?:       string;
  icon?:        string; // Material Symbol name
}

export function Input({ label, error, icon, className = '', ...props }: InputProps) {
  const id = useId();

  return (
    <div className="flex flex-col gap-xs w-full">
      {label && (
        <label
          htmlFor={id}
          className="text-label-md text-on-surface"
        >
          {label}
        </label>
      )}

      <div className="relative">
        {icon && (
          <span className="material-symbols-outlined absolute left-sm top-1/2 -translate-y-1/2 text-on-surface-variant text-[20px]">
            {icon}
          </span>
        )}
        <input
          id={id}
          className={[
            'w-full border rounded-lg py-sm text-body-md bg-surface-container-lowest',
            'placeholder:text-on-surface-variant',
            'focus:outline-none focus:border-primary focus:ring-2 focus:ring-primary/20 transition-all',
            error
              ? 'border-error focus:border-error focus:ring-error/20'
              : 'border-outline-variant',
            icon ? 'pl-10 pr-md' : 'px-md',
            className,
          ].join(' ')}
          {...props}
        />
      </div>

      {error && (
        <p className="text-label-sm text-error">{error}</p>
      )}
    </div>
  );
}
