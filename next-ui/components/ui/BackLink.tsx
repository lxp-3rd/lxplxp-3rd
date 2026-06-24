import Link from 'next/link';

interface BackLinkProps {
  href: string;
  children: React.ReactNode;
  className?: string;
}

export function BackLink({ href, children, className = '' }: BackLinkProps) {
  return (
    <Link
      href={href}
      className={[
        'inline-flex items-center gap-xs text-label-md font-label-md text-primary hover:opacity-80 transition-opacity',
        className,
      ].join(' ')}
    >
      <span className="material-symbols-outlined text-[18px]">arrow_back</span>
      {children}
    </Link>
  );
}
