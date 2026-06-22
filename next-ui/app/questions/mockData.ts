export const MOCK_QUESTIONS = [
  {
    id: 'q1',
    courseId: '1',
    courseTitle: '데이터 중심의 UI/UX 디자인 실무 입문',
    title: 'UX 리서치 방법론에서 정량/정성 조사의 차이가 궁금합니다',
    content: '강의에서 두 방법론을 설명해 주셨는데 실무에서 어떤 기준으로 선택하나요?',
    authorName: '김지혜',
    authorId: 'u1',
    createdAt: '2026-06-20',
    answerCount: 2,
    isAnswered: true,
    tags: ['리서치', 'UX'],
  },
  {
    id: 'q2',
    courseId: '2',
    courseTitle: 'AI 프로그래밍 기초',
    title: 'Python 가상환경 설정 중 오류가 발생합니다',
    content: 'venv 생성 후 activate 명령어 실행 시 "cannot be loaded" 오류가 납니다.',
    authorName: '이준호',
    authorId: 'u7',
    createdAt: '2026-06-21',
    answerCount: 0,
    isAnswered: false,
    tags: ['Python', '환경설정'],
  },
  {
    id: 'q3',
    courseId: '3',
    courseTitle: '프론트엔드 마스터: React & TypeScript',
    title: 'useEffect 의존성 배열 관련 질문입니다',
    content: 'ESLint가 의존성을 추가하라고 경고를 주는데, 추가하면 무한 루프가 생깁니다.',
    authorName: '박지민',
    authorId: 'u8',
    createdAt: '2026-06-22',
    answerCount: 1,
    isAnswered: true,
    tags: ['React', 'hooks'],
  },
];

export const getQuestionById = (id: string) =>
  MOCK_QUESTIONS.find((q) => q.id === id) ?? null;
