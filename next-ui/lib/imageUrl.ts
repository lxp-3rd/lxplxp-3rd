const IMAGE_HOST = process.env.NEXT_PUBLIC_API_URL || 'http://localhost:8080';

export function getImageUrl(src: string | null | undefined) {
  if (!src) return '';
  if (/^(https?:|blob:|data:)/.test(src)) return src;
  if (src.startsWith('//')) return src;

  const path = src.startsWith('/') ? src : `/${src}`;
  return `${IMAGE_HOST}${path}`;
}
