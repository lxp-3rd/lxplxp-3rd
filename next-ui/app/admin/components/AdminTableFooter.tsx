interface AdminTableFooterProps {
  total: number;
  visible: number;
  unit?: string;
}

export function AdminTableFooter({ total, visible, unit = '개' }: AdminTableFooterProps) {
  return (
    <div className="px-xl py-md border-t border-outline-variant flex items-center justify-between">
      <span className="text-label-sm font-label-sm text-on-surface-variant">
        전체 {total}
        {unit} 중 {visible}
        {unit} 표시
      </span>
      <div className="flex items-center gap-xs">
        <button
          className="w-8 h-8 flex items-center justify-center rounded-lg hover:bg-surface-container transition-colors"
          aria-label="Previous page"
          disabled
        >
          <span className="material-symbols-outlined text-on-surface-variant text-[18px]">chevron_left</span>
        </button>
        <span className="w-8 h-8 flex items-center justify-center rounded-lg bg-primary text-on-primary font-label-md text-label-md">
          1
        </span>
        <button
          className="w-8 h-8 flex items-center justify-center rounded-lg hover:bg-surface-container transition-colors"
          aria-label="Next page"
          disabled
        >
          <span className="material-symbols-outlined text-on-surface-variant text-[18px]">chevron_right</span>
        </button>
      </div>
    </div>
  );
}
