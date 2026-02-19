package com.app.performtrackapi.dtos.Record;

import com.app.performtrackapi.entities.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecordDto {
    private UUID evaluationId;
    private UUID employeeId;
    private UUID evaluatorId;
    private String period;
    private String comment;
    private String strengths;
    private String weaknesses;
    private String admin_training;
    private String technical_training;
    private Status status;
}
