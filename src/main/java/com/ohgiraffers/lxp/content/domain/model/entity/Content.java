package com.ohgiraffers.lxp.content.domain.model.entity;

public class Content {

    private final Long id;
    private final Long courseId;
    private final int sequence;
    private final String title;
    private final String contentUrl;

    private Content(Long id, Long courseId, int sequence, String title, String contentUrl) {
        this.id = id;
        this.courseId = courseId;
        this.sequence = sequence;
        this.title = title;
        this.contentUrl = contentUrl;
    }

    public static Content create(Long courseId, int sequence, String title, String contentUrl) {
        if (courseId == null) throw new IllegalArgumentException("강좌 ID는 필수입니다.");
        if (sequence < 0) throw new IllegalArgumentException("순서는 0 이상이어야 합니다.");
        if (title == null || title.isBlank()) throw new IllegalArgumentException("콘텐츠 제목은 필수입니다.");
        if (contentUrl == null || contentUrl.isBlank()) throw new IllegalArgumentException("콘텐츠 URL은 필수입니다.");
        return new Content(null, courseId, sequence, title, contentUrl);
    }

    public static Content restore(Long id, Long courseId, int sequence, String title, String contentUrl) {
        return new Content(id, courseId, sequence, title, contentUrl);
    }

    public Long getId() { return id; }
    public Long getCourseId() { return courseId; }
    public int getSequence() { return sequence; }
    public String getTitle() { return title; }
    public String getContentUrl() { return contentUrl; }
}
