package com.app.performtrackapi.services.Metrics;

import com.app.performtrackapi.dtos.Metrics.ScoreResultDto;
import java.util.UUID;

public interface MetricService {
    ScoreResultDto getEmployeeMetrics(UUID employeeId, String period);

    ScoreResultDto getDepartmentMetrics(UUID departmentId, String period);

    ScoreResultDto getGeneralMetrics(String period);
}
