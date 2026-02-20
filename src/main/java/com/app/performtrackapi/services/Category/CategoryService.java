package com.app.performtrackapi.services.Category;

import com.app.performtrackapi.dtos.Category.CategoryDto;
import com.app.performtrackapi.dtos.Category.CategoryResponseDto;

import java.util.UUID;

public interface CategoryService {
    CategoryResponseDto getCategoryById(UUID id);
    CategoryResponseDto createCategory(CategoryDto categoryDto);
    CategoryResponseDto updateCategory(UUID id, CategoryDto categoryDto);
    void deleteCategory(UUID id);
}
