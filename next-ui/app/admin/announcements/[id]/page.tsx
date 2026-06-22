'use client';

import Link from 'next/link';

// TODO: API 연동 필요
export default function AdminAnnouncementDetailPage() {
  return (

<div className="max-w-[1280px] mx-auto px-margin-desktop py-xl min-h-[calc(100vh-128px)]">

<div className="flex items-center gap-xs mb-lg group">
<a className="flex items-center text-on-surface-variant hover:text-primary transition-colors duration-150" href="/admin/announcements">
<span className="material-symbols-outlined text-[20px] mr-xs">arrow_back</span>
<span className="font-label-md text-label-md">공지사항 목록으로 돌아가기</span>
</a>
</div>
<div className="grid grid-cols-12 gap-gutter">

<div className="col-span-12 lg:col-span-9">
<article className="bento-card rounded-xl p-xl mb-lg">

<div className="flex flex-col gap-sm mb-lg">
<div className="flex items-center gap-sm">
<span className="bg-secondary-container text-on-secondary-container px-md py-xs rounded-full font-label-sm text-label-sm">시스템 업데이트</span>
<span className="text-outline font-label-md text-label-md">ID: #NOT-8821</span>
</div>
<h1 className="font-headline-lg text-headline-lg text-on-surface tracking-tight">플랫폼 정기 점검 안내: 성능 향상 및 인프라 업그레이드</h1>
<div className="flex items-center gap-md mt-xs">
<div className="flex items-center gap-xs">
<span className="material-symbols-outlined text-[18px] text-primary">person</span>
<span className="font-label-md text-label-md text-on-surface-variant">관리자</span>
</div>
<div className="w-1 h-1 bg-outline-variant rounded-full"></div>
<div className="flex items-center gap-xs">
<span className="material-symbols-outlined text-[18px] text-primary">calendar_today</span>
<span className="font-label-md text-label-md text-on-surface-variant">2024년 10월 24일</span>
</div>
<div className="w-1 h-1 bg-outline-variant rounded-full"></div>
<div className="flex items-center gap-xs">
<span className="material-symbols-outlined text-[18px] text-primary">visibility</span>
<span className="font-label-md text-label-md text-on-surface-variant">1,248회 조회</span>
</div>
</div>
</div>

<hr />

<div className="content-area font-body-lg text-body-lg text-on-surface-variant leading-relaxed space-y-md">
<p className="">LXP 커뮤니티 여러분, 안녕하세요.</p>
<p className="">원활하고 활력 넘치는 학습 경험을 제공하기 위한 노력의 일환으로, 이번 주말에 중요한 유지 보수 작업을 진행할 예정입니다. 저희 팀은 페이지 로딩 시간을 최대 40% 단축하고 대화형 코드 실습의 응답성을 개선하기 위해 여러 인프라 업그레이드를 배포할 예정입니다.</p>
<div className="bg-surface-container-low rounded-lg p-lg my-lg border-l-4 border-primary">
<h3 className="font-label-md text-label-md text-on-surface mb-xs uppercase tracking-wider">점검 시간 안내</h3>
<p className="font-body-md text-body-md">시작: 10월 26일 (토), 오전 11:00 (KST)<br />종료: 10월 26일 (토), 오후 02:00 (KST)</p>
</div>
<p className="">점검 기간 동안 LXP 관리자 포털 및 메인 학습 플랫폼 접속이 일시적으로 제한됩니다. 점검 시작 전 진행 상황을 저장하고 로그아웃하시기를 권장합니다.</p>
<h2 className="font-headline-md text-headline-md text-on-surface pt-md">주요 변경 사항</h2>
<ul className="list-disc pl-md space-y-xs">
<li className="">'내 학습' 대시보드 로딩 속도 향상을 위한 데이터베이스 인덱싱 강화</li>
<li className="">최신 보안 표준 준수를 위한 SSL 인증서 업데이트</li>
<li className="">부드러운 4K 스트리밍을 위한 새로운 비디오 플레이어 백엔드 도입</li>
<li className="">'공지사항' 검색 필터의 알려진 오류 수정</li>
</ul>
<div className="my-xl rounded-xl overflow-hidden shadow-sm">
<img alt="기술 다이어그램 이미지" className="w-full h-auto object-cover max-h-64" src="https://lh3.googleusercontent.com/aida-public/AB6AXuD2JhYjK5UauahY8--Yp6KjoxBft9vBofrVSrUcmn4ftuwLUxo3hUWLpqeXFL78B49WzZdJJ1qQYup4UaDF9_HzDB1ndxZaPraCL_rnHD-V0IPylceqCGjNuMvMXW4-oFdtNwMXFw5E-GRbyikcFCftSmcDp953Jz3OYrUhi7XJcWfcvGfo1QKenK72A8CcZei8hRXyFS9OINhMX33v5NyENi6LZNoiK4qaFyZyDO0oRxkOzcWPn-wq_oHMxIT8280AspLNUK4vMniB" />
</div>
<p className="">여러분의 전문성 개발을 위한 가장 신뢰할 수 있는 플랫폼이 되도록 노력하겠습니다. 점검 중 급한 문의 사항이 있으시면 고객 지원팀(help@lxp-platform.io)으로 연락해 주시기 바랍니다.</p>
<p className="">감사합니다.<br /><strong>LXP 플랫폼 운영팀 드림</strong></p>
</div>

<div className="flex flex-col md:flex-row justify-between items-center mt-xl pt-xl border-t border-surface-container-highest gap-md">
<div className="flex gap-md w-full md:w-auto">
<Link href="/admin/announcements" className="flex-1 md:flex-none px-xl h-11 flex items-center justify-center border-2 border-outline-variant text-on-surface hover:bg-surface-container-low transition-all font-label-md text-label-md rounded-lg active:scale-95">
  공지사항 목록으로 돌아가기
</Link>
<Link href="/admin/announcements/1/edit" className="flex-1 md:flex-none px-xl h-11 flex items-center justify-center border-2 border-primary-container bg-surface-container-lowest text-primary hover:bg-primary-fixed transition-all font-label-md text-label-md rounded-lg active:scale-95">
  공지사항 수정
</Link>
</div>
<button className="w-full md:w-auto px-xl h-11 flex items-center justify-center border-2 border-error-container text-error hover:bg-error-container transition-all font-label-md text-label-md rounded-lg active:scale-95">
<span className="material-symbols-outlined mr-xs text-[20px]">delete</span>
                        공지사항 삭제
                    </button>
</div>
</article>
</div>

<aside className="col-span-12 lg:col-span-3 space-y-lg">
<div className="bento-card rounded-xl p-lg">
<h3 className="font-label-md text-label-md text-on-surface mb-md">게시 상세 정보</h3>
<div className="space-y-md">
<div className="flex justify-between">
<span className="text-on-surface-variant font-label-md text-label-md">상태</span>
<span className="text-primary font-bold font-label-md text-label-md">게시됨</span>
</div>
<div className="flex justify-between">
<span className="text-on-surface-variant font-label-md text-label-md">대상</span>
<span className="text-on-surface font-label-md text-label-md">전체 사용자</span>
</div>
<div className="flex justify-between">
<span className="text-on-surface-variant font-label-md text-label-md">우선순위</span>
<span className="text-secondary font-label-md text-label-md">높음</span>
</div>
</div>
</div>

</aside>
</div>
</div>

  );
}
