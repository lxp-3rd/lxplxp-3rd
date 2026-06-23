package com.ohgiraffers.lxp.content.presentation.web;

import com.ohgiraffers.lxp.content.application.port.in.RegisterContentUseCase;
import com.ohgiraffers.lxp.content.presentation.dto.RegisterContentRequest;
import com.ohgiraffers.lxp.content.presentation.dto.RegisterContentResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/courses/{courseId}/contents")
public class ContentController {

    private final RegisterContentUseCase registerContentUseCase;

    public ContentController(RegisterContentUseCase registerContentUseCase) {
        this.registerContentUseCase = registerContentUseCase;
    }

    @PostMapping
    public ResponseEntity<RegisterContentResponse> register(
            @PathVariable Long courseId,
            @RequestBody @Valid RegisterContentRequest request) {
        Long contentId = registerContentUseCase.register(request.toCommand(courseId));
        return ResponseEntity.status(201).body(new RegisterContentResponse(contentId));
    }
}
