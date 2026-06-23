package com.ohgiraffers.lxp.content.presentation.web;

import com.ohgiraffers.lxp.content.application.port.in.DeleteContentUseCase;
import com.ohgiraffers.lxp.content.application.port.in.RegisterContentUseCase;
import com.ohgiraffers.lxp.content.application.port.in.UpdateContentUseCase;
import com.ohgiraffers.lxp.content.presentation.dto.RegisterContentRequest;
import com.ohgiraffers.lxp.content.presentation.dto.RegisterContentResponse;
import com.ohgiraffers.lxp.content.presentation.dto.UpdateContentRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/courses/{courseId}/contents")
public class ContentController {

    private final RegisterContentUseCase registerContentUseCase;
    private final UpdateContentUseCase updateContentUseCase;
    private final DeleteContentUseCase deleteContentUseCase;

    public ContentController(RegisterContentUseCase registerContentUseCase,
                             UpdateContentUseCase updateContentUseCase,
                             DeleteContentUseCase deleteContentUseCase) {
        this.registerContentUseCase = registerContentUseCase;
        this.updateContentUseCase = updateContentUseCase;
        this.deleteContentUseCase = deleteContentUseCase;
    }

    @PostMapping
    public ResponseEntity<RegisterContentResponse> register(
            @PathVariable Long courseId,
            @RequestBody @Valid RegisterContentRequest request) {
        Long contentId = registerContentUseCase.register(request.toCommand(courseId));
        return ResponseEntity.status(201).body(new RegisterContentResponse(contentId));
    }

    @PutMapping("/{contentId}")
    public ResponseEntity<Void> update(
            @PathVariable Long courseId,
            @PathVariable Long contentId,
            @RequestBody @Valid UpdateContentRequest request) {
        updateContentUseCase.update(request.toCommand(courseId, contentId));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{contentId}")
    public ResponseEntity<Void> delete(
            @PathVariable Long courseId,
            @PathVariable Long contentId) {
        deleteContentUseCase.delete(contentId);
        return ResponseEntity.noContent().build();
    }
}
