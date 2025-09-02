package com.example.enotes_api.service.impl;

import com.example.enotes_api.dto.CategoryDto;
import com.example.enotes_api.dto.CategoryResponse;
import com.example.enotes_api.entity.Category;

import com.example.enotes_api.repository.CategoryRepository;
import com.example.enotes_api.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public Boolean saveCategory(CategoryDto categoryDto) {

        Category category = mapper.map(categoryDto, Category.class);

        category.setIsDeleted(true);
        category.setCreatedBy(11);

        Category savedCategory = categoryRepository.save(category);

        return !ObjectUtils.isEmpty(savedCategory);

    }

    @Override
    public List<Category> getAllCategory() {
        List<Category> categories = categoryRepository.findAll();
        return categories;
    }

    @Override
    public List<CategoryResponse> getActiveCategory() {
        List<Category> categories = categoryRepository.findByIsActiveTrue();
        List<CategoryResponse> categoryResponses = categories.stream().map(cat -> mapper.map(cat, CategoryResponse.class)).toList();
        return categoryResponses;
    }




}
