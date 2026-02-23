package com.app.performtrackapi.dtos.Metrics;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScoreResultDto {
    private String label;
    private Double score;
    private Map<String, Double> breakdown;
}
