package com.app.performtrackapi.mappers;

import com.app.performtrackapi.dtos.Record.RecordDto;
import com.app.performtrackapi.dtos.Record.RecordResponseDto;
import com.app.performtrackapi.entities.Record;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses =  {AnswerMapper.class})
public interface RecordMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "evaluation", ignore = true)
    @Mapping(target = "employee", ignore = true)
    @Mapping(target = "evaluator", ignore = true)
    Record toEntity(RecordDto record);

    @Mapping(source = "evaluation.id", target = "evaluationId")
    @Mapping(source = "employee.id", target = "employeeId")
    @Mapping(source = "evaluator.id", target = "evaluatorId")
    RecordResponseDto toResponseDto(Record record);
}
