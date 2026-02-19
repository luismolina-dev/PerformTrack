package com.app.performtrackapi.services.Record;

import com.app.performtrackapi.dtos.Record.RecordDto;
import com.app.performtrackapi.dtos.Record.RecordResponseDto;

import java.util.List;
import java.util.UUID;

public interface RecordService {
    RecordResponseDto createRecord(RecordDto recordDto);
    RecordResponseDto updateRecord(UUID id, RecordDto recordDto);
    RecordResponseDto getRecordById(UUID id);
    RecordResponseDto getRecordByEmployeeId(UUID employeeId);
    List<RecordResponseDto> getAllCompleteRecords(String period);

    void deleteRecord(UUID id);
}
