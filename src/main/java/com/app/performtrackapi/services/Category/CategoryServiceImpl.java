package com.app.performtrackapi.services.Category;

import com.app.performtrackapi.dtos.Category.CategoryDto;
import com.app.performtrackapi.dtos.Category.CategoryResponseDto;
import com.app.performtrackapi.entities.Category;
import com.app.performtrackapi.entities.Evaluation;
import com.app.performtrackapi.mappers.CategoryMapper;
import com.app.performtrackapi.repositories.CategoryRepository;
import com.app.performtrackapi.repositories.EvaluationRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final EvaluationRepository evaluationRepository;
    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryMapper categoryMapper, CategoryRepository categoryRepository, EvaluationRepository evaluationRepository) {
        this.categoryRepository = categoryRepository;
        this.evaluationRepository = evaluationRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public CategoryResponseDto getCategoryById(UUID id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found")
                );

        return categoryMapper.toResponseDto(category);
    }

    @Override
    public CategoryResponseDto createCategory(CategoryDto categoryDto) {
        Evaluation evaluation = evaluationRepository.findById(categoryDto.getEvaluationId())
                .orElseThrow(() -> new RuntimeException("Evaluation not found")
                );

        Category category = categoryMapper.toEntity(categoryDto);
        category.setEvaluation(evaluation);

        Category savedCategory = categoryRepository.save(category);

        return categoryMapper.toResponseDto(savedCategory);
    }

    @Override
    public CategoryResponseDto updateCategory(UUID id, CategoryDto categoryDto) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        if (categoryDto.getEvaluationId() != null) {
            Evaluation evaluation = evaluationRepository.findById(categoryDto.getEvaluationId())
                    .orElseThrow(() -> new RuntimeException("Evaluation not found"));
            category.setEvaluation(evaluation);
        }

        if (categoryDto.getName() != null) {
            category.setName(categoryDto.getName());
        }

        if (categoryDto.getWeight() != null) {
            category.setWeight(categoryDto.getWeight());
        }

        if (categoryDto.getOrder_index() != null) {
            category.setOrder_index(categoryDto.getOrder_index());
        }

        Category updatedCategory = categoryRepository.save(category);

        return categoryMapper.toResponseDto(updatedCategory);
    }

    @Override
    public void deleteCategory(UUID id) {
        if (!categoryRepository.existsById(id)) {
            throw new RuntimeException("Category not found");
        }

        categoryRepository.deleteById(id);
    }
}
