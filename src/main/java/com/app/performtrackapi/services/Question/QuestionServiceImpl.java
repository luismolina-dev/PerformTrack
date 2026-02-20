package com.app.performtrackapi.services.Question;

import com.app.performtrackapi.dtos.Question.QuestionDto;
import com.app.performtrackapi.dtos.Question.QuestionResponseDto;
import com.app.performtrackapi.entities.Category;
import com.app.performtrackapi.entities.Question;
import com.app.performtrackapi.mappers.QuestionMapper;
import com.app.performtrackapi.repositories.CategoryRepository;
import com.app.performtrackapi.repositories.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;
    private final CategoryRepository categoryRepository;

    public QuestionServiceImpl(QuestionRepository questionRepository, QuestionMapper questionMapper, CategoryRepository categoryRepository) {
        this.questionRepository = questionRepository;
        this.questionMapper = questionMapper;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public QuestionResponseDto getQuestionById(UUID id) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Question not found")
                );
        return questionMapper.toResponseDto(question);
    }

    @Override
    public QuestionResponseDto createQuestion(QuestionDto questionDto) {
        Category category = categoryRepository.findById(questionDto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found")
                );
        Question question = questionMapper.toEntity(questionDto);
        question.setCategory(category);

        Question savedQuestion = questionRepository.save(question);

        return questionMapper.toResponseDto(savedQuestion);
    }

    @Override
    public QuestionResponseDto updateQuestion(UUID id, QuestionDto questionDto) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Question not found")
                );

        if (questionDto.getQuestion() != null) {
            question.setQuestion(questionDto.getQuestion());
        }

        Question updatedQuestion = questionRepository.save(question);

        return questionMapper.toResponseDto(updatedQuestion);
    }

    @Override
    public void deleteQuestion(UUID id) {

        if (!questionRepository.existsById(id)) {
            throw new RuntimeException("Question not found");
        }

        questionRepository.deleteById(id);
    }
}
