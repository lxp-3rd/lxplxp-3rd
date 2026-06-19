import Link from 'next/link';

const FOOTER_LINKS = [
  { href: '/terms',   label: '이용약관' },
  { href: '/privacy', label: '개인정보처리방침' },
  { href: '/help',    label: '고객센터' },
  { href: '/contact', label: '문의하기' },
];

export function Footer() {
  return (
    <footer className="bg-surface-container-lowest w-full py-xl border-t border-outline-variant">
      <div className="flex flex-col md:flex-row justify-between items-center px-gutter max-w-container-max mx-auto gap-xl">

        <div className="flex flex-col items-center md:items-start gap-xs">
          <span className="text-headline-sm font-bold text-primary">LXP</span>
          <p className="text-body-sm text-on-surface-variant">
            © 2024 LXP Learning Platform. All rights reserved.
          </p>
        </div>

        <nav className="flex flex-wrap justify-center gap-lg">
          {FOOTER_LINKS.map(({ href, label }) => (
            <Link
              key={href}
              href={href}
              className="text-on-surface-variant hover:text-primary text-label-md transition-all duration-300"
            >
              {label}
            </Link>
          ))}
        </nav>

      </div>
    </footer>
  );
}
