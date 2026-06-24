export function toError(error: unknown): Error {
  return error instanceof Error ? error : new Error('Failed to load admin data.');
}

export function clampPercent(value: number) {
  if (!Number.isFinite(value)) return 0;
  return Math.min(100, Math.max(0, value));
}

export function formatAdminHandle(value: string) {
  const normalized = value.trim().replace(/^@+/, '');
  const [handle] = normalized.split('@');

  return handle || normalized || 'unknown';
}

export function maskEmail(email: string) {
  const [localPart, domain] = email.split('@');
  if (!localPart || !domain) return email;

  const visibleLength = Math.min(2, localPart.length);
  const visible = localPart.slice(0, visibleLength);
  const masked = '*'.repeat(Math.max(1, localPart.length - visibleLength));

  return `${visible}${masked}@${domain}`;
}
