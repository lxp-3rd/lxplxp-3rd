'use client';

import { TopNavBar } from '@/components/TopNavBar';
import { Footer } from '@/components/Footer';
import { useMyPage } from './useMyPage';

const ROLE_LABEL = {
  LEARNER: '학습자',
  INSTRUCTOR: '강사',
  ADMIN: '관리자',
};

const STATUS_LABEL = {
  ACTIVE: '활성',
  WITHDRAWN: '탈퇴',
  SUSPENDED: '정지',
};

export default function MyPage() {
  const {
    profile,
    nickname,
    setNickname,
    currentPassword,
    setCurrentPassword,
    newPassword,
    setNewPassword,
    newPasswordConfirm,
    setNewPasswordConfirm,
    withdrawPassword,
    setWithdrawPassword,
    isLoading,
    isSavingProfile,
    isChangingPassword,
    isWithdrawing,
    message,
    errorMessage,
    handleProfileSubmit,
    handlePasswordSubmit,
    handleWithdrawSubmit,
  } = useMyPage();

  return (
    <>
      <TopNavBar />
      <main className="pt-16 min-h-screen bg-background">
        <div className="pt-xxl mt-16 pb-xxl px-gutter max-w-container-max mx-auto">
          <div className="grid grid-cols-12 gap-gutter">
            <aside className="col-span-12 md:col-span-3">
              <div className="bg-surface-container-lowest border border-outline-variant rounded-xl p-lg space-y-md">
                <div className="flex items-center gap-md pb-md border-b border-outline-variant">
                  <div className="w-12 h-12 rounded-full bg-primary-container/10 flex items-center justify-center text-primary">
                    <span className="material-symbols-outlined text-headline-md">person</span>
                  </div>
                  <div>
                    <h2 className="text-label-md font-label-md text-on-surface">
                      {profile?.nickname ?? '내 정보'}
                    </h2>
                    <p className="text-body-sm font-body-sm text-on-surface-variant">
                      {profile ? ROLE_LABEL[profile.role] : '프로필'}
                    </p>
                  </div>
                </div>
                <nav className="flex flex-col gap-xs">
                  <a className="flex items-center gap-sm p-sm rounded-lg bg-primary text-white font-bold" href="#profile">
                    <span className="material-symbols-outlined text-md">account_circle</span>
                    <span className="text-label-md font-label-md">프로필 정보</span>
                  </a>
                  <a className="flex items-center gap-sm p-sm rounded-lg text-on-surface-variant hover:bg-surface-container-low transition-all" href="#password">
                    <span className="material-symbols-outlined text-md">lock</span>
                    <span className="text-label-md font-label-md">비밀번호 변경</span>
                  </a>
                  <a className="flex items-center gap-sm p-sm rounded-lg text-error hover:bg-error-container/20 transition-all" href="#withdraw">
                    <span className="material-symbols-outlined text-md">person_remove</span>
                    <span className="text-label-md font-label-md">회원 탈퇴</span>
                  </a>
                </nav>
              </div>
            </aside>

            <section className="col-span-12 md:col-span-9 space-y-lg">
              <div className="bg-surface-container-lowest border border-outline-variant rounded-xl overflow-hidden">
                <div className="h-32 bg-primary relative">
                  <div className="absolute inset-0 opacity-10" style={{ backgroundImage: 'radial-gradient(#ffffff 1px, transparent 1px)', backgroundSize: '20px 20px' }} />
                </div>

                <div className="px-xl pb-xl">
                  <div className="relative -mt-12 mb-lg flex items-end gap-lg">
                    <div className="w-24 h-24 rounded-xl bg-surface-container-lowest p-1 shadow-sm">
                      <div className="w-full h-full rounded-lg bg-primary-container text-primary flex items-center justify-center border border-outline-variant text-headline-md font-bold">
                        {profile?.nickname?.charAt(0) ?? 'U'}
                      </div>
                    </div>
                    <div className="pb-base">
                      <h1 className="text-headline-md font-headline-md text-on-surface">프로필 정보</h1>
                      <p className="text-body-sm font-body-sm text-on-surface-variant">LXP 계정 정보를 관리합니다.</p>
                    </div>
                  </div>

                  {isLoading ? (
                    <p className="text-body-md font-body-md text-on-surface-variant">내 정보를 불러오는 중입니다.</p>
                  ) : (
                    <div id="profile" className="grid grid-cols-1 md:grid-cols-2 gap-xl border-t border-outline-variant pt-xl">
                      <div className="space-y-lg">
                        <div>
                          <label className="block text-label-md font-label-md text-on-surface-variant mb-xs">이메일 주소</label>
                          <div className="bg-surface-container-lowest border border-outline-variant rounded-lg p-md flex items-center justify-between">
                            <span className="text-body-md font-body-md text-on-surface">{profile?.email}</span>
                            <span className="material-symbols-outlined text-on-surface-variant scale-75">lock</span>
                          </div>
                          <p className="mt-xs text-body-sm font-body-sm text-on-surface-variant opacity-70">로그인 계정으로 사용되는 이메일입니다.</p>
                        </div>
                        <div>
                          <label className="block text-label-md font-label-md text-on-surface-variant mb-xs">계정 상태</label>
                          <div className="bg-surface-container-lowest border border-outline-variant rounded-lg p-md flex items-center justify-between">
                            <span className="text-body-md font-body-md text-on-surface">
                              {profile ? STATUS_LABEL[profile.status] : '-'}
                            </span>
                            <span className="text-body-sm font-body-sm text-on-surface-variant">
                              {profile ? ROLE_LABEL[profile.role] : '-'}
                            </span>
                          </div>
                        </div>
                      </div>

                      <form onSubmit={handleProfileSubmit} className="bg-surface-container-low rounded-xl p-lg border border-outline-variant">
                        <h3 className="text-label-md font-label-md text-primary mb-md flex items-center gap-xs">
                          <span className="material-symbols-outlined text-lg">edit</span>
                          정보 수정
                        </h3>
                        <div className="space-y-lg">
                          <div className="group">
                            <label className="block text-label-md font-label-md text-on-surface-variant mb-xs" htmlFor="nickname">닉네임</label>
                            <input
                              className="w-full bg-surface-container-lowest border border-outline-variant rounded-lg p-md text-body-md font-body-md custom-input-focus transition-all"
                              id="nickname"
                              type="text"
                              maxLength={30}
                              value={nickname}
                              onChange={(event) => setNickname(event.target.value)}
                            />
                            <p className="mt-xs text-body-sm font-body-sm text-on-surface-variant">커뮤니티 및 대시보드에 표시되는 이름입니다.</p>
                          </div>
                          <div className="flex items-center justify-end gap-md pt-md">
                            <button
                              type="submit"
                              disabled={isSavingProfile}
                              className="bg-primary hover:bg-on-primary-container disabled:opacity-60 text-white px-xl py-xs rounded-lg text-label-md font-label-md transition-all shadow-sm active:scale-[0.98]"
                            >
                              {isSavingProfile ? '저장 중...' : '변경사항 저장'}
                            </button>
                          </div>
                        </div>
                      </form>
                    </div>
                  )}
                </div>
              </div>

              <form id="password" onSubmit={handlePasswordSubmit} className="bg-surface-container-lowest border border-outline-variant rounded-xl p-xl space-y-lg">
                <div>
                  <h2 className="text-headline-sm font-headline-md text-on-surface">비밀번호 변경</h2>
                  <p className="text-body-sm font-body-sm text-on-surface-variant mt-xs">현재 비밀번호 확인 후 새 비밀번호로 변경합니다.</p>
                </div>
                <div className="grid grid-cols-1 md:grid-cols-3 gap-md">
                  <input className="border border-outline-variant rounded-lg p-md" type="password" aria-label="현재 비밀번호" placeholder="현재 비밀번호" value={currentPassword} onChange={(event) => setCurrentPassword(event.target.value)} required />
                  <input className="border border-outline-variant rounded-lg p-md" type="password" aria-label="새 비밀번호" placeholder="새 비밀번호" value={newPassword} onChange={(event) => setNewPassword(event.target.value)} required />
                  <input className="border border-outline-variant rounded-lg p-md" type="password" aria-label="새 비밀번호 확인" placeholder="새 비밀번호 확인" value={newPasswordConfirm} onChange={(event) => setNewPasswordConfirm(event.target.value)} required />
                </div>
                <button type="submit" disabled={isChangingPassword} className="bg-primary text-white px-xl py-sm rounded-lg disabled:opacity-60">
                  {isChangingPassword ? '변경 중...' : '비밀번호 변경'}
                </button>
              </form>

              <form id="withdraw" onSubmit={handleWithdrawSubmit} className="bg-surface-container-lowest border border-error/40 rounded-xl p-xl space-y-lg">
                <div>
                  <h2 className="text-headline-sm font-headline-md text-error">회원 탈퇴</h2>
                  <p className="text-body-sm font-body-sm text-on-surface-variant mt-xs">비밀번호 확인 후 계정을 탈퇴 상태로 변경합니다.</p>
                </div>
                <div className="flex flex-col md:flex-row gap-md">
                  <input className="flex-1 border border-outline-variant rounded-lg p-md" type="password" aria-label="회원 탈퇴 비밀번호 확인" placeholder="비밀번호 확인" value={withdrawPassword} onChange={(event) => setWithdrawPassword(event.target.value)} required />
                  <button type="submit" disabled={isWithdrawing} className="border border-error text-error px-xl py-sm rounded-lg hover:bg-error-container/20 disabled:opacity-60">
                    {isWithdrawing ? '처리 중...' : '회원 탈퇴'}
                  </button>
                </div>
              </form>

              {(message || errorMessage) && (
                <p role="alert" className={`px-lg py-sm rounded-lg text-label-md font-label-md ${errorMessage ? 'bg-error-container/30 text-error' : 'bg-secondary-container text-on-secondary-container'}`}>
                  {errorMessage || message}
                </p>
              )}
            </section>
          </div>
        </div>
      </main>
      <Footer />
    </>
  );
}
