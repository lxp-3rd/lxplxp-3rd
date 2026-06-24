package com.ohgiraffers.lxp.course.domain.model.entity;

public class Course {

    private Long id;
    private Long instructorId;
    private String title;
    private String description;
    private String thumbnailUrl;
    private CourseStatus status;
    private HiddenBy hiddenBy;

    private Course() {
    }

    public static Course create(Long instructorId, String title, String description, String thumbnailUrl) {
        if (instructorId == null) {
            throw new IllegalArgumentException("강사 ID는 필수입니다.");
        }
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("강좌 제목은 필수입니다.");
        }
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("강좌 설명은 필수입니다.");
        }
        Course course = new Course();
        course.instructorId = instructorId;
        course.title = title;
        course.description = description;
        course.thumbnailUrl = thumbnailUrl;
        course.status = CourseStatus.PUBLIC;
        course.hiddenBy = null;
        return course;
    }

    public static Course restore(Long id, Long instructorId, String title, String description,
                                  String thumbnailUrl, CourseStatus status, HiddenBy hiddenBy) {
        if (id == null) {
            throw new IllegalArgumentException("강좌 ID는 필수입니다.");
        }
        if (instructorId == null) {
            throw new IllegalArgumentException("강사 ID는 필수입니다.");
        }
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("강좌 제목은 필수입니다.");
        }
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("강좌 설명은 필수입니다.");
        }
        if (status == null) {
            throw new IllegalArgumentException("강좌 상태는 필수입니다.");
        }
        Course course = new Course();
        course.id = id;
        course.instructorId = instructorId;
        course.title = title;
        course.description = description;
        course.thumbnailUrl = thumbnailUrl;
        course.status = status;
        course.hiddenBy = hiddenBy;
        return course;
    }

    public void changeStatus(CourseStatus newStatus, HiddenBy changedBy) {
        if (newStatus == CourseStatus.PUBLIC && this.hiddenBy == HiddenBy.ADMIN && changedBy == HiddenBy.INSTRUCTOR) {
            throw new IllegalArgumentException("관리자가 숨긴 강좌는 강사가 공개할 수 없습니다.");
        }
        if (newStatus == CourseStatus.HIDDEN) {
            this.status = CourseStatus.HIDDEN;
            this.hiddenBy = changedBy;
        } else {
            this.status = newStatus;
            this.hiddenBy = null;
        }
    }

    public void update(String title, String description, String thumbnailUrl) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("강좌 제목은 필수입니다.");
        }
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("강좌 설명은 필수입니다.");
        }
        this.title = title;
        this.description = description;
        this.thumbnailUrl = thumbnailUrl;
    }

    public Long getId() {
        return id;
    }

    public Long getInstructorId() {
        return instructorId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public CourseStatus getStatus() {
        return status;
    }

    public HiddenBy getHiddenBy() {
        return hiddenBy;
    }
}
