interface AdminPageContainerProps {
  children: React.ReactNode;
  className?: string;
}

export function AdminPageContainer({ children, className = '' }: AdminPageContainerProps) {
  return (
    <div className={['flex-grow w-full max-w-[1280px] mx-auto px-gutter pt-xl pb-xxl', className].join(' ')}>
      {children}
    </div>
  );
}
