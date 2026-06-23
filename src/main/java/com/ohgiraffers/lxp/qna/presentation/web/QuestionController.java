package com.ohgiraffers.lxp.qna.presentation.web;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ohgiraffers.lxp.qna.application.port.in.QuestionUseCase;
import com.ohgiraffers.lxp.qna.presentation.dto.CreateQuestionRequest;
import com.ohgiraffers.lxp.qna.presentation.dto.QuestionResponse;
import com.ohgiraffers.lxp.qna.presentation.dto.UpdateQuestionRequest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/questions")
public class QuestionController {

    private final QuestionUseCase questionUseCase;

    @PostMapping
    public ResponseEntity<QuestionResponse> createQuestion(@Valid @RequestBody CreateQuestionRequest request) {
        QuestionResponse response = QuestionResponse.from(questionUseCase.createQuestion(request.toCommand()));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<QuestionResponse>> getQuestions(@RequestParam Long courseId) {
        List<QuestionResponse> responses = questionUseCase.getQuestions(courseId)
                .stream()
                .map(QuestionResponse::from)
                .toList();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{questionId}")
    public ResponseEntity<QuestionResponse> getQuestion(@PathVariable("questionId") Long questionId) {
        QuestionResponse response = QuestionResponse.from(questionUseCase.getQuestion(questionId));
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{questionId}")
    public ResponseEntity<QuestionResponse> updateQuestion(
            @PathVariable("questionId") Long questionId,
            @Valid @RequestBody UpdateQuestionRequest request
    ) {
        QuestionResponse response = QuestionResponse.from(questionUseCase.updateQuestion(request.toCommand(questionId)));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{questionId}")
    public ResponseEntity<Void> deleteQuestion(
            @PathVariable("questionId") Long questionId,
            @RequestParam @NotNull Long memberId
    ) {
        questionUseCase.deleteQuestion(questionId, memberId);
        return ResponseEntity.noContent().build();
    }
}
