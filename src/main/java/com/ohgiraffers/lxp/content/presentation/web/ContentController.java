package com.ohgiraffers.lxp.content.presentation.web;

import com.ohgiraffers.lxp.content.application.port.in.RegisterContentUseCase;
import com.ohgiraffers.lxp.content.infrastructure.persistence.jpa.ContentJpaEntity;
import com.ohgiraffers.lxp.content.infrastructure.persistence.jpa.ContentJpaRepository;
import com.ohgiraffers.lxp.content.presentation.dto.ContentResponse;
import com.ohgiraffers.lxp.content.presentation.dto.RegisterContentRequest;
import com.ohgiraffers.lxp.content.presentation.dto.RegisterContentResponse;
import com.ohgiraffers.lxp.global.exception.BusinessException;
import com.ohgiraffers.lxp.global.exception.ErrorCode;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/courses/{courseId}/contents")
public class ContentController {

    private final RegisterContentUseCase registerContentUseCase;
    private final ContentJpaRepository contentRepository;

    public ContentController(RegisterContentUseCase registerContentUseCase, ContentJpaRepository contentRepository) {
        this.registerContentUseCase = registerContentUseCase;
        this.contentRepository = contentRepository;
    }

    @GetMapping
    public ResponseEntity<List<ContentResponse>> getContents(@PathVariable Long courseId) {
        List<ContentResponse> responses = contentRepository.findByCourseIdAndDeletedAtIsNullOrderBySequenceAsc(courseId)
                .stream()
                .map(ContentResponse::from)
                .toList();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{contentId}")
    public ResponseEntity<ContentResponse> getContent(@PathVariable Long courseId, @PathVariable Long contentId) {
        ContentJpaEntity content = contentRepository.findByIdAndDeletedAtIsNull(contentId)
                .filter(entity -> entity.getCourseId().equals(courseId))
                .orElseThrow(() -> new BusinessException(ErrorCode.CONTENT_NOT_FOUND));
        return ResponseEntity.ok(ContentResponse.from(content));
    }

    @PostMapping
    public ResponseEntity<RegisterContentResponse> register(
            @PathVariable Long courseId,
            @RequestBody @Valid RegisterContentRequest request) {
        Long contentId = registerContentUseCase.register(request.toCommand(courseId));
        return ResponseEntity.status(201).body(new RegisterContentResponse(contentId));
    }
}
