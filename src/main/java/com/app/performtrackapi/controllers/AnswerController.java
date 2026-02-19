package com.app.performtrackapi.controllers;

import com.app.performtrackapi.dtos.Answer.AnswerDto;
import com.app.performtrackapi.dtos.Answer.AnswerResponseDto;
import com.app.performtrackapi.services.Answer.AnswerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/answer")
public class AnswerController {

    private final AnswerService answerService;
    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @GetMapping("/{answerId}")
    public ResponseEntity<AnswerResponseDto> getAnswerById(@PathVariable UUID answerId){
        return ResponseEntity.ok(answerService.getAnswerById(answerId));
    }

    @GetMapping("/record/{recordId}")
    public ResponseEntity<List<AnswerResponseDto>> getAllAnswerByRecordId(@PathVariable UUID recordId){
        return ResponseEntity.ok(answerService.getAnswerByRecordId(recordId));
    }

    @PostMapping("/")
    public ResponseEntity<AnswerResponseDto> createAnswer(@RequestBody AnswerDto answerDto){
        return new ResponseEntity<>(answerService.createAnswer(answerDto), HttpStatus.CREATED);
    }

    @PatchMapping("/{answerId}")
    public ResponseEntity<AnswerResponseDto> updateAnswer(@PathVariable UUID answerId, @RequestBody AnswerDto answerDto){
        return ResponseEntity.ok(answerService.updateAnswer(answerId, answerDto));
    }

    @DeleteMapping("/{answerId}")
    public ResponseEntity<Void> deleteAnswer(@PathVariable UUID answerId){
        answerService.deleteAnswer(answerId);
        return ResponseEntity.noContent().build();
    }
}
