package com.app.performtrackapi.services.Record;

import com.app.performtrackapi.dtos.Evaluation.EvaluationResponseDto;
import com.app.performtrackapi.dtos.Evaluation.EvaluationWithProgress;
import com.app.performtrackapi.dtos.Category.CategoryWithProgressDto;
import com.app.performtrackapi.dtos.Question.QuestionWithAnswerDto;
import com.app.performtrackapi.dtos.Record.RecordDto;
import com.app.performtrackapi.dtos.Record.RecordResponseDto;
import com.app.performtrackapi.entities.Employee;
import com.app.performtrackapi.entities.Evaluation;
import com.app.performtrackapi.entities.Record;
import com.app.performtrackapi.entities.Status;
import com.app.performtrackapi.mappers.RecordMapper;
import com.app.performtrackapi.repositories.EmployeeRepository;
import com.app.performtrackapi.repositories.EvaluationRepository;
import com.app.performtrackapi.repositories.QuestionRepository;
import com.app.performtrackapi.repositories.RecordRepository;
import com.app.performtrackapi.entities.Answer;
import com.app.performtrackapi.entities.Question;
import java.util.ArrayList;

import com.app.performtrackapi.services.Evaluation.EvaluationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RecordServiceImpl implements RecordService {

    private final RecordRepository recordRepository;
    private final RecordMapper recordMapper;
    private final EvaluationRepository evaluationRepository;
    private final EmployeeRepository employeeRepository;
    private final QuestionRepository questionRepository;
    private final EvaluationService evaluationService;

    public RecordServiceImpl(RecordRepository recordRepository, RecordMapper recordMapper,
            EvaluationRepository evaluationRepository, EmployeeRepository employeeRepository,
            QuestionRepository questionRepository, EvaluationService evaluationService) {
        this.recordRepository = recordRepository;
        this.recordMapper = recordMapper;
        this.evaluationRepository = evaluationRepository;
        this.employeeRepository = employeeRepository;
        this.questionRepository = questionRepository;
        this.evaluationService = evaluationService;
    }

    @Override
    public RecordResponseDto createRecord(RecordDto recordDto) {
        Evaluation evaluation = evaluationRepository.findById(recordDto.getEvaluationId())
                .orElseThrow(() -> new RuntimeException("Evaluation not found"));
        Employee employee = employeeRepository.findById(recordDto.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        Employee evaluator = employeeRepository.findById(recordDto.getEvaluatorId())
                .orElseThrow(() -> new RuntimeException("Evaluator not found"));

        Record record = recordMapper.toEntity(recordDto);

        record.setEvaluation(evaluation);
        record.setEmployee(employee);
        record.setEvaluator(evaluator);

        // Map and link answers
        if (recordDto.getAnswers() != null) {
            List<Answer> answers = new ArrayList<>();
            for (var answerDto : recordDto.getAnswers()) {
                Question question = questionRepository.findById(answerDto.getQuestionId())
                        .orElseThrow(
                                () -> new RuntimeException("Question not found with ID: " + answerDto.getQuestionId()));

                Answer answer = new Answer();
                answer.setQuestionId(question);
                answer.setRecordId(record);
                answer.setValue(answerDto.getValue());
                answers.add(answer);
            }
            record.setAnswers(answers);
        }

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
                .orElseThrow(() -> new RuntimeException("Record not found"));

        return recordMapper.toResponseDto(record);
    }

    @Override
    public RecordResponseDto getRecordByEmployeeId(UUID employeeId) {
        Record record = recordRepository.findByEmployeeId(employeeId);

        return recordMapper.toResponseDto(record);
    }

    @Override
    public EvaluationWithProgress getPendingRecordByEmployeeId(UUID employeeId) {

        Record record = recordRepository.findByEmployeeId(employeeId);
        EvaluationResponseDto template = evaluationService.getEvaluationByEmployeeId(employeeId);

        if (template == null) {
            return null;
        }

        // Create the progress container
        EvaluationWithProgress progress = new EvaluationWithProgress();
        progress.setId(template.getId());
        progress.setPositionId(template.getPositionId());

        // Create the map for fast lookup of answers
        Map<UUID, Integer> answeredMap = (record != null && record.getStatus() == Status.pending)
                ? record.getAnswers().stream()
                        .collect(Collectors.toMap(
                                a -> a.getQuestionId().getId(),
                                Answer::getValue,
                                (existing, replacement) -> existing // Handle duplicates if necessary
                        ))
                : Map.of();

        // Build the categories list with the progress/answers
        List<CategoryWithProgressDto> categories = template.getCategories().stream().map(cat -> {
            CategoryWithProgressDto catProgress = new CategoryWithProgressDto();
            catProgress.setId(cat.getId());
            catProgress.setName(cat.getName());
            catProgress.setWeight(cat.getWeight());
            catProgress.setOrder_index(cat.getOrder_index());

            // Build the questions list for this category
            List<QuestionWithAnswerDto> questions = cat.getQuestions().stream().map(q -> {
                QuestionWithAnswerDto qProgress = new QuestionWithAnswerDto();
                qProgress.setQuestionId(q.getId());
                qProgress.setQuestion(q.getQuestion());
                qProgress.setAnswer(answeredMap.get(q.getId()));
                return qProgress;
            }).collect(Collectors.toList());

            catProgress.setQuestions(questions);
            return catProgress;
        }).collect(Collectors.toList());

        progress.setCategories(categories);
        return progress;
    }

    @Override
    public Page<RecordResponseDto> getAllCompleteRecords(String period, Pageable pageable) {

        if (period != null) {
            return recordRepository.findByPeriod(period, Status.completed, pageable)
                    .map(recordMapper::toResponseDto);
        }

        return recordRepository.findByStatus(Status.completed, pageable)
                .map(recordMapper::toResponseDto);
    }

    @Override
    public void deleteRecord(UUID id) {
        if (!recordRepository.existsById(id)) {
            throw new RuntimeException("Record not found");
        }

        recordRepository.deleteById(id);

    }
}
