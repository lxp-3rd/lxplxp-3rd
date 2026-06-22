export interface Answer {
  id: string;
  authorName: string;
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
  isAnswered: boolean;
  tags: string[];
  answer: Answer | null;
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
    isAnswered: true,
    tags: ['Next.js', 'React'],
    answer: {
      id: 'a1',
      authorName: '이서연',
      createdAt: '1일 전',
      avatarSrc: 'https://lh3.googleusercontent.com/aida-public/AB6AXuB5ZHz0kUwUvNwbInMtKkHfVWB6Ig_-YH-3TqJV4uGMMrCftPFfiD2afbP4cr8B3dPILzpBKUu0hmDHodLaoDQoVB7TfOPgavXxGew1YtC4jXcMzk9TShaDvbm-halqDurHe2PIPSWQ8_LtqVORIYHw-j0YJdtMOJcZBI_t4t7kqtJ4RuRRe9AX5Y98sYgNIU5qB_GTcwnmceFWXpXjU11eYka9_blVhHlK4GVqEqY_ZknID2hNEqbZEf4vm71JEJu0G6Roj5jJv_7N',
      content:
        '좋은 질문입니다! "서버에서 렌더링한다"는 공통점이 있지만 핵심 차이가 있습니다.\n\n• SSR은 매 요청마다 전체 컴포넌트 트리를 서버에서 HTML로 렌더링한 뒤 클라이언트로 전송하고, 이후 JS 번들로 하이드레이션(hydration)이 일어납니다.\n• RSC(React Server Components)는 특정 컴포넌트를 아예 클라이언트 번들에서 제외합니다. 서버에서만 실행되며 직렬화된 React 트리로 전달되기 때문에 해당 컴포넌트의 JS가 브라우저로 내려가지 않습니다.\n\n쉽게 말해, SSR은 "서버에서 HTML을 만들어 보내지만 JS는 여전히 클라이언트로 가는" 방식이고, RSC는 "특정 컴포넌트의 JS 자체를 클라이언트에 보내지 않는" 방식입니다. Next.js 13+ App Router에서 기본값으로 Server Component를 사용하는 이유가 바로 번들 최적화 때문입니다.',
    },
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
    isAnswered: false,
    tags: ['Tailwind', 'CSS'],
    answer: null,
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
    isAnswered: false,
    tags: ['Figma', '디자인시스템'],
    answer: null,
  },
  {
    id: 'q4',
    courseId: '1',
    courseTitle: 'Python으로 시작하는 데이터 분석',
    title: 'pandas DataFrame에서 NaN 처리할 때 fillna vs dropna 어떤 걸 써야 하나요?',
    content:
      '안녕하세요! 데이터 전처리 강의에서 결측값 처리를 배우고 있는데요.\n\n실습 데이터에서 NaN이 꽤 많이 나오는데, fillna()로 특정 값을 채우는 게 나은지 아니면 dropna()로 행 자체를 제거하는 게 나은지 판단 기준이 헷갈립니다. 분석 목적에 따라 다르다는 건 알겠는데, 실무에서는 어떤 기준으로 결정하시나요?',
    authorName: '김수현',
    authorId: 'u2',
    createdAt: '1일 전',
    isAnswered: true,
    tags: ['pandas', 'Python'],
    answer: {
      id: 'a4',
      authorName: '최재원',
      createdAt: '1일 전',
      avatarSrc: 'https://lh3.googleusercontent.com/aida-public/AB6AXuCpW1lxL5W-xjUFr-8xb4JsEhZGEOr0SX8aVfbR6tUknVYdPQmBqkdmSbYjCJPy4sj3HYSwf3ILqHXBujr8tO4B7CGb5L0kgAp9YOAQ2M5-hhDxAtQ9UD0BfqmhZEaAQWpqGBx1E7V-b-y-JwNjMRMpLj8Vqnz7Ss7qNbHB4g5H48LqW9r_bBQFQpkCKMvPi6D1bRr5lGBYcZWP2Wt3nEj8YjsLbJe4GGqHE5OXqmz0fJL9-HJkVY04cH0LoFkr8SiPMthPkGRD',
      content:
        '아주 좋은 질문입니다! 실무 기준으로 설명해 드릴게요.\n\n• dropna() 권장 상황: 결측값이 전체 데이터의 5% 미만이고, 해당 행/열이 분석에 꼭 필요하지 않을 때. 또는 결측이 무작위(MCAR)가 아닌 특정 패턴이 있을 때 오히려 제거하는 것이 모델 편향을 줄입니다.\n• fillna() 권장 상황: 결측값이 많아 dropna() 시 데이터가 너무 줄어들 때. 수치형 컬럼은 중앙값(median), 범주형은 최빈값(mode)으로 채우는 것이 일반적입니다. 시계열 데이터라면 ffill()/bfill()도 많이 씁니다.\n\n핵심은 "왜 결측이 발생했는가"를 먼저 파악하는 것입니다. 데이터를 이해하지 않고 기계적으로 채우거나 버리면 분석 결과가 왜곡될 수 있어요.',
    },
  },
  {
    id: 'q5',
    courseId: '1',
    courseTitle: 'Python으로 시작하는 데이터 분석',
    title: 'matplotlib 그래프가 Jupyter에서 출력이 안 됩니다.',
    content:
      'plt.show()를 호출해도 그래프가 나타나지 않고 아무 출력도 없습니다. %matplotlib inline 설정을 했는데도 동일합니다. 혹시 원인이 무엇인지 알 수 있을까요?',
    authorName: '오태양',
    authorId: 'u3',
    createdAt: '4일 전',
    isAnswered: true,
    tags: ['matplotlib', 'Jupyter'],
    answer: {
      id: 'a5',
      authorName: '최재원',
      createdAt: '3일 전',
      avatarSrc: 'https://lh3.googleusercontent.com/aida-public/AB6AXuCpW1lxL5W-xjUFr-8xb4JsEhZGEOr0SX8aVfbR6tUknVYdPQmBqkdmSbYjCJPy4sj3HYSwf3ILqHXBujr8tO4B7CGb5L0kgAp9YOAQ2M5-hhDxAtQ9UD0BfqmhZEaAQWpqGBx1E7V-b-y-JwNjMRMpLj8Vqnz7Ss7qNbHB4g5H48LqW9r_bBQFQpkCKMvPi6D1bRr5lGBYcZWP2Wt3nEj8YjsLbJe4GGqHE5OXqmz0fJL9-HJkVY04cH0LoFkr8SiPMthPkGRD',
      content:
        '몇 가지 체크리스트를 드릴게요.\n\n1. %matplotlib inline 은 반드시 import matplotlib.pyplot as plt 보다 앞 셀에서 실행되어야 합니다.\n2. VS Code에서 Jupyter를 사용 중이라면 inline 대신 %matplotlib widget 을 써보세요.\n3. 가상환경이 다를 경우 ipykernel이 제대로 설치되지 않은 경우도 있습니다. pip install ipykernel 후 커널을 다시 선택해 보세요.',
    },
  },
  {
    id: 'q6',
    courseId: '3',
    courseTitle: '프론트엔드 마스터: React & TypeScript',
    title: 'TypeScript에서 unknown vs any 차이가 헷갈립니다.',
    content:
      'any를 쓰면 타입 체크를 전혀 안 하는 건 알겠는데, unknown은 언제 쓰는 건지 명확하게 이해가 안 됩니다. try-catch의 error 타입을 unknown으로 해야 한다고 들었는데, 실제로 어떻게 활용하면 되나요?',
    authorName: '윤지아',
    authorId: 'u4',
    createdAt: '6일 전',
    isAnswered: false,
    tags: ['TypeScript'],
    answer: null,
  },
  {
    id: 'q7',
    courseId: '2',
    courseTitle: 'AI 프로그래밍 기초',
    title: 'LLM API 호출 시 토큰 비용 최소화하는 방법이 있을까요?',
    content:
      '프로젝트에서 OpenAI API를 연동했는데 테스트만 했는데도 비용이 생각보다 많이 나왔습니다. 프롬프트 길이를 줄이는 것 외에 토큰을 절약할 수 있는 실용적인 방법이 있는지 알고 싶습니다.',
    authorName: '임도현',
    authorId: 'u5',
    createdAt: '1주 전',
    isAnswered: true,
    tags: ['LLM', 'OpenAI', '비용최적화'],
    answer: {
      id: 'a7',
      authorName: '김나영',
      createdAt: '6일 전',
      avatarSrc: 'https://lh3.googleusercontent.com/aida-public/AB6AXuB5ZHz0kUwUvNwbInMtKkHfVWB6Ig_-YH-3TqJV4uGMMrCftPFfiD2afbP4cr8B3dPILzpBKUu0hmDHodLaoDQoVB7TfOPgavXxGew1YtC4jXcMzk9TShaDvbm-halqDurHe2PIPSWQ8_LtqVORIYHw-j0YJdtMOJcZBI_t4t7kqtJ4RuRRe9AX5Y98sYgNIU5qB_GTcwnmceFWXpXjU11eYka9_blVhHlK4GVqEqY_ZknID2hNEqbZEf4vm71JEJu0G6Roj5jJv_7N',
      content:
        '비용 최적화는 LLM 프로젝트에서 정말 중요한 주제입니다. 실용적인 방법을 정리해 드릴게요.\n\n• 모델 선택: 모든 요청에 GPT-4를 쓸 필요가 없습니다. 간단한 분류, 요약 작업은 gpt-4o-mini나 gpt-3.5-turbo로도 충분합니다.\n• 캐싱: 동일하거나 유사한 프롬프트 응답을 Redis 등에 캐시하면 중복 호출을 줄일 수 있습니다.\n• 시스템 프롬프트 최적화: 불필요한 설명보다 명확하고 짧은 지시문을 사용하세요. Few-shot 예시도 꼭 필요한 것만.\n• 스트리밍 활용: 응답 토큰 수 자체를 줄이는 건 아니지만, 사용자 경험상 빠르게 느껴져 중간에 중단하는 경우를 줄일 수 있습니다.\n• max_tokens 설정: 응답 최대 길이를 명시적으로 제한하면 불필요하게 긴 답변을 방지할 수 있습니다.',
    },
  },
  {
    id: 'q8',
    courseId: '1',
    courseTitle: 'Python으로 시작하는 데이터 분석',
    title: 'scikit-learn에서 train_test_split 비율을 어떻게 정해야 하나요?',
    content:
      '모델 학습 시 train/test 데이터를 나누는 비율이 궁금합니다. 강의에서는 80:20을 사용했는데, 데이터가 적을 때도 같은 비율을 써야 하나요? 아니면 상황에 따라 달리 가져가야 할까요?',
    authorName: '한소희',
    authorId: 'u9',
    createdAt: '2일 전',
    isAnswered: false,
    tags: ['scikit-learn', 'Python'],
    answer: null,
  },
  {
    id: 'q9',
    courseId: '1',
    courseTitle: 'Python으로 시작하는 데이터 분석',
    title: 'groupby 후 여러 집계 함수를 동시에 적용하는 방법이 있나요?',
    content:
      'groupby를 사용할 때 mean()만 쓰면 되는데, 동시에 sum()이나 count()도 같이 보고 싶습니다. agg()를 써보려고 했는데 문법이 헷갈려서요.',
    authorName: '정재현',
    authorId: 'u10',
    createdAt: '3일 전',
    isAnswered: false,
    tags: ['pandas', 'Python'],
    answer: null,
  },
  {
    id: 'q10',
    courseId: '3',
    courseTitle: '프론트엔드 마스터: React & TypeScript',
    title: 'useEffect 의존성 배열에 함수를 넣으면 무한 루프가 되는 이유가 궁금합니다.',
    content:
      'useEffect 안에서 props로 받은 콜백 함수를 호출하고, 그 함수를 의존성 배열에 넣었더니 무한 렌더링이 발생했습니다. 왜 이런 현상이 생기는 건지, useCallback으로 감싸면 해결되는지 알고 싶습니다.',
    authorName: '이하은',
    authorId: 'u11',
    createdAt: '1일 전',
    isAnswered: false,
    tags: ['React', 'Hooks'],
    answer: null,
  },
  {
    id: 'q11',
    courseId: '3',
    courseTitle: '프론트엔드 마스터: React & TypeScript',
    title: 'Next.js에서 환경변수를 클라이언트에서 사용하려면 어떻게 해야 하나요?',
    content:
      '.env.local에 API 키를 넣었는데 클라이언트 컴포넌트에서 undefined가 뜹니다. NEXT_PUBLIC_ 접두사가 필요하다고 들었는데, 그냥 서버에서만 써야 하는 변수와 클라이언트에서 쓰는 변수를 어떻게 구분해서 관리하면 좋을지 알고 싶습니다.',
    authorName: '박준서',
    authorId: 'u12',
    createdAt: '5일 전',
    isAnswered: false,
    tags: ['Next.js', '환경변수'],
    answer: null,
  },
  {
    id: 'q12',
    courseId: '2',
    courseTitle: 'AI 프로그래밍 기초',
    title: 'RAG 구현 시 청크 크기를 어떻게 설정해야 할까요?',
    content:
      'PDF 문서를 벡터 DB에 임베딩하는 실습을 하고 있습니다. 청크 크기(chunk size)와 오버랩(overlap)을 어떤 기준으로 설정해야 검색 품질이 좋아지는지 감이 잡히지 않습니다.',
    authorName: '최유진',
    authorId: 'u13',
    createdAt: '2일 전',
    isAnswered: false,
    tags: ['RAG', 'LLM', '벡터DB'],
    answer: null,
  },
];

export const getQuestionById = (id: string) =>
  MOCK_QUESTIONS.find((q) => q.id === id) ?? null;