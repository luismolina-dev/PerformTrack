package com.app.performtrackapi.controllers;

import com.app.performtrackapi.dtos.Evaluation.EvaluationWithProgress;
import com.app.performtrackapi.dtos.Record.RecordDto;
import com.app.performtrackapi.dtos.Record.RecordResponseDto;
import com.app.performtrackapi.services.Record.RecordService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/records")
public class RecordController {

    private final RecordService recordService;

    public RecordController(RecordService recordService) {
        this.recordService = recordService;
    }

    @GetMapping("/{recordId}")
    public ResponseEntity<RecordResponseDto> getRecordById(@PathVariable UUID recordId){
        return ResponseEntity.ok(recordService.getRecordById(recordId));
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<RecordResponseDto> getRecordByEmployeeId(@PathVariable UUID employeeId){
        return ResponseEntity.ok(recordService.getRecordByEmployeeId(employeeId));
    }

    @GetMapping("/pending/{employeeId}")
    public ResponseEntity<EvaluationWithProgress> getAllPendingRecords(@PathVariable UUID employeeId){
        return ResponseEntity.ok(recordService.getPendingRecordByEmployeeId(employeeId));
    }

    @GetMapping("/")
    public ResponseEntity<Page<RecordResponseDto>> getAllCompleteRecords(
            @RequestParam(required = false) String period,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return ResponseEntity.ok(recordService.getAllCompleteRecords(period, pageable));
    }

    @PostMapping("/")
    public ResponseEntity<RecordResponseDto> createRecord(@RequestBody RecordDto recordDto){
        return new ResponseEntity<>(recordService.createRecord(recordDto), HttpStatus.CREATED);
    }

    @PatchMapping("/{recordId}")
    public ResponseEntity<RecordResponseDto> updateRecord(@PathVariable UUID recordId, @RequestBody RecordDto recordDto){
        return ResponseEntity.ok(recordService.updateRecord(recordId, recordDto));
    }

    @DeleteMapping("/{recordId}")
    public ResponseEntity<Void> deleteRecord(@PathVariable UUID recordId){
        recordService.deleteRecord(recordId);
        return ResponseEntity.noContent().build();
    }
}
