import type { Course } from '@/app/courses/types';
import type { Instructor } from '@/app/instructors/types';
import type { Roadmap } from '@/app/roadmaps/types';

export type AdminDashboardStat = {
  label: string;
  value: string;
  icon: string;
  tone: 'primary' | 'secondary' | 'tertiary' | 'danger';
  trendLabel: string;
  progress: number;
};

export type AdminMenuItem = {
  href: string;
  icon: string;
  title: string;
  description: string;
};

export type AdminCourse = Course & {
  hidden?: boolean;
};

export type AdminRoadmap = Roadmap & {
  hidden?: boolean;
};

export type AdminInstructor = Instructor;

export type AdminInstructorDetail = {
  instructor: Instructor;
  courses: Course[];
};

export type AdminInstructorApplicationStatus = 'PENDING' | 'APPROVED' | 'REJECTED';

export type AdminInstructorApplication = {
  id: number;
  memberId: number;
  instructorName: string;
  introduction: string;
  expertise: string;
  status: AdminInstructorApplicationStatus;
  rejectionReason: string | null;
  createdAt: string;
  resolvedAt: string | null;
};

export type AdminAnnouncement = {
  id: number;
  title: string;
  content: string;
  status: 'PUBLISH' | 'HIDDEN';
  createdAt: string;
  updatedAt: string;
};

export type AdminCourseEnrollment = {
  id: string;
  nickname: string;
  email: string;
  enrolledAt: string;
  progress: number;
};

export type AdminCourseEnrollmentDetail = {
  course: Course;
  enrollments: AdminCourseEnrollment[];
};
