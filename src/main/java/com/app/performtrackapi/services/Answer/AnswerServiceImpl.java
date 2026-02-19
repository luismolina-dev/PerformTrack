package com.app.performtrackapi.services.Answer;

import com.app.performtrackapi.dtos.Answer.AnswerDto;
import com.app.performtrackapi.dtos.Answer.AnswerResponseDto;
import com.app.performtrackapi.entities.Answer;
import com.app.performtrackapi.entities.Question;
import com.app.performtrackapi.entities.Record;
import com.app.performtrackapi.mappers.AnswerMapper;
import com.app.performtrackapi.repositories.AnswerRepository;
import com.app.performtrackapi.repositories.QuestionRepository;
import com.app.performtrackapi.repositories.RecordRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AnswerServiceImpl implements AnswerService{

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final RecordRepository recordRepository;
    private final AnswerMapper answerMapper;

    public AnswerServiceImpl(AnswerRepository answerRepository, QuestionRepository questionRepository, RecordRepository recordRepository, AnswerMapper answerMapper) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
        this.recordRepository = recordRepository;
        this.answerMapper = answerMapper;
    }

    @Override
    public AnswerResponseDto createAnswer(AnswerDto answerDto) {

        Question question = questionRepository.findById(answerDto.getQuestionId())
                .orElseThrow(() -> new RuntimeException("Question not found")
                );

        Record record = recordRepository.findById(answerDto.getRecordId())
                .orElseThrow(() -> new RuntimeException("Record not found")
                );

        Answer answer = answerMapper.toEntity(answerDto);

        answer.setQuestionId(question);
        answer.setRecordId(record);

        Answer savedAnswer = answerRepository.save(answer);

        return answerMapper.toRespondDto(savedAnswer);
    }

    @Override
    public AnswerResponseDto updateAnswer(UUID id, AnswerDto answerDto) {
        Answer answer = answerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Answer not found")
                );

        if (answerDto.getQuestionId() != null) {
            Question question = questionRepository.findById(answerDto.getQuestionId())
                    .orElseThrow(() -> new RuntimeException("Question not found"));
            answer.setQuestionId(question);
        }

        if (answerDto.getRecordId() != null) {
            Record record = recordRepository.findById(answerDto.getRecordId())
                    .orElseThrow(() -> new RuntimeException("Record not found"));
            answer.setRecordId(record);
        }

        if (answerDto.getValue() != null) {
            answer.setValue(answerDto.getValue());
        }

        Answer updatedAnswer = answerRepository.save(answer);

        return answerMapper.toRespondDto(updatedAnswer);
    }

    @Override
    public AnswerResponseDto getAnswerById(UUID id) {
        Answer answer = answerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Answer not found")
                );

        return answerMapper.toRespondDto(answer);
    }

    @Override
    public List<AnswerResponseDto> getAnswerByRecordId(UUID recordId) {
        List<Answer> answers = answerRepository.findByRecordId_Id(recordId);

        return answers.stream().map(answerMapper::toRespondDto).toList();
    }

    @Override
    public void deleteAnswer(UUID id) {
        if (!answerRepository.existsById(id)){
            throw new RuntimeException("Answer not found");
        }

        answerRepository.deleteById(id);
    }
}
