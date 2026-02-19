package com.app.performtrackapi.services.Answer;

import com.app.performtrackapi.dtos.Answer.AnswerDto;
import com.app.performtrackapi.dtos.Answer.AnswerResponseDto;

import java.util.List;
import java.util.UUID;

public interface AnswerService {
    AnswerResponseDto createAnswer(AnswerDto answerDto);
    AnswerResponseDto updateAnswer(UUID id, AnswerDto answerDto);
    AnswerResponseDto getAnswerById(UUID id);
    List<AnswerResponseDto> getAnswerByRecordId(UUID recordId);
    void deleteAnswer(UUID id);
}
