package com.ohgiraffers.lxp.qna.infrastructure.persistence.jpa;

import com.ohgiraffers.lxp.global.entity.BaseEntity;
import com.ohgiraffers.lxp.qna.domain.model.entity.Question;
import com.ohgiraffers.lxp.qna.domain.model.entity.QuestionStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "questions")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuestionJpaEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long courseId;

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, length = 500)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private QuestionStatus status;

    private QuestionJpaEntity(Long courseId, Long memberId, String title, String content, QuestionStatus status) {
        this.courseId = courseId;
        this.memberId = memberId;
        this.title = title;
        this.content = content;
        this.status = status;
    }

    public static QuestionJpaEntity from(Question question) {
        return new QuestionJpaEntity(
                question.getCourseId(),
                question.getMemberId(),
                question.getTitle(),
                question.getContent(),
                question.getStatus()
        );
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Question toDomain() {
        return new Question(
                id,
                courseId,
                memberId,
                title,
                content,
                status,
                getCreatedAt(),
                getUpdatedAt(),
                getDeletedAt()
        );
    }
}
