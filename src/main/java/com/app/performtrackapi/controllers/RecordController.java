package com.app.performtrackapi.controllers;

import com.app.performtrackapi.dtos.Record.RecordDto;
import com.app.performtrackapi.dtos.Record.RecordResponseDto;
import com.app.performtrackapi.services.Record.RecordService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @GetMapping("/")
    public ResponseEntity<List<RecordResponseDto>> getAllRecord(@RequestParam(required = false) String period){
        return ResponseEntity.ok(recordService.getAllRecord(period));
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
