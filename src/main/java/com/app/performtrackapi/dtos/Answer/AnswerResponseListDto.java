package com.app.performtrackapi.dtos.Answer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerResponseListDto {
    private UUID questionId;
    private Integer value;
}
