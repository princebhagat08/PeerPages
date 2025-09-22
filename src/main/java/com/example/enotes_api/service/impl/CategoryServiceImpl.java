package com.example.enotes_api.service.impl;

import com.example.enotes_api.dto.CategoryDto;
import com.example.enotes_api.dto.CategoryResponse;
import com.example.enotes_api.entity.Category;

import com.example.enotes_api.exception.ExistDataException;
import com.example.enotes_api.exception.ResourceNotFoundException;
import com.example.enotes_api.repository.CategoryRepository;
import com.example.enotes_api.service.CacheManagerService;
import com.example.enotes_api.service.CategoryService;
import com.example.enotes_api.utils.Validation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.Date;
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

    @Autowired
    private CacheManagerService cacheService;

    @Override
    public Boolean saveCategory(CategoryDto categoryDto) {

        validation.categoryValidation(categoryDto);

        if(ObjectUtils.isEmpty(categoryDto.getId())){

            Boolean isAlreadyExist = categoryRepository.existsByName(categoryDto.getName().trim());

            if(isAlreadyExist){
                throw new ExistDataException("Category already exist");
            }

        }


        Category category = mapper.map(categoryDto, Category.class);

        category.setIsDeleted(false);

        Category savedCategory = categoryRepository.save(category);

        return !ObjectUtils.isEmpty(savedCategory);
    }


    @Override
    @Cacheable("allCategory")
    public List<CategoryResponse> getAllCategory() {
        List<Category> categories = categoryRepository.findByIsDeletedFalse();
        List<CategoryResponse> categoryResponses =  categories.stream().map(
                cat -> mapper.map(cat, CategoryResponse.class)).toList();
        return categoryResponses;
    }



    @Override
    @Cacheable("activeCategory")
    public List<CategoryResponse> getActiveCategory() {
        List<Category> categories = categoryRepository.findByIsActiveTrueAndIsDeletedFalse();
        List<CategoryResponse> categoryResponses =  categories.stream().map(cat -> mapper.map(cat, CategoryResponse.class)).toList();
        return categoryResponses;

    }

    @Override
    @Cacheable(value = "getCategoryById" , key = "#id")
    public CategoryDto getCategoryById(Integer id) throws Exception{
        Category category = categoryRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(()-> new ResourceNotFoundException("Category not found with id=" + id));

        if(!ObjectUtils.isEmpty(category)){
            return mapper.map(category,CategoryDto.class);
        }
        return null;
    }

    @Override
    @CacheEvict(value = "getCategoryById" , key = "#id")
    public Boolean deleteCategory(Integer id) {
        Optional<Category> findCategory = categoryRepository.findById(id);
        if(findCategory.isPresent()){
            Category category = findCategory.get();
            category.setIsDeleted(true);
            categoryRepository.save(category);

            // remove from cache
            cacheService.removeCacheByName(Arrays.asList("allCategory","activeCategory"));

            return true;

        }
        return  false;
    }




}
