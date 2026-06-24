export type AdminMemberRole = 'LEARNER' | 'INSTRUCTOR' | 'ADMIN';
export type AdminMemberStatus = 'ACTIVE' | 'WITHDRAWN' | 'SUSPENDED';

export type AdminMemberResponse = {
  memberId: number;
  email: string;
  nickname: string;
  role: AdminMemberRole;
  status: AdminMemberStatus;
  createdAt: string;
  updatedAt: string;
};

export type ChangeMemberStatusRequest = {
  status: AdminMemberStatus;
};
