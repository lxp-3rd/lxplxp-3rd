'use client';

import Link from 'next/link';
import { usePathname, useRouter } from 'next/navigation';
import { useAuth } from '@/lib/AuthContext';
import { Role } from '@/lib/mockAuth';

const NAV_LINKS: Partial<Record<Role, { href: string; label: string; exact?: boolean }[]>> = {
  member: [
    { href: '/courses',       label: '전체 강좌', exact: true },
    { href: '/roadmaps',      label: '로드맵' },
    { href: '/enrollments',   label: '내 강의' },
    { href: '/announcements', label: '공지사항' },
  ],
  instructor: [
    { href: '/courses',          label: '전체 강좌', exact: true },
    { href: '/courses/manage',   label: '강좌 관리' },
    { href: '/instructor/stats', label: '내 강의 통계' },
    { href: '/roadmaps',         label: '로드맵' },
    { href: '/announcements',    label: '공지사항' },
  ],
};

const GUEST_LINKS = [
  { href: '/courses',       label: '전체 강좌', exact: true },
  { href: '/roadmaps',      label: '로드맵' },
  { href: '/announcements', label: '공지사항' },
];

export function TopNavBar() {
  const pathname = usePathname();
  const { user, role, isLoggedIn, logout } = useAuth();
  const router = useRouter();

  const links = (role && NAV_LINKS[role]) ?? GUEST_LINKS;

  const isActive = (href: string, exact?: boolean) =>
    exact ? pathname === href : pathname === href || pathname.startsWith(href + '/');

  return (
    <header className="fixed top-0 w-full h-16 bg-surface-container-lowest border-b border-outline-variant z-50">
      <div className="flex justify-between items-center w-full px-gutter max-w-container-max mx-auto h-full">

        {/* 좌: 로고 + 네비게이션 */}
        <div className="flex items-center gap-xl">
          <Link
            href={role === 'admin' ? '/admin' : role === 'instructor' ? '/courses/manage' : '/'}
            className="text-headline-md font-headline-md font-bold text-primary"
          >
            LXP
          </Link>
          <nav className="hidden md:flex gap-lg">
            {links.map(({ href, label, exact }) => (
              <Link
                key={href + label}
                href={href}
                className={
                  isActive(href, exact)
                    ? 'text-primary font-bold border-b-2 border-primary pb-1 text-label-md font-label-md transition-colors'
                    : 'text-on-surface-variant hover:text-primary text-label-md font-label-md transition-colors'
                }
              >
                {label}
              </Link>
            ))}
          </nav>
        </div>

        {/* 우: 검색 + 역할 뱃지 + 프로필 + 로그아웃 */}
        <div className="flex items-center gap-md">

          {/* 검색 (관리자 제외) */}
          {role !== 'admin' && (
            <div className="hidden md:flex items-center gap-xs bg-surface-container rounded-full px-md py-xs border border-outline-variant">
              <span className="material-symbols-outlined text-on-surface-variant text-[18px]">search</span>
              <input
                className="bg-transparent outline-none text-body-md font-body-md text-on-surface placeholder:text-on-surface-variant w-40"
                placeholder="강좌 검색..."
                onKeyDown={(e) => {
                  if (e.key === 'Enter') router.push('/courses');
                }}
              />
            </div>
          )}

          {isLoggedIn ? (
            <>
              {/* 역할 뱃지 */}
              {role && (
                <span className="hidden md:inline-flex items-center px-sm py-xs rounded-full text-label-sm font-label-sm bg-secondary-container text-on-secondary-container">
                  {role === 'admin' ? '관리자' : role === 'instructor' ? '강사' : '회원'}
                </span>
              )}
              {/* 프로필 */}
              <Link href={role === 'admin' ? '/admin' : `/instructors/${user?.id}`} className="w-8 h-8 rounded-full bg-primary-container flex items-center justify-center text-on-primary-container text-label-sm font-label-sm">
                {user?.name?.charAt(0) ?? 'U'}
              </Link>
              {/* 로그아웃 */}
              <button
                onClick={() => { logout(); router.push('/login'); }}
                className="p-xs rounded-full hover:bg-surface-container transition-colors text-on-surface-variant"
                aria-label="로그아웃"
              >
                <span className="material-symbols-outlined text-[20px]">logout</span>
              </button>
            </>
          ) : (
            <Link
              href="/login"
              className="bg-primary text-on-primary px-md py-xs rounded-lg text-label-md font-label-md hover:opacity-90 transition-opacity"
            >
              로그인
            </Link>
          )}
        </div>
      </div>
    </header>
  );
}
