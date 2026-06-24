package com.ohgiraffers.lxp.global.init;

import com.ohgiraffers.lxp.announcement.domain.model.vo.AnnouncementStatus;
import com.ohgiraffers.lxp.announcement.infrastructure.persistence.jpa.AnnouncementJpaEntity;
import com.ohgiraffers.lxp.announcement.infrastructure.persistence.jpa.AnnouncementRepository;
import com.ohgiraffers.lxp.content.domain.model.entity.Content;
import com.ohgiraffers.lxp.content.infrastructure.persistence.jpa.ContentJpaEntity;
import com.ohgiraffers.lxp.content.infrastructure.persistence.jpa.ContentJpaRepository;
import com.ohgiraffers.lxp.course.domain.model.entity.Course;
import com.ohgiraffers.lxp.course.domain.model.entity.CourseStatus;
import com.ohgiraffers.lxp.course.domain.model.entity.HiddenBy;
import com.ohgiraffers.lxp.course.infrastructure.persistence.jpa.CourseJpaEntity;
import com.ohgiraffers.lxp.course.infrastructure.persistence.jpa.CourseJpaRepository;
import com.ohgiraffers.lxp.enrollment.domain.model.vo.EnrollmentStatus;
import com.ohgiraffers.lxp.enrollment.infrastructure.persistence.jpa.EnrollmentJpaEntity;
import com.ohgiraffers.lxp.enrollment.infrastructure.persistence.jpa.EnrollmentJpaRepository;
import com.ohgiraffers.lxp.instructor.domain.model.entity.Instructor;
import com.ohgiraffers.lxp.instructor.domain.model.entity.InstructorProfile;
import com.ohgiraffers.lxp.instructor.infrastructure.persistence.jpa.InstructorJpaEntity;
import com.ohgiraffers.lxp.instructor.infrastructure.persistence.jpa.InstructorJpaRepository;
import com.ohgiraffers.lxp.instructor.infrastructure.persistence.jpa.InstructorProfileJpaEntity;
import com.ohgiraffers.lxp.instructor.infrastructure.persistence.jpa.InstructorProfileJpaRepository;
import com.ohgiraffers.lxp.member.domain.model.entity.Member;
import com.ohgiraffers.lxp.member.domain.model.entity.MemberRole;
import com.ohgiraffers.lxp.member.domain.model.entity.MemberStatus;
import com.ohgiraffers.lxp.member.domain.model.vo.Email;
import com.ohgiraffers.lxp.member.domain.model.vo.EncodedPassword;
import com.ohgiraffers.lxp.member.domain.model.vo.Nickname;
import com.ohgiraffers.lxp.member.infrastructure.persistence.jpa.MemberJpaEntity;
import com.ohgiraffers.lxp.member.infrastructure.persistence.jpa.MemberJpaRepository;
import com.ohgiraffers.lxp.qna.domain.model.entity.Question;
import com.ohgiraffers.lxp.qna.infrastructure.persistence.jpa.QuestionJpaEntity;
import com.ohgiraffers.lxp.qna.infrastructure.persistence.jpa.QuestionJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Component
@Profile("!test")
@RequiredArgsConstructor
public class MockDataInitializer implements CommandLineRunner {

    private final MemberJpaRepository memberRepository;
    private final InstructorJpaRepository instructorRepository;
    private final InstructorProfileJpaRepository instructorProfileRepository;
    private final CourseJpaRepository courseRepository;
    private final ContentJpaRepository contentRepository;
    private final EnrollmentJpaRepository enrollmentRepository;
    private final AnnouncementRepository announcementRepository;
    private final QuestionJpaRepository questionRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    @Transactional
    public void run(String... args) {
        if (memberRepository.count() > 0) {
            log.info("[MockData] 데이터가 이미 존재합니다. 초기화를 건너뜁니다.");
            return;
        }

        log.info("[MockData] 목 데이터 초기화 시작...");
        String pw = passwordEncoder.encode("testtest");

        // ── 1. 회원 ──────────────────────────────────────────────
        MemberJpaEntity admin    = saveMember("admin@admin.admin",    "관리자",   pw, MemberRole.ADMIN);
        MemberJpaEntity hayeon   = saveMember("test1@test.test",   "이하연",   pw, MemberRole.INSTRUCTOR);
        MemberJpaEntity minjun   = saveMember("minjun@lxp.com",   "박민준",   pw, MemberRole.INSTRUCTOR);
        MemberJpaEntity seojun   = saveMember("seojun@lxp.com",   "최서준",   pw, MemberRole.INSTRUCTOR);
        MemberJpaEntity student1 = saveMember("test2@test.test", "김수현",   pw, MemberRole.LEARNER);
        MemberJpaEntity student2 = saveMember("student2@lxp.com", "이준호",   pw, MemberRole.LEARNER);
        MemberJpaEntity student3 = saveMember("student3@lxp.com", "박지민",   pw, MemberRole.LEARNER);
        MemberJpaEntity student4 = saveMember("student4@lxp.com", "오태양",   pw, MemberRole.LEARNER);
        MemberJpaEntity student5 = saveMember("student5@lxp.com", "윤지아",   pw, MemberRole.LEARNER);

        // ── 2. 강사 & 프로필 ──────────────────────────────────────
        InstructorJpaEntity instrHayeon = saveInstructor(hayeon.getId());
        InstructorJpaEntity instrMinjun = saveInstructor(minjun.getId());
        InstructorJpaEntity instrSeojun = saveInstructor(seojun.getId());

        saveInstructorProfile(instrHayeon.getId(),
                "10년 경력의 UX 디자이너. 삼성전자, 카카오 UX 리드를 거쳐 현재 강의 중.");
        saveInstructorProfile(instrMinjun.getId(),
                "ML 엔지니어 출신 강사. 네이버 클로바 AI팀 근무 경험.");
        saveInstructorProfile(instrSeojun.getId(),
                "AWS 공인 솔루션스 아키텍트. 클라우드 인프라 컨설팅 전문.");

        // ── 3. 강좌 (instructorId = instructor 테이블 PK) ─────────
        CourseJpaEntity course1 = saveCourse(instrHayeon.getId(),
                "데이터 중심의 UI/UX 디자인 실무 입문",
                "데이터 기반 사고로 사용자 경험을 설계하는 실무 중심 강좌입니다.",
                "https://images.unsplash.com/photo-1474511320723-9a56873867b5?w=900&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Mnx8JUVCJThGJTk5JUVCJUFDJUJDfGVufDB8MHwwfHx8MA%3D%3D",
                CourseStatus.PUBLIC);
        CourseJpaEntity course2 = saveCourse(instrMinjun.getId(),
                "AI 프로그래밍 기초: Python으로 시작하기",
                "Python 기초부터 머신러닝 모델 구현까지, AI 입문자를 위한 완성형 강좌.",
                "https://images.unsplash.com/photo-1589656966895-2f33e7653819?w=900&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8M3x8JUVCJThGJTk5JUVCJUFDJUJDfGVufDB8MHwwfHx8MA%3D%3D",
                CourseStatus.PUBLIC);
        CourseJpaEntity course3 = saveCourse(instrHayeon.getId(),
                "프론트엔드 마스터: React & TypeScript",
                "React와 TypeScript로 실무 수준의 웹 앱을 만드는 중급 개발자 코스.",
                "https://images.unsplash.com/photo-1497752531616-c3afd9760a11?w=900&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Nnx8JUVCJThGJTk5JUVCJUFDJUJDfGVufDB8MHwwfHx8MA%3D%3D",
                CourseStatus.PUBLIC);
        CourseJpaEntity course4 = saveCourse(instrSeojun.getId(),
                "클라우드 인프라 AWS 실전",
                "AWS 핵심 서비스를 실습 중심으로 배우는 클라우드 실무 강좌.",
                "https://www.gstatic.com/labs-code/stitch/stitch-placeholder-300x300.svg",
                CourseStatus.PUBLIC);
        CourseJpaEntity course5 = saveCourse(instrMinjun.getId(),
                "비즈니스 데이터 분석 with Excel & Python",
                "Excel부터 Python Pandas까지 비즈니스 데이터 분석의 모든 것.",
                "https://www.gstatic.com/labs-code/stitch/stitch-placeholder-300x300.svg",
                CourseStatus.PUBLIC);
        saveCourse(instrHayeon.getId(),
                "UX Writing: 말하는 디자인",
                "사용자의 행동을 이끄는 UX 라이팅 원칙과 실습.",
                "https://www.gstatic.com/labs-code/stitch/stitch-placeholder-300x300.svg",
                CourseStatus.HIDDEN);

        // ── 4. 콘텐츠 ────────────────────────────────────────────
        saveContent(course1.getId(), 1, "오리엔테이션 및 커리큘럼 안내",  "https://example.com/videos/c1-01");
        saveContent(course1.getId(), 2, "데이터와 디자인 사고의 연결",    "https://example.com/videos/c1-02");
        saveContent(course1.getId(), 3, "UX 리서치 방법론 개요",         "https://example.com/videos/c1-03");
        saveContent(course1.getId(), 4, "사용자 인터뷰 실습",            "https://example.com/videos/c1-04");
        saveContent(course1.getId(), 5, "1차 퀴즈: UX 기초 개념",        "https://example.com/quiz/c1-01");

        saveContent(course2.getId(), 1, "Python 환경 설정",  "https://example.com/videos/c2-01");
        saveContent(course2.getId(), 2, "변수와 자료형",      "https://example.com/videos/c2-02");
        saveContent(course2.getId(), 3, "제어문과 함수",      "https://example.com/videos/c2-03");

        saveContent(course3.getId(), 1, "TypeScript 핵심 개념",    "https://example.com/videos/c3-01");
        saveContent(course3.getId(), 2, "React 컴포넌트 심화",     "https://example.com/videos/c3-02");

        // ── 5. 수강 신청 ──────────────────────────────────────────
        saveEnrollment(student1.getId(), course1.getId(), EnrollmentStatus.ACTIVE);
        saveEnrollment(student1.getId(), course3.getId(), EnrollmentStatus.ACTIVE);
        saveEnrollment(student2.getId(), course3.getId(), EnrollmentStatus.ACTIVE);
        saveEnrollment(student3.getId(), course2.getId(), EnrollmentStatus.COMPLETED);
        saveEnrollment(student4.getId(), course1.getId(), EnrollmentStatus.ACTIVE);
        saveEnrollment(student5.getId(), course3.getId(), EnrollmentStatus.ACTIVE);

        // ── 6. 공지사항 ───────────────────────────────────────────
        saveAnnouncement(admin.getId(),
                "2025년 2분기 신규 강좌 대거 오픈!",
                "안녕하세요. LXP 운영팀입니다. 이번 분기에는 AI, 클라우드, UX 분야의 신규 강좌가 대거 오픈됩니다.",
                AnnouncementStatus.PUBLISH);
        saveAnnouncement(admin.getId(),
                "플랫폼 서비스 점검 안내 (6/15 02:00~04:00)",
                "안정적인 서비스 제공을 위해 정기 서버 점검을 진행합니다. 점검 시간 동안 서비스 이용이 불가합니다.",
                AnnouncementStatus.PUBLISH);
        saveAnnouncement(admin.getId(),
                "수료증 발급 기능 업데이트",
                "강좌 수료 후 PDF 형태의 수료증을 즉시 발급받을 수 있는 기능이 추가되었습니다.",
                AnnouncementStatus.PUBLISH);

        // ── 7. Q&A ───────────────────────────────────────────────
        QuestionJpaEntity q1 = saveQuestion(course3.getId(), student2.getId(),
                "React Server Components의 동작 원리가 궁금합니다.",
                "React 18에서 도입된 Server Components와 기존 SSR의 구체적인 차이가 궁금합니다.");
        q1.updateAnswer(
                "SSR은 매 요청마다 전체 트리를 HTML로 렌더링하고 JS 번들로 하이드레이션합니다. " +
                "RSC는 특정 컴포넌트의 JS 자체를 클라이언트에 보내지 않아 번들 크기를 줄입니다.",
                hayeon.getId(), LocalDateTime.now().minusDays(1));

        saveQuestion(course3.getId(), student2.getId(),
                "Tailwind CSS v4 마이그레이션 방법이 궁금합니다.",
                "v3에서 v4로 마이그레이션할 때 주의해야 할 breaking change가 무엇인지 알고 싶습니다.");

        saveQuestion(course2.getId(), student3.getId(),
                "Figma 디자인토큰을 코드와 동기화하는 방법을 질문합니다.",
                "Figma Variables와 CSS 커스텀 프로퍼티를 자동으로 동기화하는 방법을 찾고 있습니다.");

        QuestionJpaEntity q4 = saveQuestion(course1.getId(), student1.getId(),
                "pandas DataFrame에서 NaN 처리 기준이 궁금합니다.",
                "fillna()로 특정 값을 채우는 게 나은지 dropna()로 행을 제거하는 게 나은지 판단 기준이 헷갈립니다.");
        q4.updateAnswer(
                "결측값이 5% 미만이면 dropna(), 그 이상이면 수치형은 중앙값, 범주형은 최빈값으로 fillna()를 권장합니다.",
                minjun.getId(), LocalDateTime.now().minusHours(6));

        saveQuestion(course3.getId(), student5.getId(),
                "useEffect 의존성 배열에 함수를 넣으면 무한 루프가 생기는 이유가 궁금합니다.",
                "props로 받은 콜백 함수를 의존성 배열에 넣었더니 무한 렌더링이 발생했습니다.");

        log.info("[MockData] 초기화 완료 — 회원 {}명, 강사 {}명, 강좌 {}개, 질문 {}개",
                memberRepository.count(),
                instructorRepository.count(),
                courseRepository.count(),
                questionRepository.count());
    }

    // ── 헬퍼 메서드 ───────────────────────────────────────────────

    private MemberJpaEntity saveMember(String email, String nickname, String password, MemberRole role) {
        Member domain = Member.restore(null,
                new Email(email), new Nickname(nickname), new EncodedPassword(password),
                role, MemberStatus.ACTIVE);
        return memberRepository.save(MemberJpaEntity.from(domain));
    }

    private InstructorJpaEntity saveInstructor(Long memberId) {
        return instructorRepository.save(InstructorJpaEntity.from(Instructor.create(memberId)));
    }

    private void saveInstructorProfile(Long instructorId, String bio) {
        InstructorProfile profile = InstructorProfile.create(instructorId, "https://example.com/profile/default.png", bio);
        instructorProfileRepository.save(InstructorProfileJpaEntity.from(profile));
    }

    private CourseJpaEntity saveCourse(Long instructorId, String title, String description,
                                       String thumbnailUrl, CourseStatus status) {
        Course course = Course.create(instructorId, title, description, thumbnailUrl);
        if (status == CourseStatus.PUBLIC) {
            course.changeStatus(CourseStatus.PUBLIC, HiddenBy.INSTRUCTOR);
        }
        return courseRepository.save(CourseJpaEntity.from(course));
    }

    private void saveContent(Long courseId, int sequence, String title, String contentUrl) {
        Content content = Content.create(courseId, sequence, title, contentUrl);
        contentRepository.save(ContentJpaEntity.from(content));
    }

    private void saveEnrollment(Long memberId, Long courseId, EnrollmentStatus status) {
        enrollmentRepository.save(new EnrollmentJpaEntity(memberId, courseId, status));
    }

    private void saveAnnouncement(Long adminId, String title, String content, AnnouncementStatus status) {
        announcementRepository.save(new AnnouncementJpaEntity(adminId, title, content, status));
    }

    private QuestionJpaEntity saveQuestion(Long courseId, Long memberId, String title, String content) {
        Question question = Question.create(courseId, memberId, title, content);
        return questionRepository.save(QuestionJpaEntity.from(question));
    }
}
