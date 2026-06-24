/** @type {import('next').NextConfig} */
const backendApiUrl = process.env.BACKEND_API_URL ?? 'http://localhost:8080';

const nextConfig = {
  images: {
    remotePatterns: [
      {
        protocol: 'https',
        hostname: 'lh3.googleusercontent.com',
      },
      {
        protocol: 'http',
        hostname: 'localhost',
        port: '8080',
        pathname: '/images/**',
      },
    ],
  },
  async rewrites() {
    return [
      {
        source: '/api/:path*',
        destination: `${backendApiUrl}/api/:path*`,
      },
    ];
  },
};

module.exports = nextConfig;
