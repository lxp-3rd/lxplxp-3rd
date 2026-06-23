'use client';

import Link from 'next/link';
import { usePathname, useRouter } from 'next/navigation';

const ADMIN_NAV_LINKS = [
  { href: '/admin',                         label: '대시보드',    exact: true  },
  { href: '/admin/members',                 label: '회원 관리',   exact: false },
  { href: '/admin/instructors',             label: '강사 관리',   exact: false },
  { href: '/admin/instructor-applications', label: '강사 신청',   exact: false },
  { href: '/admin/courses',                 label: '강좌 관리',   exact: false },
  { href: '/admin/roadmaps',                label: '로드맵 관리', exact: false },
  { href: '/admin/announcements',           label: '공지사항',    exact: false },
];

export function AdminNavBar() {
  const pathname = usePathname();
  const router = useRouter();

  const handleLogout = () => {
    router.push('/admin/login');
  };

  const isActive = (href: string, exact: boolean) =>
    exact ? pathname === href : pathname === href || pathname.startsWith(href + '/');

  return (
    <header className="fixed top-0 w-full h-16 bg-surface border-b border-surface-variant z-50 flex items-center px-gutter">
      <div className="flex justify-between items-center w-full max-w-container-max mx-auto h-full">

        {/* 좌: 로고 + 네비게이션 */}
        <div className="flex items-center gap-lg h-full">
          <Link
            href="/admin"
            className="text-headline-md font-bold text-primary whitespace-nowrap"
          >
            LXP 관리자
          </Link>

          <nav className="hidden md:flex gap-lg h-full">
            {ADMIN_NAV_LINKS.map(({ href, label, exact }) => (
              <Link
                key={href}
                href={href}
                className={
                  isActive(href, exact)
                    ? 'flex items-center h-16 text-primary border-b-2 border-primary font-label-md text-label-md transition-colors'
                    : 'flex items-center h-16 text-on-surface-variant hover:text-primary font-label-md text-label-md transition-colors'
                }
              >
                {label}
              </Link>
            ))}
          </nav>
        </div>

        {/* 우: 검색 + 알림 + 설정 + 프로필 */}
        <div className="flex items-center gap-md">

          {/* 검색 */}
          <div className="relative hidden sm:block">
            <span className="material-symbols-outlined absolute left-sm top-1/2 -translate-y-1/2 text-on-surface-variant text-[20px]">
              search
            </span>
            <input
              type="text"
              placeholder="리소스 검색..."
              className="pl-10 pr-md py-xs bg-surface-container-low border border-outline-variant rounded-lg text-body-sm w-56 focus:outline-none focus:border-primary transition-all"
            />
          </div>

          {/* 알림 */}
          <button
            type="button"
            aria-label="알림"
            className="relative p-xs text-on-surface-variant hover:bg-surface-variant rounded-full transition-colors"
          >
            <span className="material-symbols-outlined">notifications</span>
            <span className="absolute top-1 right-1 w-2 h-2 bg-primary rounded-full" />
          </button>

          {/* 설정 */}
          <button
            type="button"
            aria-label="설정"
            className="p-xs text-on-surface-variant hover:bg-surface-variant rounded-full transition-colors"
          >
            <span className="material-symbols-outlined">settings</span>
          </button>

          {/* 프로필 */}
          <div className="w-8 h-8 rounded-full bg-primary-container flex items-center justify-center text-on-primary-container font-bold overflow-hidden cursor-pointer">
            <span className="material-symbols-outlined text-[20px]">person</span>
          </div>

          {/* 로그아웃 */}
          <button
            type="button"
            onClick={handleLogout}
            aria-label="로그아웃"
            className="flex items-center gap-xs px-md py-xs border border-outline-variant rounded-lg text-on-surface-variant hover:text-error hover:border-error hover:bg-error-container/20 font-label-sm text-label-sm transition-colors"
          >
            <span className="material-symbols-outlined text-[18px]">logout</span>
            로그아웃
          </button>
        </div>

      </div>
    </header>
  );
}
