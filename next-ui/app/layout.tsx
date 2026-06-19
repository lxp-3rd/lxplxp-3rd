import type { Metadata } from 'next';
import './globals.css';

export const metadata: Metadata = {
  title: { default: 'LXP', template: '%s | LXP' },
  description: 'Learning Experience Platform',
};

export default function RootLayout({ children }: { children: React.ReactNode }) {
  return (
    <html lang="ko">
      <body>{children}</body>
    </html>
  );
}
