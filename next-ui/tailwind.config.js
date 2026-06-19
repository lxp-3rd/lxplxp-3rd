/** @type {import('tailwindcss').Config} */
module.exports = {
  darkMode: 'class',
  content: [
    './app/**/*.{js,ts,jsx,tsx,mdx}',
    './components/**/*.{js,ts,jsx,tsx,mdx}',
  ],
  theme: {
    extend: {
      colors: {
        // Primary
        primary:                   '#ff7e21',
        'on-primary':              '#ffffff',
        'primary-container':       '#ff7e21',
        'on-primary-container':    '#602900',
        'primary-fixed':           '#ffdbc9',
        'primary-fixed-dim':       '#ffb68d',
        'on-primary-fixed':        '#331200',
        'on-primary-fixed-variant':'#763300',
        'inverse-primary':         '#ffb68d',
        'surface-tint':            '#9b4500',

        // Secondary
        secondary:                 '#895026',
        'on-secondary':            '#ffffff',
        'secondary-container':     '#ffb380',
        'on-secondary-container':  '#794319',
        'secondary-fixed':         '#ffdcc7',
        'secondary-fixed-dim':     '#ffb786',
        'on-secondary-fixed':      '#311300',
        'on-secondary-fixed-variant': '#6d3910',

        // Tertiary
        tertiary:                  '#5b5f62',
        'on-tertiary':             '#ffffff',
        'tertiary-container':      '#9fa3a6',
        'on-tertiary-container':   '#35393c',
        'tertiary-fixed':          '#e0e3e6',
        'tertiary-fixed-dim':      '#c4c7ca',
        'on-tertiary-fixed':       '#181c1f',
        'on-tertiary-fixed-variant': '#43474a',

        // Surface
        surface:                   '#f8f9fa',
        'surface-dim':             '#d9dadb',
        'surface-bright':          '#f8f9fa',
        'surface-container-lowest':'#ffffff',
        'surface-container-low':   '#f3f4f5',
        'surface-container':       '#edeeef',
        'surface-container-high':  '#e7e8e9',
        'surface-container-highest':'#e1e3e4',
        'surface-variant':         '#e1e3e4',
        'on-surface':              '#191c1d',
        'on-surface-variant':      '#584236',
        'inverse-surface':         '#2e3132',
        'inverse-on-surface':      '#f0f1f2',

        // Outline
        outline:                   '#8b7264',
        'outline-variant':         '#dfc0b1',

        // Background
        background:                '#f8f9fa',
        'on-background':           '#191c1d',

        // Error
        error:                     '#ba1a1a',
        'on-error':                '#ffffff',
        'error-container':         '#ffdad6',
        'on-error-container':      '#93000a',
      },

      spacing: {
        xs:               '4px',
        sm:               '8px',
        md:               '16px',
        lg:               '24px',
        xl:               '40px',
        xxl:              '48px',
        gutter:           '24px',
        'margin-mobile':  '16px',
        'margin-desktop': '64px',
        'container-max':  '1280px',
        unit:             '4px',
      },

      maxWidth: {
        'container-max': '1280px',
      },

      borderRadius: {
        DEFAULT: '0.5rem',
        sm:      '0.25rem',
        md:      '0.75rem',
        lg:      '1rem',
        xl:      '1.5rem',
        full:    '9999px',
      },

      fontFamily: {
        sans: ['Hanken Grotesk', 'sans-serif'],
        'headline-lg':        ['Hanken Grotesk', 'sans-serif'],
        'headline-lg-mobile': ['Hanken Grotesk', 'sans-serif'],
        'headline-md':        ['Hanken Grotesk', 'sans-serif'],
        'headline-sm':        ['Hanken Grotesk', 'sans-serif'],
        'body-lg':            ['Hanken Grotesk', 'sans-serif'],
        'body-md':            ['Hanken Grotesk', 'sans-serif'],
        'body-sm':            ['Hanken Grotesk', 'sans-serif'],
        'label-md':           ['Hanken Grotesk', 'sans-serif'],
        'label-sm':           ['Hanken Grotesk', 'sans-serif'],
        'display-lg':         ['Hanken Grotesk', 'sans-serif'],
      },

      fontSize: {
        'display-lg':         ['48px',  { lineHeight: '56px',  fontWeight: '700', letterSpacing: '-0.02em' }],
        'headline-lg':        ['32px',  { lineHeight: '40px',  fontWeight: '700', letterSpacing: '-0.01em' }],
        'headline-lg-mobile': ['28px',  { lineHeight: '36px',  fontWeight: '700' }],
        'headline-md':        ['24px',  { lineHeight: '32px',  fontWeight: '600' }],
        'headline-sm':        ['20px',  { lineHeight: '28px',  fontWeight: '600' }],
        'body-lg':            ['18px',  { lineHeight: '28px',  fontWeight: '400' }],
        'body-md':            ['16px',  { lineHeight: '24px',  fontWeight: '400' }],
        'body-sm':            ['14px',  { lineHeight: '20px',  fontWeight: '400' }],
        'label-md':           ['14px',  { lineHeight: '20px',  fontWeight: '600', letterSpacing: '0.01em' }],
        'label-sm':           ['12px',  { lineHeight: '16px',  fontWeight: '500' }],
      },
    },
  },
  plugins: [],
};
