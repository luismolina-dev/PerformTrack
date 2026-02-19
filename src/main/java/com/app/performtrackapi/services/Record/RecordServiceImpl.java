package com.app.performtrackapi.services.Record;

import com.app.performtrackapi.dtos.Record.RecordDto;
import com.app.performtrackapi.dtos.Record.RecordResponseDto;
import com.app.performtrackapi.entities.Employee;
import com.app.performtrackapi.entities.Evaluation;
import com.app.performtrackapi.entities.Record;
import com.app.performtrackapi.mappers.RecordMapper;
import com.app.performtrackapi.repositories.EmployeeRepository;
import com.app.performtrackapi.repositories.EvaluationRepository;
import com.app.performtrackapi.repositories.RecordRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class RecordServiceImpl implements RecordService{

    private final RecordRepository recordRepository;
    private final RecordMapper recordMapper;
    private final EvaluationRepository evaluationRepository;
    private final EmployeeRepository employeeRepository;

    public RecordServiceImpl(RecordRepository recordRepository, RecordMapper recordMapper, EvaluationRepository evaluationRepository, EmployeeRepository employeeRepository) {
        this.recordRepository = recordRepository;
        this.recordMapper = recordMapper;
        this.evaluationRepository = evaluationRepository;
        this.employeeRepository = employeeRepository;
    }


    @Override
    public RecordResponseDto createRecord(RecordDto recordDto) {
        Evaluation evaluation = evaluationRepository.findById(recordDto.getEvaluationId())
                .orElseThrow(() -> new RuntimeException("Evaluation not found")
                );
        Employee employee = employeeRepository.findById(recordDto.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        Employee evaluator = employeeRepository.findById(recordDto.getEvaluatorId())
                .orElseThrow(() -> new RuntimeException("Evaluator not found"));

        Record record = recordMapper.toEntity(recordDto);

        record.setEvaluation(evaluation);
        record.setEmployee(employee);
        record.setEvaluator(evaluator);

        Record savedRecord = recordRepository.save(record);

        return recordMapper.toResponseDto(savedRecord);
    }

    @Override
    public RecordResponseDto updateRecord(UUID id, RecordDto recordDto) {

        Record record = recordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found"));

        if (recordDto.getEvaluatorId() != null) {
            Employee evaluator = employeeRepository.findById(recordDto.getEvaluatorId())
                    .orElseThrow(() -> new RuntimeException("Evaluator not found"));
            record.setEvaluator(evaluator);
        }

        if (recordDto.getEmployeeId() != null) {
            Employee employee = employeeRepository.findById(recordDto.getEmployeeId())
                    .orElseThrow(() -> new RuntimeException("Employee not found"));
            record.setEmployee(employee);
        }

        if (recordDto.getEvaluatorId() != null) {
            Employee evaluator = employeeRepository.findById(recordDto.getEvaluatorId())
                    .orElseThrow(() -> new RuntimeException("Evaluator not found"));
            record.setEvaluator(evaluator);
        }

        if (recordDto.getPeriod() != null) {
            record.setPeriod(recordDto.getPeriod());
        }

        if (recordDto.getComment() != null) {
            record.setComment(recordDto.getComment());
        }

        if (recordDto.getStrengths() != null) {
            record.setStrengths(recordDto.getStrengths());
        }

        if (recordDto.getWeaknesses() != null) {
            record.setWeakness(recordDto.getWeaknesses());
        }

        if (recordDto.getAdmin_training() != null) {
            record.setAdmin_training(recordDto.getAdmin_training());
        }

        if (recordDto.getTechnical_training() != null) {
            record.setTechnical_training(recordDto.getTechnical_training());
        }

        if (recordDto.getStatus() != null) {
            record.setStatus(recordDto.getStatus());
        }

        Record updatedRecord = recordRepository.save(record);

        return recordMapper.toResponseDto(updatedRecord);
    }

    @Override
    public RecordResponseDto getRecordById(UUID id) {
        Record record = recordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found")
                );

        return recordMapper.toResponseDto(record);
    }

    @Override
    public RecordResponseDto getRecordByEmployeeId(UUID employeeId) {
        Record record = recordRepository.findByEmployeeId(employeeId);

        return recordMapper.toResponseDto(record);
    }

    @Override
    public List<RecordResponseDto> getAllRecord(String period) {

        if(period != null) {
            return recordRepository.findByPeriod(period).stream().map(recordMapper::toResponseDto).toList();
        }

        return recordRepository.findAll().stream().map(recordMapper::toResponseDto).toList();
    }

    @Override
    public void deleteRecord(UUID id) {
        if (!recordRepository.existsById(id)) {
            throw new RuntimeException("Record not found");
        }

        recordRepository.deleteById(id);

    }
}
