package com.app.performtrackapi.services.Evaluation;

import com.app.performtrackapi.dtos.Evaluation.EvaluationDto;
import com.app.performtrackapi.dtos.Evaluation.EvaluationResponseDto;

import java.util.List;
import java.util.UUID;

public interface EvaluationService {
    EvaluationResponseDto createEvaluation(EvaluationDto evaluationDto);
    EvaluationResponseDto getEvaluationByPositionId(UUID positionId);
    EvaluationResponseDto getEvaluationByEmployeeId(UUID id);
    List<EvaluationResponseDto> getAllEvaluation();
    EvaluationResponseDto updateEvaluation(UUID id, EvaluationDto evaluationDto);
    void deleteEvaluation(UUID id);
}
