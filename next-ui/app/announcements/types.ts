// Announcement 도메인 타입 정의 (API 응답 기준)
export type Announcement = {
  id: string;
  title: string;
  content: string;
  authorId: string;
  authorName: string;  // member join
  category: string;
  views: number;
  createdAt: string;
  updatedAt: string;
};
