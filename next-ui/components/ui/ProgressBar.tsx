interface ProgressBarProps {
  value:     number; // 0~100
  showLabel?: boolean;
  className?: string;
}

export function ProgressBar({ value, showLabel = false, className = '' }: ProgressBarProps) {
  const clamped = Math.min(100, Math.max(0, value));

  return (
    <div className={`flex items-center gap-sm ${className}`}>
      <div className="flex-1 h-2 bg-surface-container rounded-full overflow-hidden">
        <div
          className="h-full bg-primary rounded-full transition-all duration-300"
          style={{ width: `${clamped}%` }}
          role="progressbar"
          aria-valuenow={clamped}
          aria-valuemin={0}
          aria-valuemax={100}
        />
      </div>
      {showLabel && (
        <span className="text-label-sm text-on-surface-variant whitespace-nowrap">
          {clamped}%
        </span>
      )}
    </div>
  );
}
