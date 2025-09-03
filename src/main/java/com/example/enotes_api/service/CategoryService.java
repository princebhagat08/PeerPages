package com.example.enotes_api.service;
import java.util.List;

import com.example.enotes_api.dto.CategoryDto;
import com.example.enotes_api.dto.CategoryResponse;
import com.example.enotes_api.entity.Category;

public interface CategoryService {

    public Boolean saveCategory(CategoryDto categoryDto);

    public List<CategoryResponse> getActiveCategory();

    public CategoryDto getCategoryById(Integer id);

    public Boolean deleteCategory(Integer id);
}
