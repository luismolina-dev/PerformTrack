package com.app.performtrackapi.services.Record;

import com.app.performtrackapi.dtos.Evaluation.EvaluationWithProgress;
import com.app.performtrackapi.dtos.Record.RecordDto;
import com.app.performtrackapi.dtos.Record.RecordResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface RecordService {
    RecordResponseDto createRecord(RecordDto recordDto);

    RecordResponseDto updateRecord(UUID id, RecordDto recordDto);

    RecordResponseDto getRecordById(UUID id);

    RecordResponseDto getRecordByEmployeeId(UUID employeeId);

    EvaluationWithProgress getPendingRecordByEmployeeId(UUID employeeId);

    Page<RecordResponseDto> getAllCompleteRecords(String period, Pageable pageable);

    void deleteRecord(UUID id);
}
