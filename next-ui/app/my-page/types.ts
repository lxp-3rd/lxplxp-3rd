export type MemberRole = 'LEARNER' | 'INSTRUCTOR' | 'ADMIN';
export type MemberStatus = 'ACTIVE' | 'WITHDRAWN' | 'SUSPENDED';

export type MyProfileResponse = {
  memberId: number;
  email: string;
  nickname: string;
  role: MemberRole;
  status: MemberStatus;
};

export type UpdateMyProfileRequest = {
  nickname: string;
};

export type ChangePasswordRequest = {
  currentPassword: string;
  newPassword: string;
  newPasswordConfirm: string;
};

export type WithdrawMemberRequest = {
  password: string;
};
