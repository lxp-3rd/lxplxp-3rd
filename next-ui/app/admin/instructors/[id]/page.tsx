'use client';

import Link from 'next/link';
import { useAdminInstructorDetail } from './useAdminInstructorDetail';
import { AdminInstructorDetailView } from './components/AdminInstructorDetailView';

export default function InstructorDetailPage({ params }: { params: { id: string } }) {
  const { detail } = useAdminInstructorDetail(params.id);

  if (!detail) {
    return (
      <div className="max-w-[1000px] mx-auto px-gutter py-xl flex items-center justify-center">
        <div className="text-center">
          <h1 className="text-headline-lg font-headline-lg text-on-surface mb-md">강사를 찾을 수 없습니다</h1>
          <Link href="/admin/instructors" className="text-primary hover:underline text-label-md font-label-md">
            목록으로 돌아가기
          </Link>
        </div>
      </div>
    );
  }

  return <AdminInstructorDetailView detail={detail} />;
}
