import type { Announcement } from '@/app/announcements/types';
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
  id: string;
  nickname: string;
  displayName: string;
  expertise: string;
  appliedAt: string;
  status: AdminInstructorApplicationStatus;
  rejectReason: string;
  bio: string;
};

export type AdminAnnouncement = Announcement;

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
