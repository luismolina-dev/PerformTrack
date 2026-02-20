package com.app.performtrackapi.controllers;

import com.app.performtrackapi.dtos.Question.QuestionDto;
import com.app.performtrackapi.dtos.Question.QuestionResponseDto;
import com.app.performtrackapi.services.Question.QuestionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    private final QuestionService questionService;
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/{questionId}")
    public ResponseEntity<QuestionResponseDto> getQuestionById(@PathVariable UUID questionId){
        return ResponseEntity.ok(questionService.getQuestionById(questionId));
    }

    @PostMapping("/")
    public ResponseEntity<QuestionResponseDto> createQuestion(@RequestBody QuestionDto questionDto){
        return new ResponseEntity<>(questionService.createQuestion(questionDto), HttpStatus.CREATED);
    }

    @PatchMapping("/{questionId}")
    public ResponseEntity<QuestionResponseDto> updateQuestion(@PathVariable UUID questionId, @RequestBody QuestionDto questionDto){
        return ResponseEntity.ok(questionService.updateQuestion(questionId, questionDto));
    }

    @DeleteMapping("/{questionId}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable UUID questionId){
        questionService.deleteQuestion(questionId);
        return ResponseEntity.noContent().build();
    }
}
