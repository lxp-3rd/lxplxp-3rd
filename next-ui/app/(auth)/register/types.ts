export type SignUpRequest = {
  email: string;
  nickname: string;
  password: string;
  passwordConfirm: string;
};

export type SignUpResponse = {
  memberId: number;
  email: string;
  nickname: string;
  role: 'LEARNER' | 'INSTRUCTOR' | 'ADMIN';
  status: 'ACTIVE' | 'WITHDRAWN' | 'SUSPENDED';
};

export type AuthRequest = SignUpRequest;
