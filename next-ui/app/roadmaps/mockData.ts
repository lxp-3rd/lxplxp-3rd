export const MOCK_ROADMAPS = [
  {
    id: '1',
    title: '프론트엔드 마스터: 실무 중심의 로드맵',
    description: 'HTML/CSS 기초부터 React, TypeScript까지 체계적으로 학습하는 6개월 코스.',
    category: '웹개발',
    courseCount: 8,
    enrollmentCount: 1420,
    createdBy: 'u2',
    creatorName: '이하연',
    progress: 35,
    isEnrolled: true,
    thumbnail: 'https://www.gstatic.com/labs-code/stitch/stitch-placeholder-300x300.svg',
  },
  {
    id: '2',
    title: 'AI/ML 엔지니어 입문 로드맵',
    description: 'Python 기초부터 딥러닝 모델 배포까지, AI 엔지니어 취업을 위한 완성형 로드맵.',
    category: 'AI/ML',
    courseCount: 10,
    enrollmentCount: 2380,
    createdBy: 'u4',
    creatorName: '박민준',
    progress: 0,
    isEnrolled: false,
    thumbnail: 'https://www.gstatic.com/labs-code/stitch/stitch-placeholder-300x300.svg',
  },
  {
    id: '3',
    title: 'UX 디자이너 커리어 로드맵',
    description: '리서치, 와이어프레임, 프로토타입, 포트폴리오 제작까지 UX 전 과정.',
    category: 'UI/UX',
    courseCount: 6,
    enrollmentCount: 980,
    createdBy: 'u2',
    creatorName: '이하연',
    progress: 75,
    isEnrolled: true,
    thumbnail: 'https://www.gstatic.com/labs-code/stitch/stitch-placeholder-300x300.svg',
  },
  {
    id: '4',
    title: '데이터 분석가 양성 로드맵',
    description: 'Excel, SQL, Python, 시각화 도구까지 데이터 분석 업무 전반을 커버.',
    category: '데이터분석',
    courseCount: 7,
    enrollmentCount: 1750,
    createdBy: 'u6',
    creatorName: '정수아',
    progress: 0,
    isEnrolled: false,
    thumbnail: 'https://www.gstatic.com/labs-code/stitch/stitch-placeholder-300x300.svg',
  },
];

export const getRoadmapById = (id: string) =>
  MOCK_ROADMAPS.find((r) => r.id === id) ?? null;

export const getMyRoadmaps = () =>
  MOCK_ROADMAPS.filter((r) => r.isEnrolled);
