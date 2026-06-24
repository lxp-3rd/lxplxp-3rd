-- 로컬(dev) 전용 더미 데이터. application-dev.yaml 의 spring.sql.init 설정으로만 로드된다.
-- 스키마 생성(create-drop) 이후 실행되도록 spring.jpa.defer-datasource-initialization=true 필요.

-- 강사(member, role=INSTRUCTOR)
INSERT INTO members (id, email, nickname, password, role, status, created_at, updated_at) VALUES
 (1, 'kim@lxp.dev', '김태희', 'x', 'INSTRUCTOR', 'ACTIVE', '2026-05-01 09:00:00', '2026-05-01 09:00:00'),
 (2, 'lee@lxp.dev', '이순신', 'x', 'INSTRUCTOR', 'ACTIVE', '2026-05-01 09:00:00', '2026-05-01 09:00:00');

-- 강좌(course)
-- 1·2·3: PUBLIC(노출), 4: HIDDEN(제외), 5: PUBLIC이지만 soft-delete(제외)
INSERT INTO course (id, instructor_id, title, description, thumbnail_url, status, hidden_by, created_at, updated_at, deleted_at) VALUES
 (1, 1, 'Java 기초',        '자바 입문 강좌',   'https://www.gstatic.com/labs-code/stitch/stitch-placeholder-300x300.svg', 'PUBLIC', NULL, '2026-06-01 10:00:00', '2026-06-01 10:00:00', NULL),
 (2, 2, 'Spring 부트 입문', '스프링 부트 강좌', NULL,                                                                      'PUBLIC', NULL, '2026-06-10 10:00:00', '2026-06-10 10:00:00', NULL),
 (3, 1, 'JPA 심화',         'JPA 심화 강좌',    'https://www.gstatic.com/labs-code/stitch/stitch-placeholder-300x300.svg', 'PUBLIC', NULL, '2026-06-20 10:00:00', '2026-06-20 10:00:00', NULL),
 (4, 2, '숨김 강좌',        '숨김 처리된 강좌', NULL,                                                                      'HIDDEN', 'INSTRUCTOR', '2026-06-15 10:00:00', '2026-06-15 10:00:00', NULL),
 (5, 1, '삭제된 강좌',      '삭제된 강좌',      NULL,                                                                      'PUBLIC', NULL, '2026-06-22 10:00:00', '2026-06-22 10:00:00', '2026-06-23 10:00:00');

-- 수강(enrollment) — course 1: ACTIVE 3 + COMPLETED 1 + CANCELED 1(soft-delete) => 현재 수강 중 3명
INSERT INTO enrollment (id, member_id, course_id, status, created_at, updated_at, deleted_at) VALUES
 (1, 101, 1, 'ACTIVE',    '2026-06-02 10:00:00', '2026-06-02 10:00:00', NULL),
 (2, 102, 1, 'ACTIVE',    '2026-06-02 10:00:00', '2026-06-02 10:00:00', NULL),
 (3, 103, 1, 'ACTIVE',    '2026-06-02 10:00:00', '2026-06-02 10:00:00', NULL),
 (4, 104, 1, 'COMPLETED', '2026-06-02 10:00:00', '2026-06-02 10:00:00', NULL),
 (5, 105, 1, 'CANCELED',  '2026-06-02 10:00:00', '2026-06-02 10:00:00', '2026-06-05 10:00:00'),
 (6, 101, 2, 'ACTIVE',    '2026-06-11 10:00:00', '2026-06-11 10:00:00', NULL);
-- course 3: 수강생 0

-- 좋아요(course_like) — course 1: 2개, course 3: 1개
INSERT INTO course_like (id, course_id, learner_id, created_at) VALUES
 (1, 1, 201, '2026-06-03 10:00:00'),
 (2, 1, 202, '2026-06-03 10:00:00'),
 (3, 3, 203, '2026-06-21 10:00:00');

-- 명시적 ID 삽입 이후 IDENTITY 시퀀스를 띄워, 이후 API INSERT 시 PK 충돌 방지
ALTER TABLE members ALTER COLUMN id RESTART WITH 100;
ALTER TABLE course ALTER COLUMN id RESTART WITH 100;
ALTER TABLE enrollment ALTER COLUMN id RESTART WITH 100;
ALTER TABLE course_like ALTER COLUMN id RESTART WITH 100;
