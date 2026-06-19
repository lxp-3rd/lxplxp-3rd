'use client';

import Link from 'next/link';
import { usePathname } from 'next/navigation';

const NAV_LINKS = [
  { href: '/courses',       label: '전체 강좌' },
  { href: '/roadmaps',      label: '로드맵' },
  { href: '/announcements', label: '공지사항' },
];

export function TopNavBar() {
  const pathname = usePathname();

  const isActive = (href: string) =>
    pathname === href || pathname.startsWith(href + '/');

  return (
    <header className="fixed top-0 w-full h-16 bg-surface-container-lowest border-b border-outline-variant z-50">
      <div className="flex justify-between items-center w-full px-gutter max-w-container-max mx-auto h-full">

        {/* 좌: 로고 + 네비게이션 */}
        <div className="flex items-center gap-xl">
          <Link
            href="/"
            className="text-headline-md font-headline-md font-bold text-primary"
          >
            LXP
          </Link>

          <nav className="hidden md:flex gap-lg">
            {NAV_LINKS.map(({ href, label }) => (
              <Link
                key={href}
                href={href}
                className={
                  isActive(href)
                    ? 'text-primary font-bold border-b-2 border-primary pb-1 text-label-md font-label-md transition-colors'
                    : 'text-on-surface-variant hover:text-primary text-label-md font-label-md transition-colors'
                }
              >
                {label}
              </Link>
            ))}
          </nav>
        </div>

        {/* 우: 검색 + 알림 + 프로필 */}
        <div className="flex items-center gap-md">

          {/* 검색 */}
          <div className="relative hidden md:block">
            <span className="material-symbols-outlined absolute left-sm top-1/2 -translate-y-1/2 text-on-surface-variant text-[20px]">
              search
            </span>
            <input
              type="text"
              placeholder="강의 검색하기"
              className="bg-surface-container-low border border-outline-variant rounded-lg pl-10 pr-md py-xs text-body-sm w-64 focus:border-primary focus:ring-0 focus:outline-none transition-all"
            />
          </div>

          {/* 알림 */}
          <button
            type="button"
            aria-label="알림"
            className="text-on-surface-variant hover:text-primary transition-colors p-xs flex items-center"
          >
            <span className="material-symbols-outlined">notifications</span>
          </button>

          {/* 프로필 */}
          <Link href="/my-page">
            <div className="w-8 h-8 rounded-full overflow-hidden bg-outline-variant flex items-center justify-center">
              <span className="material-symbols-outlined text-on-surface-variant text-[20px]">
                person
              </span>
            </div>
          </Link>
        </div>

      </div>
    </header>
  );
}
