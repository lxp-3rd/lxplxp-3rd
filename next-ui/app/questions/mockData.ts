export interface Answer {
  id: string;
  authorName: string;
  role: 'instructor' | 'student';
  content: string;
  createdAt: string;
  avatarSrc?: string;
}

export interface Question {
  id: string;
  courseId: string;
  courseTitle: string;
  title: string;
  content: string;
  authorName: string;
  authorId: string;
  createdAt: string;
  answerCount: number;
  isAnswered: boolean;
  tags: string[];
  answers: Answer[];
}

export const MOCK_QUESTIONS: Question[] = [
  {
    id: 'q1',
    courseId: '3',
    courseTitle: '프론트엔드 마스터: React & TypeScript',
    title: 'React Server Components의 동작 원리가 궁금합니다.',
    content:
      '안녕하세요, 강의 잘 듣고 있습니다 :)\n\nReact 18에서 도입된 Server Components의 동작 원리를 좀 더 깊게 이해하고 싶습니다. 기존의 SSR(Server Side Rendering)과 Server Components가 구체적으로 어떤 점이 다른가요? 특히 번들 크기와 Server Components가 완전히 다른 패러다임인지 궁금합니다.',
    authorName: '박민준',
    authorId: 'u1',
    createdAt: '2일 전',
    answerCount: 1,
    isAnswered: true,
    tags: ['Next.js', 'React'],
    answers: [
      {
        id: 'a1',
        authorName: '이서연',
        role: 'instructor',
        createdAt: '1일 전',
        avatarSrc: 'https://lh3.googleusercontent.com/aida-public/AB6AXuB5ZHz0kUwUvNwbInMtKkHfVWB6Ig_-YH-3TqJV4uGMMrCftPFfiD2afbP4cr8B3dPILzpBKUu0hmDHodLaoDQoVB7TfOPgavXxGew1YtC4jXcMzk9TShaDvbm-halqDurHe2PIPSWQ8_LtqVORIYHw-j0YJdtMOJcZBI_t4t7kqtJ4RuRRe9AX5Y98sYgNIU5qB_GTcwnmceFWXpXjU11eYka9_blVhHlK4GVqEqY_ZknID2hNEqbZEf4vm71JEJu0G6Roj5jJv_7N',
        content:
          '좋은 질문입니다! "서버에서 렌더링한다"는 공통점이 있지만 핵심 차이가 있습니다.\n\n• SSR은 매 요청마다 전체 컴포넌트 트리를 서버에서 HTML로 렌더링한 뒤 클라이언트로 전송하고, 이후 JS 번들로 하이드레이션(hydration)이 일어납니다.\n• RSC(React Server Components)는 특정 컴포넌트를 아예 클라이언트 번들에서 제외합니다. 서버에서만 실행되며 직렬화된 React 트리로 전달되기 때문에 해당 컴포넌트의 JS가 브라우저로 내려가지 않습니다.\n\n쉽게 말해, SSR은 "서버에서 HTML을 만들어 보내지만 JS는 여전히 클라이언트로 가는" 방식이고, RSC는 "특정 컴포넌트의 JS 자체를 클라이언트에 보내지 않는" 방식입니다. Next.js 13+ App Router에서 기본값으로 Server Component를 사용하는 이유가 바로 번들 최적화 때문입니다.',
      },
    ],
  },
  {
    id: 'q2',
    courseId: '3',
    courseTitle: '프론트엔드 마스터: React & TypeScript',
    title: 'Tailwind CSS v4 마이그레이션 방법이 궁금합니다.',
    content: 'v3에서 v4로 마이그레이션할 때 주의해야 할 breaking change가 무엇인지 알고 싶습니다.',
    authorName: '이준호',
    authorId: 'u7',
    createdAt: '3일 전',
    answerCount: 0,
    isAnswered: false,
    tags: ['Tailwind', 'CSS'],
    answers: [],
  },
  {
    id: 'q3',
    courseId: '2',
    courseTitle: 'AI 프로그래밍 기초',
    title: 'Figma 디자인토큰을 코드와 동기화하는 방법 질문이 있습니다.',
    content: 'Figma Variables와 CSS 커스텀 프로퍼티를 자동으로 동기화하는 방법을 찾고 있습니다.',
    authorName: '박지민',
    authorId: 'u8',
    createdAt: '5일 전',
    answerCount: 0,
    isAnswered: false,
    tags: ['Figma', '디자인시스템'],
    answers: [],
  },
];

export const getQuestionById = (id: string) =>
  MOCK_QUESTIONS.find((q) => q.id === id) ?? null;