// 담당: B (학습 홈 - 로드맵 대시보드)
import { TopNavBar } from '@/components/TopNavBar';
import { Footer } from '@/components/Footer';

export default function HomePage() {
  return (
    <>
      <TopNavBar />
      <main className="page-top min-h-screen">
        <div className="page-container py-xl">
          <h1 className="text-headline-lg text-on-surface">학습 홈</h1>
          {/* TODO: B팀 - 로드맵 대시보드 구현 예정 */}
        </div>
      </main>
      <Footer />
    </>
  );
}
