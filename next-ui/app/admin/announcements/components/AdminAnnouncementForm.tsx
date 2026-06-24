interface AdminAnnouncementFormProps {
  title: string;
  content: string;
  author: string;
  isPublic: boolean;
  onTitleChange: (value: string) => void;
  onContentChange: (value: string) => void;
  onAuthorChange: (value: string) => void;
  onPublicChange: (value: boolean) => void;
}

export function AdminAnnouncementForm({
  title,
  content,
  author,
  isPublic,
  onTitleChange,
  onContentChange,
  onAuthorChange,
  onPublicChange,
}: AdminAnnouncementFormProps) {
  return (
    <div className="bento-grid">
      <div className="col-span-12 lg:col-span-8 flex flex-col gap-lg">
        <div className="bg-surface-container-lowest p-lg rounded-xl border border-outline-variant shadow-sm">
          <label className="block font-label-md text-on-surface-variant mb-sm">제목 입력</label>
          <input
            className="w-full text-headline-md font-headline-md bg-transparent border-none p-0 placeholder:text-on-surface-variant/40 focus:ring-0 focus:outline-none"
            placeholder="공지사항 제목을 입력하세요"
            type="text"
            value={title}
            onChange={(event) => onTitleChange(event.target.value)}
          />
        </div>

        <div className="bg-surface-container-lowest p-lg rounded-xl border border-outline-variant shadow-sm flex flex-col gap-md">
          <div className="flex items-center justify-between border-b border-outline-variant pb-md">
            <label className="font-label-md text-on-surface-variant">본문 입력</label>
            <div className="flex items-center gap-sm text-on-surface-variant">
              <button type="button" className="material-symbols-outlined hover:text-primary">format_bold</button>
              <button type="button" className="material-symbols-outlined hover:text-primary">format_italic</button>
              <button type="button" className="material-symbols-outlined hover:text-primary">format_list_bulleted</button>
              <button type="button" className="material-symbols-outlined hover:text-primary">link</button>
            </div>
          </div>
          <textarea
            className="w-full min-h-[400px] bg-transparent border-none resize-y focus:ring-0 focus:outline-none text-body-lg leading-relaxed placeholder:text-on-surface-variant/40"
            placeholder="공지사항 본문을 입력하세요."
            value={content}
            onChange={(event) => onContentChange(event.target.value)}
          />
        </div>
      </div>

      <aside className="col-span-12 lg:col-span-4 flex flex-col gap-lg">
        <div className="bg-surface-container-lowest p-lg rounded-xl border border-outline-variant shadow-sm">
          <h3 className="font-title-md text-title-md text-on-surface mb-md">게시 설정</h3>
          <label className="block font-label-md text-on-surface-variant mb-sm">작성자</label>
          <input
            className="w-full border border-outline-variant rounded-lg px-md py-sm mb-md bg-surface-container-lowest"
            value={author}
            onChange={(event) => onAuthorChange(event.target.value)}
          />
          <label className="flex items-center gap-sm text-body-md text-on-surface">
            <input
              type="checkbox"
              checked={isPublic}
              onChange={(event) => onPublicChange(event.target.checked)}
            />
            공개 상태로 게시
          </label>
        </div>
      </aside>
    </div>
  );
}
