interface PageHeaderProps {
  title: string;
  subtitle?: string;
  actions?: React.ReactNode;
}

export function PageHeader({ title, subtitle, actions }: PageHeaderProps) {
  return (
    <div className="flex flex-col md:flex-row justify-between items-start md:items-end mb-xl gap-md">
      <div>
        <h1 className="text-headline-lg font-headline-lg text-on-surface">{title}</h1>
        {subtitle && (
          <p className="text-body-md font-body-md text-on-surface-variant mt-xs">{subtitle}</p>
        )}
      </div>
      {actions && <div className="flex gap-sm">{actions}</div>}
    </div>
  );
}