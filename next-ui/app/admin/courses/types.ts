// GET /api/admin/courses 응답 형태
export type AdminCourseStatus = 'PUBLIC' | 'HIDDEN' | 'CLOSED';
export type AdminCourseHiddenBy = 'INSTRUCTOR' | 'ADMIN';

export type AdminCourseResponse = {
  id: number;
  title: string;
  thumbnailUrl: string | null;
  instructorName: string | null;
  enrollmentCount: number;
  status: AdminCourseStatus;
  hiddenBy: AdminCourseHiddenBy | null;
};

// PATCH /api/courses/{id}/status 요청 형태
export type ChangeCourseStatusRequest = {
  status: AdminCourseStatus;
  changedBy: AdminCourseHiddenBy;
};