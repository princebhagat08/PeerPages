package com.example.enotes_api.service.impl;

import com.example.enotes_api.dto.CategoryDto;
import com.example.enotes_api.dto.CategoryResponse;
import com.example.enotes_api.entity.Category;

import com.example.enotes_api.repository.CategoryRepository;
import com.example.enotes_api.service.CategoryService;
import com.example.enotes_api.utils.Validation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private Validation validation;

    @Override
    public Boolean saveCategory(CategoryDto categoryDto) {

        validation.categoryValidation(categoryDto);

        Category category = mapper.map(categoryDto, Category.class);

        category.setIsDeleted(true);
        category.setCreatedBy(11);


        Category savedCategory = categoryRepository.save(category);

        return !ObjectUtils.isEmpty(savedCategory);

    }



    @Override
    public List<CategoryResponse> getActiveCategory() {
        List<Category> categories = categoryRepository.findByIsActiveTrueAndIsDeletedFalse();
        List<CategoryResponse> categoryResponses =  categories.stream().map(cat -> mapper.map(cat, CategoryResponse.class)).toList();
        return categoryResponses;

    }

    @Override
    public CategoryDto getCategoryById(Integer id) {
        Optional<Category> category = categoryRepository.findByIdAndIsDeletedFalse(id);

        if(category.isPresent()){
            Category finalCategory = category.get();
            return mapper.map(finalCategory,CategoryDto.class);
        }
        return null;
    }

    @Override
    public Boolean deleteCategory(Integer id) {
        Optional<Category> findCategory = categoryRepository.findById(id);
        if(findCategory.isPresent()){
            Category category = findCategory.get();
            category.setIsDeleted(true);
            categoryRepository.save(category);
            return true;
        }
        return  false;
    }


}
