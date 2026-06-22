export const MOCK_MEMBERS = [
  { id: 'u1', name: '김지혜', email: 'member@lxp.com', role: 'member', joinedAt: '2024-03-15', courseCount: 3, status: 'ACTIVE' },
  { id: 'u7', name: '이준호', email: 'junho@lxp.com', role: 'member', joinedAt: '2024-03-12', courseCount: 1, status: 'ACTIVE' },
  { id: 'u8', name: '박지민', email: 'jimin@lxp.com', role: 'member', joinedAt: '2024-03-10', courseCount: 2, status: 'ACTIVE' },
  { id: 'u9', name: '최서연', email: 'sy@lxp.com', role: 'member', joinedAt: '2024-03-05', courseCount: 1, status: 'ACTIVE' },
  { id: 'u10', name: '정우진', email: 'wj@lxp.com', role: 'member', joinedAt: '2024-03-01', courseCount: 2, status: 'INACTIVE' },
];

export const getMemberById = (id: string) =>
  MOCK_MEMBERS.find((m) => m.id === id) ?? null;
