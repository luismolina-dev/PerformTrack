package com.app.performtrackapi.mappers;

import com.app.performtrackapi.dtos.Answer.AnswerDto;
import com.app.performtrackapi.dtos.Answer.AnswerResponseDto;
import com.app.performtrackapi.entities.Answer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AnswerMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "questionId", ignore = true)
    @Mapping(target = "recordId", ignore = true)
    Answer toEntity(AnswerDto answer);

    @Mapping(source = "questionId.id", target = "questionId")
    @Mapping(source = "recordId.id", target = "recordId")
    AnswerResponseDto toRespondDto(Answer answer);
}
