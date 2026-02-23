package com.app.performtrackapi.services.Metrics;

import com.app.performtrackapi.dtos.Metrics.ScoreResultDto;
import com.app.performtrackapi.repositories.DepartmentRepository;
import com.app.performtrackapi.repositories.RecordRepository;
import com.app.performtrackapi.services.Metrics.formulas.MetricStrategy;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MetricServiceImpl implements MetricService {

    private final RecordRepository recordRepository;
    private final DepartmentRepository departmentRepository;
    private final MetricStrategy defaultStrategy;

    public MetricServiceImpl(RecordRepository recordRepository,
            DepartmentRepository departmentRepository,
            MetricStrategy defaultStrategy) {
        this.recordRepository = recordRepository;
        this.departmentRepository = departmentRepository;
        this.defaultStrategy = defaultStrategy;
    }

    @Override
    public ScoreResultDto getEmployeeMetrics(UUID employeeId, String period) {
        return null;
    }

    @Override
    public ScoreResultDto getDepartmentMetrics(UUID departmentId, String period) {
        return null;
    }

    // 5: General Metrics
    @Override
    public ScoreResultDto getGeneralMetrics(String period) {
        return null;
    }
}
