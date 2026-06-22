'use client';

import { useAuth } from '@/lib/AuthContext';
import { ROLE_LABEL, Role } from '@/lib/mockAuth';

const QUICK_ROLES: { role: Role; icon: string; desc: string }[] = [
  { role: 'member',     icon: 'school',               desc: '강좌 수강, 로드맵 참여' },
  { role: 'instructor', icon: 'co_present',            desc: '강좌 관리, 강의 편집' },
  { role: 'admin',      icon: 'admin_panel_settings',  desc: '전체 관리자 패널' },
];

export default function LoginPage() {
  const { login } = useAuth();

  return (
    <>
      <div className="fixed inset-0 pointer-events-none overflow-hidden z-0">
        <div className="absolute -top-24 -left-24 w-96 h-96 rounded-full bg-primary-container/10 blur-3xl"></div>
        <div className="absolute -bottom-24 -right-24 w-96 h-96 rounded-full bg-secondary-container/20 blur-3xl"></div>
      </div>

      <main className="flex-grow flex items-center justify-center px-gutter relative z-10 py-xl">
        <div className="w-full max-w-[440px]">

          <div className="text-center mb-xl">
            <h1 className="text-headline-lg font-headline-lg text-primary tracking-tight">LXP</h1>
            <p className="text-body-md font-body-md text-on-surface-variant mt-xs">미래를 위한 스마트 러닝 플랫폼</p>
          </div>

          <div className="bg-surface-container-lowest border border-outline-variant rounded-xl p-xl shadow-sm">
            <h2 className="text-headline-md font-headline-md text-on-surface mb-lg">로그인</h2>
            <form className="space-y-md">
              <div className="space-y-xs">
                <label className="text-label-md font-label-md text-on-surface-variant" htmlFor="email">이메일 주소</label>
                <div className="relative flex items-center border border-outline-variant rounded-lg bg-surface px-sm h-12">
                  <span className="material-symbols-outlined text-on-surface-variant mr-xs">mail</span>
                  <input
                    className="w-full bg-transparent border-none focus:ring-0 p-0 text-body-md font-body-md text-on-surface placeholder:text-outline"
                    id="email" name="email" placeholder="example@lxp.com" type="email"
                  />
                </div>
              </div>

              <div className="space-y-xs">
                <label className="text-label-md font-label-md text-on-surface-variant" htmlFor="password">비밀번호</label>
                <div className="relative flex items-center border border-outline-variant rounded-lg bg-surface px-sm h-12">
                  <span className="material-symbols-outlined text-on-surface-variant mr-xs">lock</span>
                  <input
                    className="w-full bg-transparent border-none focus:ring-0 p-0 text-body-md font-body-md text-on-surface placeholder:text-outline"
                    id="password" name="password" placeholder="••••••••" type="password"
                  />
                  <button className="text-on-surface-variant hover:text-on-surface transition-colors" type="button">
                    <span className="material-symbols-outlined">visibility</span>
                  </button>
                </div>
              </div>

              <button
                type="button"
                className="w-full bg-primary hover:opacity-90 text-on-primary font-label-md text-label-md py-md rounded-lg shadow-sm transition-all active:scale-[0.98] mt-lg"
              >
                로그인하기
              </button>
            </form>

            <div className="mt-lg pt-lg border-t border-outline-variant text-center">
              <p className="text-body-md font-body-md text-on-surface-variant">
                아직 계정이 없으신가요?{' '}
                <a className="text-primary font-bold hover:underline" href="/register">지금 가입하기</a>
              </p>
            </div>
          </div>

          {/* 개발용 빠른 접속 */}
          <div className="mt-lg bg-surface-container-low border border-dashed border-outline-variant rounded-xl p-lg">
            <div className="flex items-center gap-xs mb-md">
              <span className="material-symbols-outlined text-[16px] text-on-surface-variant">code</span>
              <p className="text-label-sm font-label-sm text-on-surface-variant uppercase tracking-wider">개발용 빠른 접속</p>
            </div>
            <div className="grid grid-cols-3 gap-sm">
              {QUICK_ROLES.map(({ role, icon, desc }) => (
                <button
                  key={role}
                  type="button"
                  onClick={() => login(role)}
                  className="flex flex-col items-center gap-xs p-md rounded-lg border border-outline-variant bg-surface-container-lowest hover:border-primary hover:bg-primary-fixed transition-all group text-center"
                >
                  <span
                    className="material-symbols-outlined text-[28px] text-on-surface-variant group-hover:text-primary transition-colors"
                    style={{ fontVariationSettings: "'FILL' 1" }}
                  >
                    {icon}
                  </span>
                  <span className="text-label-md font-label-md text-on-surface group-hover:text-primary transition-colors">
                    {ROLE_LABEL[role]}
                  </span>
                  <span className="text-[10px] text-on-surface-variant leading-tight">{desc}</span>
                </button>
              ))}
            </div>
          </div>

        </div>
      </main>
    </>
  );
}
