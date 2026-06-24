'use client';

import Link from 'next/link';
import { AdminCourseEnrollmentView } from './components/AdminCourseEnrollmentView';
import { useAdminCourseEnrollments } from './useAdminCourseEnrollments';

export default function AdminCourseEnrollmentsPage({ params }: { params: { id: string } }) {
  const { detail } = useAdminCourseEnrollments(params.id);

  if (!detail) {
    return (
      <div className="flex-grow w-full max-w-[1280px] mx-auto px-margin-mobile md:px-margin-desktop py-xl">
        <div className="text-center">
          <h1 className="text-headline-lg font-headline-lg text-on-surface mb-md">강좌를 찾을 수 없습니다</h1>
          <Link href="/admin/courses" className="text-primary hover:underline text-label-md font-label-md">
            강좌 관리로 돌아가기
          </Link>
        </div>
      </div>
    );
  }

  return <AdminCourseEnrollmentView detail={detail} />;
}
